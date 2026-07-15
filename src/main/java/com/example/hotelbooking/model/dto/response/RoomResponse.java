package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private RoomStatus status;
    private BigDecimal pricePerNight;
    private String description;
    private Long hotelId;
    private String hotelName;
    private Long roomTypeId;
    private String roomTypeName;
}
