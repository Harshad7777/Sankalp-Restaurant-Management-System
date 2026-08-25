package com.restaurant.dto;

public class RecentOrderDTO {

    private int orderId;
    private String customerName;
    private double totalAmount;
    private String status;
    private String orderDate;

    public RecentOrderDTO() {
    }

    public RecentOrderDTO(int orderId, String customerName, double totalAmount, String status, String orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int orderId;
        private String customerName;
        private double totalAmount;
        private String status;
        private String orderDate;

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder orderDate(String orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public RecentOrderDTO build() {
            return new RecentOrderDTO(orderId, customerName, totalAmount, status, orderDate);
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}