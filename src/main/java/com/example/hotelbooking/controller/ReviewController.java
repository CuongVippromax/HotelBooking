package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.ReviewCreationRequest;
import com.example.hotelbooking.model.dto.request.ReviewUpdateRequest;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.dto.response.ReviewResponse;
import com.example.hotelbooking.model.enums.ReviewStatus;
import com.example.hotelbooking.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j(topic = "REVIEW_CONTROLLER")
@Tag(name = "Reviews", description = "APIs for managing reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new review for a hotel by the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody ReviewCreationRequest request,
            Principal principal) {
        ReviewResponse response = reviewService.createReview(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<PageResponse<ReviewResponse>> getApprovedReviewsByHotel(@PathVariable Long hotelId,
                                                                                  Pageable pageable) {
        return ResponseEntity.ok(reviewService.getApprovedReviewsByHotel(hotelId, pageable));
    }

    @GetMapping("/hotel/{hotelId}/all")
    public ResponseEntity<PageResponse<ReviewResponse>> getAllReviewsByHotel(
            @PathVariable Long hotelId,
            Principal principal,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviewsByHotel(hotelId, principal.getName(), pageable));
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponse<ReviewResponse>> getMyReviews(Principal principal, Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(principal.getName(), pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewUpdateRequest request,
            Principal principal) {
        return ResponseEntity.ok(reviewService.updateReview(id, request, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id, Principal principal) {
        reviewService.deleteReview(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReviewResponse> updateReviewStatus(
            @PathVariable Long id,
            @RequestParam ReviewStatus status,
            Principal principal) {
        return ResponseEntity.ok(reviewService.updateReviewStatus(id, status, principal.getName()));
    }
}
