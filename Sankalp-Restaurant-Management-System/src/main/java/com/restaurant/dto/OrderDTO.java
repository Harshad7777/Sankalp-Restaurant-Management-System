package com.restaurant.dto;

import java.util.List;
import com.restaurant.model.OrderDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {

    @NotNull(message = "Customer id is required")
    @Min(value = 1, message = "Customer id must be greater than 0")
    private Integer customerId;

    @NotNull(message = "Table id is required")
    @Min(value = 1, message = "Table id must be greater than 0")
    private Integer tableId;

    @NotNull(message = "Staff id is required")
    @Min(value = 1, message = "Staff id must be greater than 0")
    private Integer staffId;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderDetails> items;

    public OrderDTO() {
    }

    public OrderDTO(Integer customerId, Integer tableId, Integer staffId, List<OrderDetails> items) {
        this.customerId = customerId;
        this.tableId = tableId;
        this.staffId = staffId;
        this.items = items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer customerId;
        private Integer tableId;
        private Integer staffId;
        private List<OrderDetails> items;

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder tableId(Integer tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder staffId(Integer staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder items(List<OrderDetails> items) {
            this.items = items;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(customerId, tableId, staffId, items);
        }
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public List<OrderDetails> getItems() {
        return items;
    }

    public void setItems(List<OrderDetails> items) {
        this.items = items;
    }
}