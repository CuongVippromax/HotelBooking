package com.example.hotelbooking.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressResponse {
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
}
