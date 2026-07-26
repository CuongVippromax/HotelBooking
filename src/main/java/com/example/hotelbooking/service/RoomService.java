package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.RoomCreationRequest;
import com.example.hotelbooking.model.dto.request.RoomUpdateRequest;
import com.example.hotelbooking.model.dto.response.AmenityResponse;
import com.example.hotelbooking.model.dto.response.RoomResponse;
import com.example.hotelbooking.model.entity.Amenity;
import com.example.hotelbooking.model.entity.Hotel;
import com.example.hotelbooking.model.entity.Room;
import com.example.hotelbooking.model.entity.RoomAmenity;
import com.example.hotelbooking.model.entity.RoomType;
import com.example.hotelbooking.model.enums.RoomStatus;
import com.example.hotelbooking.repository.AmenityRepository;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.repository.RoomAmenityRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.RoomTypeRepository;
import com.example.hotelbooking.model.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ROOM_SERVICE")
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AmenityRepository amenityRepository;
    private final RoomAmenityRepository roomAmenityRepository;

    @Transactional
    public RoomResponse createRoom(RoomCreationRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + request.getHotelId()));
        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("RoomType not found with id: " + request.getRoomTypeId()));

        if (roomRepository.existsByHotelIdAndRoomNumber(hotel.getId(), request.getRoomNumber())) {
            throw new DuplicateResourceException(
                    "Room number '" + request.getRoomNumber() + "' already exists in this hotel");
        }

        Room room = Room.builder()
                .roomNumber(request.getRoomNumber())
                .status(request.getStatus() != null ? request.getStatus() : RoomStatus.AVAILABLE)
                .pricePerNight(request.getPricePerNight())
                .description(request.getDescription())
                .hotel(hotel)
                .roomType(roomType)
                .build();

        Room saved = roomRepository.save(room);
        log.info("Created room id={} in hotel id={}", saved.getId(), hotel.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public RoomResponse getRoom(Long id) {
        Room room = findRoomOrThrow(id);
        return toResponse(room);
    }

    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> getAllRooms(Pageable pageable) {
        return PageResponse.of(roomRepository.findAll(pageable), this::toResponse);
    }

    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> getRoomsByHotel(Long hotelId, Pageable pageable) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }
        return PageResponse.of(roomRepository.findByHotelId(hotelId, pageable), this::toResponse);
    }

    @Transactional
    public RoomResponse updateRoom(Long id, RoomUpdateRequest request) {
        Room room = findRoomOrThrow(id);

        if (request.getRoomNumber() != null && !request.getRoomNumber().equals(room.getRoomNumber())) {
            if (roomRepository.existsByHotelIdAndRoomNumber(room.getHotel().getId(), request.getRoomNumber())) {
                throw new DuplicateResourceException(
                        "Room number '" + request.getRoomNumber() + "' already exists in this hotel");
            }
            room.setRoomNumber(request.getRoomNumber());
        }

        if (request.getStatus() != null) {
            room.setStatus(request.getStatus());
        }
        if (request.getPricePerNight() != null) {
            room.setPricePerNight(request.getPricePerNight());
        }
        if (request.getDescription() != null) {
            room.setDescription(request.getDescription());
        }
        if (request.getRoomTypeId() != null) {
            RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "RoomType not found with id: " + request.getRoomTypeId()));
            room.setRoomType(roomType);
        }

        Room saved = roomRepository.save(room);
        log.info("Updated room id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = findRoomOrThrow(id);
        roomRepository.delete(room);
        log.info("Deleted room id={}", id);
    }

    @Transactional
    public RoomResponse addAmenity(Long roomId, Long amenityId) {
        Room room = findRoomOrThrow(roomId);
        Amenity amenity = amenityRepository.findById(amenityId)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity not found with id: " + amenityId));

        if (roomAmenityRepository.existsByRoomIdAndAmenityId(roomId, amenityId)) {
            throw new DuplicateResourceException(
                    "Amenity id=" + amenityId + " is already assigned to room id=" + roomId);
        }

        RoomAmenity roomAmenity = new RoomAmenity(room, amenity);
        roomAmenityRepository.save(roomAmenity);
        log.info("Added amenity id={} to room id={}", amenityId, roomId);
        return toResponse(findRoomOrThrow(roomId));
    }

    @Transactional
    public RoomResponse removeAmenity(Long roomId, Long amenityId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ResourceNotFoundException("Room not found with id: " + roomId);
        }
        if (!roomAmenityRepository.existsByRoomIdAndAmenityId(roomId, amenityId)) {
            throw new ResourceNotFoundException(
                    "Amenity id=" + amenityId + " is not assigned to room id=" + roomId);
        }
        roomAmenityRepository.deleteByRoomIdAndAmenityId(roomId, amenityId);
        log.info("Removed amenity id={} from room id={}", amenityId, roomId);
        return toResponse(findRoomOrThrow(roomId));
    }

    private Room findRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    private RoomResponse toResponse(Room room) {
        List<AmenityResponse> amenities = room.getAmenities().stream()
                .map(RoomAmenity::getAmenity)
                .map(a -> AmenityResponse.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .description(a.getDescription())
                        .iconName(a.getIconName())
                        .type(a.getType())
                        .build())
                .toList();

        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .status(room.getStatus())
                .pricePerNight(room.getPricePerNight())
                .description(room.getDescription())
                .hotelId(room.getHotel() != null ? room.getHotel().getId() : null)
                .hotelName(room.getHotel() != null ? room.getHotel().getName() : null)
                .roomTypeId(room.getRoomType() != null ? room.getRoomType().getId() : null)
                .roomTypeName(room.getRoomType() != null ? room.getRoomType().getName() : null)
                .amenities(amenities)
                .build();
    }
}
