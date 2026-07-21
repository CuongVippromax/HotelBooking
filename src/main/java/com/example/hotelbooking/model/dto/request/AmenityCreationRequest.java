package com.example.hotelbooking.model.dto.request;

import com.example.hotelbooking.model.enums.AmenityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenityCreationRequest {

    @NotBlank(message = "Amenity name is required")
    @Size(max = 100, message = "Amenity name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Size(max = 50, message = "Icon name must not exceed 50 characters")
    private String iconName;

    @NotNull(message = "Amenity type is required")
    private AmenityType type;
}
