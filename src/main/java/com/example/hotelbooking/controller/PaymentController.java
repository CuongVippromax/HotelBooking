package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.PaymentCreationRequest;
import com.example.hotelbooking.model.dto.response.PaymentResponse;
import com.example.hotelbooking.model.enums.PaymentStatus;
import com.example.hotelbooking.service.PaymentService;
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
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentCreationRequest request,
            Principal principal) {
        PaymentResponse response = paymentService.createPayment(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(paymentService.getPayment(id, principal.getName()));
    }

    @GetMapping("/booking/{bookingId}")
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
}
