package com.restaurant.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

        int orderId = dto.getOrderId();


        // -------------------------------------------------
        // CHECK ORDER EXISTS
        // -------------------------------------------------

        Integer orderExists =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM orders
                        WHERE order_id=?
                        """,
                        Integer.class,
                        orderId
                );


        if (orderExists == null ||
            orderExists == 0) {

            throw new RuntimeException(
                    "Order not found: " + orderId
            );
        }


        // -------------------------------------------------
        // CHECK WHETHER BILL ALREADY EXISTS
        // -------------------------------------------------

        Integer billExists =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM bill
                        WHERE order_id=?
                        """,
                        Integer.class,
                        orderId
                );


        if (billExists != null &&
            billExists > 0) {

            throw new RuntimeException(
                    "Bill already exists for Order #" +
                    orderId
            );
        }


        // -------------------------------------------------
        // GET ORDER STATUS
        // -------------------------------------------------

        String orderStatus =
                jdbcTemplate.queryForObject(
                        """
                        SELECT order_status
                        FROM orders
                        WHERE order_id=?
                        """,
                        String.class,
                        orderId
                );


        if ("CANCELLED".equalsIgnoreCase(orderStatus)) {

            throw new RuntimeException(
                    "Cancelled orders cannot be billed."
            );
        }


        if ("BILLED".equalsIgnoreCase(orderStatus)) {

            throw new RuntimeException(
                    "Order #" +
                    orderId +
                    " is already billed."
            );
        }


        // -------------------------------------------------
        // GET ORDER AMOUNT
        // -------------------------------------------------

        double subtotal =
                getOrderAmount(orderId);


        if (subtotal <= 0) {

            throw new RuntimeException(
                    "Order amount must be greater than zero."
            );
        }


        // -------------------------------------------------
        // GST 5%
        // -------------------------------------------------

        double gst =
                subtotal * 0.05;


        // -------------------------------------------------
        // DISCOUNT
        // -------------------------------------------------

        double discount =
                dto.getDiscount();


        if (discount < 0) {

            discount = 0;
        }


        if (discount > subtotal + gst) {

            discount =
                    subtotal + gst;
        }


     // -------------------------------------------------
     // GRAND TOTAL
     // -------------------------------------------------

     double grandTotal =
             subtotal +
             gst -
             discount;

     // -------------------------------------------------
     // FINAL DISCOUNT FOR LAMBDA
     // -------------------------------------------------

     final double finalDiscount = discount;

     // -------------------------------------------------
     // BILL NUMBER
     // -------------------------------------------------

        // -------------------------------------------------
        // BILL NUMBER
        // -------------------------------------------------

        String billNo =
                "BILL-" +
                UUID.randomUUID()
                     .toString()
                     .substring(0, 8)
                     .toUpperCase();


        // -------------------------------------------------
        // INSERT BILL
        // -------------------------------------------------

        String sql = """
                INSERT INTO bill
                (
                    bill_no,
                    order_id,
                    bill_date,
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
                    NOW(),
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

            var ps =
                    connection.prepareStatement(
                            sql,
                            java.sql.Statement
                                .RETURN_GENERATED_KEYS
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
                    finalDiscount
            );


            ps.setDouble(
                    6,
                    grandTotal
            );


            ps.setString(
                    7,
                    dto.getPaymentMethod()
            );


            ps.setString(
                    8,
                    "PAID"
            );


            return ps;

        }, keyHolder);


        Number generatedKey =
                keyHolder.getKey();


        if (generatedKey == null) {

            throw new RuntimeException(
                    "Unable to generate Bill ID."
            );
        }


        int billId =
                generatedKey.intValue();


        // -------------------------------------------------
        // UPDATE ORDER STATUS
        // -------------------------------------------------

        jdbcTemplate.update(
                """
                UPDATE orders
                SET order_status='BILLED'
                WHERE order_id=?
                """,
                orderId
        );


        return billId;
    }


    // =====================================================
    // GET ORDER AMOUNT
    // =====================================================

    public double getOrderAmount(
            int orderId) {

        Double amount =
                jdbcTemplate.queryForObject(
                        """
                        SELECT total_amount
                        FROM orders
                        WHERE order_id=?
                        """,
                        Double.class,
                        orderId
                );


        return amount != null
                ? amount
                : 0.0;
    }


    // =====================================================
    // GET ALL BILLS
    // =====================================================

    public List<Bill> getAllBills() {

        String sql = """
                SELECT
                    bill_id AS billId,
                    bill_no AS billNo,
                    order_id AS orderId,
                    bill_date AS billDate,
                    subtotal,
                    gst,
                    discount,
                    grand_total AS grandTotal,
                    payment_method AS paymentMethod,
                    payment_status AS paymentStatus
                FROM bill
                ORDER BY bill_id DESC
                """;


        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        Bill.class
                )
        );
    }


    // =====================================================
    // GET BILLS - PAGINATED
    // =====================================================

    public List<Bill> getBillsPage(int limit, int offset) {

        String sql = """
                SELECT
                    bill_id AS billId,
                    bill_no AS billNo,
                    order_id AS orderId,
                    bill_date AS billDate,
                    subtotal,
                    gst,
                    discount,
                    grand_total AS grandTotal,
                    payment_method AS paymentMethod,
                    payment_status AS paymentStatus
                FROM bill
                ORDER BY bill_id DESC
                LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Bill.class),
                limit,
                offset);
    }

    public long countBills() {

        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bill",
                Long.class);

        return count != null ? count : 0L;
    }


    // =====================================================
    // GET BILL BY BILL ID
    // =====================================================

    public Bill getBillById(int id) {

        String sql = """
                SELECT
                    bill_id AS billId,
                    bill_no AS billNo,
                    order_id AS orderId,
                    bill_date AS billDate,
                    subtotal,
                    gst,
                    discount,
                    grand_total AS grandTotal,
                    payment_method AS paymentMethod,
                    payment_status AS paymentStatus
                FROM bill
                WHERE bill_id=?
                """;


        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(
                        Bill.class
                ),
                id
        );
    }


    // =====================================================
    // GET BILL BY ORDER ID
    // =====================================================

    public Bill getBillByOrderId(
            int orderId) {

        String sql = """
                SELECT
                    bill_id AS billId,
                    bill_no AS billNo,
                    order_id AS orderId,
                    bill_date AS billDate,
                    subtotal,
                    gst,
                    discount,
                    grand_total AS grandTotal,
                    payment_method AS paymentMethod,
                    payment_status AS paymentStatus
                FROM bill
                WHERE order_id=?
                """;


        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(
                        Bill.class
                ),
                orderId
        );
    }


    // =====================================================
    // UPDATE PAYMENT
    // =====================================================

    public int updatePayment(
            int billId,
            String paymentMethod) {

        String sql = """
                UPDATE bill
                SET
                    payment_method=?,
                    payment_status='PAID'
                WHERE bill_id=?
                """;


        return jdbcTemplate.update(
                sql,
                paymentMethod,
                billId
        );
    }


    // =====================================================
    // TODAY BILLS
    // =====================================================

    public List<Bill> getTodayBills() {

        String sql = """
                SELECT
                    bill_id AS billId,
                    bill_no AS billNo,
                    order_id AS orderId,
                    bill_date AS billDate,
                    subtotal,
                    gst,
                    discount,
                    grand_total AS grandTotal,
                    payment_method AS paymentMethod,
                    payment_status AS paymentStatus
                FROM bill
                WHERE DATE(bill_date)=CURDATE()
                ORDER BY bill_date DESC
                """;


        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        Bill.class
                )
        );
    }

}