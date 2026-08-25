package com.restaurant.dto;

public class KitchenStatsDTO {

    private int newCount;
    private int acceptedCount;
    private int preparingCount;
    private int readyCount;

    public KitchenStatsDTO() {
    }

    public KitchenStatsDTO(int newCount, int acceptedCount, int preparingCount, int readyCount) {
        this.newCount = newCount;
        this.acceptedCount = acceptedCount;
        this.preparingCount = preparingCount;
        this.readyCount = readyCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int newCount;
        private int acceptedCount;
        private int preparingCount;
        private int readyCount;

        public Builder newCount(int newCount) {
            this.newCount = newCount;
            return this;
        }

        public Builder acceptedCount(int acceptedCount) {
            this.acceptedCount = acceptedCount;
            return this;
        }

        public Builder preparingCount(int preparingCount) {
            this.preparingCount = preparingCount;
            return this;
        }

        public Builder readyCount(int readyCount) {
            this.readyCount = readyCount;
            return this;
        }

        public KitchenStatsDTO build() {
            return new KitchenStatsDTO(newCount, acceptedCount, preparingCount, readyCount);
        }
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public int getPreparingCount() {
        return preparingCount;
    }

    public void setPreparingCount(int preparingCount) {
        this.preparingCount = preparingCount;
    }

    public int getReadyCount() {
        return readyCount;
    }

    public void setReadyCount(int readyCount) {
        this.readyCount = readyCount;
    }
}