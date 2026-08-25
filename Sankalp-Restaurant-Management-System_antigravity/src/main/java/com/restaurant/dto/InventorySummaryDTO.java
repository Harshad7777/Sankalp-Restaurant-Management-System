package com.restaurant.dto;

public class InventorySummaryDTO {

    private long totalItems;
    private long inStockItems;
    private long lowStockItems;
    private long outOfStockItems;
    private double totalInventoryValue;

    public InventorySummaryDTO() {
    }

    public InventorySummaryDTO(long totalItems, long inStockItems, long lowStockItems, long outOfStockItems, double totalInventoryValue) {
        this.totalItems = totalItems;
        this.inStockItems = inStockItems;
        this.lowStockItems = lowStockItems;
        this.outOfStockItems = outOfStockItems;
        this.totalInventoryValue = totalInventoryValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long totalItems;
        private long inStockItems;
        private long lowStockItems;
        private long outOfStockItems;
        private double totalInventoryValue;

        public Builder totalItems(long totalItems) {
            this.totalItems = totalItems;
            return this;
        }

        public Builder inStockItems(long inStockItems) {
            this.inStockItems = inStockItems;
            return this;
        }

        public Builder lowStockItems(long lowStockItems) {
            this.lowStockItems = lowStockItems;
            return this;
        }

        public Builder outOfStockItems(long outOfStockItems) {
            this.outOfStockItems = outOfStockItems;
            return this;
        }

        public Builder totalInventoryValue(double totalInventoryValue) {
            this.totalInventoryValue = totalInventoryValue;
            return this;
        }

        public InventorySummaryDTO build() {
            return new InventorySummaryDTO(totalItems, inStockItems, lowStockItems, outOfStockItems, totalInventoryValue);
        }
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getInStockItems() {
        return inStockItems;
    }

    public void setInStockItems(long inStockItems) {
        this.inStockItems = inStockItems;
    }

    public long getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(long lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public long getOutOfStockItems() {
        return outOfStockItems;
    }

    public void setOutOfStockItems(long outOfStockItems) {
        this.outOfStockItems = outOfStockItems;
    }

    public double getTotalInventoryValue() {
        return totalInventoryValue;
    }

    public void setTotalInventoryValue(double totalInventoryValue) {
        this.totalInventoryValue = totalInventoryValue;
    }
}
