package com.restaurant.dto;

import java.util.List;

public class DashboardDTO {

    private int totalCategories;
    private int totalCustomers;
    private int totalMenuItems;
    private int totalOrders;

    private int todayOrders;

    private double todaySales;
    private double totalRevenue;

    private List<RecentOrderDTO> recentOrders;

    private List<TopSellingItemDTO> topSellingItems;


    // =========================================
    // CONSTRUCTOR
    // =========================================

    public DashboardDTO() {
    }


    // =========================================
    // TOTAL CATEGORIES
    // =========================================

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }


    // =========================================
    // TOTAL CUSTOMERS
    // =========================================

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }


    // =========================================
    // TOTAL MENU ITEMS
    // =========================================

    public int getTotalMenuItems() {
        return totalMenuItems;
    }

    public void setTotalMenuItems(int totalMenuItems) {
        this.totalMenuItems = totalMenuItems;
    }


    // =========================================
    // TOTAL ORDERS
    // =========================================

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }


    // =========================================
    // TODAY ORDERS
    // =========================================

    public int getTodayOrders() {
        return todayOrders;
    }

    public void setTodayOrders(int todayOrders) {
        this.todayOrders = todayOrders;
    }


    // =========================================
    // TODAY SALES
    // =========================================

    public double getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(double todaySales) {
        this.todaySales = todaySales;
    }


    // =========================================
    // TOTAL REVENUE
    // =========================================

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }


    // =========================================
    // RECENT ORDERS
    // =========================================

    public List<RecentOrderDTO> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(
            List<RecentOrderDTO> recentOrders) {

        this.recentOrders = recentOrders;
    }


    // =========================================
    // TOP SELLING ITEMS
    // =========================================

    public List<TopSellingItemDTO> getTopSellingItems() {
        return topSellingItems;
    }

    public void setTopSellingItems(
            List<TopSellingItemDTO> topSellingItems) {

        this.topSellingItems = topSellingItems;
    }
}