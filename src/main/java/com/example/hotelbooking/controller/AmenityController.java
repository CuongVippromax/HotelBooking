package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.AmenityCreationRequest;
import com.example.hotelbooking.model.dto.request.AmenityUpdateRequest;
import com.example.hotelbooking.model.dto.response.AmenityResponse;
import com.example.hotelbooking.service.AmenityService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
@Slf4j(topic = "AMENITY_CONTROLLER")
@Tag(name = "Amenities", description = "APIs for managing amenities")
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping
    @Operation(summary = "Create a new amenity", description = "Creates a new amenity with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Amenity created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AmenityResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<AmenityResponse> createAmenity(@Valid @RequestBody AmenityCreationRequest request) {
        AmenityResponse response = amenityService.createAmenity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get amenity by ID", description = "Retrieves an amenity's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenity found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AmenityResponse.class))),
            @ApiResponse(responseCode = "404", description = "Amenity not found",
                    content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<AmenityResponse> getAmenity(@PathVariable Long id) {
        return ResponseEntity.ok(amenityService.getAmenity(id));
    }

    @GetMapping
    @Operation(summary = "Get all amenities", description = "Retrieves a list of all amenities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of amenities",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AmenityResponse.class)))
    })
    @SecurityRequirement(name = "bearerAuth")
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
