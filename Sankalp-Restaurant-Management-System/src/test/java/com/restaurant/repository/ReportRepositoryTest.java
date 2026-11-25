package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;

@ExtendWith(MockitoExtension.class)
class ReportRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ReportRepository reportRepository;

    @Test
    @DisplayName("Should get total sales")
    void testGetTotalSales() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Double.class), any())).thenReturn(75000.0);
        assertEquals(75000.0, reportRepository.getTotalSales());
    }

    @Test
    @DisplayName("Should get date wise sales")
    void testGetDateWiseSales() {
        SalesReportDTO dto = new SalesReportDTO();
        dto.setReportDate(java.time.LocalDate.of(2026, 2, 20));

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(Collections.singletonList(dto));

        List<SalesReportDTO> list = reportRepository.getDateWiseSales();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should get total orders")
    void testGetTotalOrders() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(95);
        assertEquals(95, reportRepository.getTotalOrders());
    }

    @Test
    @DisplayName("Should get top menu")
    void testGetTopMenu() {
        TopMenuDTO dto = new TopMenuDTO();
        dto.setItemName("Paneer Tikka");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(Collections.singletonList(dto));

        List<TopMenuDTO> list = reportRepository.getTopMenu();
        assertNotNull(list);
        assertEquals(1, list.size());
    }
}
