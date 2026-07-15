package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.RoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room", indexes = {
        @Index(name = "idx_room_hotel_room_number", columnList = "hotel_id, room_number", unique = true),
        @Index(name = "idx_room_status", columnList = "status"),
        @Index(name = "idx_room_price", columnList = "price_per_night")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_hotel_room_number", columnNames = {"hotel_id", "room_number"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Column(nullable = false, length = 20, name = "room_number")
    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Room status is required")
    @Builder.Default
    private RoomStatus status = RoomStatus.AVAILABLE;

    @Column(nullable = false, precision = 10, scale = 2, name = "price_per_night")
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", message = "Price per night must be positive")
    @Digits(integer = 8, fraction = 2, message = "Price must have max 8 integer digits and 2 decimal places")
    private BigDecimal pricePerNight;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @NotNull(message = "Hotel is required")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RoomAmenity> amenities = new ArrayList<>();
}
