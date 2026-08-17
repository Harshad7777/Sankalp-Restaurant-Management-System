package com.restaurant.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.repository.query.KitchenQueries;

@Repository
public class KitchenRepository {

    private final JdbcTemplate jdbcTemplate;

    public KitchenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getKitchenOrders() {
        return jdbcTemplate.queryForList(KitchenQueries.GET_KITCHEN_ORDERS);
    }

    public List<Map<String, Object>> getKitchenOrderById(int orderId) {
        return jdbcTemplate.queryForList(KitchenQueries.GET_KITCHEN_ORDER_BY_ID, orderId);
    }

    public boolean orderExists(int orderId) {
        Integer count = jdbcTemplate.queryForObject(
                KitchenQueries.CHECK_ORDER_EXISTS,
                Integer.class,
                orderId);
        return count != null && count > 0;
    }

    public boolean detailExists(int detailId) {
        Integer count = jdbcTemplate.queryForObject(
                KitchenQueries.CHECK_DETAIL_EXISTS,
                Integer.class,
                detailId);
        return count != null && count > 0;
    }

    public Integer getOrderIdFromDetail(int detailId) {
        List<Integer> result = jdbcTemplate.query(
                KitchenQueries.GET_ORDER_ID_FROM_DETAIL,
                (rs, rowNum) -> rs.getInt("order_id"),
                detailId);
        return result.isEmpty() ? null : result.get(0);
    }

    public String getKitchenStatus(int detailId) {
        List<String> result = jdbcTemplate.query(
                KitchenQueries.GET_KITCHEN_STATUS,
                (rs, rowNum) -> rs.getString("kitchen_status"),
                detailId);
        return result.isEmpty() ? null : result.get(0);
    }

    public int updateKitchenStatus(int detailId, String status) {
        return jdbcTemplate.update(
                KitchenQueries.UPDATE_KITCHEN_STATUS,
                status,
                detailId);
    }

    public int updatePreparedBy(int detailId, Integer chefId) {
        return jdbcTemplate.update(
                KitchenQueries.UPDATE_PREPARED_BY,
                chefId,
                detailId);
    }

    public int updateServedAt(int detailId) {
        return jdbcTemplate.update(
                KitchenQueries.UPDATE_SERVED_AT,
                detailId);
    }

    public int clearServedAt(int detailId) {
        return jdbcTemplate.update(
                KitchenQueries.CLEAR_SERVED_AT,
                detailId);
    }

    public int updateOrderStatus(int orderId, String status) {
        return jdbcTemplate.update(
                KitchenQueries.UPDATE_ORDER_STATUS,
                status,
                orderId);
    }

    public String getOrderStatus(int orderId) {
        List<String> result = jdbcTemplate.query(
                KitchenQueries.GET_ORDER_STATUS,
                (rs, rowNum) -> rs.getString("order_status"),
                orderId);
        return result.isEmpty() ? null : result.get(0);
    }

    public Map<String, Object> getOrderKitchenSummary(int orderId) {
        return jdbcTemplate.queryForMap(
                KitchenQueries.GET_ORDER_KITCHEN_SUMMARY,
                orderId);
    }

    public List<String> getKitchenStatuses(int orderId) {
        return jdbcTemplate.queryForList(
                KitchenQueries.GET_KITCHEN_STATUSES,
                String.class,
                orderId);
    }

    public List<Integer> getDetailIds(int orderId) {
        return jdbcTemplate.queryForList(
                KitchenQueries.GET_DETAIL_IDS,
                Integer.class,
                orderId);
    }

    public boolean hasOrderDetails(int orderId) {
        Integer count = jdbcTemplate.queryForObject(
                KitchenQueries.HAS_ORDER_DETAILS,
                Integer.class,
                orderId);
        return count != null && count > 0;
    }

    public int getItemCountByStatus(int orderId, String status) {
        Integer count = jdbcTemplate.queryForObject(
                KitchenQueries.GET_ITEM_COUNT_BY_STATUS,
                Integer.class,
                orderId,
                status);
        return count == null ? 0 : count;
    }

    public int resetKitchenStatus(int detailId) {
        return jdbcTemplate.update(
                KitchenQueries.RESET_KITCHEN_STATUS,
                detailId);
    }
}