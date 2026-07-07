package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import com.example.hotelbooking.model.enums.AmenityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "amenity", indexes = {
        @Index(name = "idx_amenity_name", columnList = "name", unique = true),
        @Index(name = "idx_amenity_type", columnList = "type")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Amenity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "Amenity name is required")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(length = 50, name = "icon_name")
    private String iconName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull(message = "Amenity type is required")
    private AmenityType type;

    @OneToMany(mappedBy = "amenity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RoomAmenity> roomAmenities = new ArrayList<>();
}
