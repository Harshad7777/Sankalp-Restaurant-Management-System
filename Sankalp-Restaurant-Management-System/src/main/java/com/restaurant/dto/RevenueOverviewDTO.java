package com.restaurant.dto;

import java.io.Serializable;

public class RevenueOverviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;
    private String date;
    private double amount;

    public RevenueOverviewDTO() {
    }

    public RevenueOverviewDTO(String day, String date, double amount) {
        this.day = day;
        this.date = date;
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String day;
        private String date;
        private double amount;

        public Builder day(String day) {
            this.day = day;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public RevenueOverviewDTO build() {
            return new RevenueOverviewDTO(day, date, amount);
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
