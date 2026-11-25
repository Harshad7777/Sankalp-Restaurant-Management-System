package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.dto.DashboardDTO;
import com.restaurant.repository.DashboardRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DashboardRepository repository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    @DisplayName("Should assemble complete dashboard summary")
    void testGetDashboardSummary() {
        when(repository.getTotalCategories()).thenReturn(5);
        when(repository.getTotalCustomers()).thenReturn(20);
        when(repository.getTotalMenuItems()).thenReturn(35);
        when(repository.getTotalOrders()).thenReturn(100);
        when(repository.getTodayOrders()).thenReturn(8);
        when(repository.getTodaySales()).thenReturn(4500.0);
        when(repository.getTotalRevenue()).thenReturn(95000.0);

        DashboardDTO dto = dashboardService.getDashboardSummary();

        assertNotNull(dto);
        assertEquals(5, dto.getTotalCategories());
        assertEquals(20, dto.getTotalCustomers());
        assertEquals(35, dto.getTotalMenuItems());
        assertEquals(100, dto.getTotalOrders());
        assertEquals(8, dto.getTodayOrders());
        assertEquals(4500.0, dto.getTodaySales());
        assertEquals(95000.0, dto.getTotalRevenue());
    }

    @Test
    @DisplayName("Should get counts")
    void testGetCounts() {
        when(repository.getTotalCategories()).thenReturn(5);
        when(repository.getTotalCustomers()).thenReturn(20);
        when(repository.getTotalMenuItems()).thenReturn(35);
        when(repository.getTotalOrders()).thenReturn(100);

        DashboardDTO dto = dashboardService.getCounts();

        assertNotNull(dto);
        assertEquals(5, dto.getTotalCategories());
        assertEquals(20, dto.getTotalCustomers());
    }

    @Test
    @DisplayName("Should get today sales")
    void testGetTodaySales() {
        when(repository.getTodaySales()).thenReturn(3200.0);
        assertEquals(3200.0, dashboardService.getTodaySales());
    }

    @Test
    @DisplayName("Should get today orders")
    void testGetTodayOrders() {
        when(repository.getTodayOrders()).thenReturn(12);
        assertEquals(12, dashboardService.getTodayOrders());
    }

    @Test
    @DisplayName("Should get total revenue")
    void testGetRevenue() {
        when(repository.getTotalRevenue()).thenReturn(50000.0);
        assertEquals(50000.0, dashboardService.getRevenue());
    }
}
