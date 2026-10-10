package com.restaurant.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Bill entity in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    private int billId;
    private int restaurantId;
    private String billNo;
    private int orderId;
    private LocalDateTime billDate;
    private double subtotal;
    private double gst;
    private double discount;
    private double grandTotal;
    private String paymentMethod;
    private String paymentStatus;

    // Convenience constructor for legacy instantiation without restaurantId
    public Bill(int billId, String billNo, int orderId, LocalDateTime billDate, double subtotal, double gst, double discount, double grandTotal, String paymentMethod, String paymentStatus) {
        this(billId, 0, billNo, orderId, billDate, subtotal, gst, discount, grandTotal, paymentMethod, paymentStatus);
    }
}