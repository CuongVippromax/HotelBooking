package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.RoomCreationRequest;
import com.example.hotelbooking.model.dto.request.RoomUpdateRequest;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.dto.response.RoomResponse;
import com.example.hotelbooking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Slf4j(topic = "ROOM_CONTROLLER")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomCreationRequest request) {
        RoomResponse response = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<RoomResponse>> getAllRooms(Pageable pageable) {
        return ResponseEntity.ok(roomService.getAllRooms(pageable));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<PageResponse<RoomResponse>> getRoomsByHotel(@PathVariable Long hotelId,
                                                                      Pageable pageable) {
        return ResponseEntity.ok(roomService.getRoomsByHotel(hotelId, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id,
                                                   @Valid @RequestBody RoomUpdateRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/amenities/{amenityId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomResponse> addAmenity(@PathVariable Long id, @PathVariable Long amenityId) {
        return ResponseEntity.ok(roomService.addAmenity(id, amenityId));
    }

    @DeleteMapping("/{id}/amenities/{amenityId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_MANAGER')")
    public ResponseEntity<RoomResponse> removeAmenity(@PathVariable Long id, @PathVariable Long amenityId) {
        return ResponseEntity.ok(roomService.removeAmenity(id, amenityId));
    }
}
