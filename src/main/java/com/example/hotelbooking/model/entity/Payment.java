package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.PaymentMethod;
import com.example.hotelbooking.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_transaction_id", columnList = "transaction_id", unique = true),
        @Index(name = "idx_payment_booking", columnList = "booking_id"),
        @Index(name = "idx_payment_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_payment_transaction_id", columnNames = {"transaction_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Column(nullable = false, length = 100, name = "transaction_id", unique = true)
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", message = "Amount must be positive")
    @Digits(integer = 10, fraction = 2, message = "Amount must have max 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "payment_method")
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Payment status is required")
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    @NotNull(message = "Booking is required")
    private Booking booking;
}
