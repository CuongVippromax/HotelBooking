package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.ReviewCreationRequest;
import com.example.hotelbooking.model.dto.request.ReviewUpdateRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ReviewService reviewService;

    private User customer;
    private Hotel hotel;
    private Review review;

    @BeforeEach
    void setUp() {
        customer = User.builder()
                .email("customer@example.com")
                .firstName("John")
                .lastName("Doe")
                .role(UserRole.CUSTOMER)
                .enabled(true)
                .build();
        customer.setId(1L);

        hotel = Hotel.builder()
                .name("Grand Palace")
                .email("grand@palace.com")
                .starRating(5)
                .build();
        hotel.setId(10L);

        review = Review.builder()
                .rating(5)
                .title("Excellent stay!")
                .comment("We had a wonderful time here. Highly recommended.")
                .stayDate(LocalDate.now().minusDays(2))
                .status(ReviewStatus.APPROVED)
                .user(customer)
                .hotel(hotel)
                .build();
        review.setId(100L);
    }

    @Test
    void createReview_Success() {
        ReviewCreationRequest request = ReviewCreationRequest.builder()
                .hotelId(10L)
                .rating(5)
                .title("Excellent stay!")
                .comment("We had a wonderful time here. Highly recommended.")
                .stayDate(LocalDate.now().minusDays(2))
                .build();

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(hotelRepository.findById(10L)).thenReturn(Optional.of(hotel));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse response = reviewService.createReview(request, customer.getEmail());

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(5, response.getRating());
        assertEquals("Excellent stay!", response.getTitle());
        assertEquals(ReviewStatus.APPROVED, response.getStatus());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void createReview_UserNotFound() {
        ReviewCreationRequest request = ReviewCreationRequest.builder()
                .hotelId(10L)
                .build();

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> 
                reviewService.createReview(request, "unknown@example.com"));
    }

    @Test
    void getApprovedReviewsByHotel_Success() {
        when(hotelRepository.existsById(10L)).thenReturn(true);
        when(reviewRepository.findByHotelIdAndStatus(10L, ReviewStatus.APPROVED)).thenReturn(List.of(review));

        List<ReviewResponse> reviews = reviewService.getApprovedReviewsByHotel(10L);

        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
        assertEquals(100L, reviews.get(0).getId());
    }

    @Test
    void updateReviewStatus_Success_Admin() {
        User admin = User.builder()
                .email("admin@example.com")
                .role(UserRole.ADMIN)
                .build();
        admin.setId(2L);

        when(reviewRepository.findById(100L)).thenReturn(Optional.of(review));
        when(userRepository.findByEmail(admin.getEmail())).thenReturn(admin);
        
        Review updatedReview = Review.builder()
                .rating(5)
                .title("Excellent stay!")
                .comment("We had a wonderful time here. Highly recommended.")
                .stayDate(LocalDate.now().minusDays(2))
                .status(ReviewStatus.APPROVED)
                .user(customer)
                .hotel(hotel)
                .build();
        updatedReview.setId(100L);
        updatedReview.setStatus(ReviewStatus.FLAGGED);
        
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

        ReviewResponse response = reviewService.updateReviewStatus(100L, ReviewStatus.FLAGGED, admin.getEmail());

        assertNotNull(response);
        assertEquals(ReviewStatus.FLAGGED, response.getStatus());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void updateReviewStatus_AccessDenied_Customer() {
        when(reviewRepository.findById(100L)).thenReturn(Optional.of(review));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        assertThrows(AccessDeniedException.class, () ->
                reviewService.updateReviewStatus(100L, ReviewStatus.APPROVED, customer.getEmail()));
    }
}
