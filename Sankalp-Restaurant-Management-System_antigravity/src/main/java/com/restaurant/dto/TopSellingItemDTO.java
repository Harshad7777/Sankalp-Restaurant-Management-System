package com.restaurant.dto;

public class TopSellingItemDTO {

    private int itemId;
    private String itemName;
    private String image;
    private int orders;
    private int soldQuantity;
    private double percentage;

    public TopSellingItemDTO() {
    }

    public TopSellingItemDTO(int itemId, String itemName, String image, int orders, int soldQuantity, double percentage) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.image = image;
        this.orders = orders;
        this.soldQuantity = soldQuantity;
        this.percentage = percentage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int itemId;
        private String itemName;
        private String image;
        private int orders;
        private int soldQuantity;
        private double percentage;

        public Builder itemId(int itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder orders(int orders) {
            this.orders = orders;
            return this;
        }

        public Builder soldQuantity(int soldQuantity) {
            this.soldQuantity = soldQuantity;
            return this;
        }

        public Builder percentage(double percentage) {
            this.percentage = percentage;
            return this;
        }

        public TopSellingItemDTO build() {
            return new TopSellingItemDTO(itemId, itemName, image, orders, soldQuantity, percentage);
        }
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}