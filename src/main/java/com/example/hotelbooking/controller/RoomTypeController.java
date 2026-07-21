package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.dto.request.RoomTypeCreationRequest;
import com.example.hotelbooking.model.dto.request.RoomTypeUpdateRequest;
import com.example.hotelbooking.model.dto.response.RoomTypeResponse;
import com.example.hotelbooking.service.RoomTypeService;
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
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping
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
