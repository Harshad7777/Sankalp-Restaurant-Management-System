package com.restaurant.model;

import java.time.LocalDateTime;

public class Inventory {

    private int inventoryId;
    private String itemName;
    private String category;
    private double quantity;
    private String unit;
    private double minThreshold;
    private double costPerUnit;
    private String supplierName;
    private String supplierContact;
    private LocalDateTime lastRestocked;
    private String status; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Inventory() {
    }

    public Inventory(int inventoryId, String itemName, String category, double quantity, String unit, double minThreshold, double costPerUnit, String supplierName, String supplierContact, LocalDateTime lastRestocked, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.inventoryId = inventoryId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.minThreshold = minThreshold;
        this.costPerUnit = costPerUnit;
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
        this.lastRestocked = lastRestocked;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int inventoryId;
        private String itemName;
        private String category;
        private double quantity;
        private String unit;
        private double minThreshold;
        private double costPerUnit;
        private String supplierName;
        private String supplierContact;
        private LocalDateTime lastRestocked;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder inventoryId(int inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder minThreshold(double minThreshold) {
            this.minThreshold = minThreshold;
            return this;
        }

        public Builder costPerUnit(double costPerUnit) {
            this.costPerUnit = costPerUnit;
            return this;
        }

        public Builder supplierName(String supplierName) {
            this.supplierName = supplierName;
            return this;
        }

        public Builder supplierContact(String supplierContact) {
            this.supplierContact = supplierContact;
            return this;
        }

        public Builder lastRestocked(LocalDateTime lastRestocked) {
            this.lastRestocked = lastRestocked;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Inventory build() {
            return new Inventory(inventoryId, itemName, category, quantity, unit, minThreshold, costPerUnit, supplierName, supplierContact, lastRestocked, status, createdAt, updatedAt);
        }
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public LocalDateTime getLastRestocked() {
        return lastRestocked;
    }

    public void setLastRestocked(LocalDateTime lastRestocked) {
        this.lastRestocked = lastRestocked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getTotalValue() {
        return Math.round(this.quantity * this.costPerUnit * 100.0) / 100.0;
    }
}
