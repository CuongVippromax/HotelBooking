package com.example.hotelbooking.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_amenity",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_room_amenity", columnNames = {"room_id", "amenity_id"})
       },
       indexes = {
           @Index(name = "idx_room_amenity_room", columnList = "room_id"),
           @Index(name = "idx_room_amenity_amenity", columnList = "amenity_id")
       })
@IdClass(RoomAmenityId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomAmenity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;
}
