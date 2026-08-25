package com.restaurant.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderDetails {

    private Integer orderDetailId;
    private Integer orderId;

    @NotNull(message = "Menu item id required")
    @Min(
        value = 1,
        message = "Menu item id must be greater than 0"
    )
    private Integer itemId;

    private String itemName;

    @NotNull(message = "Quantity required")
    @Min(
        value = 1,
        message = "Quantity must be at least 1"
    )
    private Integer quantity;

    private Double price;
    private Double subtotal;

    private String kitchenNote;
    private String kitchenStatus;

    private Integer preparedBy;
    private String preparedByName;

    private LocalDateTime servedAt;

    public OrderDetails() {
    }

    public OrderDetails(Integer orderDetailId, Integer orderId, Integer itemId, String itemName, Integer quantity, Double price, Double subtotal, String kitchenNote, String kitchenStatus, Integer preparedBy, String preparedByName, LocalDateTime servedAt) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.kitchenNote = kitchenNote;
        this.kitchenStatus = kitchenStatus;
        this.preparedBy = preparedBy;
        this.preparedByName = preparedByName;
        this.servedAt = servedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer orderDetailId;
        private Integer orderId;
        private Integer itemId;
        private String itemName;
        private Integer quantity;
        private Double price;
        private Double subtotal;
        private String kitchenNote;
        private String kitchenStatus;
        private Integer preparedBy;
        private String preparedByName;
        private LocalDateTime servedAt;

        public Builder orderDetailId(Integer orderDetailId) {
            this.orderDetailId = orderDetailId;
            return this;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder subtotal(Double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder kitchenNote(String kitchenNote) {
            this.kitchenNote = kitchenNote;
            return this;
        }

        public Builder kitchenStatus(String kitchenStatus) {
            this.kitchenStatus = kitchenStatus;
            return this;
        }

        public Builder preparedBy(Integer preparedBy) {
            this.preparedBy = preparedBy;
            return this;
        }

        public Builder preparedByName(String preparedByName) {
            this.preparedByName = preparedByName;
            return this;
        }

        public Builder servedAt(LocalDateTime servedAt) {
            this.servedAt = servedAt;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(orderDetailId, orderId, itemId, itemName, quantity, price, subtotal, kitchenNote, kitchenStatus, preparedBy, preparedByName, servedAt);
        }
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getKitchenNote() {
        return kitchenNote;
    }

    public void setKitchenNote(String kitchenNote) {
        this.kitchenNote = kitchenNote;
    }

    public String getKitchenStatus() {
        return kitchenStatus;
    }

    public void setKitchenStatus(String kitchenStatus) {
        this.kitchenStatus = kitchenStatus;
    }

    public Integer getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public String getPreparedByName() {
        return preparedByName;
    }

    public void setPreparedByName(String preparedByName) {
        this.preparedByName = preparedByName;
    }

    public LocalDateTime getServedAt() {
        return servedAt;
    }

    public void setServedAt(LocalDateTime servedAt) {
        this.servedAt = servedAt;
    }
}