package com.restaurant.dto;

public class TopMenuDTO {

    private String itemName;
    private int totalQuantity;
    private double totalSales;

    public TopMenuDTO() {
    }

    public TopMenuDTO(String itemName, int totalQuantity, double totalSales) {
        this.itemName = itemName;
        this.totalQuantity = totalQuantity;
        this.totalSales = totalSales;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String itemName;
        private int totalQuantity;
        private double totalSales;

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder totalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
            return this;
        }

        public Builder totalSales(double totalSales) {
            this.totalSales = totalSales;
            return this;
        }

        public TopMenuDTO build() {
            return new TopMenuDTO(itemName, totalQuantity, totalSales);
        }
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}