package com.restaurant.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for payment gateway order creation requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOrderRequestDTO {

    @NotNull(message = "Bill ID is required")
    @Min(value = 1, message = "Bill ID must be valid")
    private Integer billId;

    private String gateway; // RAZORPAY, STRIPE, PAYTM
    private BigDecimal amount;
    private String currency; // INR, USD
}
