package com.restaurant.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Swiggy, Zomato, and UrbanPiper food delivery aggregator orders.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregatorOrderDTO {

    private String channel; // SWIGGY, ZOMATO, URBANPIPER
    private String externalOrderId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String specialInstructions;
    private BigDecimal totalAmount;
    private List<AggregatorOrderItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AggregatorOrderItemDTO {
        private Integer itemId;
        private String externalSku;
        private String itemName;
        private Integer quantity;
        private BigDecimal price;
        private String kitchenNote;
    }
}
