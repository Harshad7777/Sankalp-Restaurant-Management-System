package com.restaurant.model;

import java.time.LocalDateTime;

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

    public Orders() {
    }

    public Orders(Integer orderId, String orderNo, Integer customerId, String customerName, Integer tableId, String tableNumber, Integer staffId, LocalDateTime orderDate, Double totalAmount, String orderStatus, String orderType, String remarks) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.customerId = customerId;
        this.customerName = customerName;
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.staffId = staffId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.remarks = remarks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
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

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public Builder tableId(Integer tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        public Builder staffId(Integer staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
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

        public Builder orderType(String orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        public Orders build() {
            return new Orders(orderId, orderNo, customerId, customerName, tableId, tableNumber, staffId, orderDate, totalAmount, orderStatus, orderType, remarks);
        }
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}