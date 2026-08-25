package com.restaurant.service;

import org.springframework.stereotype.Service;

import com.restaurant.dto.DashboardDTO;
import com.restaurant.repository.DashboardRepository;

@Service
public class DashboardService {

    private final DashboardRepository repository;


    public DashboardService(
            DashboardRepository repository) {

        this.repository = repository;
    }


    // =====================================================
    // COMPLETE DASHBOARD SUMMARY
    // =====================================================

    public DashboardDTO getDashboardSummary() {

        DashboardDTO dto =
                new DashboardDTO();


        // ---------------------------------------------
        // BASIC COUNTS
        // ---------------------------------------------

        dto.setTotalCategories(
                repository.getTotalCategories()
        );


        dto.setTotalCustomers(
                repository.getTotalCustomers()
        );


        dto.setTotalMenuItems(
                repository.getTotalMenuItems()
        );


        dto.setTotalOrders(
                repository.getTotalOrders()
        );


        // ---------------------------------------------
        // TODAY
        // ---------------------------------------------

        dto.setTodayOrders(
                repository.getTodayOrders()
        );


        dto.setTodaySales(
                repository.getTodaySales()
        );


        // ---------------------------------------------
        // REVENUE
        // ---------------------------------------------

        dto.setTotalRevenue(
                repository.getTotalRevenue()
        );


        // ---------------------------------------------
        // RECENT ORDERS
        // ---------------------------------------------

        dto.setRecentOrders(
                repository.getRecentOrders()
        );


        // ---------------------------------------------
        // TOP SELLING ITEMS
        // ---------------------------------------------

        dto.setTopSellingItems(
                repository.getTopSellingItems()
        );


        // ---------------------------------------------
        // REVENUE OVERVIEW (LAST 7 DAYS)
        // ---------------------------------------------

        dto.setRevenueOverview(
                repository.getRevenueOverview()
        );


        return dto;
    }


    // =====================================================
    // TOTAL COUNTS
    // =====================================================

    public DashboardDTO getCounts() {

        DashboardDTO dto =
                new DashboardDTO();


        dto.setTotalCategories(
                repository.getTotalCategories()
        );


        dto.setTotalCustomers(
                repository.getTotalCustomers()
        );


        dto.setTotalMenuItems(
                repository.getTotalMenuItems()
        );


        dto.setTotalOrders(
                repository.getTotalOrders()
        );


        return dto;
    }


    // =====================================================
    // TODAY SALES
    // =====================================================

    public double getTodaySales() {

        return repository.getTodaySales();
    }


    // =====================================================
    // TODAY ORDERS
    // =====================================================

    public int getTodayOrders() {

        return repository.getTodayOrders();
    }


    // =====================================================
    // TOTAL REVENUE
    // =====================================================

    public double getRevenue() {

        return repository.getTotalRevenue();
    }
}