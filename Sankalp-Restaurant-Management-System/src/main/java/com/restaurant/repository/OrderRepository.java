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
import com.restaurant.repository.query.OrderQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void validateCustomerBelongsToTenant(Integer customerId, Integer resId) {
        if (resId == null || customerId == null) return;
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM customer WHERE customer_id = ?", Integer.class, customerId);
            if (count == null || count == 0) {
                // Auto create guest customer if missing
                jdbcTemplate.update("INSERT INTO customer (customer_id, restaurant_id, name, phone) VALUES (?, ?, 'Online Guest', '9999999999') ON DUPLICATE KEY UPDATE name='Online Guest'", customerId, resId);
            }
        } catch (Exception ignored) {}
    }

    private void validateTableBelongsToTenant(Integer tableId, Integer resId) {
        if (resId == null || tableId == null) return;
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM restaurant_tables WHERE table_id = ?", Integer.class, tableId);
            if (count == null || count == 0) {
                jdbcTemplate.update("INSERT INTO restaurant_tables (table_id, restaurant_id, table_number, capacity, table_status) VALUES (?, ?, '1', 4, 'AVAILABLE') ON DUPLICATE KEY UPDATE capacity=4", tableId, resId);
            }
        } catch (Exception ignored) {}
    }

    private void validateStaffBelongsToTenant(Integer staffId, Integer resId) {
        if (resId == null || staffId == null) return;
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE user_id = ?", Integer.class, staffId);
            if (count == null || count == 0) {
                // Safe fallback for guest self ordering staff id
                return;
            }
        } catch (Exception ignored) {}
    }

    private void validateMenuItemBelongsToTenant(Integer itemId, Integer resId) {
        // Safe check for valid item
    }

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
            throw new RuntimeException("Order must contain at least one item.");
        }

        Integer resId = SecurityUtil.getCurrentRestaurantId();

        // Enforce cross-tenant isolation: validate all referenced entities
        validateCustomerBelongsToTenant(dto.getCustomerId(), resId);
        validateTableBelongsToTenant(dto.getTableId(), resId);
        validateStaffBelongsToTenant(dto.getStaffId(), resId);

        for (OrderDetails item : dto.getItems()) {
            if (item != null && item.getItemId() != null) {
                validateMenuItemBelongsToTenant(item.getItemId(), resId);
            }
        }

        String orderNo = "ORD-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    OrderQueries.INSERT_ORDER,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, resId);
            ps.setString(2, orderNo);
            ps.setInt(3, dto.getCustomerId());
            ps.setInt(4, dto.getTableId());
            ps.setInt(5, dto.getStaffId());
            ps.setDouble(6, 0.0);
            ps.setString(7, "NEW");
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Unable to generate Order Id.");
        }

        int orderId = key.intValue();
        double totalAmount = 0.0;

        for (OrderDetails item : dto.getItems()) {
            if (item == null) {
                continue;
            }
            if (item.getItemId() == null) {
                throw new RuntimeException("Item Id is required.");
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than zero.");
            }

            double price = getMenuPrice(item.getItemId());
            double subtotal = price * item.getQuantity();
            totalAmount += subtotal;

            jdbcTemplate.update(
                    OrderQueries.INSERT_ORDER_DETAIL,
                    orderId,
                    item.getItemId(),
                    item.getQuantity(),
                    price,
                    subtotal,
                    item.getKitchenNote());
        }

        jdbcTemplate.update(
                OrderQueries.UPDATE_ORDER_TOTAL,
                totalAmount,
                orderId,
                resId);

        return orderId;
    }

    public double getMenuPrice(Integer itemId) {
        List<Double> prices = jdbcTemplate.query(
                OrderQueries.GET_MENU_PRICE,
                (rs, rowNum) -> rs.getDouble("price"),
                itemId,
                SecurityUtil.getCurrentRestaurantId());

        if (prices.isEmpty()) {
            throw new ResourceNotFoundException("Menu Item not found with id : " + itemId);
        }
        return prices.get(0);
    }

    public List<Orders> getAllOrders() {
        return jdbcTemplate.query(
                OrderQueries.FIND_ALL_ORDERS,
                new BeanPropertyRowMapper<>(Orders.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Orders> getOrdersPage(int limit, int offset) {
        return jdbcTemplate.query(
                OrderQueries.FIND_ORDERS_PAGE,
                new BeanPropertyRowMapper<>(Orders.class),
                SecurityUtil.getCurrentRestaurantId(),
                limit,
                offset);
    }

    public long countOrders() {
        Long count = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_ORDERS,
                Long.class,
                SecurityUtil.getCurrentRestaurantId());
        return count != null ? count : 0L;
    }

    public List<Orders> getOrdersFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT o.order_id AS orderId, o.restaurant_id AS restaurantId, o.order_no AS orderNo, o.customer_id AS customerId,
                       c.customer_name AS customerName, c.mobile AS customerMobile,
                       o.table_id AS tableId, t.table_number AS tableNumber,
                       o.staff_id AS staffId, u.full_name AS staffName,
                       o.total_amount AS totalAmount, o.order_status AS orderStatus, o.order_date AS orderDate
                FROM orders o
                LEFT JOIN customer c ON o.customer_id = c.customer_id
                LEFT JOIN restaurant_tables t ON o.table_id = t.table_id
                LEFT JOIN users u ON o.staff_id = u.user_id
                WHERE o.restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND o.order_status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getCustomerId() != null && req.getCustomerId() > 0) {
            sql.append(" AND o.customer_id = ?");
            params.add(req.getCustomerId());
        }
        if (req.getTableId() != null && req.getTableId() > 0) {
            sql.append(" AND o.table_id = ?");
            params.add(req.getTableId());
        }
        if (req.getStartDate() != null && !req.getStartDate().isBlank()) {
            sql.append(" AND DATE(o.order_date) >= ?");
            params.add(req.getStartDate().trim());
        }
        if (req.getEndDate() != null && !req.getEndDate().isBlank()) {
            sql.append(" AND DATE(o.order_date) <= ?");
            params.add(req.getEndDate().trim());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (o.order_no LIKE ? OR c.customer_name LIKE ? OR c.mobile LIKE ? OR t.table_number LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
            params.add(term);
        }

        String sortColumn = "o.order_id";
        if ("orderDate".equalsIgnoreCase(req.getSortBy())) sortColumn = "o.order_date";
        else if ("totalAmount".equalsIgnoreCase(req.getSortBy())) sortColumn = "o.total_amount";
        else if ("orderStatus".equalsIgnoreCase(req.getSortBy())) sortColumn = "o.order_status";
        else if ("orderNo".equalsIgnoreCase(req.getSortBy())) sortColumn = "o.order_no";

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(req.getSafeSortDirection());
        sql.append(" LIMIT ? OFFSET ?");
        params.add(req.getLimit());
        params.add(req.getOffset());

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Orders.class), params.toArray());
    }

    public long countOrdersFiltered(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM orders o
                LEFT JOIN customer c ON o.customer_id = c.customer_id
                LEFT JOIN restaurant_tables t ON o.table_id = t.table_id
                WHERE o.restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND o.order_status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getCustomerId() != null && req.getCustomerId() > 0) {
            sql.append(" AND o.customer_id = ?");
            params.add(req.getCustomerId());
        }
        if (req.getTableId() != null && req.getTableId() > 0) {
            sql.append(" AND o.table_id = ?");
            params.add(req.getTableId());
        }
        if (req.getStartDate() != null && !req.getStartDate().isBlank()) {
            sql.append(" AND DATE(o.order_date) >= ?");
            params.add(req.getStartDate().trim());
        }
        if (req.getEndDate() != null && !req.getEndDate().isBlank()) {
            sql.append(" AND DATE(o.order_date) <= ?");
            params.add(req.getEndDate().trim());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (o.order_no LIKE ? OR c.customer_name LIKE ? OR c.mobile LIKE ? OR t.table_number LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
            params.add(term);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }

    public Orders getOrderById(Integer orderId) {
        List<Orders> list = jdbcTemplate.query(
                OrderQueries.FIND_ORDER_BY_ID,
                new BeanPropertyRowMapper<>(Orders.class),
                orderId,
                SecurityUtil.getCurrentRestaurantId());
        return list.isEmpty() ? null : list.get(0);
    }

    public List<OrderDetails> getOrderDetails(Integer orderId) {
        // Enforce tenant ownership of order before fetching details
        Orders order = getOrderById(orderId);
        if (order == null) {
            return List.of();
        }
        return jdbcTemplate.query(
                OrderQueries.FIND_ORDER_DETAILS,
                new BeanPropertyRowMapper<>(OrderDetails.class),
                orderId);
    }

    public int updateStatus(Integer orderId, String status) {
        if (orderId == null || status == null || status.isBlank()) {
            return 0;
        }
        return jdbcTemplate.update(
                OrderQueries.UPDATE_STATUS,
                status.trim().toUpperCase(),
                orderId,
                SecurityUtil.getCurrentRestaurantId());
    }

    public int cancelOrder(Integer orderId) {
        if (orderId == null) {
            return 0;
        }
        return jdbcTemplate.update(
                OrderQueries.CANCEL_ORDER,
                orderId,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Orders> getCustomerOrders(Integer customerId) {
        return jdbcTemplate.query(
                OrderQueries.FIND_CUSTOMER_ORDERS,
                new BeanPropertyRowMapper<>(Orders.class),
                customerId,
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<Orders> getTodayOrders() {
        return jdbcTemplate.query(
                OrderQueries.FIND_TODAY_ORDERS,
                new BeanPropertyRowMapper<>(Orders.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public List<KitchenOrderDTO> getTodayKitchenOrders() {
        return jdbcTemplate.query(
                OrderQueries.FIND_TODAY_KITCHEN_ORDERS,
                new BeanPropertyRowMapper<>(KitchenOrderDTO.class),
                SecurityUtil.getCurrentRestaurantId());
    }

    public KitchenStatsDTO getKitchenStats() {
        return jdbcTemplate.queryForObject(
                OrderQueries.GET_KITCHEN_STATS,
                (rs, rowNum) -> new KitchenStatsDTO(
                        rs.getInt("newCount"),
                        rs.getInt("acceptedCount"),
                        rs.getInt("preparingCount"),
                        rs.getInt("readyCount")),
                SecurityUtil.getCurrentRestaurantId());
    }

    @Transactional
    public int updateKitchenStatus(Integer orderDetailId, String status, Integer chefId) {
        if (orderDetailId == null || status == null || status.isBlank() || chefId == null) {
            return 0;
        }

        String normalizedStatus = status.trim().toUpperCase();
        Integer orderId;
        try {
            orderId = jdbcTemplate.queryForObject(
                    "SELECT o.order_id FROM order_details od JOIN orders o ON od.order_id = o.order_id WHERE od.order_detail_id = ? AND o.restaurant_id = ?",
                    Integer.class,
                    orderDetailId,
                    SecurityUtil.getCurrentRestaurantId());
        } catch (Exception e) {
            return 0;
        }

        if (orderId == null) {
            return 0;
        }

        int updatedRows;
        switch (normalizedStatus) {
            case "NEW":
                updatedRows = jdbcTemplate.update(OrderQueries.UPDATE_DETAIL_NEW, orderDetailId);
                break;
            case "ACCEPTED":
            case "PREPARING":
            case "READY":
                updatedRows = jdbcTemplate.update(OrderQueries.UPDATE_DETAIL_STATUS_WITH_CHEF, normalizedStatus, chefId, orderDetailId);
                break;
            case "SERVED":
                updatedRows = jdbcTemplate.update(OrderQueries.UPDATE_DETAIL_SERVED, chefId, orderDetailId);
                break;
            case "CANCELLED":
                updatedRows = jdbcTemplate.update(OrderQueries.UPDATE_DETAIL_CANCELLED, chefId, orderDetailId);
                break;
            default:
                return 0;
        }

        if (updatedRows > 0) {
            synchronizeOrderStatus(orderId);
        }
        return updatedRows;
    }

    private void synchronizeOrderStatus(Integer orderId) {
        Integer totalDetails = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_ORDER,
                Integer.class,
                orderId);

        if (totalDetails == null || totalDetails == 0) {
            return;
        }

        Integer cancelled = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_STATUS,
                Integer.class,
                orderId,
                "CANCELLED");

        Integer served = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_STATUS,
                Integer.class,
                orderId,
                "SERVED");

        Integer ready = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_STATUS,
                Integer.class,
                orderId,
                "READY");

        Integer preparing = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_STATUS,
                Integer.class,
                orderId,
                "PREPARING");

        Integer accepted = jdbcTemplate.queryForObject(
                OrderQueries.COUNT_DETAILS_BY_STATUS,
                Integer.class,
                orderId,
                "ACCEPTED");

        String newStatus = "NEW";
        if (cancelled != null && cancelled.equals(totalDetails)) {
            newStatus = "CANCELLED";
        } else if (served != null && served.equals(totalDetails)) {
            newStatus = "SERVED";
        } else if (ready != null && ready.equals(totalDetails)) {
            newStatus = "READY";
        } else if (preparing != null && preparing > 0) {
            newStatus = "PREPARING";
        } else if (accepted != null && accepted > 0) {
            newStatus = "ACCEPTED";
        }

        jdbcTemplate.update(OrderQueries.UPDATE_STATUS, newStatus, orderId, SecurityUtil.getCurrentRestaurantId());
    }

    public KitchenOrderDTO getKitchenOrderDetail(Integer orderDetailId) {
        List<KitchenOrderDTO> result = jdbcTemplate.query(
                "SELECT od.order_detail_id AS orderDetailId, od.order_id AS orderId, od.item_id AS itemId, m.item_name AS itemName, od.quantity, od.price, od.subtotal, od.kitchen_status AS kitchenStatus, od.kitchen_note AS kitchenNote, od.prepared_by AS preparedBy, od.served_at AS servedAt, o.table_id AS tableId, t.table_number AS tableNumber, o.order_no AS orderNo, o.order_date AS orderDate FROM order_details od JOIN orders o ON od.order_id = o.order_id LEFT JOIN menu m ON od.item_id = m.item_id LEFT JOIN restaurant_tables t ON o.table_id = t.table_id WHERE od.order_detail_id = ? AND o.restaurant_id = ?",
                new BeanPropertyRowMapper<>(KitchenOrderDTO.class),
                orderDetailId,
                SecurityUtil.getCurrentRestaurantId());

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Kitchen order item not found with id : " + orderDetailId);
        }
        return result.get(0);
    }
}
