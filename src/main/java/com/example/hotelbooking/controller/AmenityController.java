package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.AmenityCreationRequest;
import com.example.hotelbooking.model.dto.request.AmenityUpdateRequest;
import com.example.hotelbooking.model.dto.response.AmenityResponse;
import com.example.hotelbooking.service.AmenityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
@Slf4j(topic = "AMENITY_CONTROLLER")
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<AmenityResponse> createAmenity(@Valid @RequestBody AmenityCreationRequest request) {
        AmenityResponse response = amenityService.createAmenity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityResponse> getAmenity(@PathVariable Long id) {
        return ResponseEntity.ok(amenityService.getAmenity(id));
    }

    @GetMapping
    public ResponseEntity<List<AmenityResponse>> getAllAmenities() {
        return ResponseEntity.ok(amenityService.getAllAmenities());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<AmenityResponse> updateAmenity(@PathVariable Long id,
                                                         @Valid @RequestBody AmenityUpdateRequest request) {
        return ResponseEntity.ok(amenityService.updateAmenity(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
