package com.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class BillDTO {

    @Positive(message = "Order ID must be positive")
    private int orderId;

    private double subtotal;
    private double gst;

    @Min(value = 0, message = "Discount cannot be negative")
    private double discount;

    private double grandTotal;

    @Pattern(regexp = "^(CASH|CARD|UPI)$", message = "Payment method must be CASH, CARD or UPI")
    private String paymentMethod;

    @Pattern(regexp = "^(PENDING|PAID)$", message = "Payment status must be PENDING or PAID")
    private String paymentStatus;

    public BillDTO() {
    }

    public BillDTO(int orderId, double subtotal, double gst, double discount, double grandTotal, String paymentMethod, String paymentStatus) {
        this.orderId = orderId;
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
        private int orderId;
        private double subtotal;
        private double gst;
        private double discount;
        private double grandTotal;
        private String paymentMethod;
        private String paymentStatus;

        public Builder orderId(int orderId) {
            this.orderId = orderId;
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

        public BillDTO build() {
            return new BillDTO(orderId, subtotal, gst, discount, grandTotal, paymentMethod, paymentStatus);
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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