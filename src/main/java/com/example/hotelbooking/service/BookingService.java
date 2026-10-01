package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.BookingCreationRequest;
import com.example.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.example.hotelbooking.model.dto.response.BookingResponse;
import com.example.hotelbooking.model.entity.Booking;
import com.example.hotelbooking.model.entity.Room;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.BookingStatus;
import com.example.hotelbooking.model.enums.RoomStatus;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.model.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "BOOKING_SERVICE")
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse createBooking(BookingCreationRequest request, String userEmail) {
        User customer = findUserOrThrow(userEmail);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        if (room.getStatus() == RoomStatus.OUT_OF_SERVICE || room.getStatus() == RoomStatus.MAINTENANCE) {
            throw new IllegalArgumentException("Room is not available for booking");
        }

        if (bookingRepository.existsOverlappingBooking(
                room.getId(), request.getCheckInDate(), request.getCheckOutDate(), null)) {
            throw new IllegalArgumentException(
                    "Room is already booked for the selected dates");
        }

        int nights = (int) ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = Booking.builder()
                .bookingReference(generateBookingReference())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(request.getNumberOfGuests())
                .totalAmount(totalAmount)
                .specialRequests(request.getSpecialRequests())
                .status(BookingStatus.PENDING)
                .customer(customer)
                .room(room)
                .build();

        Booking saved = bookingRepository.save(booking);
        log.info("Created booking id={} ref={} for room id={} by user id={}",
                saved.getId(), saved.getBookingReference(), room.getId(), customer.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long id, String userEmail) {
        Booking booking = findBookingOrThrow(id);
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(booking, user);
        return toResponse(booking);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "myBookings", key = "#userEmail + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public PageResponse<BookingResponse> getMyBookings(String userEmail, Pageable pageable) {
        User user = findUserOrThrow(userEmail);
        return PageResponse.of(
                bookingRepository.findByCustomerId(user.getId(), pageable),
                this::toResponse);
    }

    @Transactional(readOnly = true)
    public PageResponse<BookingResponse> getAllBookings(String userEmail, Pageable pageable) {
        User user = findUserOrThrow(userEmail);
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can view all bookings");
        }
        return PageResponse.of(bookingRepository.findAll(pageable), this::toResponse);
    }

    @Transactional
    public BookingResponse updateBooking(Long id, BookingUpdateRequest request, String userEmail) {
        Booking booking = findBookingOrThrow(id);
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(booking, user);

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalArgumentException("Only pending bookings can be updated");
        }

        LocalDate newCheckIn = request.getCheckInDate() != null ? request.getCheckInDate() : booking.getCheckInDate();
        LocalDate newCheckOut = request.getCheckOutDate() != null ? request.getCheckOutDate() : booking.getCheckOutDate();

        if (!newCheckOut.isAfter(newCheckIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        boolean datesChanged = request.getCheckInDate() != null || request.getCheckOutDate() != null;
        if (datesChanged) {
            if (bookingRepository.existsOverlappingBooking(
                    booking.getRoom().getId(), newCheckIn, newCheckOut, booking.getId())) {
                throw new IllegalArgumentException("Room is already booked for the selected dates");
            }
            booking.setCheckInDate(newCheckIn);
            booking.setCheckOutDate(newCheckOut);
            int nights = (int) ChronoUnit.DAYS.between(newCheckIn, newCheckOut);
            booking.setTotalAmount(booking.getRoom().getPricePerNight().multiply(BigDecimal.valueOf(nights)));
        }

        if (request.getNumberOfGuests() != null) {
            booking.setNumberOfGuests(request.getNumberOfGuests());
        }
        if (request.getSpecialRequests() != null) {
            booking.setSpecialRequests(request.getSpecialRequests());
        }

        Booking saved = bookingRepository.save(booking);
        log.info("Updated booking id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public BookingResponse updateBookingStatus(Long id, BookingStatus newStatus, String userEmail) {
        Booking booking = findBookingOrThrow(id);
        User user = findUserOrThrow(userEmail);
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can change booking status");
        }
        booking.setStatus(newStatus);
        Booking saved = bookingRepository.save(booking);
        log.info("Updated status of booking id={} to {}", saved.getId(), newStatus);
        return toResponse(saved);
    }

    @Transactional
    public BookingResponse cancelBooking(Long id, String userEmail) {
        Booking booking = findBookingOrThrow(id);
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(booking, user);

        if (booking.getStatus() == BookingStatus.CHECKED_IN
                || booking.getStatus() == BookingStatus.CHECKED_OUT) {
            throw new IllegalArgumentException("Cannot cancel a booking that is already checked in or checked out");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(booking);
        log.info("Cancelled booking id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteBooking(Long id, String userEmail) {
        Booking booking = findBookingOrThrow(id);
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(booking, user);
        // Prevent deletion of bookings that are checked in or checked out
        if (booking.getStatus() == BookingStatus.CHECKED_IN
                || booking.getStatus() == BookingStatus.CHECKED_OUT) {
            throw new IllegalArgumentException("Cannot delete a booking that is already checked in or checked out");
        }
        bookingRepository.delete(booking);
        log.info("Deleted booking id={}", id);
    }

    private void ensureCanAccess(Booking booking, User user) {
        boolean isOwner = booking.getCustomer().getId().equals(user.getId());
        boolean isPrivileged = user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.HOTEL_MANAGER;
        if (!isOwner && !isPrivileged) {
            throw new AccessDeniedException("You are not authorized to access this booking");
        }
    }

    private User findUserOrThrow(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }
        return user;
    }

    private Booking findBookingOrThrow(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    private String generateBookingReference() {
        String reference;
        do {
            reference = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingReference(reference));
        return reference;
    }

    private BookingResponse toResponse(Booking booking) {
        Room room = booking.getRoom();
        User customer = booking.getCustomer();
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numberOfGuests(booking.getNumberOfGuests())
                .numberOfNights(booking.getNumberOfNights())
                .totalAmount(booking.getTotalAmount())
                .specialRequests(booking.getSpecialRequests())
                .status(booking.getStatus())
                .customerId(customer != null ? customer.getId() : null)
                .customerName(customer != null ? (customer.getFirstName() + " " + customer.getLastName()) : null)
                .roomId(room != null ? room.getId() : null)
                .roomNumber(room != null ? room.getRoomNumber() : null)
                .hotelId(room != null && room.getHotel() != null ? room.getHotel().getId() : null)
                .hotelName(room != null && room.getHotel() != null ? room.getHotel().getName() : null)
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
