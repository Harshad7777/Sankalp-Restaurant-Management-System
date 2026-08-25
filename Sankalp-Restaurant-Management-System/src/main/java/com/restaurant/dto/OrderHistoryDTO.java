package com.restaurant.dto;

import java.time.LocalDateTime;

public class OrderHistoryDTO {

    private Integer orderId;
    private Integer customerId;
    private String customerName;
    private Double totalAmount;
    private String orderStatus;
    private LocalDateTime orderDate;

    public OrderHistoryDTO() {
    }

    public OrderHistoryDTO(Integer orderId, Integer customerId, String customerName, Double totalAmount, String orderStatus, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer orderId;
        private Integer customerId;
        private String customerName;
        private Double totalAmount;
        private String orderStatus;
        private LocalDateTime orderDate;

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder orderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderHistoryDTO build() {
            return new OrderHistoryDTO(orderId, customerId, customerName, totalAmount, orderStatus, orderDate);
        }
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}