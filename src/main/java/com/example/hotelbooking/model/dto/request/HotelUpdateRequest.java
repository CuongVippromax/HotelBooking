package com.example.hotelbooking.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelUpdateRequest {

    @Size(max = 200, message = "Hotel name must not exceed 200 characters")
    private String name;

    private String description;

    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating must not exceed 5")
    private Integer starRating;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be 10-15 digits")
    private String phoneNumber;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private Boolean enabled;

    @Valid
    private AddressRequest address;
}
