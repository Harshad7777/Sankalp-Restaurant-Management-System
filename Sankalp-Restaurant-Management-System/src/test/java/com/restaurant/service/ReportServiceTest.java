package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository repository;

    @InjectMocks
    private ReportService reportService;

    @Test
    @DisplayName("Should get total sales")
    void testGetTotalSales() {
        when(repository.getTotalSales()).thenReturn(52000.0);
        assertEquals(52000.0, reportService.getTotalSales());
    }

    @Test
    @DisplayName("Should get date wise sales")
    void testGetDateWiseSales() {
        SalesReportDTO dto = new SalesReportDTO();
        dto.setReportDate(LocalDate.of(2026, 3, 1));
        dto.setTotalSales(6000.0);

        when(repository.getDateWiseSales()).thenReturn(List.of(dto));

        List<SalesReportDTO> list = reportService.getDateWiseSales();
        assertEquals(1, list.size());
        assertEquals(LocalDate.of(2026, 3, 1), list.get(0).getReportDate());
    }

    @Test
    @DisplayName("Should get sales by date range")
    void testGetSalesByDateRange_Success() {
        LocalDate start = LocalDate.now().minusDays(7);
        LocalDate end = LocalDate.now();

        SalesReportDTO dto = new SalesReportDTO();
        dto.setReportDate(start);
        dto.setTotalSales(12000.0);

        when(repository.getSalesByDateRange(start, end)).thenReturn(List.of(dto));

        List<SalesReportDTO> list = reportService.getSalesByDateRange(start, end);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when start date is after end date")
    void testGetSalesByDateRange_InvalidDates() {
        LocalDate start = LocalDate.now().plusDays(5);
        LocalDate end = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> reportService.getSalesByDateRange(start, end));
    }

    @Test
    @DisplayName("Should get top menu items")
    void testGetTopMenu() {
        TopMenuDTO dto = new TopMenuDTO();
        dto.setItemName("Butter Naan");
        dto.setTotalQuantity(100);

        when(repository.getTopMenu()).thenReturn(List.of(dto));

        List<TopMenuDTO> list = reportService.getTopMenu();
        assertEquals(1, list.size());
        assertEquals("Butter Naan", list.get(0).getItemName());
    }
}
