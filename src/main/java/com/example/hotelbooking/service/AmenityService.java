package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.AmenityCreationRequest;
import com.example.hotelbooking.model.dto.request.AmenityUpdateRequest;
import com.example.hotelbooking.model.dto.response.AmenityResponse;
import com.example.hotelbooking.model.entity.Amenity;
import com.example.hotelbooking.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AMENITY_SERVICE")
public class AmenityService {

    private final AmenityRepository amenityRepository;

    @Transactional
    public AmenityResponse createAmenity(AmenityCreationRequest request) {
        if (amenityRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Amenity with name '" + request.getName() + "' already exists");
        }

        Amenity amenity = Amenity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconName(request.getIconName())
                .type(request.getType())
                .build();

        Amenity saved = amenityRepository.save(amenity);
        log.info("Created amenity id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AmenityResponse getAmenity(Long id) {
        return toResponse(findAmenityOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<AmenityResponse> getAllAmenities() {
        return amenityRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AmenityResponse updateAmenity(Long id, AmenityUpdateRequest request) {
        Amenity amenity = findAmenityOrThrow(id);

        if (request.getName() != null && !request.getName().equals(amenity.getName())) {
            if (amenityRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException(
                        "Amenity with name '" + request.getName() + "' already exists");
            }
            amenity.setName(request.getName());
        }

        if (request.getDescription() != null) {
            amenity.setDescription(request.getDescription());
        }
        if (request.getIconName() != null) {
            amenity.setIconName(request.getIconName());
        }
        if (request.getType() != null) {
            amenity.setType(request.getType());
        }

        Amenity saved = amenityRepository.save(amenity);
        log.info("Updated amenity id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteAmenity(Long id) {
        Amenity amenity = findAmenityOrThrow(id);
        amenityRepository.delete(amenity);
        log.info("Deleted amenity id={}", id);
    }

    private Amenity findAmenityOrThrow(Long id) {
        return amenityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity not found with id: " + id));
    }

    private AmenityResponse toResponse(Amenity amenity) {
        return AmenityResponse.builder()
                .id(amenity.getId())
                .name(amenity.getName())
                .description(amenity.getDescription())
                .iconName(amenity.getIconName())
                .type(amenity.getType())
                .build();
    }
}
