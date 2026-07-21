package com.example.hotelbooking.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateRequest {

    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of guests must be at least 1")
    private Integer numberOfGuests;

    @Size(max = 2000, message = "Special requests must not exceed 2000 characters")
    private String specialRequests;

    @AssertTrue(message = "Check-out date must be after check-in date")
    public boolean isCheckOutDateAfterCheckInDate() {
        if (checkInDate == null || checkOutDate == null) {
            return true;
        }
        return checkOutDate.isAfter(checkInDate);
    }
}
