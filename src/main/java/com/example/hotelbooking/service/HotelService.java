package com.example.hotelbooking.service;

import com.example.hotelbooking.exception.DuplicateResourceException;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.model.dto.request.AddressRequest;
import com.example.hotelbooking.model.dto.request.HotelCreationRequest;
import com.example.hotelbooking.model.dto.request.HotelUpdateRequest;
import com.example.hotelbooking.model.dto.response.AddressResponse;
import com.example.hotelbooking.model.dto.response.HotelResponse;
import com.example.hotelbooking.model.dto.response.PageResponse;
import com.example.hotelbooking.model.entity.Address;
import com.example.hotelbooking.model.entity.Hotel;
import com.example.hotelbooking.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "HOTEL_SERVICE")
public class HotelService {

    private final HotelRepository hotelRepository;

    @Transactional
    public HotelResponse createHotel(HotelCreationRequest request) {
        if (hotelRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Hotel with email '" + request.getEmail() + "' already exists");
        }

        Address address = Address.builder()
                .streetAddress(request.getAddress().getStreetAddress())
                .city(request.getAddress().getCity())
                .state(request.getAddress().getState())
                .build();

        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .starRating(request.getStarRating())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .checkInTime(request.getCheckInTime() != null ? request.getCheckInTime() : LocalTime.of(14, 0))
                .checkOutTime(request.getCheckOutTime() != null ? request.getCheckOutTime() : LocalTime.of(12, 0))
                .address(address)
                .build();

        Hotel saved = hotelRepository.save(hotel);
        log.info("Created hotel id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public HotelResponse getHotel(Long id) {
        return toResponse(findHotelOrThrow(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<HotelResponse> getAllHotels(Pageable pageable) {
        return PageResponse.of(hotelRepository.findAll(pageable), this::toResponse);
    }

    @Transactional
    public HotelResponse updateHotel(Long id, HotelUpdateRequest request) {
        Hotel hotel = findHotelOrThrow(id);

        if (request.getEmail() != null && !request.getEmail().equals(hotel.getEmail())) {
            if (hotelRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException(
                        "Hotel with email '" + request.getEmail() + "' already exists");
            }
            hotel.setEmail(request.getEmail());
        }

        if (request.getName() != null) {
            hotel.setName(request.getName());
        }
        if (request.getDescription() != null) {
            hotel.setDescription(request.getDescription());
        }
        if (request.getStarRating() != null) {
            hotel.setStarRating(request.getStarRating());
        }
        if (request.getPhoneNumber() != null) {
            hotel.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getCheckInTime() != null) {
            hotel.setCheckInTime(request.getCheckInTime());
        }
        if (request.getCheckOutTime() != null) {
            hotel.setCheckOutTime(request.getCheckOutTime());
        }
        if (request.getEnabled() != null) {
            hotel.setEnabled(request.getEnabled());
        }

        AddressRequest addressRequest = request.getAddress();
        if (addressRequest != null) {
            Address address = hotel.getAddress();
            if (addressRequest.getStreetAddress() != null) {
                address.setStreetAddress(addressRequest.getStreetAddress());
            }
            if (addressRequest.getCity() != null) {
                address.setCity(addressRequest.getCity());
            }
            if (addressRequest.getState() != null) {
                address.setState(addressRequest.getState());
            }
        }

        Hotel saved = hotelRepository.save(hotel);
        log.info("Updated hotel id={}", saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = findHotelOrThrow(id);
        hotelRepository.delete(hotel);
        log.info("Deleted hotel id={}", id);
    }

    private Hotel findHotelOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

    private HotelResponse toResponse(Hotel hotel) {
        Address address = hotel.getAddress();
        AddressResponse addressResponse = address == null ? null : AddressResponse.builder()
                .id(address.getId())
                .streetAddress(address.getStreetAddress())
                .city(address.getCity())
                .state(address.getState())
                .build();

        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .starRating(hotel.getStarRating())
                .email(hotel.getEmail())
                .phoneNumber(hotel.getPhoneNumber())
                .checkInTime(hotel.getCheckInTime())
                .checkOutTime(hotel.getCheckOutTime())
                .enabled(hotel.getEnabled())
                .address(addressResponse)
                .build();
    }
}
