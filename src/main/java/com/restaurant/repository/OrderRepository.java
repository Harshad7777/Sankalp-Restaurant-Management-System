
package com.restaurant.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.dto.KitchenOrderDTO;
import com.restaurant.dto.KitchenStatsDTO;
import com.restaurant.dto.OrderDTO;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.model.OrderDetails;
import com.restaurant.model.Orders;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // CREATE ORDER
    // =====================================================

    @Transactional
    public int createOrder(OrderDTO dto) {

        if (dto == null) {
            throw new RuntimeException("Order data cannot be null.");
        }

        if (dto.getCustomerId() == null) {
            throw new RuntimeException("Customer Id is required.");
        }

        if (dto.getTableId() == null) {
            throw new RuntimeException("Table Id is required.");
        }

        if (dto.getStaffId() == null) {
            throw new RuntimeException("Staff Id is required.");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Order must contain at least one item.");
        }

        String orderNo =
                "ORD-" +
                java.util.UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        String orderSql = """
                INSERT INTO orders
                (
                    order_no,
                    customer_id,
                    table_id,
                    staff_id,
                    order_date,
                    total_amount,
                    order_status
                )
                VALUES
                (
                    ?, ?, ?, ?, NOW(), ?, ?
                )
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps =
                    connection.prepareStatement(
                            orderSql,
                            Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, orderNo);
            ps.setInt(2, dto.getCustomerId());
            ps.setInt(3, dto.getTableId());
            ps.setInt(4, dto.getStaffId());
            ps.setDouble(5, 0.0);
            ps.setString(6, "NEW");

            return ps;

        }, keyHolder);

        Number key = keyHolder.getKey();

        if (key == null) {
            throw new RuntimeException(
                    "Unable to generate Order Id.");
        }

        int orderId = key.intValue();

        double totalAmount = 0.0;

        // =================================================
        // INSERT ORDER DETAILS
        // =================================================

        for (OrderDetails item : dto.getItems()) {

            if (item == null) {
                continue;
            }

            if (item.getItemId() == null) {
                throw new RuntimeException(
                        "Item Id is required.");
            }

            if (item.getQuantity() == null ||
                    item.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than zero.");
            }

            double price =
                    getMenuPrice(item.getItemId());

            double subtotal =
                    price * item.getQuantity();

            totalAmount += subtotal;

            jdbcTemplate.update(
                    """
                    INSERT INTO order_details
                    (
                        order_id,
                        item_id,
                        quantity,
                        price,
                        subtotal,
                        kitchen_note,
                        kitchen_status
                    )
                    VALUES
                    (
                        ?, ?, ?, ?, ?, ?, 'NEW'
                    )
                    """,
                    orderId,
                    item.getItemId(),
                    item.getQuantity(),
                    price,
                    subtotal,
                    item.getKitchenNote());
        }

        // =================================================
        // UPDATE TOTAL
        // =================================================

        jdbcTemplate.update(
                """
                UPDATE orders
                SET total_amount = ?
                WHERE order_id = ?
                """,
                totalAmount,
                orderId);

        return orderId;
    }

    // =====================================================
    // GET MENU PRICE
    // =====================================================

    public double getMenuPrice(Integer itemId) {

        String sql =
                "SELECT price FROM menu WHERE item_id = ?";

        List<Double> prices =
                jdbcTemplate.query(
                        sql,
                        (rs, rowNum) ->
                                rs.getDouble("price"),
                        itemId);

        if (prices.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Menu Item not found with id : " + itemId);
        }

        return prices.get(0);
    }

    // =====================================================
    // GET ALL ORDERS
    // =====================================================

    public List<Orders> getAllOrders() {

        String sql = """
                SELECT
                    o.order_id AS orderId,
                    o.customer_id AS customerId,
                    c.customer_name AS customerName,
                    o.table_id AS tableId,
                    rt.table_number AS tableNumber,
                    o.staff_id AS staffId,
                    o.order_date AS orderDate,
                    o.total_amount AS totalAmount,
                    o.order_status AS orderStatus,
                    o.order_type AS orderType,
                    o.remarks AS remarks

                FROM orders o

                INNER JOIN customer c
                    ON o.customer_id = c.customer_id

                INNER JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                ORDER BY o.order_id DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Orders.class));
    }

    // =====================================================
    // GET ORDERS - PAGINATED
    // =====================================================

    public List<Orders> getOrdersPage(
            int limit,
            int offset) {

        String sql = """
                SELECT
                    o.order_id AS orderId,
                    o.customer_id AS customerId,
                    c.customer_name AS customerName,
                    o.table_id AS tableId,
                    rt.table_number AS tableNumber,
                    o.staff_id AS staffId,
                    o.order_date AS orderDate,
                    o.total_amount AS totalAmount,
                    o.order_status AS orderStatus,
                    o.order_type AS orderType,
                    o.remarks AS remarks

                FROM orders o

                INNER JOIN customer c
                    ON o.customer_id = c.customer_id

                INNER JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                ORDER BY o.order_id DESC

                LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Orders.class),
                limit,
                offset);
    }

    // =====================================================
    // COUNT ORDERS
    // =====================================================

    public long countOrders() {

        Long count =
                jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM orders",
                        Long.class);

        return count != null ? count : 0L;
    }

    // =====================================================
    // GET ORDER BY ID
    // =====================================================

    public Orders getOrderById(Integer orderId) {

        String sql = """
                SELECT
                    o.order_id AS orderId,
                    o.customer_id AS customerId,
                    c.customer_name AS customerName,
                    o.table_id AS tableId,
                    rt.table_number AS tableNumber,
                    o.staff_id AS staffId,
                    o.order_date AS orderDate,
                    o.total_amount AS totalAmount,
                    o.order_status AS orderStatus,
                    o.order_type AS orderType,
                    o.remarks AS remarks

                FROM orders o

                INNER JOIN customer c
                    ON o.customer_id = c.customer_id

                INNER JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                WHERE o.order_id = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Orders.class),
                orderId);
    }

    // =====================================================
    // GET ORDER DETAILS
    // =====================================================

    public List<OrderDetails> getOrderDetails(
            Integer orderId) {

        String sql = """
                SELECT
                    od.order_detail_id AS orderDetailId,
                    od.order_id AS orderId,
                    od.item_id AS itemId,
                    m.item_name AS itemName,
                    od.quantity AS quantity,
                    od.price AS price,
                    od.subtotal AS subtotal,
                    od.kitchen_note AS kitchenNote,
                    od.kitchen_status AS kitchenStatus,
                    od.prepared_by AS preparedBy,
                    u.full_name AS preparedByName,
                    od.served_at AS servedAt

                FROM order_details od

                INNER JOIN menu m
                    ON od.item_id = m.item_id

                LEFT JOIN users u
                    ON od.prepared_by = u.user_id

                WHERE od.order_id = ?

                ORDER BY od.order_detail_id
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        OrderDetails.class),
                orderId);
    }

    // =====================================================
    // UPDATE ORDER STATUS
    // =====================================================

    public int updateStatus(
            Integer orderId,
            String status) {

        if (orderId == null ||
                status == null ||
                status.isBlank()) {

            return 0;
        }

        String sql = """
                UPDATE orders
                SET order_status = ?
                WHERE order_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                status.trim().toUpperCase(),
                orderId);
    }

    // =====================================================
    // CANCEL ORDER
    // =====================================================

    public int cancelOrder(Integer orderId) {

        if (orderId == null) {
            return 0;
        }

        String sql = """
                UPDATE orders
                SET order_status = 'CANCELLED'
                WHERE order_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                orderId);
    }

    // =====================================================
    // CUSTOMER ORDERS
    // =====================================================

    public List<Orders> getCustomerOrders(
            Integer customerId) {

        String sql = """
                SELECT
                    o.order_id AS orderId,
                    o.customer_id AS customerId,
                    c.customer_name AS customerName,
                    o.table_id AS tableId,
                    rt.table_number AS tableNumber,
                    o.staff_id AS staffId,
                    o.order_date AS orderDate,
                    o.total_amount AS totalAmount,
                    o.order_status AS orderStatus,
                    o.order_type AS orderType,
                    o.remarks AS remarks

                FROM orders o

                INNER JOIN customer c
                    ON o.customer_id = c.customer_id

                INNER JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                WHERE o.customer_id = ?

                ORDER BY o.order_date DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Orders.class),
                customerId);
    }

    // =====================================================
    // TODAY'S ORDERS
    // =====================================================

    public List<Orders> getTodayOrders() {

        String sql = """
                SELECT
                    o.order_id AS orderId,
                    o.customer_id AS customerId,
                    c.customer_name AS customerName,
                    o.table_id AS tableId,
                    rt.table_number AS tableNumber,
                    o.staff_id AS staffId,
                    o.order_date AS orderDate,
                    o.total_amount AS totalAmount,
                    o.order_status AS orderStatus,
                    o.order_type AS orderType,
                    o.remarks AS remarks

                FROM orders o

                INNER JOIN customer c
                    ON o.customer_id = c.customer_id

                INNER JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                WHERE DATE(o.order_date) = CURDATE()

                ORDER BY o.order_date DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Orders.class));
    }

    // =====================================================
    // TODAY'S KITCHEN ORDERS
    // =====================================================

    public List<KitchenOrderDTO> getTodayKitchenOrders() {

        String sql = """
                SELECT
                    od.order_detail_id AS orderDetailId,
                    od.order_id AS orderId,
                    o.order_no AS orderNo,
                    c.customer_name AS customerName,
                    rt.table_number AS tableNumber,
                    od.item_id AS itemId,
                    m.item_name AS itemName,
                    od.quantity AS quantity,
                    od.price AS price,
                    od.subtotal AS subtotal,
                    od.kitchen_note AS kitchenNote,
                    od.kitchen_status AS kitchenStatus,
                    od.prepared_by AS preparedBy,
                    u.full_name AS preparedByName,
                    o.order_date AS orderDate,
                    od.served_at AS servedAt

                FROM order_details od

                INNER JOIN orders o
                    ON od.order_id = o.order_id

                LEFT JOIN customer c
                    ON o.customer_id = c.customer_id

                LEFT JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                INNER JOIN menu m
                    ON od.item_id = m.item_id

                LEFT JOIN users u
                    ON od.prepared_by = u.user_id

                WHERE DATE(o.order_date) = CURDATE()

                AND o.order_status <> 'CANCELLED'

                ORDER BY
                    CASE od.kitchen_status
                        WHEN 'NEW' THEN 1
                        WHEN 'ACCEPTED' THEN 2
                        WHEN 'PREPARING' THEN 3
                        WHEN 'READY' THEN 4
                        WHEN 'SERVED' THEN 5
                        ELSE 6
                    END,
                    o.order_date ASC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(
                        KitchenOrderDTO.class));
    }

    // =====================================================
    // KITCHEN STATISTICS
    // =====================================================

    public KitchenStatsDTO getKitchenStats() {

        String sql = """
                SELECT

                    COALESCE(
                        SUM(
                            CASE
                                WHEN od.kitchen_status = 'NEW'
                                THEN 1
                                ELSE 0
                            END
                        ), 0
                    ) AS newCount,

                    COALESCE(
                        SUM(
                            CASE
                                WHEN od.kitchen_status = 'ACCEPTED'
                                THEN 1
                                ELSE 0
                            END
                        ), 0
                    ) AS acceptedCount,

                    COALESCE(
                        SUM(
                            CASE
                                WHEN od.kitchen_status = 'PREPARING'
                                THEN 1
                                ELSE 0
                            END
                        ), 0
                    ) AS preparingCount,

                    COALESCE(
                        SUM(
                            CASE
                                WHEN od.kitchen_status = 'READY'
                                THEN 1
                                ELSE 0
                            END
                        ), 0
                    ) AS readyCount

                FROM order_details od

                INNER JOIN orders o
                    ON od.order_id = o.order_id

                WHERE DATE(o.order_date) = CURDATE()

                AND o.order_status <> 'CANCELLED'
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                        new KitchenStatsDTO(
                                rs.getInt("newCount"),
                                rs.getInt("acceptedCount"),
                                rs.getInt("preparingCount"),
                                rs.getInt("readyCount")
                        ));
    }

    // =====================================================
    // UPDATE KITCHEN STATUS
    // =====================================================

    @Transactional
    public int updateKitchenStatus(
            Integer orderDetailId,
            String status,
            Integer chefId) {

        if (orderDetailId == null) {
            return 0;
        }

        if (status == null || status.isBlank()) {
            return 0;
        }

        if (chefId == null) {
            return 0;
        }

        String normalizedStatus =
                status.trim().toUpperCase();

        // =================================================
        // CHECK DETAIL EXISTS
        // =================================================

        Integer orderId;

        try {

            orderId = jdbcTemplate.queryForObject(
                    """
                    SELECT order_id
                    FROM order_details
                    WHERE order_detail_id = ?
                    """,
                    Integer.class,
                    orderDetailId);

        } catch (Exception e) {

            return 0;
        }

        if (orderId == null) {
            return 0;
        }

        // =================================================
        // UPDATE DETAIL STATUS
        // =================================================

        int updatedRows;

        switch (normalizedStatus) {

            case "NEW":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'NEW',
                            prepared_by = NULL,
                            served_at = NULL
                        WHERE order_detail_id = ?
                        """,
                        orderDetailId);

                break;

            case "ACCEPTED":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'ACCEPTED',
                            prepared_by = ?,
                            served_at = NULL
                        WHERE order_detail_id = ?
                        """,
                        chefId,
                        orderDetailId);

                break;

            case "PREPARING":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'PREPARING',
                            prepared_by = ?,
                            served_at = NULL
                        WHERE order_detail_id = ?
                        """,
                        chefId,
                        orderDetailId);

                break;

            case "READY":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'READY',
                            prepared_by = ?,
                            served_at = NULL
                        WHERE order_detail_id = ?
                        """,
                        chefId,
                        orderDetailId);

                break;

            case "SERVED":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'SERVED',
                            prepared_by = ?,
                            served_at = NOW()
                        WHERE order_detail_id = ?
                        """,
                        chefId,
                        orderDetailId);

                break;

            case "CANCELLED":

                updatedRows = jdbcTemplate.update(
                        """
                        UPDATE order_details
                        SET
                            kitchen_status = 'CANCELLED',
                            prepared_by = ?,
                            served_at = NULL
                        WHERE order_detail_id = ?
                        """,
                        chefId,
                        orderDetailId);

                break;

            default:

                return 0;
        }

        if (updatedRows <= 0) {
            return 0;
        }

        // =================================================
        // SYNCHRONIZE PARENT ORDER STATUS
        // =================================================

        synchronizeOrderStatus(orderId);

        return updatedRows;
    }

    // =====================================================
    // SYNCHRONIZE ORDER STATUS
    // =====================================================

    private void synchronizeOrderStatus(Integer orderId) {

        /*
         * Priority:
         *
         * CANCELLED
         * SERVED
         * READY
         * PREPARING
         * ACCEPTED
         * NEW
         */

        Integer totalDetails =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        """,
                        Integer.class,
                        orderId);

        if (totalDetails == null ||
                totalDetails == 0) {

            return;
        }

        Integer cancelled =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        AND kitchen_status = 'CANCELLED'
                        """,
                        Integer.class,
                        orderId);

        Integer served =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        AND kitchen_status = 'SERVED'
                        """,
                        Integer.class,
                        orderId);

        Integer ready =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        AND kitchen_status = 'READY'
                        """,
                        Integer.class,
                        orderId);

        Integer preparing =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        AND kitchen_status = 'PREPARING'
                        """,
                        Integer.class,
                        orderId);

        Integer accepted =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        AND kitchen_status = 'ACCEPTED'
                        """,
                        Integer.class,
                        orderId);

        String newStatus = "NEW";

        if (cancelled != null &&
                cancelled.equals(totalDetails)) {

            newStatus = "CANCELLED";

        } else if (served != null &&
                served.equals(totalDetails)) {

            newStatus = "SERVED";

        } else if (ready != null &&
                ready.equals(totalDetails)) {

            newStatus = "READY";

        } else if (preparing != null &&
                preparing > 0) {

            newStatus = "PREPARING";

        } else if (accepted != null &&
                accepted > 0) {

            newStatus = "ACCEPTED";
        }

        jdbcTemplate.update(
                """
                UPDATE orders
                SET order_status = ?
                WHERE order_id = ?
                """,
                newStatus,
                orderId);
    }

    // =====================================================
    // GET KITCHEN ORDER DETAIL
    // =====================================================

    public KitchenOrderDTO getKitchenOrderDetail(
            Integer orderDetailId) {

        String sql = """
                SELECT
                    od.order_detail_id AS orderDetailId,
                    od.order_id AS orderId,
                    o.order_no AS orderNo,
                    c.customer_name AS customerName,
                    rt.table_number AS tableNumber,
                    od.item_id AS itemId,
                    m.item_name AS itemName,
                    od.quantity AS quantity,
                    od.price AS price,
                    od.subtotal AS subtotal,
                    od.kitchen_note AS kitchenNote,
                    od.kitchen_status AS kitchenStatus,
                    od.prepared_by AS preparedBy,
                    u.full_name AS preparedByName,
                    o.order_date AS orderDate,
                    od.served_at AS servedAt

                FROM order_details od

                INNER JOIN orders o
                    ON od.order_id = o.order_id

                LEFT JOIN customer c
                    ON o.customer_id = c.customer_id

                LEFT JOIN restaurant_tables rt
                    ON o.table_id = rt.table_id

                INNER JOIN menu m
                    ON od.item_id = m.item_id

                LEFT JOIN users u
                    ON od.prepared_by = u.user_id

                WHERE od.order_detail_id = ?
                """;

        List<KitchenOrderDTO> result =
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(
                                KitchenOrderDTO.class),
                        orderDetailId);

        if (result.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Kitchen order item not found with id : "
                            + orderDetailId);
        }

        return result.get(0);
    }
}

