package com.restaurant.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.service.ReportService;

@RestController
@RequestMapping({"/report", "/reports"})
@CrossOrigin("*")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    // =====================================================
    // TOTAL SALES
    // GET /api/report/sales
    // =====================================================

    @GetMapping("/sales")
    public ResponseEntity<Double> totalSales() {

        return ResponseEntity.ok(
                service.getTotalSales()
        );
    }

    // =====================================================
    // DATE WISE SALES
    // GET /api/report/sales/date
    // =====================================================

    @GetMapping("/sales/date")
    public ResponseEntity<List<SalesReportDTO>> dateWiseSales() {

        return ResponseEntity.ok(
                service.getDateWiseSales()
        );
    }

    // =====================================================
    // DATE-RANGE SALES
    // GET /api/report/sales/range?startDate=2026-01-01&endDate=2026-01-31
    // =====================================================

    @GetMapping("/sales/range")
    public ResponseEntity<List<SalesReportDTO>> salesByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                service.getSalesByDateRange(startDate, endDate)
        );
    }

    // =====================================================
    // TOTAL ORDERS
    // GET /api/report/orders
    // =====================================================

    @GetMapping("/orders")
    public ResponseEntity<Integer> totalOrders() {

        return ResponseEntity.ok(
                service.getTotalOrders()
        );
    }

    // =====================================================
    // TOTAL CUSTOMERS
    // GET /api/report/customer
    // =====================================================

    @GetMapping("/customer")
    public ResponseEntity<Integer> totalCustomers() {

        return ResponseEntity.ok(
                service.getTotalCustomers()
        );
    }

    // =====================================================
    // TOP MENU
    // GET /api/report/top-menu
    // =====================================================

    @GetMapping("/top-menu")
    public ResponseEntity<List<TopMenuDTO>> topMenu() {

        return ResponseEntity.ok(
                service.getTopMenu()
        );
    }
}