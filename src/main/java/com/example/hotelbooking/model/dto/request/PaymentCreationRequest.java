package com.example.hotelbooking.model.dto.request;

import com.example.hotelbooking.model.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreationRequest {

    @NotNull(message = "Booking id is required")
    @Min(value = 1, message = "Booking id must be greater than zero")
    private Long bookingId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}
