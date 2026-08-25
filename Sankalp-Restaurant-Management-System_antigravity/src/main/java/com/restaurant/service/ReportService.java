package com.restaurant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    // =====================================================
    // TOTAL SALES
    // =====================================================

    public double getTotalSales() {

        return repository.getTotalSales();
    }

    // =====================================================
    // DATE WISE SALES
    // =====================================================

    public List<SalesReportDTO> getDateWiseSales() {

        return repository.getDateWiseSales();
    }

    // =====================================================
    // DATE-RANGE SALES
    // =====================================================

    private static final int MAX_RANGE_DAYS = 366;

    public List<SalesReportDTO> getSalesByDateRange(
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "startDate and endDate are required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "startDate must not be after endDate.");
        }

        if (java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate) > MAX_RANGE_DAYS) {

            throw new IllegalArgumentException(
                    "Date range cannot exceed "
                    + MAX_RANGE_DAYS + " days.");
        }

        return repository.getSalesByDateRange(startDate, endDate);
    }

    // =====================================================
    // TOTAL ORDERS
    // =====================================================

    public int getTotalOrders() {

        return repository.getTotalOrders();
    }

    // =====================================================
    // TOTAL CUSTOMERS
    // =====================================================

    public int getTotalCustomers() {

        return repository.getTotalCustomers();
    }

    // =====================================================
    // TOP MENU
    // =====================================================

    public List<TopMenuDTO> getTopMenu() {

        return repository.getTopMenu();
    }
}