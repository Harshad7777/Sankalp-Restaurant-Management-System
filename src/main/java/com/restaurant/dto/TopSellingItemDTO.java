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

    public TopSellingItemDTO(
            int itemId,
            String itemName,
            String image,
            int orders,
            int soldQuantity,
            double percentage) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.image = image;
        this.orders = orders;
        this.soldQuantity = soldQuantity;
        this.percentage = percentage;
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