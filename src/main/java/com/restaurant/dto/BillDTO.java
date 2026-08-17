package com.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDTO {

    @Positive(message = "Order ID must be positive")
    private int orderId;

    private double subtotal;
    private double gst;

    @Min(value = 0, message = "Discount cannot be negative")
    private double discount;

    private double grandTotal;

    @Pattern(regexp = "^(CASH|CARD|UPI)$", message = "Payment method must be CASH, CARD or UPI")
    private String paymentMethod;

    @Pattern(regexp = "^(PENDING|PAID)$", message = "Payment status must be PENDING or PAID")
    private String paymentStatus;
}