package com.example.hotelbooking.repository;

import com.example.hotelbooking.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBookingId(Long bookingId);

    Optional<Payment> findByTransactionId(String transactionId);

    boolean existsByBookingId(Long bookingId);

    boolean existsByTransactionId(String transactionId);
}
