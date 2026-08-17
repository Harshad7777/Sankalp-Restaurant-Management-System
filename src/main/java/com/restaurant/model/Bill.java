package com.restaurant.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    private int billId;
    private String billNo;
    private int orderId;

    private LocalDateTime billDate;

    private double subtotal;
    private double gst;
    private double discount;
    private double grandTotal;

    private String paymentMethod;
    private String paymentStatus;
}