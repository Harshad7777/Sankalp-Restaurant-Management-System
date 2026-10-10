package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Customer;
import com.restaurant.repository.query.CustomerQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Customer customer) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                CustomerQueries.INSERT_CUSTOMER,
                resId,
                customer.getCustomerName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getStatus());
    }

    public int update(Customer customer) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                CustomerQueries.UPDATE_CUSTOMER,
                customer.getCustomerName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getStatus(),
                customer.getCustomerId(),
                resId);
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                CustomerQueries.DELETE_CUSTOMER,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }

    public Customer findById(int id) {
        List<Customer> list = jdbcTemplate.query(
                CustomerQueries.FIND_BY_ID,
                new BeanPropertyRowMapper<>(Customer.class),
                id,
                SecurityUtil.getCurrentRestaurantId());
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(
                CustomerQueries.FIND_ALL,
                new BeanPropertyRowMapper<>(Customer.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public boolean existsByMobile(String mobile) {
        Integer count = jdbcTemplate.queryForObject(
                CustomerQueries.EXISTS_BY_MOBILE,
                Integer.class,
                mobile,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public List<Customer> search(String keyword) {
        String value = "%" + keyword + "%";
        return jdbcTemplate.query(
                CustomerQueries.SEARCH_CUSTOMER,
                new BeanPropertyRowMapper<>(Customer.class),
                SecurityUtil.getCurrentRestaurantId(),
                value,
                value);
    }

    public List<Customer> getCustomersFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT customer_id AS customerId, restaurant_id AS restaurantId, customer_name AS customerName, mobile, email, address, status
                FROM customer WHERE restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (customer_name LIKE ? OR mobile LIKE ? OR email LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
        }

        String sortColumn = "customer_id";
        if ("customerName".equalsIgnoreCase(req.getSortBy())) sortColumn = "customer_name";
        else if ("mobile".equalsIgnoreCase(req.getSortBy())) sortColumn = "mobile";
        else if ("status".equalsIgnoreCase(req.getSortBy())) sortColumn = "status";

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(req.getSafeSortDirection());
        sql.append(" LIMIT ? OFFSET ?");
        params.add(req.getLimit());
        params.add(req.getOffset());

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Customer.class), params.toArray());
    }

    public long countCustomersFiltered(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM customer WHERE restaurant_id = ?");
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (customer_name LIKE ? OR mobile LIKE ? OR email LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }
}