package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.RoomTypeCreationRequest;
import com.example.hotelbooking.model.dto.request.RoomTypeUpdateRequest;
import com.example.hotelbooking.model.dto.response.RoomTypeResponse;
import com.example.hotelbooking.model.entity.RoomType;
import com.example.hotelbooking.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ROOM_TYPE_SERVICE")
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Transactional
    public RoomTypeResponse createRoomType(RoomTypeCreationRequest request) {
        if (roomTypeRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Room type with name '" + request.getName() + "' already exists");
        }

        RoomType roomType = RoomType.builder()
                .name(request.getName())
                .description(request.getDescription())
                .maxOccupancy(request.getMaxOccupancy())
                .bedType(request.getBedType())
                .numberOfBeds(request.getNumberOfBeds())
                .sizeSquareMeters(request.getSizeSquareMeters())
                .basePrice(request.getBasePrice())
                .build();

        RoomType saved = roomTypeRepository.save(roomType);
        log.info("Created room type id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public RoomTypeResponse getRoomType(Long id) {
        return toResponse(findRoomTypeOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public RoomTypeResponse updateRoomType(Long id, RoomTypeUpdateRequest request) {
        RoomType roomType = findRoomTypeOrThrow(id);

        if (request.getName() != null && !request.getName().equals(roomType.getName())) {
            if (roomTypeRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException(
                        "Room type with name '" + request.getName() + "' already exists");
            }
            roomType.setName(request.getName());
        }

        if (request.getDescription() != null) {
            roomType.setDescription(request.getDescription());
        }
        if (request.getMaxOccupancy() != null) {
            roomType.setMaxOccupancy(request.getMaxOccupancy());
        }
        if (request.getBedType() != null) {
            roomType.setBedType(request.getBedType());
        }
        if (request.getNumberOfBeds() != null) {
            roomType.setNumberOfBeds(request.getNumberOfBeds());
        }
        if (request.getSizeSquareMeters() != null) {
            roomType.setSizeSquareMeters(request.getSizeSquareMeters());
        }
        if (request.getBasePrice() != null) {
            roomType.setBasePrice(request.getBasePrice());
        }

        RoomType saved = roomTypeRepository.save(roomType);
        log.info("Updated room type id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteRoomType(Long id) {
        RoomType roomType = findRoomTypeOrThrow(id);
        roomTypeRepository.delete(roomType);
        log.info("Deleted room type id={}", id);
    }

    private RoomType findRoomTypeOrThrow(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomType not found with id: " + id));
    }

    private RoomTypeResponse toResponse(RoomType roomType) {
        return RoomTypeResponse.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .description(roomType.getDescription())
                .maxOccupancy(roomType.getMaxOccupancy())
                .bedType(roomType.getBedType())
                .numberOfBeds(roomType.getNumberOfBeds())
                .sizeSquareMeters(roomType.getSizeSquareMeters())
                .basePrice(roomType.getBasePrice())
                .build();
    }
}
