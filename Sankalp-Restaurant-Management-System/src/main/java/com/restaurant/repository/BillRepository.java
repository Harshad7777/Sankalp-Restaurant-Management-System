package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.dto.BillDTO;
import com.restaurant.model.Bill;
import com.restaurant.repository.query.BillQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class BillRepository {

    private final JdbcTemplate jdbcTemplate;

    public BillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int createBill(BillDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Bill request cannot be null");
        }

        int orderId = dto.getOrderId();
        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid Order ID");
        }

        Integer resId = SecurityUtil.getCurrentRestaurantId();

        // Check Order belongs to current tenant
        Integer orderCount = jdbcTemplate.queryForObject(
                BillQueries.CHECK_ORDER_EXISTS,
                Integer.class,
                orderId,
                resId);

        if (orderCount == null || orderCount == 0) {
            throw new IllegalArgumentException("Order not found or does not belong to the current restaurant");
        }

        // Check Existing Bill
        Integer billCount = jdbcTemplate.queryForObject(
                BillQueries.CHECK_BILL_EXISTS_FOR_ORDER,
                Integer.class,
                orderId,
                resId);

        if (billCount != null && billCount > 0) {
            throw new IllegalArgumentException("A bill already exists for this order");
        }

        // Calculate Subtotal safely for tenant's order
        Double dbSubtotal = jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(od.subtotal), 0) FROM order_details od JOIN orders o ON od.order_id = o.order_id WHERE od.order_id = ? AND o.restaurant_id = ?",
                Double.class,
                orderId,
                resId);

        double subtotal = dbSubtotal == null ? 0.0 : dbSubtotal;

        // Discount
        double calculatedDiscount = Math.max(0.0, dto.getDiscount());
        if (calculatedDiscount > subtotal) {
            calculatedDiscount = subtotal;
        }
        final double discount = calculatedDiscount;

        // Taxable & GST
        double taxable = Math.max(0.0, subtotal - discount);
        double gst = Math.round(taxable * 0.05 * 100.0) / 100.0;
        double grandTotal = Math.round((taxable + gst) * 100.0) / 100.0;

        String paymentMethod = normalizePaymentMethod(dto.getPaymentMethod());
        String paymentStatus = normalizePaymentStatus(dto.getPaymentStatus());
        String billNo = generateBillNumber();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(
                    BillQueries.INSERT_BILL,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, resId);
            ps.setString(2, billNo);
            ps.setInt(3, orderId);
            ps.setDouble(4, subtotal);
            ps.setDouble(5, gst);
            ps.setDouble(6, discount);
            ps.setDouble(7, grandTotal);
            ps.setString(8, paymentMethod);
            ps.setString(9, paymentStatus);

            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to generate Bill ID");
        }

        int billId = key.intValue();

        // Update Order
        jdbcTemplate.update(
                BillQueries.UPDATE_ORDER_AFTER_BILLING,
                grandTotal,
                orderId,
                resId);

        return billId;
    }

    public List<Bill> getAllBills() {
        return jdbcTemplate.query(
                BillQueries.FIND_ALL_BILLS,
                this::mapBill,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Bill> getBillsPage(int size, int offset) {
        return jdbcTemplate.query(
                BillQueries.FIND_BILLS_PAGE,
                this::mapBill,
                SecurityUtil.getCurrentRestaurantId(),
                size,
                offset);
    }

    public long countBills() {
        Long count = jdbcTemplate.queryForObject(
                BillQueries.COUNT_BILLS,
                Long.class,
                SecurityUtil.getCurrentRestaurantId());
        return count == null ? 0 : count;
    }

    public List<Bill> getBillsFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT b.bill_id AS billId, b.restaurant_id AS restaurantId, b.bill_no AS billNo, b.order_id AS orderId,
                       b.bill_date AS billDate, b.subtotal, b.tax_amount AS gst,
                       b.discount_amount AS discount, b.grand_total AS grandTotal,
                       b.payment_mode AS paymentMethod, b.payment_status AS paymentStatus
                FROM bill b
                WHERE b.restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getPaymentStatus() != null && !req.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ?");
            params.add(req.getPaymentStatus().trim().toUpperCase());
        }
        if (req.getPaymentMethod() != null && !req.getPaymentMethod().isBlank()) {
            sql.append(" AND b.payment_mode = ?");
            params.add(req.getPaymentMethod().trim().toUpperCase());
        }
        if (req.getStartDate() != null && !req.getStartDate().isBlank()) {
            sql.append(" AND DATE(b.bill_date) >= ?");
            params.add(req.getStartDate().trim());
        }
        if (req.getEndDate() != null && !req.getEndDate().isBlank()) {
            sql.append(" AND DATE(b.bill_date) <= ?");
            params.add(req.getEndDate().trim());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (b.bill_no LIKE ? OR CAST(b.order_id AS CHAR) LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
        }

        String sortColumn = "b.bill_id";
        if ("billDate".equalsIgnoreCase(req.getSortBy())) sortColumn = "b.bill_date";
        else if ("grandTotal".equalsIgnoreCase(req.getSortBy())) sortColumn = "b.grand_total";
        else if ("billNo".equalsIgnoreCase(req.getSortBy())) sortColumn = "b.bill_no";
        else if ("paymentStatus".equalsIgnoreCase(req.getSortBy())) sortColumn = "b.payment_status";

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(req.getSafeSortDirection());
        sql.append(" LIMIT ? OFFSET ?");
        params.add(req.getLimit());
        params.add(req.getOffset());

        return jdbcTemplate.query(sql.toString(), this::mapBill, params.toArray());
    }

    public long countBillsFiltered(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM bill b WHERE b.restaurant_id = ?");
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getPaymentStatus() != null && !req.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ?");
            params.add(req.getPaymentStatus().trim().toUpperCase());
        }
        if (req.getPaymentMethod() != null && !req.getPaymentMethod().isBlank()) {
            sql.append(" AND b.payment_mode = ?");
            params.add(req.getPaymentMethod().trim().toUpperCase());
        }
        if (req.getStartDate() != null && !req.getStartDate().isBlank()) {
            sql.append(" AND DATE(b.bill_date) >= ?");
            params.add(req.getStartDate().trim());
        }
        if (req.getEndDate() != null && !req.getEndDate().isBlank()) {
            sql.append(" AND DATE(b.bill_date) <= ?");
            params.add(req.getEndDate().trim());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (b.bill_no LIKE ? OR CAST(b.order_id AS CHAR) LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }

    public Bill getBillById(int billId) {
        List<Bill> bills = jdbcTemplate.query(
                BillQueries.FIND_BILL_BY_ID,
                this::mapBill,
                billId,
                SecurityUtil.getCurrentRestaurantId());
        return bills.isEmpty() ? null : bills.get(0);
    }

    public Bill getBillByOrderId(int orderId) {
        List<Bill> bills = jdbcTemplate.query(
                BillQueries.FIND_BILL_BY_ORDER_ID,
                this::mapBill,
                orderId,
                SecurityUtil.getCurrentRestaurantId());
        return bills.isEmpty() ? null : bills.get(0);
    }

    public int updatePayment(int billId, String paymentMethod, String paymentStatus) {
        String method = normalizePaymentMethod(paymentMethod);
        String status = normalizePaymentStatus(paymentStatus);

        return jdbcTemplate.update(
                BillQueries.UPDATE_PAYMENT,
                method,
                status,
                billId,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Bill> getTodayBills() {
        return jdbcTemplate.query(
                BillQueries.FIND_TODAY_BILLS,
                this::mapBill,
                SecurityUtil.getCurrentRestaurantId());
    }

    private String generateBillNumber() {
        Integer maxNumber = jdbcTemplate.queryForObject(
                BillQueries.GET_MAX_BILL_NUMBER,
                Integer.class);

        int nextNumber = (maxNumber == null ? 0 : maxNumber) + 1;
        return String.format("BILL-%06d", nextNumber);
    }

    private String normalizePaymentMethod(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "CASH";
        }
        String method = value.trim().toUpperCase();
        if (method.contains("CARD")) return "CARD";
        if (method.contains("UPI") || method.contains("ONLINE")) return "UPI";
        return "CASH";
    }

    private String normalizePaymentStatus(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "PAID";
        }
        String status = value.trim().toUpperCase();
        if (status.contains("PENDING") || status.contains("UNPAID")) return "PENDING";
        return "PAID";
    }

    private Bill mapBill(ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getInt("billId"));
        try {
            bill.setRestaurantId(rs.getInt("restaurantId"));
        } catch (SQLException ignored) {}
        bill.setBillNo(rs.getString("billNo"));
        bill.setOrderId(rs.getInt("orderId"));
        if (rs.getTimestamp("billDate") != null) {
            bill.setBillDate(rs.getTimestamp("billDate").toLocalDateTime());
        }
        bill.setSubtotal(rs.getDouble("subtotal"));
        bill.setGst(rs.getDouble("gst"));
        bill.setDiscount(rs.getDouble("discount"));
        bill.setGrandTotal(rs.getDouble("grandTotal"));
        bill.setPaymentMethod(rs.getString("paymentMethod"));
        bill.setPaymentStatus(rs.getString("paymentStatus"));
        return bill;
    }
}
