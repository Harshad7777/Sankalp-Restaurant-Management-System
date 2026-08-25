package com.restaurant.dto;

import java.io.Serializable;
import java.util.List;

public class DashboardDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalCategories;
    private int totalCustomers;
    private int totalMenuItems;
    private int totalOrders;

    private int todayOrders;

    private double todaySales;
    private double totalRevenue;

    private List<RecentOrderDTO> recentOrders;
    private List<TopSellingItemDTO> topSellingItems;
    private List<RevenueOverviewDTO> revenueOverview;

    public DashboardDTO() {
    }

    public DashboardDTO(int totalCategories, int totalCustomers, int totalMenuItems, int totalOrders, int todayOrders, double todaySales, double totalRevenue, List<RecentOrderDTO> recentOrders, List<TopSellingItemDTO> topSellingItems, List<RevenueOverviewDTO> revenueOverview) {
        this.totalCategories = totalCategories;
        this.totalCustomers = totalCustomers;
        this.totalMenuItems = totalMenuItems;
        this.totalOrders = totalOrders;
        this.todayOrders = todayOrders;
        this.todaySales = todaySales;
        this.totalRevenue = totalRevenue;
        this.recentOrders = recentOrders;
        this.topSellingItems = topSellingItems;
        this.revenueOverview = revenueOverview;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int totalCategories;
        private int totalCustomers;
        private int totalMenuItems;
        private int totalOrders;
        private int todayOrders;
        private double todaySales;
        private double totalRevenue;
        private List<RecentOrderDTO> recentOrders;
        private List<TopSellingItemDTO> topSellingItems;
        private List<RevenueOverviewDTO> revenueOverview;

        public Builder totalCategories(int totalCategories) {
            this.totalCategories = totalCategories;
            return this;
        }

        public Builder totalCustomers(int totalCustomers) {
            this.totalCustomers = totalCustomers;
            return this;
        }

        public Builder totalMenuItems(int totalMenuItems) {
            this.totalMenuItems = totalMenuItems;
            return this;
        }

        public Builder totalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
            return this;
        }

        public Builder todayOrders(int todayOrders) {
            this.todayOrders = todayOrders;
            return this;
        }

        public Builder todaySales(double todaySales) {
            this.todaySales = todaySales;
            return this;
        }

        public Builder totalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
            return this;
        }

        public Builder recentOrders(List<RecentOrderDTO> recentOrders) {
            this.recentOrders = recentOrders;
            return this;
        }

        public Builder topSellingItems(List<TopSellingItemDTO> topSellingItems) {
            this.topSellingItems = topSellingItems;
            return this;
        }

        public Builder revenueOverview(List<RevenueOverviewDTO> revenueOverview) {
            this.revenueOverview = revenueOverview;
            return this;
        }

        public DashboardDTO build() {
            return new DashboardDTO(totalCategories, totalCustomers, totalMenuItems, totalOrders, todayOrders, todaySales, totalRevenue, recentOrders, topSellingItems, revenueOverview);
        }
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public int getTotalMenuItems() {
        return totalMenuItems;
    }

    public void setTotalMenuItems(int totalMenuItems) {
        this.totalMenuItems = totalMenuItems;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTodayOrders() {
        return todayOrders;
    }

    public void setTodayOrders(int todayOrders) {
        this.todayOrders = todayOrders;
    }

    public double getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(double todaySales) {
        this.todaySales = todaySales;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<RecentOrderDTO> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<RecentOrderDTO> recentOrders) {
        this.recentOrders = recentOrders;
    }

    public List<TopSellingItemDTO> getTopSellingItems() {
        return topSellingItems;
    }

    public void setTopSellingItems(List<TopSellingItemDTO> topSellingItems) {
        this.topSellingItems = topSellingItems;
    }

    public List<RevenueOverviewDTO> getRevenueOverview() {
        return revenueOverview;
    }

    public void setRevenueOverview(List<RevenueOverviewDTO> revenueOverview) {
        this.revenueOverview = revenueOverview;
    }
}