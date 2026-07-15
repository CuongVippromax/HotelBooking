package com.example.hotelbooking.model.dto.request;

import com.example.hotelbooking.model.enums.RoomStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreationRequest {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    private RoomStatus status;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", message = "Price per night must be positive")
    @Digits(integer = 8, fraction = 2, message = "Price must have max 8 integer digits and 2 decimal places")
    private BigDecimal pricePerNight;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Hotel id is required")
    private Long hotelId;

    @NotNull(message = "Room type id is required")
    private Long roomTypeId;
}
