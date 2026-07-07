package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_type", indexes = {
        @Index(name = "idx_room_type_name", columnList = "name")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomType extends BaseEntity {

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Room type name is required")
    private String name;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(nullable = false, name = "max_occupancy")
    @NotNull(message = "Max occupancy is required")
    @Min(value = 1, message = "Max occupancy must be at least 1")
    private Integer maxOccupancy;

    @Column(nullable = false, length = 50, name = "bed_type")
    @NotBlank(message = "Bed type is required")
    private String bedType;

    @Column(nullable = false, name = "number_of_beds")
    @NotNull(message = "Number of beds is required")
    @Min(value = 1, message = "Number of beds must be at least 1")
    private Integer numberOfBeds;

    @Column(name = "size_square_meters")
    @DecimalMin(value = "0.0", message = "Size must be positive")
    private Double sizeSquareMeters;

    @Column(nullable = false, precision = 10, scale = 2, name = "base_price")
    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", message = "Base price must be positive")
    @Digits(integer = 8, fraction = 2, message = "Base price must have max 8 integer digits and 2 decimal places")
    private BigDecimal basePrice;

    @OneToMany(mappedBy = "roomType", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();
}
