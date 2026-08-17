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

    // =====================================================
    // BILL ID
    // =====================================================

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    // =====================================================
    // BILL NUMBER
    // =====================================================

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
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
    // BILL DATE
    // =====================================================

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
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

    // =====================================================
    // TO STRING
    // =====================================================

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", billNo='" + billNo + '\'' +
                ", orderId=" + orderId +
                ", billDate=" + billDate +
                ", subtotal=" + subtotal +
                ", gst=" + gst +
                ", discount=" + discount +
                ", grandTotal=" + grandTotal +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}