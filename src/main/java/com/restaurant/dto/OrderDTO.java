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

    public OrderDTO() {}

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getTableId() { return tableId; }
    public void setTableId(Integer tableId) { this.tableId = tableId; }

    public Integer getStaffId() { return staffId; }
    public void setStaffId(Integer staffId) { this.staffId = staffId; }

    public List<OrderDetails> getItems() { return items; }
    public void setItems(List<OrderDetails> items) { this.items = items; }
}