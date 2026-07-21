package com.example.hotelbooking.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters")
    private String title;

    @Size(min = 20, max = 2000, message = "Comment must be between 20 and 2000 characters")
    private String comment;

    @PastOrPresent(message = "Stay date must be in the past or present")
    private LocalDate stayDate;
}
