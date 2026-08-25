package com.restaurant.model;

import java.time.LocalDateTime;

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

    public Bill() {
    }

    public Bill(int billId, String billNo, int orderId, LocalDateTime billDate, double subtotal, double gst, double discount, double grandTotal, String paymentMethod, String paymentStatus) {
        this.billId = billId;
        this.billNo = billNo;
        this.orderId = orderId;
        this.billDate = billDate;
        this.subtotal = subtotal;
        this.gst = gst;
        this.discount = discount;
        this.grandTotal = grandTotal;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
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

        public Builder billId(int billId) {
            this.billId = billId;
            return this;
        }

        public Builder billNo(String billNo) {
            this.billNo = billNo;
            return this;
        }

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder billDate(LocalDateTime billDate) {
            this.billDate = billDate;
            return this;
        }

        public Builder subtotal(double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder gst(double gst) {
            this.gst = gst;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder grandTotal(double grandTotal) {
            this.grandTotal = grandTotal;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Bill build() {
            return new Bill(billId, billNo, orderId, billDate, subtotal, gst, discount, grandTotal, paymentMethod, paymentStatus);
        }
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}