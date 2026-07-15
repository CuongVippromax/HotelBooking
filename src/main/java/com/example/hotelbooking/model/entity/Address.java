package com.example.hotelbooking.model.entity;

import com.example.hotelbooking.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "address", indexes = {
        @Index(name = "idx_address_city", columnList = "city")
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

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Hotel hotel;
}
