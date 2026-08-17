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
public class Orders {

    private Integer orderId;
    private String orderNo;
    private Integer customerId;
    private String customerName;

    private Integer tableId;
    private String tableNumber;

    private Integer staffId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    private String orderType;
    private String remarks;
}