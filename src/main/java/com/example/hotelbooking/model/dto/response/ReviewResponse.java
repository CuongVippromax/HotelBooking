package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.ReviewStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String title;
    private String comment;
    private LocalDate stayDate;
    private ReviewStatus status;
    private Long userId;
    private String userName;
    private Long hotelId;
    private String hotelName;
    private Long bookingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
