package com.restaurant.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Menu {

    private Integer itemId;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    private String categoryName;

    @NotBlank(message = "Item Name is required")
    @Size(min = 2, max = 100, message = "Item Name must be between 2 and 100 characters")
    private String itemName;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double price;

    private String image;

    @Pattern(regexp = "^(AVAILABLE|UNAVAILABLE)$", message = "Status must be AVAILABLE or UNAVAILABLE")
    private String status;

    public Menu() {
    }

    public Menu(Integer itemId, Integer categoryId, String categoryName, String itemName, String description, Double price, String image, String status) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer itemId;
        private Integer categoryId;
        private String categoryName;
        private String itemName;
        private String description;
        private Double price;
        private String image;
        private String status;

        public Builder itemId(Integer itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder categoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Menu build() {
            return new Menu(itemId, categoryId, categoryName, itemName, description, price, image, status);
        }
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}