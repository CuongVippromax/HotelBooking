package com.example.hotelbooking.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeUpdateRequest {

    @Size(max = 100, message = "Room type name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Min(value = 1, message = "Max occupancy must be at least 1")
    private Integer maxOccupancy;

    @Size(max = 50, message = "Bed type must not exceed 50 characters")
    private String bedType;

    @Min(value = 1, message = "Number of beds must be at least 1")
    private Integer numberOfBeds;

    @DecimalMin(value = "0.0", message = "Size must be positive")
    private Double sizeSquareMeters;

    @DecimalMin(value = "0.0", message = "Base price must be positive")
    @Digits(integer = 8, fraction = 2, message = "Base price must have max 8 integer digits and 2 decimal places")
    private BigDecimal basePrice;
}
