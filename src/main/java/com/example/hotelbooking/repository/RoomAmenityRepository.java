package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.RoomAmenity;
import com.example.hotelbooking.model.entity.RoomAmenityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, RoomAmenityId> {

    List<RoomAmenity> findByRoomId(Long roomId);

    boolean existsByRoomIdAndAmenityId(Long roomId, Long amenityId);

    void deleteByRoomIdAndAmenityId(Long roomId, Long amenityId);
}
