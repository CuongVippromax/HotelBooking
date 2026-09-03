package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.BookingCreationRequest;
import com.example.hotelbooking.model.dto.request.BookingUpdateRequest;
import com.example.hotelbooking.model.dto.response.BookingResponse;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.enums.BookingStatus;
import com.example.hotelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Bookings", description = "APIs for managing bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingCreationRequest request,
            Principal principal) {
        BookingResponse response = bookingService.createBooking(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieves a booking's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(bookingService.getBooking(id, principal.getName()));
    }

    @GetMapping("/my")
    @Operation(summary = "Get current user's bookings", description = "Retrieves a list of bookings for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of bookings",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PageResponse<BookingResponse>> getMyBookings(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getMyBookings(principal.getName(), pageable));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL_MANAGER')")
    @Operation(summary = "Get all bookings", description = "Retrieves a list of all bookings with pagination (admin or hotel manager only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of bookings",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PageResponse<BookingResponse>> getAllBookings(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(bookingService.getAllBookings(principal.getName(), pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a booking", description = "Updates a booking's details (only for pending bookings)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or booking not pending status required"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @SecurityRequirement(name = "bearerAuth")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id, Principal principal) {
        bookingService.deleteBooking(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
