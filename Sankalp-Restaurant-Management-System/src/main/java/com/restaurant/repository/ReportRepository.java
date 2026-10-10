package com.restaurant.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.SalesReportDTO;
import com.restaurant.dto.TopMenuDTO;
import com.restaurant.repository.query.ReportQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double getTotalSales() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_SALES,
                Double.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<SalesReportDTO> getDateWiseSales() {
        return jdbcTemplate.query(
                ReportQueries.GET_DATE_WISE_SALES,
                new BeanPropertyRowMapper<>(SalesReportDTO.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<SalesReportDTO> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        return jdbcTemplate.query(
                ReportQueries.GET_SALES_BY_DATE_RANGE,
                new BeanPropertyRowMapper<>(SalesReportDTO.class),
                SecurityUtil.getCurrentRestaurantId(),
                startDate,
                endDate);
    }

    public int getTotalOrders() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_ORDERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int getTotalCustomers() {
        return jdbcTemplate.queryForObject(
                ReportQueries.GET_TOTAL_CUSTOMERS,
                Integer.class,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<TopMenuDTO> getTopMenu() {
        return jdbcTemplate.query(
                ReportQueries.GET_TOP_MENU,
                new BeanPropertyRowMapper<>(TopMenuDTO.class),
                SecurityUtil.getCurrentRestaurantId());
    }
}