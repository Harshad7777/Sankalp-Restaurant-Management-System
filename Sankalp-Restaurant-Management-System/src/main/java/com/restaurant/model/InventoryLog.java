package com.restaurant.model;

import java.time.LocalDateTime;

public class InventoryLog {

    private int logId;
    private int inventoryId;
    private String itemName;
    private String actionType; // RESTOCK, CONSUME, WASTAGE, ADJUSTMENT
    private double quantity;
    private double previousQuantity;
    private double newQuantity;
    private String reason;
    private String performedBy;
    private LocalDateTime createdAt;

    public InventoryLog() {
    }

    public InventoryLog(int logId, int inventoryId, String itemName, String actionType, double quantity, double previousQuantity, double newQuantity, String reason, String performedBy, LocalDateTime createdAt) {
        this.logId = logId;
        this.inventoryId = inventoryId;
        this.itemName = itemName;
        this.actionType = actionType;
        this.quantity = quantity;
        this.previousQuantity = previousQuantity;
        this.newQuantity = newQuantity;
        this.reason = reason;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int logId;
        private int inventoryId;
        private String itemName;
        private String actionType;
        private double quantity;
        private double previousQuantity;
        private double newQuantity;
        private String reason;
        private String performedBy;
        private LocalDateTime createdAt;

        public Builder logId(int logId) {
            this.logId = logId;
            return this;
        }

        public Builder inventoryId(int inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder actionType(String actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder quantity(double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder previousQuantity(double previousQuantity) {
            this.previousQuantity = previousQuantity;
            return this;
        }

        public Builder newQuantity(double newQuantity) {
            this.newQuantity = newQuantity;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder performedBy(String performedBy) {
            this.performedBy = performedBy;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public InventoryLog build() {
            return new InventoryLog(logId, inventoryId, itemName, actionType, quantity, previousQuantity, newQuantity, reason, performedBy, createdAt);
        }
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPreviousQuantity() {
        return previousQuantity;
    }

    public void setPreviousQuantity(double previousQuantity) {
        this.previousQuantity = previousQuantity;
    }

    public double getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(double newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
