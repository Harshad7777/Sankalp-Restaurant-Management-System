package com.restaurant.dto;

public class PageRequestDTO {

    private int page = 0;
    private int size = 10;
    private String sortBy;
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

    public PageRequestDTO() {
    }

    public PageRequestDTO(int page, int size, String sortBy, String sortDirection, String search, String status, String role, String category, Integer categoryId, Integer customerId, Integer tableId, String orderType, Double minPrice, Double maxPrice, String paymentMethod, String paymentStatus, String startDate, String endDate) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.search = search;
        this.status = status;
        this.role = role;
        this.category = category;
        this.categoryId = categoryId;
        this.customerId = customerId;
        this.tableId = tableId;
        this.orderType = orderType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int page = 0;
        private int size = 10;
        private String sortBy;
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

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public Builder sortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        public Builder search(String search) {
            this.search = search;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder categoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder tableId(Integer tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder orderType(String orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder minPrice(Double minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder maxPrice(Double maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public PageRequestDTO build() {
            return new PageRequestDTO(page, size, sortBy, sortDirection, search, status, role, category, categoryId, customerId, tableId, orderType, minPrice, maxPrice, paymentMethod, paymentStatus, startDate, endDate);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

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
