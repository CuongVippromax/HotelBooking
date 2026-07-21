package com.example.hotelbooking.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreationRequest {

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    private Long bookingId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters")
    private String title;

    @NotBlank(message = "Comment is required")
    @Size(min = 20, max = 2000, message = "Comment must be between 20 and 2000 characters")
    private String comment;

    @NotNull(message = "Stay date is required")
    @PastOrPresent(message = "Stay date must be in the past or present")
    private LocalDate stayDate;
}
