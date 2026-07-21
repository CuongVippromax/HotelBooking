package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.Review;
import com.example.hotelbooking.model.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHotelId(Long hotelId);
    List<Review> findByHotelIdAndStatus(Long hotelId, ReviewStatus status);
    List<Review> findByUserId(Long userId);
    boolean existsByBookingId(Long bookingId);
}
