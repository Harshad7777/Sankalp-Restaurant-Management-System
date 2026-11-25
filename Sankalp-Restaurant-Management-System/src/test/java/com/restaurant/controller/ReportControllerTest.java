package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.service.ReportService;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    @DisplayName("Should get total sales")
    void testTotalSales() throws Exception {
        when(reportService.getTotalSales()).thenReturn(45000.0);

        mockMvc.perform(get("/report/sales"))
                .andExpect(status().isOk())
                .andExpect(content().string("45000.0"));
    }

    @Test
    @DisplayName("Should get date wise sales")
    void testDateWiseSales() throws Exception {
        SalesReportDTO dto = new SalesReportDTO();
        dto.setReportDate(LocalDate.of(2026, 2, 15));
        dto.setTotalSales(3500.0);

        when(reportService.getDateWiseSales()).thenReturn(List.of(dto));

        mockMvc.perform(get("/report/sales/date"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].totalSales").value(3500.0));
    }

    @Test
    @DisplayName("Should get sales by date range")
    void testSalesByDateRange() throws Exception {
        SalesReportDTO dto = new SalesReportDTO();
        dto.setReportDate(LocalDate.of(2026, 2, 10));
        dto.setTotalSales(2000.0);

        when(reportService.getSalesByDateRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/report/sales/range")
                .param("startDate", "2026-02-01")
                .param("endDate", "2026-02-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Should get total orders count")
    void testTotalOrders() throws Exception {
        when(reportService.getTotalOrders()).thenReturn(85);

        mockMvc.perform(get("/report/orders"))
                .andExpect(status().isOk())
                .andExpect(content().string("85"));
    }

    @Test
    @DisplayName("Should get top menu items")
    void testTopMenu() throws Exception {
        TopMenuDTO dto = new TopMenuDTO();
        dto.setItemName("Paneer Butter Masala");
        dto.setTotalQuantity(42);

        when(reportService.getTopMenu()).thenReturn(List.of(dto));

        mockMvc.perform(get("/report/top-menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemName").value("Paneer Butter Masala"));
    }
}
