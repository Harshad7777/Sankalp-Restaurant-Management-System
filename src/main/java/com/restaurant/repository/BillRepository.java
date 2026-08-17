
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

@Repository
public class BillRepository {

    private final JdbcTemplate jdbcTemplate;

    public BillRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // CREATE BILL
    // =====================================================

    @Transactional
    public int createBill(BillDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "Bill request cannot be null"
            );
        }

        int orderId = dto.getOrderId();

        if (orderId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid Order ID"
            );
        }

        // -------------------------------------------------
        // CHECK ORDER
        // -------------------------------------------------

        Integer orderCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM orders WHERE order_id = ?",
                Integer.class,
                orderId
        );

        if (orderCount == null || orderCount == 0) {
            throw new IllegalArgumentException(
                    "Order not found"
            );
        }

        // -------------------------------------------------
        // CHECK EXISTING BILL
        // -------------------------------------------------

        Integer billCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bill WHERE order_id = ?",
                Integer.class,
                orderId
        );

        if (billCount != null && billCount > 0) {
            throw new IllegalArgumentException(
                    "A bill already exists for this order"
            );
        }

        // -------------------------------------------------
        // CALCULATE SUBTOTAL FROM ORDER DETAILS
        // -------------------------------------------------

        Double dbSubtotal = jdbcTemplate.queryForObject(
                """
                SELECT COALESCE(
                    SUM(subtotal),
                    0
                )
                FROM order_details
                WHERE order_id = ?
                """,
                Double.class,
                orderId
        );

        double subtotal =
                dbSubtotal == null ? 0.0 : dbSubtotal;

        // -------------------------------------------------
        // DISCOUNT
        // -------------------------------------------------

        double calculatedDiscount = Math.max(
                0.0,
                dto.getDiscount()
        );

        if (calculatedDiscount > subtotal) {
            calculatedDiscount = subtotal;
        }

        /*
         * IMPORTANT:
         * discount is never modified after this point.
         * Therefore it is effectively final and can safely
         * be used inside the jdbcTemplate lambda.
         */
        final double discount = calculatedDiscount;

        // -------------------------------------------------
        // TAXABLE AMOUNT
        // -------------------------------------------------

        double taxable = Math.max(
                0.0,
                subtotal - discount
        );

        // -------------------------------------------------
        // GST 5%
        // -------------------------------------------------

        double gst = Math.round(
                taxable * 0.05 * 100.0
        ) / 100.0;

        // -------------------------------------------------
        // GRAND TOTAL
        // -------------------------------------------------

        double grandTotal = Math.round(
                (taxable + gst) * 100.0
        ) / 100.0;

        // -------------------------------------------------
        // PAYMENT
        // -------------------------------------------------

        String paymentMethod =
                normalizePaymentMethod(
                        dto.getPaymentMethod()
                );

        String paymentStatus =
                normalizePaymentStatus(
                        dto.getPaymentStatus()
                );

        // -------------------------------------------------
        // BILL NUMBER
        // -------------------------------------------------

        String billNo = generateBillNumber();

        // -------------------------------------------------
        // INSERT BILL
        // -------------------------------------------------

        String sql =
                """
                INSERT INTO bill
                (
                    bill_no,
                    order_id,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                )
                VALUES
                (
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,
                    ?,
                    ?
                )
                """;

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            var ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(
                    1,
                    billNo
            );

            ps.setInt(
                    2,
                    orderId
            );

            ps.setDouble(
                    3,
                    subtotal
            );

            ps.setDouble(
                    4,
                    gst
            );

            ps.setDouble(
                    5,
                    discount
            );

            ps.setDouble(
                    6,
                    grandTotal
            );

            if (paymentMethod == null) {

                ps.setNull(
                        7,
                        java.sql.Types.VARCHAR
                );

            } else {

                ps.setString(
                        7,
                        paymentMethod
                );
            }

            ps.setString(
                    8,
                    paymentStatus
            );

            return ps;

        }, keyHolder);

        // -------------------------------------------------
        // GENERATED BILL ID
        // -------------------------------------------------

        Number generatedKey =
                keyHolder.getKey();

        if (generatedKey == null) {

            throw new IllegalStateException(
                    "Bill ID was not generated"
            );
        }

        int billId =
                generatedKey.intValue();

        // -------------------------------------------------
        // UPDATE ORDER
        // -------------------------------------------------

        jdbcTemplate.update(
                """
                UPDATE orders
                SET
                    total_amount = ?,
                    order_status = 'BILLED'
                WHERE order_id = ?
                """,
                grandTotal,
                orderId
        );

        return billId;
    }

    // =====================================================
    // GET ALL BILLS
    // =====================================================

    public List<Bill> getAllBills() {

        return jdbcTemplate.query(
                """
                SELECT
                    bill_id,
                    bill_no,
                    order_id,
                    bill_date,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                FROM bill
                ORDER BY bill_id DESC
                """,
                this::mapBill
        );
    }

    // =====================================================
    // GET PAGINATED BILLS
    // =====================================================

    public List<Bill> getBillsPage(
            int size,
            int offset) {

        return jdbcTemplate.query(
                """
                SELECT
                    bill_id,
                    bill_no,
                    order_id,
                    bill_date,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                FROM bill
                ORDER BY bill_id DESC
                LIMIT ? OFFSET ?
                """,
                this::mapBill,
                size,
                offset
        );
    }

    // =====================================================
    // COUNT BILLS
    // =====================================================

    public long countBills() {

        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bill",
                Long.class
        );

        return count == null ? 0 : count;
    }

    // =====================================================
    // GET BILL BY ID
    // =====================================================

    public Bill getBillById(int billId) {

        List<Bill> bills = jdbcTemplate.query(
                """
                SELECT
                    bill_id,
                    bill_no,
                    order_id,
                    bill_date,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                FROM bill
                WHERE bill_id = ?
                """,
                this::mapBill,
                billId
        );

        return bills.isEmpty()
                ? null
                : bills.get(0);
    }

    // =====================================================
    // GET BILL BY ORDER ID
    // =====================================================

    public Bill getBillByOrderId(int orderId) {

        List<Bill> bills = jdbcTemplate.query(
                """
                SELECT
                    bill_id,
                    bill_no,
                    order_id,
                    bill_date,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                FROM bill
                WHERE order_id = ?
                """,
                this::mapBill,
                orderId
        );

        return bills.isEmpty()
                ? null
                : bills.get(0);
    }

    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    public int updatePayment(
            int billId,
            String paymentMethod,
            String paymentStatus) {

        String method =
                normalizePaymentMethod(
                        paymentMethod
                );

        String status =
                normalizePaymentStatus(
                        paymentStatus
                );

        return jdbcTemplate.update(
                """
                UPDATE bill
                SET
                    payment_method = ?,
                    payment_status = ?
                WHERE bill_id = ?
                """,
                method,
                status,
                billId
        );
    }

    // =====================================================
    // TODAY BILLS
    // =====================================================

    public List<Bill> getTodayBills() {

        return jdbcTemplate.query(
                """
                SELECT
                    bill_id,
                    bill_no,
                    order_id,
                    bill_date,
                    subtotal,
                    gst,
                    discount,
                    grand_total,
                    payment_method,
                    payment_status
                FROM bill
                WHERE DATE(bill_date) = CURDATE()
                ORDER BY bill_id DESC
                """,
                this::mapBill
        );
    }

    // =====================================================
    // GENERATE BILL NUMBER
    // =====================================================

    private String generateBillNumber() {

        Integer maxNumber =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COALESCE(
                            MAX(
                                CAST(
                                    SUBSTRING_INDEX(
                                        bill_no,
                                        '-',
                                        -1
                                    ) AS UNSIGNED
                                )
                            ),
                            0
                        )
                        FROM bill
                        WHERE bill_no LIKE 'BILL-%'
                        """,
                        Integer.class
                );

        int nextNumber =
                (maxNumber == null
                        ? 0
                        : maxNumber) + 1;

        return String.format(
                "BILL-%06d",
                nextNumber
        );
    }

    // =====================================================
    // NORMALIZE PAYMENT METHOD
    // =====================================================

    private String normalizePaymentMethod(
            String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            return null;
        }

        String method =
                value.trim().toUpperCase();

        if (!method.equals("CASH") &&
                !method.equals("CARD") &&
                !method.equals("UPI")) {

            throw new IllegalArgumentException(
                    "Invalid payment method. Use CASH, CARD or UPI"
            );
        }

        return method;
    }

    // =====================================================
    // NORMALIZE PAYMENT STATUS
    // =====================================================

    private String normalizePaymentStatus(
            String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            return "PENDING";
        }

        String status =
                value.trim().toUpperCase();

        if (!status.equals("PENDING") &&
                !status.equals("PAID")) {

            throw new IllegalArgumentException(
                    "Invalid payment status. Use PENDING or PAID"
            );
        }

        return status;
    }

    // =====================================================
    // MAP RESULT SET
    // =====================================================

    private Bill mapBill(
            ResultSet rs,
            int rowNum)
            throws SQLException {

        Bill bill = new Bill();

        bill.setBillId(
                rs.getInt("bill_id")
        );

        bill.setBillNo(
                rs.getString("bill_no")
        );

        bill.setOrderId(
                rs.getInt("order_id")
        );

        if (rs.getTimestamp("bill_date") != null) {

            bill.setBillDate(
                    rs.getTimestamp("bill_date")
                            .toLocalDateTime()
            );
        }

        bill.setSubtotal(
                rs.getDouble("subtotal")
        );

        bill.setGst(
                rs.getDouble("gst")
        );

        bill.setDiscount(
                rs.getDouble("discount")
        );

        bill.setGrandTotal(
                rs.getDouble("grand_total")
        );

        bill.setPaymentMethod(
                rs.getString("payment_method")
        );

        bill.setPaymentStatus(
                rs.getString("payment_status")
        );

        return bill;
    }
}

