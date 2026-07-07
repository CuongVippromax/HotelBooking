package com.example.hotelbooking.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomAmenityId implements Serializable {

    private Long room;
    private Long amenity;
}
