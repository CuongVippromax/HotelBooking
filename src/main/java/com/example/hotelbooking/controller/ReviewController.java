package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.ReviewCreationRequest;
import com.example.hotelbooking.model.dto.request.ReviewUpdateRequest;
import com.example.hotelbooking.model.dto.response.ReviewResponse;
import com.example.hotelbooking.model.enums.ReviewStatus;
import com.example.hotelbooking.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j(topic = "REVIEW_CONTROLLER")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
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
    public ResponseEntity<List<ReviewResponse>> getApprovedReviewsByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(reviewService.getApprovedReviewsByHotel(hotelId));
    }

    @GetMapping("/hotel/{hotelId}/all")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByHotel(
            @PathVariable Long hotelId,
            Principal principal) {
        return ResponseEntity.ok(reviewService.getAllReviewsByHotel(hotelId, principal.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(Principal principal) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(principal.getName()));
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
