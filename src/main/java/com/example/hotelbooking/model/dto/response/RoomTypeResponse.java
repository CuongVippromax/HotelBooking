package com.example.hotelbooking.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomTypeResponse {
    private Long id;
    private String name;
    private String description;
    private Integer maxOccupancy;
    private String bedType;
    private Integer numberOfBeds;
    private Double sizeSquareMeters;
    private BigDecimal basePrice;
}
