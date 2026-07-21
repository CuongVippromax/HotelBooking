package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.PaymentMethod;
import com.example.hotelbooking.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private LocalDateTime paidAt;
    private Long bookingId;
    private String bookingReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
