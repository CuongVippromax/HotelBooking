package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "booking", indexes = {
        @Index(name = "idx_booking_reference", columnList = "booking_reference", unique = true),
        @Index(name = "idx_booking_customer", columnList = "customer_id"),
        @Index(name = "idx_booking_room_dates", columnList = "room_id, check_in_date, check_out_date"),
        @Index(name = "idx_booking_status", columnList = "status"),
        @Index(name = "idx_booking_check_in_date", columnList = "check_in_date")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_booking_reference", columnNames = {"booking_reference"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @Column(nullable = false, length = 50, name = "booking_reference", unique = true)
    @NotBlank(message = "Booking reference is required")
    private String bookingReference;

    @Column(nullable = false, name = "check_in_date")
    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @Column(nullable = false, name = "check_out_date")
    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @Column(nullable = false, name = "number_of_guests")
    @NotNull(message = "Number of guests is required")
    @Min(value = 1, message = "Number of guests must be at least 1")
    private Integer numberOfGuests;

    @Column(name = "number_of_nights")
    private Integer numberOfNights;

    @Column(nullable = false, precision = 10, scale = 2, name = "total_amount")
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be positive")
    @Digits(integer = 8, fraction = 2, message = "Total amount must have max 8 integer digits and 2 decimal places")
    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT", name = "special_requests")
    private String specialRequests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Booking status is required")
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @NotNull(message = "Room is required")
    private Room room;

    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Payment payment;

    @PrePersist
    @PreUpdate
    private void calculateNumberOfNights() {
        if (checkInDate != null && checkOutDate != null) {
            this.numberOfNights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
    }

    @AssertTrue(message = "Check-out date must be after check-in date")
    public boolean isCheckOutDateAfterCheckInDate() {
        if (checkInDate == null || checkOutDate == null) {
            return true;
        }
        return checkOutDate.isAfter(checkInDate);
    }
}
