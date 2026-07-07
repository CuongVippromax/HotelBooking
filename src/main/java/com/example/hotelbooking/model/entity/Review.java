package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.ReviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "review", indexes = {
        @Index(name = "idx_review_hotel", columnList = "hotel_id"),
        @Index(name = "idx_review_user", columnList = "user_id"),
        @Index(name = "idx_review_status", columnList = "status"),
        @Index(name = "idx_review_hotel_status_created", columnList = "hotel_id, status, created_at")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Column(nullable = false)
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Comment is required")
    @Size(min = 20, max = 2000, message = "Comment must be between 20 and 2000 characters")
    private String comment;

    @Column(nullable = false, name = "stay_date")
    @NotNull(message = "Stay date is required")
    @PastOrPresent(message = "Stay date must be in the past or present")
    private LocalDate stayDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Review status is required")
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel is required")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
