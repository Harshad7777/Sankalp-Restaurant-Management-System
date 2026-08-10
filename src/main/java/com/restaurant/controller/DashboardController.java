package com.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.DashboardDTO;
import com.restaurant.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    // =====================================
    // Dashboard Summary
    // GET : /api/dashboard/summary
    // =====================================
    @GetMapping("/summary")
    public ResponseEntity<DashboardDTO> getDashboardSummary() {

        DashboardDTO dashboard = service.getDashboardSummary();

        return ResponseEntity.ok(dashboard);
    }

    // =====================================
    // Total Counts
    // GET : /api/dashboard/count
    // =====================================
    @GetMapping("/count")
    public ResponseEntity<DashboardDTO> getCounts() {

        DashboardDTO dashboard = service.getCounts();

        return ResponseEntity.ok(dashboard);
    }

    // =====================================
    // Today's Sales
    // GET : /api/dashboard/today-sales
    // =====================================
    @GetMapping("/today-sales")
    public ResponseEntity<Double> getTodaySales() {

        return ResponseEntity.ok(service.getTodaySales());
    }

    // =====================================
    // Today's Orders
    // GET : /api/dashboard/today-orders
    // =====================================
    @GetMapping("/today-orders")
    public ResponseEntity<Integer> getTodayOrders() {

        return ResponseEntity.ok(service.getTodayOrders());
    }

    // =====================================
    // Total Revenue
    // GET : /api/dashboard/revenue
    // =====================================
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {

        return ResponseEntity.ok(service.getRevenue());
    }
}