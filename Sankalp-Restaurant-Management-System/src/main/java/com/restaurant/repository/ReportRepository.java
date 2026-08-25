package com.restaurant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.repository.query.ReportQueries;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double getTotalSales() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_SALES,
                Double.class);
    }

    public List<SalesReportDTO> getDateWiseSales() {
        return jdbcTemplate.query(
                ReportQueries.GET_DATE_WISE_SALES,
                new BeanPropertyRowMapper<>(SalesReportDTO.class));
    }

    public List<SalesReportDTO> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(
                ReportQueries.GET_SALES_BY_DATE_RANGE,
                new BeanPropertyRowMapper<>(SalesReportDTO.class),
                startDate,
                endDate);
    }

    public int getTotalOrders() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_ORDERS,
                Integer.class);
    }

    public int getTotalCustomers() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_CUSTOMERS,
                Integer.class);
    }

    public List<TopMenuDTO> getTopMenu() {
        return jdbcTemplate.query(
                ReportQueries.GET_TOP_MENU,
                new BeanPropertyRowMapper<>(TopMenuDTO.class));
    }
}