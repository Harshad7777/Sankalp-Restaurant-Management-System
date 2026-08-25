package com.restaurant.dto;

import java.time.LocalDate;

public class SalesReportDTO {

    private LocalDate reportDate;
    private int totalOrders;
    private double totalSales;

    public SalesReportDTO() {
    }

    public SalesReportDTO(LocalDate reportDate, int totalOrders, double totalSales) {
        this.reportDate = reportDate;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDate reportDate;
        private int totalOrders;
        private double totalSales;

        public Builder reportDate(LocalDate reportDate) {
            this.reportDate = reportDate;
            return this;
        }

        public Builder totalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
            return this;
        }

        public Builder totalSales(double totalSales) {
            this.totalSales = totalSales;
            return this;
        }

        public SalesReportDTO build() {
            return new SalesReportDTO(reportDate, totalOrders, totalSales);
        }
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}