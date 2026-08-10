package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Customer;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Customer customer) {

        String sql = """
                INSERT INTO customer
                (customer_name,mobile,email,address,status)
                VALUES(?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                customer.getCustomerName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getStatus());
    }

    public int update(Customer customer) {

        String sql = """
                UPDATE customer
                SET customer_name=?,
                    mobile=?,
                    email=?,
                    address=?,
                    status=?
                WHERE customer_id=?
                """;

        return jdbcTemplate.update(sql,
                customer.getCustomerName(),
                customer.getMobile(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getStatus(),
                customer.getCustomerId());
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                "DELETE FROM customer WHERE customer_id=?",
                id);
    }

    public Customer findById(int id) {

        String sql = """
                SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
                FROM customer
                WHERE customer_id=?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Customer.class),
                id);
    }

    public List<Customer> findAll() {

        String sql = """
                SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
                FROM customer
                ORDER BY customer_id DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Customer.class));
    }

    public boolean existsByMobile(String mobile) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM customer WHERE mobile=?",
                Integer.class,
                mobile);

        return count != null && count > 0;
    }

    public List<Customer> search(String keyword) {

        String sql = """
                SELECT
                customer_id AS customerId,
                customer_name AS customerName,
                mobile,
                email,
                address,
                status
                FROM customer
                WHERE customer_name LIKE ?
                   OR mobile LIKE ?
                """;

        String value = "%" + keyword + "%";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Customer.class),
                value, value);
    }
}