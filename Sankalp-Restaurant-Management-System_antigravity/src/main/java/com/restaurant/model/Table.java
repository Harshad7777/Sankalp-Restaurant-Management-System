package com.restaurant.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Table {

    private Integer tableId;

    @NotBlank(message = "Table number is required")
    @Size(min = 1, max = 50, message = "Table number must be between 1 and 50 characters")
    private String tableNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1 person")
    private Integer capacity;

    @Pattern(regexp = "^(AVAILABLE|OCCUPIED|RESERVED|CLEANING|BILLED|MAINTENANCE|INACTIVE)$", message = "Table status must be AVAILABLE, OCCUPIED, RESERVED, CLEANING, BILLED, MAINTENANCE or INACTIVE")
    private String tableStatus;

    public Table() {
    }

    public Table(Integer tableId, String tableNumber, Integer capacity, String tableStatus) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.tableStatus = tableStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer tableId;
        private String tableNumber;
        private Integer capacity;
        private String tableStatus;

        public Builder tableId(Integer tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder tableNumber(String tableNumber) {
            this.tableNumber = tableNumber;
            return this;
        }

        public Builder capacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder tableStatus(String tableStatus) {
            this.tableStatus = tableStatus;
            return this;
        }

        public Table build() {
            return new Table(tableId, tableNumber, capacity, tableStatus);
        }
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getStatus() {
        return tableStatus;
    }

    public void setStatus(String status) {
        this.tableStatus = status;
    }
}