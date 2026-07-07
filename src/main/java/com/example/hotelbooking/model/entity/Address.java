package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "address", indexes = {
        @Index(name = "idx_address_city_country", columnList = "city, country"),
        @Index(name = "idx_address_coordinates", columnList = "latitude, longitude")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {

    @Column(nullable = false, length = 255, name = "street_address")
    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "City is required")
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20, name = "postal_code")
    private String postalCode;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Country is required")
    private String country;

    @Column(length = 2, name = "country_code")
    @Pattern(regexp = "[A-Z]{2}", message = "Country code must be 2 uppercase letters")
    private String countryCode;

    @Column
    @Min(-90)
    @Max(90)
    private Double latitude;

    @Column
    @Min(-180)
    @Max(180)
    private Double longitude;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Hotel hotel;
}
