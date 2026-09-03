package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.RoomTypeCreationRequest;
import com.example.hotelbooking.model.dto.request.RoomTypeUpdateRequest;
import com.example.hotelbooking.model.dto.response.RoomTypeResponse;
import com.example.hotelbooking.service.RoomTypeService;
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
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
@Slf4j(topic = "ROOM_TYPE_CONTROLLER")
@Tag(name = "Room Types", description = "APIs for managing room types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping
    @Operation(summary = "Create a new room type", description = "Creates a new room type with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room type created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoomTypeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomTypeResponse> createRoomType(@Valid @RequestBody RoomTypeCreationRequest request) {
        RoomTypeResponse response = roomTypeService.createRoomType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> getRoomType(@PathVariable Long id) {
        return ResponseEntity.ok(roomTypeService.getRoomType(id));
    }

    @GetMapping
    public ResponseEntity<List<RoomTypeResponse>> getAllRoomTypes() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomTypeResponse> updateRoomType(@PathVariable Long id,
                                                           @Valid @RequestBody RoomTypeUpdateRequest request) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }
}
