package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long customerId);

    Optional<Booking> findByBookingReference(String bookingReference);

    boolean existsByBookingReference(String bookingReference);

    List<Booking> findByRoomId(Long roomId);

    @Query("""
            SELECT COUNT(b) > 0 FROM Booking b
            WHERE b.room.id = :roomId
              AND b.status IN (com.example.hotelbooking.model.enums.BookingStatus.PENDING,
                               com.example.hotelbooking.model.enums.BookingStatus.CONFIRMED,
                               com.example.hotelbooking.model.enums.BookingStatus.CHECKED_IN)
              AND b.checkInDate < :checkOutDate
              AND b.checkOutDate > :checkInDate
              AND (:excludeBookingId IS NULL OR b.id <> :excludeBookingId)
            """)
    boolean existsOverlappingBooking(@Param("roomId") Long roomId,
                                     @Param("checkInDate") LocalDate checkInDate,
                                     @Param("checkOutDate") LocalDate checkOutDate,
                                     @Param("excludeBookingId") Long excludeBookingId);
}
