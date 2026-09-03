package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.PaymentCreationRequest;
import com.example.hotelbooking.model.dto.response.PaymentResponse;
import com.example.hotelbooking.model.enums.PaymentStatus;
import com.example.hotelbooking.service.PaymentService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT_CONTROLLER")
@Tag(name = "Payments", description = "APIs for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Creates a new payment for a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or booking not found or payment already exists"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentCreationRequest request,
            Principal principal) {
        PaymentResponse response = paymentService.createPayment(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID", description = "Retrieves a payment's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(paymentService.getPayment(id, principal.getName()));
    }

    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get payment by booking ID", description = "Retrieves the payment details for a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found for the booking",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PaymentResponse> getPaymentByBooking(@PathVariable Long bookingId, Principal principal) {
        return ResponseEntity.ok(paymentService.getPaymentByBooking(bookingId, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments(Principal principal) {
        return ResponseEntity.ok(paymentService.getAllPayments(principal.getName()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status,
            Principal principal) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id, Principal principal) {
        paymentService.deletePayment(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
