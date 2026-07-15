package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    boolean existsByName(String name);
}
