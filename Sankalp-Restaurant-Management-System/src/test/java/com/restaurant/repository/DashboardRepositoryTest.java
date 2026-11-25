package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class DashboardRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DashboardRepository dashboardRepository;

    @Test
    @DisplayName("Should get total customers count")
    void testGetTotalCustomers() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(45);
        assertEquals(45, dashboardRepository.getTotalCustomers());
    }

    @Test
    @DisplayName("Should get total menu items count")
    void testGetTotalMenuItems() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(28);
        assertEquals(28, dashboardRepository.getTotalMenuItems());
    }

    @Test
    @DisplayName("Should get total orders count")
    void testGetTotalOrders() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(150);
        assertEquals(150, dashboardRepository.getTotalOrders());
    }

    @Test
    @DisplayName("Should get today orders count")
    void testGetTodayOrders() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(14);
        assertEquals(14, dashboardRepository.getTodayOrders());
    }

    @Test
    @DisplayName("Should get total revenue")
    void testGetTotalRevenue() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Double.class), any())).thenReturn(85000.0);
        assertEquals(85000.0, dashboardRepository.getTotalRevenue());
    }
}
