package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.PaymentCreationRequest;
import com.example.hotelbooking.model.dto.response.PaymentResponse;
import com.example.hotelbooking.model.entity.Booking;
import com.example.hotelbooking.model.entity.Payment;
import com.example.hotelbooking.model.entity.User;
import com.example.hotelbooking.model.enums.BookingStatus;
import com.example.hotelbooking.model.enums.PaymentStatus;
import com.example.hotelbooking.model.enums.UserRole;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.PaymentRepository;
import com.example.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT_SERVICE")
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional
    public PaymentResponse createPayment(PaymentCreationRequest request, String userEmail) {
        User user = findUserOrThrow(userEmail);
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        ensureCanAccess(booking, user);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot pay for a cancelled booking");
        }
        if (paymentRepository.existsByBookingId(booking.getId())) {
            throw new DuplicateResourceException(
                    "A payment already exists for booking id=" + booking.getId());
        }

        Payment payment = Payment.builder()
                .transactionId(generateTransactionId())
                .amount(booking.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .booking(booking)
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Created payment id={} txn={} for booking id={}",
                saved.getId(), saved.getTransactionId(), booking.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long id, String userEmail) {
        Payment payment = findPaymentOrThrow(id);
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(payment.getBooking(), user);
        return toResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBooking(Long bookingId, String userEmail) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found for booking id: " + bookingId));
        User user = findUserOrThrow(userEmail);
        ensureCanAccess(payment.getBooking(), user);
        return toResponse(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments(String userEmail) {
        User user = findUserOrThrow(userEmail);
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can view all payments");
        }
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PaymentResponse updatePaymentStatus(Long id, PaymentStatus newStatus, String userEmail) {
        Payment payment = findPaymentOrThrow(id);
        User user = findUserOrThrow(userEmail);
        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.HOTEL_MANAGER) {
            throw new AccessDeniedException("Only Admins and Hotel Managers can change payment status");
        }

        payment.setStatus(newStatus);
        if (newStatus == PaymentStatus.COMPLETED) {
            payment.setPaidAt(LocalDateTime.now());
            // Thanh toán thành công -> xác nhận booking (nếu còn đang chờ).
            Booking booking = payment.getBooking();
            if (booking.getStatus() == BookingStatus.PENDING) {
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(booking);
            }
        }

        Payment saved = paymentRepository.save(payment);
        log.info("Updated status of payment id={} to {}", saved.getId(), newStatus);
        return toResponse(saved);
    }

    @Transactional
    public void deletePayment(Long id) {
        Payment payment = findPaymentOrThrow(id);
        // Only allow deletion of pending payments (not completed or cancelled)
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalArgumentException("Only pending payments can be deleted");
        }
        paymentRepository.delete(payment);
        log.info("Deleted payment id={}", id);
    }

    private void ensureCanAccess(Booking booking, User user) {
        boolean isOwner = booking.getCustomer().getId().equals(user.getId());
        boolean isPrivileged = user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.HOTEL_MANAGER;
        if (!isOwner && !isPrivileged) {
            throw new AccessDeniedException("You are not authorized to access this payment");
        }
    }

    private User findUserOrThrow(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + userEmail);
        }
        return user;
    }

    private Payment findPaymentOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    private String generateTransactionId() {
        String transactionId;
        do {
            transactionId = "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        } while (paymentRepository.existsByTransactionId(transactionId));
        return transactionId;
    }

    private PaymentResponse toResponse(Payment payment) {
        Booking booking = payment.getBooking();
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .paidAt(payment.getPaidAt())
                .bookingId(booking != null ? booking.getId() : null)
                .bookingReference(booking != null ? booking.getBookingReference() : null)
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
