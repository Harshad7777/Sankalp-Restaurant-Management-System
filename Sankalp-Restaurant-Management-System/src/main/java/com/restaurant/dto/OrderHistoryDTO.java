package com.restaurant.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for order history records.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistoryDTO {

    private Integer orderId;
    private Integer customerId;
    private String customerName;
    private Double totalAmount;
    private String orderStatus;
    private LocalDateTime orderDate;
}