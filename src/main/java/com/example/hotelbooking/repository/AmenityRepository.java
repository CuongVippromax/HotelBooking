package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    boolean existsByName(String name);
}
