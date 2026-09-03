package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.HotelCreationRequest;
import com.example.hotelbooking.model.dto.request.HotelUpdateRequest;
import com.example.hotelbooking.model.dto.response.HotelResponse;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.service.HotelService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@Slf4j(topic = "HOTEL_CONTROLLER")
@Tag(name = "Hotels", description = "APIs for managing hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    @Operation(summary = "Create a new hotel", description = "Creates a new hotel with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelCreationRequest request) {
        HotelResponse response = hotelService.createHotel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotel(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<HotelResponse>> getAllHotels(Pageable pageable) {
        return ResponseEntity.ok(hotelService.getAllHotels(pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id,
                                                     @Valid @RequestBody HotelUpdateRequest request) {
        return ResponseEntity.ok(hotelService.updateHotel(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
