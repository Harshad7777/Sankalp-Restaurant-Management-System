package com.restaurant.dto;

public class BillDTO {

    private int orderId;

    private double subtotal;
    private double gst;
    private double discount;
    private double grandTotal;

    private String paymentMethod;
    private String paymentStatus;

    public BillDTO() {
    }

    // =====================================================
    // ORDER ID
    // =====================================================

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // =====================================================
    // SUBTOTAL
    // =====================================================

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // =====================================================
    // GST
    // =====================================================

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    // =====================================================
    // DISCOUNT
    // =====================================================

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    // =====================================================
    // GRAND TOTAL
    // =====================================================

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    // =====================================================
    // PAYMENT METHOD
    // =====================================================

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // =====================================================
    // PAYMENT STATUS
    // =====================================================

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}