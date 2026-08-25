package com.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InventoryRequestDTO {

    private Integer inventoryId;

    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name cannot exceed 100 characters")
    private String itemName;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    private String category;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantity cannot be negative")
    private Double quantity;

    @NotBlank(message = "Unit of measurement is required")
    @Size(max = 20, message = "Unit cannot exceed 20 characters")
    private String unit;

    @NotNull(message = "Minimum threshold is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum threshold cannot be negative")
    private Double minThreshold;

    @NotNull(message = "Cost per unit is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Cost per unit cannot be negative")
    private Double costPerUnit;

    @Size(max = 100, message = "Supplier name cannot exceed 100 characters")
    private String supplierName;

    @Size(max = 50, message = "Supplier contact cannot exceed 50 characters")
    private String supplierContact;

    private String status;

    public InventoryRequestDTO() {
    }

    public InventoryRequestDTO(Integer inventoryId, String itemName, String category, Double quantity, String unit, Double minThreshold, Double costPerUnit, String supplierName, String supplierContact, String status) {
        this.inventoryId = inventoryId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.minThreshold = minThreshold;
        this.costPerUnit = costPerUnit;
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer inventoryId;
        private String itemName;
        private String category;
        private Double quantity;
        private String unit;
        private Double minThreshold;
        private Double costPerUnit;
        private String supplierName;
        private String supplierContact;
        private String status;

        public Builder inventoryId(Integer inventoryId) {
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

        public Builder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder minThreshold(Double minThreshold) {
            this.minThreshold = minThreshold;
            return this;
        }

        public Builder costPerUnit(Double costPerUnit) {
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

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public InventoryRequestDTO build() {
            return new InventoryRequestDTO(inventoryId, itemName, category, quantity, unit, minThreshold, costPerUnit, supplierName, supplierContact, status);
        }
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(Double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public Double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(Double costPerUnit) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
