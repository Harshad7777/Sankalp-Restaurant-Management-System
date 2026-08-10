package com.restaurant.dto;

public class BillDTO {

    private int orderId;
    private String paymentMethod;
    private double discount;


    public BillDTO() {
    }


    public BillDTO(
            int orderId,
            String paymentMethod,
            double discount) {

        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.discount = discount;
    }


    public int getOrderId() {

        return orderId;
    }


    public void setOrderId(int orderId) {

        this.orderId = orderId;
    }


    public String getPaymentMethod() {

        return paymentMethod;
    }


    public void setPaymentMethod(String paymentMethod) {

        this.paymentMethod = paymentMethod;
    }


    public double getDiscount() {

        return discount;
    }


    public void setDiscount(double discount) {

        this.discount = discount;
    }

}