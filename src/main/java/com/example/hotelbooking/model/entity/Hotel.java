package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel", indexes = {
        @Index(name = "idx_hotel_name", columnList = "name"),
        @Index(name = "idx_hotel_star_rating", columnList = "star_rating"),
        @Index(name = "idx_hotel_enabled", columnList = "enabled")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel extends BaseEntity {

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Hotel name is required")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(nullable = false, name = "star_rating")
    @NotNull(message = "Star rating is required")
    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating must not exceed 5")
    private Integer starRating;

    @Column(nullable = false, length = 100)
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Column(length = 20, name = "phone_number")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be 10-15 digits")
    private String phoneNumber;

    @Column(nullable = false, name = "check_in_time")
    @NotNull(message = "Check-in time is required")
    @Builder.Default
    private LocalTime checkInTime = LocalTime.of(14, 0);

    @Column(nullable = false, name = "check_out_time")
    @NotNull(message = "Check-out time is required")
    @Builder.Default
    private LocalTime checkOutTime = LocalTime.of(12, 0);

    @Column(columnDefinition = "TEXT", name = "cancellation_policy")
    private String cancellationPolicy;

    @Column(nullable = false)
    @NotNull(message = "Enabled status is required")
    @Builder.Default
    private Boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @NotNull(message = "Address is required")
    private Address address;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
}
