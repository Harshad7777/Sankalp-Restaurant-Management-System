package com.restaurant.model;

import java.time.LocalDateTime;

public class Orders {

    private Integer orderId;
    private Integer customerId;
    private String customerName;
    private Integer staffId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;

    public Orders() {
    }

    // ============================
    // Order ID
    // ============================

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    // ============================
    // Customer ID
    // ============================

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    // ============================
    // Customer Name
    // ============================

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // ============================
    // Staff ID
    // ============================

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    // ============================
    // Order Date
    // ============================

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    // ============================
    // Total Amount
    // ============================

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // ============================
    // Order Status
    // ============================

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}