package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.BookingCreationRequest;
import com.example.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.example.hotelbooking.model.dto.response.BookingResponse;
import com.example.hotelbooking.model.entity.Booking;
import com.example.hotelbooking.model.entity.Hotel;
import com.example.hotelbooking.model.entity.Room;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.BookingStatus;
import com.example.hotelbooking.model.enums.RoomStatus;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class
BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    private User customer;
    private Hotel hotel;
    private Room room;
    private Booking booking;

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

        room = Room.builder()
                .roomNumber("101")
                .status(RoomStatus.AVAILABLE)
                .pricePerNight(new BigDecimal("100.00"))
                .hotel(hotel)
                .build();
        room.setId(20L);

        booking = Booking.builder()
                .bookingReference("BK-ABCD1234")
                .checkInDate(LocalDate.now().plusDays(5))
                .checkOutDate(LocalDate.now().plusDays(8))
                .numberOfGuests(2)
                .numberOfNights(3)
                .totalAmount(new BigDecimal("300.00"))
                .status(BookingStatus.PENDING)
                .customer(customer)
                .room(room)
                .build();
        booking.setId(100L);
    }

    @Test
    void createBooking_Success() {
        BookingCreationRequest request = BookingCreationRequest.builder()
                .roomId(20L)
                .checkInDate(LocalDate.now().plusDays(5))
                .checkOutDate(LocalDate.now().plusDays(8))
                .numberOfGuests(2)
                .build();

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(roomRepository.findById(20L)).thenReturn(Optional.of(room));
        when(bookingRepository.existsOverlappingBooking(eq(20L), any(), any(), isNull())).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse response = bookingService.createBooking(request, customer.getEmail());

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals(new BigDecimal("300.00"), response.getTotalAmount());
        assertEquals(BookingStatus.PENDING, response.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBooking_RoomNotAvailable() {
        room.setStatus(RoomStatus.MAINTENANCE);
        BookingCreationRequest request = BookingCreationRequest.builder()
                .roomId(20L)
                .checkInDate(LocalDate.now().plusDays(5))
                .checkOutDate(LocalDate.now().plusDays(8))
                .numberOfGuests(2)
                .build();

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(roomRepository.findById(20L)).thenReturn(Optional.of(room));

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.createBooking(request, customer.getEmail()));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_OverlappingDates() {
        BookingCreationRequest request = BookingCreationRequest.builder()
                .roomId(20L)
                .checkInDate(LocalDate.now().plusDays(5))
                .checkOutDate(LocalDate.now().plusDays(8))
                .numberOfGuests(2)
                .build();

        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(roomRepository.findById(20L)).thenReturn(Optional.of(room));
        when(bookingRepository.existsOverlappingBooking(eq(20L), any(), any(), isNull())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.createBooking(request, customer.getEmail()));
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void createBooking_UserNotFound() {
        BookingCreationRequest request = BookingCreationRequest.builder()
                .roomId(20L)
                .build();

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () ->
                bookingService.createBooking(request, "unknown@example.com"));
    }

    @Test
    void getBooking_AccessDenied_OtherCustomer() {
        User other = User.builder()
                .email("other@example.com")
                .role(UserRole.CUSTOMER)
                .build();
        other.setId(2L);

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(other.getEmail())).thenReturn(other);

        assertThrows(AccessDeniedException.class, () ->
                bookingService.getBooking(100L, other.getEmail()));
    }

    @Test
    void getBooking_Success_Owner() {
        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        BookingResponse response = bookingService.getBooking(100L, customer.getEmail());

        assertEquals(100L, response.getId());
        assertEquals("BK-ABCD1234", response.getBookingReference());
    }

    @Test
    void getAllBookings_AccessDenied_Customer() {
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        assertThrows(AccessDeniedException.class, () ->
                bookingService.getAllBookings(customer.getEmail()));
    }

    @Test
    void cancelBooking_Success() {
        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        BookingResponse response = bookingService.cancelBooking(100L, customer.getEmail());

        assertEquals(BookingStatus.CANCELLED, response.getStatus());
    }

    @Test
    void cancelBooking_AlreadyCheckedIn() {
        booking.setStatus(BookingStatus.CHECKED_IN);
        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.cancelBooking(100L, customer.getEmail()));
    }

    @Test
    void updateBooking_NotPending() {
        booking.setStatus(BookingStatus.CONFIRMED);
        BookingUpdateRequest request = BookingUpdateRequest.builder()
                .numberOfGuests(3)
                .build();

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.updateBooking(100L, request, customer.getEmail()));
    }

    @Test
    void updateBookingStatus_Success_Manager() {
        User manager = User.builder()
                .email("manager@example.com")
                .role(UserRole.HOTEL_MANAGER)
                .build();
        manager.setId(3L);

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(userRepository.findByEmail(manager.getEmail())).thenReturn(manager);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        BookingResponse response = bookingService.updateBookingStatus(100L, BookingStatus.CONFIRMED, manager.getEmail());

        assertEquals(BookingStatus.CONFIRMED, response.getStatus());
    }

    @Test
    void getMyBookings_Success() {
        when(userRepository.findByEmail(customer.getEmail())).thenReturn(customer);
        when(bookingRepository.findByCustomerId(1L)).thenReturn(List.of(booking));

        List<BookingResponse> bookings = bookingService.getMyBookings(customer.getEmail());

        assertEquals(1, bookings.size());
        assertEquals(100L, bookings.get(0).getId());
    }
}
