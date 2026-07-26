package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.BookingCreationRequest;
import com.example.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.example.hotelbooking.model.dto.response.BookingResponse;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.enums.BookingStatus;
import com.example.hotelbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j(topic = "BOOKING_CONTROLLER")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingCreationRequest request,
            Principal principal) {
        BookingResponse response = bookingService.createBooking(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(bookingService.getBooking(id, principal.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponse<BookingResponse>> getMyBookings(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getMyBookings(principal.getName(), pageable));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookingResponse>> getAllBookings(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(principal.getName(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingUpdateRequest request,
            Principal principal) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request, principal.getName()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status,
            Principal principal) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status, principal.getName()));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, principal.getName()));
    }
}
