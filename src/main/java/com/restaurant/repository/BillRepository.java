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

        // Check Order
        Integer orderCount = jdbcTemplate.queryForObject(
                BillQueries.CHECK_ORDER_EXISTS,
                Integer.class,
                orderId);

        if (orderCount == null || orderCount == 0) {
            throw new IllegalArgumentException("Order not found");
        }

        // Check Existing Bill
        Integer billCount = jdbcTemplate.queryForObject(
                BillQueries.CHECK_BILL_EXISTS_FOR_ORDER,
                Integer.class,
                orderId);

        if (billCount != null && billCount > 0) {
            throw new IllegalArgumentException("A bill already exists for this order");
        }

        // Calculate Subtotal
        Double dbSubtotal = jdbcTemplate.queryForObject(
                BillQueries.CALCULATE_ORDER_SUBTOTAL,
                Double.class,
                orderId);

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

            ps.setString(1, billNo);
            ps.setInt(2, orderId);
            ps.setDouble(3, subtotal);
            ps.setDouble(4, gst);
            ps.setDouble(5, discount);
            ps.setDouble(6, grandTotal);

            if (paymentMethod == null) {
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else {
                ps.setString(7, paymentMethod);
            }

            ps.setString(8, paymentStatus);
            return ps;
        }, keyHolder);

        Number generatedKey = keyHolder.getKey();
        if (generatedKey == null) {
            throw new IllegalStateException("Bill ID was not generated");
        }

        int billId = generatedKey.intValue();

        // Update Order
        jdbcTemplate.update(
                BillQueries.UPDATE_ORDER_AFTER_BILLING,
                grandTotal,
                orderId);

        return billId;
    }

    public List<Bill> getAllBills() {
        return jdbcTemplate.query(
                BillQueries.FIND_ALL_BILLS,
                this::mapBill);
    }

    public List<Bill> getBillsPage(int size, int offset) {
        return jdbcTemplate.query(
                BillQueries.FIND_BILLS_PAGE,
                this::mapBill,
                size,
                offset);
    }

    public long countBills() {
        Long count = jdbcTemplate.queryForObject(
                BillQueries.COUNT_BILLS,
                Long.class);
        return count == null ? 0 : count;
    }

    public List<Bill> getBillsFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT b.bill_id, b.bill_no, b.order_id, b.bill_date, b.subtotal, b.gst,
                       b.discount, b.grand_total, b.payment_method, b.payment_status
                FROM bill b
                WHERE 1=1
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();

        if (req.getPaymentStatus() != null && !req.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ?");
            params.add(req.getPaymentStatus().trim().toUpperCase());
        }
        if (req.getPaymentMethod() != null && !req.getPaymentMethod().isBlank()) {
            sql.append(" AND b.payment_method = ?");
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
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM bill b WHERE 1=1");
        java.util.List<Object> params = new java.util.ArrayList<>();

        if (req.getPaymentStatus() != null && !req.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ?");
            params.add(req.getPaymentStatus().trim().toUpperCase());
        }
        if (req.getPaymentMethod() != null && !req.getPaymentMethod().isBlank()) {
            sql.append(" AND b.payment_method = ?");
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
                billId);
        return bills.isEmpty() ? null : bills.get(0);
    }

    public Bill getBillByOrderId(int orderId) {
        List<Bill> bills = jdbcTemplate.query(
                BillQueries.FIND_BILL_BY_ORDER_ID,
                this::mapBill,
                orderId);
        return bills.isEmpty() ? null : bills.get(0);
    }

    public int updatePayment(int billId, String paymentMethod, String paymentStatus) {
        String method = normalizePaymentMethod(paymentMethod);
        String status = normalizePaymentStatus(paymentStatus);

        return jdbcTemplate.update(
                BillQueries.UPDATE_PAYMENT,
                method,
                status,
                billId);
    }

    public List<Bill> getTodayBills() {
        return jdbcTemplate.query(
                BillQueries.FIND_TODAY_BILLS,
                this::mapBill);
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
            return null;
        }
        String method = value.trim().toUpperCase();
        if (!method.equals("CASH") && !method.equals("CARD") && !method.equals("UPI")) {
            throw new IllegalArgumentException("Invalid payment method. Use CASH, CARD or UPI");
        }
        return method;
    }

    private String normalizePaymentStatus(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "PENDING";
        }
        String status = value.trim().toUpperCase();
        if (!status.equals("PENDING") && !status.equals("PAID")) {
            throw new IllegalArgumentException("Invalid payment status. Use PENDING or PAID");
        }
        return status;
    }

    private Bill mapBill(ResultSet rs, int rowNum) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getInt("bill_id"));
        bill.setBillNo(rs.getString("bill_no"));
        bill.setOrderId(rs.getInt("order_id"));
        if (rs.getTimestamp("bill_date") != null) {
            bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
        }
        bill.setSubtotal(rs.getDouble("subtotal"));
        bill.setGst(rs.getDouble("gst"));
        bill.setDiscount(rs.getDouble("discount"));
        bill.setGrandTotal(rs.getDouble("grand_total"));
        bill.setPaymentMethod(rs.getString("payment_method"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        return bill;
    }
}
