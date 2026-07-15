package com.example.hotelbooking.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String description;
    private Integer starRating;
    private String email;
    private String phoneNumber;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Boolean enabled;
    private AddressResponse address;
}
