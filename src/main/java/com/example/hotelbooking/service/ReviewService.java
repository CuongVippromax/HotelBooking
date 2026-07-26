package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.ReviewCreationRequest;
import com.example.hotelbooking.model.dto.request.ReviewUpdateRequest;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.dto.response.ReviewResponse;
import com.example.hotelbooking.model.entity.Booking;
import com.example.hotelbooking.model.entity.Hotel;
import com.example.hotelbooking.model.entity.Review;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.ReviewStatus;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.ReviewRepository;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "REVIEW_SERVICE")
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public ReviewResponse createReview(ReviewCreationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + request.getHotelId()));

        Booking booking = null;
        if (request.getBookingId() != null) {
            booking = bookingRepository.findById(request.getBookingId())
                    .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + request.getBookingId()));

            // Validate booking details
            if (!booking.getCustomer().getId().equals(user.getId())) {
                throw new AccessDeniedException("You cannot review a booking that does not belong to you");
            }
            if (!booking.getRoom().getHotel().getId().equals(hotel.getId())) {
                throw new IllegalArgumentException("Booking does not belong to the selected hotel");
            }
            if (reviewRepository.existsByBookingId(booking.getId())) {
                throw new DuplicateResourceException("A review already exists for this booking");
            }
        }

        Review review = Review.builder()
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .stayDate(request.getStayDate())
                .status(ReviewStatus.PENDING) // New reviews are pending approval by default
                .user(user)
                .hotel(hotel)
                .booking(booking)
                .build();

        Review saved = reviewRepository.save(review);
        log.info("Created review id={} for hotel id={} by user id={}", saved.getId(), hotel.getId(), user.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long id) {
        Review review = findReviewOrThrow(id);
        return toResponse(review);
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getApprovedReviewsByHotel(Long hotelId, Pageable pageable) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }
        return PageResponse.of(
                reviewRepository.findByHotelIdAndStatus(hotelId, ReviewStatus.APPROVED, pageable),
                this::toResponse);
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getAllReviewsByHotel(Long hotelId, String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        // Only ADMIN or HOTEL_MANAGER can view all reviews (including pending/rejected)
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can view all reviews");
        }

        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }

        return PageResponse.of(reviewRepository.findByHotelId(hotelId, pageable), this::toResponse);
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> getReviewsByUser(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        return PageResponse.of(reviewRepository.findByUserId(user.getId(), pageable), this::toResponse);
    }

    @Transactional
    public ReviewResponse updateReview(Long id, ReviewUpdateRequest request, String userEmail) {
        Review review = findReviewOrThrow(id);
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        // Only the author or an ADMIN can update a review
        if (!review.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("You are not authorized to update this review");
        }

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getTitle() != null) {
            review.setTitle(request.getTitle());
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }
        if (request.getStayDate() != null) {
            review.setStayDate(request.getStayDate());
        }

        // Reset status to pending when updated
        review.setStatus(ReviewStatus.PENDING);

        Review saved = reviewRepository.save(review);
        log.info("Updated review id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteReview(Long id, String userEmail) {
        Review review = findReviewOrThrow(id);
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        // Only the author or an ADMIN can delete a review
        if (!review.getUser().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("You are not authorized to delete this review");
        }

        reviewRepository.delete(review);
        log.info("Deleted review id={}", id);
    }

    @Transactional
    public ReviewResponse updateReviewStatus(Long id, ReviewStatus newStatus, String userEmail) {
        Review review = findReviewOrThrow(id);
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }

        // Only ADMIN or HOTEL_MANAGER can approve/reject/moderate reviews
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can moderate reviews");
        }

        review.setStatus(newStatus);
        Review saved = reviewRepository.save(review);
        log.info("Updated status of review id={} to {}", saved.getId(), newStatus);
        return toResponse(saved);
    }

    private Review findReviewOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .stayDate(review.getStayDate())
                .status(review.getStatus())
                .userId(review.getUser() != null ? review.getUser().getId() : null)
                .userName(review.getUser() != null ? (review.getUser().getFirstName() + " " + review.getUser().getLastName()) : null)
                .hotelId(review.getHotel() != null ? review.getHotel().getId() : null)
                .hotelName(review.getHotel() != null ? review.getHotel().getName() : null)
                .bookingId(review.getBooking() != null ? review.getBooking().getId() : null)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
