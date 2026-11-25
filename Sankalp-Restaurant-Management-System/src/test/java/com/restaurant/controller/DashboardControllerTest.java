package com.restaurant.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.dto.DashboardDTO;
import com.restaurant.service.DashboardService;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private DashboardDTO testDashboard;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
        testDashboard = new DashboardDTO();
        testDashboard.setTotalCustomers(50);
        testDashboard.setTotalOrders(120);
        testDashboard.setTodayOrders(10);
        testDashboard.setTodaySales(5400.0);
        testDashboard.setTotalRevenue(125000.0);
    }

    @Test
    @DisplayName("Should get dashboard summary")
    void testGetDashboardSummary() throws Exception {
        when(dashboardService.getDashboardSummary()).thenReturn(testDashboard);

        mockMvc.perform(get("/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCustomers").value(50))
                .andExpect(jsonPath("$.todaySales").value(5400.0));
    }

    @Test
    @DisplayName("Should get counts")
    void testGetCounts() throws Exception {
        when(dashboardService.getCounts()).thenReturn(testDashboard);

        mockMvc.perform(get("/dashboard/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalOrders").value(120));
    }

    @Test
    @DisplayName("Should get today sales")
    void testGetTodaySales() throws Exception {
        when(dashboardService.getTodaySales()).thenReturn(5400.0);

        mockMvc.perform(get("/dashboard/today-sales"))
                .andExpect(status().isOk())
                .andExpect(content().string("5400.0"));
    }

    @Test
    @DisplayName("Should get today orders")
    void testGetTodayOrders() throws Exception {
        when(dashboardService.getTodayOrders()).thenReturn(10);

        mockMvc.perform(get("/dashboard/today-orders"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    @DisplayName("Should get total revenue")
    void testGetRevenue() throws Exception {
        when(dashboardService.getRevenue()).thenReturn(125000.0);

        mockMvc.perform(get("/dashboard/revenue"))
                .andExpect(status().isOk())
                .andExpect(content().string("125000.0"));
    }
}
