package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.AmenityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AmenityResponse {
    private Long id;
    private String name;
    private String description;
    private String iconName;
    private AmenityType type;
}
