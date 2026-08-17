package com.restaurant.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KitchenRepository {

    private final JdbcTemplate jdbcTemplate;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public KitchenRepository(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate =
                jdbcTemplate;
    }


    // =========================================================
    // GET ALL ACTIVE KITCHEN ORDERS
    // =========================================================

    public List<Map<String, Object>> getKitchenOrders() {

        String sql = """
            SELECT
                o.order_id AS orderId,
                o.customer_id AS customerId,

                COALESCE(
                    c.customer_name,
                    'Walk-in Customer'
                ) AS customerName,

                o.table_id AS tableId,

                o.order_date AS orderDate,

                COALESCE(
                    o.order_status,
                    'NEW'
                ) AS orderStatus,

                COALESCE(
                    o.remarks,
                    ''
                ) AS remarks,

                COALESCE(
                    o.total_amount,
                    0
                ) AS totalAmount,

                od.order_detail_id AS detailId,

                od.item_id AS itemId,

                COALESCE(
                    m.item_name,
                    'Unknown Item'
                ) AS itemName,

                COALESCE(
                    od.quantity,
                    1
                ) AS quantity,

                COALESCE(
                    od.price,
                    0
                ) AS price,

                COALESCE(
                    od.subtotal,
                    0
                ) AS subtotal,

                COALESCE(
                    od.kitchen_status,
                    'NEW'
                ) AS kitchenStatus,

                od.is_extra AS isExtra,

                od.kitchen_note AS kitchenNote,

                od.prepared_by AS preparedBy,

                od.served_at AS servedAt,

                od.created_at AS createdAt

            FROM orders o

            LEFT JOIN customer c
                ON c.customer_id = o.customer_id

            INNER JOIN order_details od
                ON od.order_id = o.order_id

            LEFT JOIN menu m
                ON m.item_id = od.item_id

            WHERE
                COALESCE(
                    od.kitchen_status,
                    'NEW'
                ) IN (
                    'NEW',
                    'ACCEPTED',
                    'PREPARING',
                    'READY',
                    'SERVED'
                )

                AND COALESCE(
                    o.order_status,
                    'NEW'
                ) NOT IN (
                    'CANCELLED',
                    'PAID',
                    'CLOSED',
                    'BILLED'
                )

            ORDER BY
                o.order_date DESC,
                od.order_detail_id ASC
            """;


        return jdbcTemplate.queryForList(
                sql
        );
    }


    // =========================================================
    // GET SINGLE KITCHEN ORDER
    // =========================================================

    public List<Map<String, Object>> getKitchenOrderById(
            int orderId) {

        String sql = """
            SELECT
                o.order_id AS orderId,

                o.customer_id AS customerId,

                COALESCE(
                    c.customer_name,
                    'Walk-in Customer'
                ) AS customerName,

                o.table_id AS tableId,

                o.order_date AS orderDate,

                COALESCE(
                    o.order_status,
                    'NEW'
                ) AS orderStatus,

                COALESCE(
                    o.remarks,
                    ''
                ) AS remarks,

                COALESCE(
                    o.total_amount,
                    0
                ) AS totalAmount,

                od.order_detail_id AS detailId,

                od.item_id AS itemId,

                COALESCE(
                    m.item_name,
                    'Unknown Item'
                ) AS itemName,

                COALESCE(
                    od.quantity,
                    1
                ) AS quantity,

                COALESCE(
                    od.price,
                    0
                ) AS price,

                COALESCE(
                    od.subtotal,
                    0
                ) AS subtotal,

                COALESCE(
                    od.kitchen_status,
                    'NEW'
                ) AS kitchenStatus,

                od.is_extra AS isExtra,

                od.kitchen_note AS kitchenNote,

                od.prepared_by AS preparedBy,

                od.served_at AS servedAt,

                od.created_at AS createdAt

            FROM orders o

            LEFT JOIN customer c
                ON c.customer_id = o.customer_id

            INNER JOIN order_details od
                ON od.order_id = o.order_id

            LEFT JOIN menu m
                ON m.item_id = od.item_id

            WHERE
                o.order_id = ?

            ORDER BY
                od.order_detail_id ASC
            """;


        return jdbcTemplate.queryForList(
                sql,
                orderId
        );
    }


    // =========================================================
    // CHECK ORDER EXISTS
    // =========================================================

    public boolean orderExists(
            int orderId) {

        Integer count =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM orders
                        WHERE order_id = ?
                        """,
                        Integer.class,
                        orderId
                );


        return count != null &&
                count > 0;
    }


    // =========================================================
    // CHECK DETAIL EXISTS
    // =========================================================

    public boolean detailExists(
            int detailId) {

        Integer count =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_detail_id = ?
                        """,
                        Integer.class,
                        detailId
                );


        return count != null &&
                count > 0;
    }


    // =========================================================
    // GET ORDER ID FROM DETAIL
    // =========================================================

    public Integer getOrderIdFromDetail(
            int detailId) {

        List<Integer> result =
                jdbcTemplate.query(
                        """
                        SELECT order_id
                        FROM order_details
                        WHERE order_detail_id = ?
                        """,

                        (rs, rowNum) ->
                                rs.getInt("order_id"),

                        detailId
                );


        if (result.isEmpty()) {

            return null;
        }


        return result.get(0);
    }


    // =========================================================
    // GET CURRENT KITCHEN STATUS
    // =========================================================

    public String getKitchenStatus(
            int detailId) {

        List<String> result =
                jdbcTemplate.query(
                        """
                        SELECT
                            COALESCE(
                                kitchen_status,
                                'NEW'
                            ) AS kitchen_status

                        FROM order_details

                        WHERE order_detail_id = ?
                        """,

                        (rs, rowNum) ->
                                rs.getString(
                                        "kitchen_status"
                                ),

                        detailId
                );


        if (result.isEmpty()) {

            return null;
        }


        return result.get(0);
    }


    // =========================================================
    // UPDATE KITCHEN STATUS
    // =========================================================

    public int updateKitchenStatus(
            int detailId,
            String status) {

        return jdbcTemplate.update(
                """
                UPDATE order_details
                SET kitchen_status = ?
                WHERE order_detail_id = ?
                """,

                status,
                detailId
        );
    }


    // =========================================================
    // UPDATE PREPARED BY
    // =========================================================

    public int updatePreparedBy(
            int detailId,
            Integer chefId) {

        return jdbcTemplate.update(
                """
                UPDATE order_details
                SET prepared_by = ?
                WHERE order_detail_id = ?
                """,

                chefId,
                detailId
        );
    }


    // =========================================================
    // UPDATE SERVED AT
    // =========================================================

    public int updateServedAt(
            int detailId) {

        return jdbcTemplate.update(
                """
                UPDATE order_details
                SET served_at = CURRENT_TIMESTAMP
                WHERE order_detail_id = ?
                """,

                detailId
        );
    }


    // =========================================================
    // CLEAR SERVED AT
    // =========================================================

    public int clearServedAt(
            int detailId) {

        return jdbcTemplate.update(
                """
                UPDATE order_details
                SET served_at = NULL
                WHERE order_detail_id = ?
                """,

                detailId
        );
    }


    // =========================================================
    // UPDATE MAIN ORDER STATUS
    // =========================================================
    //
    // IMPORTANT:
    //
    // This method accepts ONLY statuses that exist
    // in orders.order_status.
    //
    // =========================================================

    public int updateOrderStatus(
            int orderId,
            String status) {

        return jdbcTemplate.update(
                """
                UPDATE orders
                SET order_status = ?
                WHERE order_id = ?
                """,

                status,
                orderId
        );
    }


    // =========================================================
    // GET MAIN ORDER STATUS
    // =========================================================

    public String getOrderStatus(
            int orderId) {

        List<String> result =
                jdbcTemplate.query(
                        """
                        SELECT
                            COALESCE(
                                order_status,
                                'NEW'
                            ) AS order_status

                        FROM orders

                        WHERE order_id = ?
                        """,

                        (rs, rowNum) ->
                                rs.getString(
                                        "order_status"
                                ),

                        orderId
                );


        if (result.isEmpty()) {

            return null;
        }


        return result.get(0);
    }


    // =========================================================
    // GET ORDER KITCHEN SUMMARY
    // =========================================================

    public Map<String, Object>
    getOrderKitchenSummary(
            int orderId) {

        String sql = """
            SELECT

                COUNT(*) AS totalItems,

                SUM(
                    CASE
                        WHEN COALESCE(
                            kitchen_status,
                            'NEW'
                        ) = 'NEW'
                        THEN 1
                        ELSE 0
                    END
                ) AS newItems,

                SUM(
                    CASE
                        WHEN kitchen_status = 'ACCEPTED'
                        THEN 1
                        ELSE 0
                    END
                ) AS acceptedItems,

                SUM(
                    CASE
                        WHEN kitchen_status = 'PREPARING'
                        THEN 1
                        ELSE 0
                    END
                ) AS preparingItems,

                SUM(
                    CASE
                        WHEN kitchen_status = 'READY'
                        THEN 1
                        ELSE 0
                    END
                ) AS readyItems,

                SUM(
                    CASE
                        WHEN kitchen_status = 'SERVED'
                        THEN 1
                        ELSE 0
                    END
                ) AS servedItems

            FROM order_details

            WHERE order_id = ?
            """;


        return jdbcTemplate.queryForMap(
                sql,
                orderId
        );
    }


    // =========================================================
    // GET KITCHEN STATUSES
    // =========================================================

    public List<String> getKitchenStatuses(
            int orderId) {

        return jdbcTemplate.queryForList(
                """
                SELECT
                    COALESCE(
                        kitchen_status,
                        'NEW'
                    )

                FROM order_details

                WHERE order_id = ?

                ORDER BY order_detail_id ASC
                """,

                String.class,

                orderId
        );
    }


    // =========================================================
    // GET ALL DETAIL IDS
    // =========================================================

    public List<Integer> getDetailIds(
            int orderId) {

        return jdbcTemplate.queryForList(
                """
                SELECT
                    order_detail_id

                FROM order_details

                WHERE order_id = ?

                ORDER BY order_detail_id ASC
                """,

                Integer.class,

                orderId
        );
    }


    // =========================================================
    // CHECK ORDER DETAILS
    // =========================================================

    public boolean hasOrderDetails(
            int orderId) {

        Integer count =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details
                        WHERE order_id = ?
                        """,

                        Integer.class,

                        orderId
                );


        return count != null &&
                count > 0;
    }


    // =========================================================
    // GET ITEM COUNT BY STATUS
    // =========================================================

    public int getItemCountByStatus(
            int orderId,
            String status) {

        Integer count =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM order_details

                        WHERE order_id = ?

                        AND COALESCE(
                            kitchen_status,
                            'NEW'
                        ) = ?
                        """,

                        Integer.class,

                        orderId,
                        status
                );


        return count == null
                ? 0
                : count;
    }


    // =========================================================
    // RESET KITCHEN STATUS
    // =========================================================

    public int resetKitchenStatus(
            int detailId) {

        return jdbcTemplate.update(
                """
                UPDATE order_details
                SET
                    kitchen_status = 'NEW',
                    prepared_by = NULL,
                    served_at = NULL
                WHERE order_detail_id = ?
                """,

                detailId
        );
    }
}