package com.restaurant.dto;

public class KitchenStatsDTO {

    private int newCount;
    private int acceptedCount;
    private int preparingCount;
    private int readyCount;


    public KitchenStatsDTO() {
    }


    public KitchenStatsDTO(
            int newCount,
            int acceptedCount,
            int preparingCount,
            int readyCount) {

        this.newCount =
                newCount;

        this.acceptedCount =
                acceptedCount;

        this.preparingCount =
                preparingCount;

        this.readyCount =
                readyCount;
    }


    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(
            int newCount) {

        this.newCount =
                newCount;
    }


    public int getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(
            int acceptedCount) {

        this.acceptedCount =
                acceptedCount;
    }


    public int getPreparingCount() {
        return preparingCount;
    }

    public void setPreparingCount(
            int preparingCount) {

        this.preparingCount =
                preparingCount;
    }


    public int getReadyCount() {
        return readyCount;
    }

    public void setReadyCount(
            int readyCount) {

        this.readyCount =
                readyCount;
    }
}