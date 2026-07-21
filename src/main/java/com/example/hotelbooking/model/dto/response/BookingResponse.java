package com.example.hotelbooking.model.dto.response;

import com.example.hotelbooking.model.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingResponse {
    private Long id;
    private String bookingReference;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private Integer numberOfNights;
    private BigDecimal totalAmount;
    private String specialRequests;
    private BookingStatus status;
    private Long customerId;
    private String customerName;
    private Long roomId;
    private String roomNumber;
    private Long hotelId;
    private String hotelName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
