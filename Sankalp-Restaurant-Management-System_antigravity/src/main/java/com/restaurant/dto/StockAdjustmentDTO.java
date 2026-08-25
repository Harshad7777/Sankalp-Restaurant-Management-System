package com.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class StockAdjustmentDTO {

    @NotNull(message = "Adjustment quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
    private Double quantity;

    @NotBlank(message = "Action type is required")
    @Pattern(regexp = "RESTOCK|CONSUME|WASTAGE|ADJUSTMENT", message = "Action type must be RESTOCK, CONSUME, WASTAGE, or ADJUSTMENT")
    private String actionType;

    private String reason;

    private String performedBy;

    public StockAdjustmentDTO() {
    }

    public StockAdjustmentDTO(Double quantity, String actionType, String reason, String performedBy) {
        this.quantity = quantity;
        this.actionType = actionType;
        this.reason = reason;
        this.performedBy = performedBy;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double quantity;
        private String actionType;
        private String reason;
        private String performedBy;

        public Builder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder actionType(String actionType) {
            this.actionType = actionType;
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

        public StockAdjustmentDTO build() {
            return new StockAdjustmentDTO(quantity, actionType, reason, performedBy);
        }
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
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
}
