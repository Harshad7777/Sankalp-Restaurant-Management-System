package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for pagination and filtering request parameters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    private String sortBy;

    @Builder.Default
    private String sortDirection = "DESC";

    private String search;
    private String status;
    private String role;
    private String category;
    private Integer categoryId;
    private Integer customerId;
    private Integer tableId;
    private String orderType;
    private Double minPrice;
    private Double maxPrice;
    private String paymentMethod;
    private String paymentStatus;
    private String startDate;
    private String endDate;

    public int getOffset() {
        return Math.max(page, 0) * Math.min(Math.max(size, 1), 100);
    }

    public int getLimit() {
        return Math.min(Math.max(size, 1), 100);
    }

    public String getSafeSortDirection() {
        return "ASC".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC";
    }
}
