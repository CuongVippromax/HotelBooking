package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.Review;
import com.example.hotelbooking.model.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByHotelId(Long hotelId, Pageable pageable);
    Page<Review> findByHotelIdAndStatus(Long hotelId, ReviewStatus status, Pageable pageable);
    Page<Review> findByUserId(Long userId, Pageable pageable);
    boolean existsByBookingId(Long bookingId);
}
