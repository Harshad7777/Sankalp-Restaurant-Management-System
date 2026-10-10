package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Inventory;
import com.restaurant.model.InventoryLog;
import com.restaurant.repository.query.InventoryQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class InventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Inventory mapInventoryRow(ResultSet rs, int rowNum) throws SQLException {
        Inventory item = new Inventory();
        item.setInventoryId(rs.getInt("inventory_id"));
        try {
            item.setRestaurantId(rs.getInt("restaurant_id"));
        } catch (SQLException ignored) {}
        item.setItemName(rs.getString("item_name"));
        item.setCategory(rs.getString("category"));
        item.setQuantity(rs.getDouble("quantity"));
        item.setUnit(rs.getString("unit"));
        item.setMinThreshold(rs.getDouble("min_threshold"));
        item.setCostPerUnit(rs.getDouble("cost_per_unit"));
        item.setSupplierName(rs.getString("supplier_name"));
        item.setSupplierContact(rs.getString("supplier_contact"));

        Timestamp lastRestocked = rs.getTimestamp("last_restocked");
        if (lastRestocked != null) {
            item.setLastRestocked(lastRestocked.toLocalDateTime());
        }
        item.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            item.setCreatedAt(createdAt.toLocalDateTime());
        }
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            item.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return item;
    }

    private InventoryLog mapInventoryLogRow(ResultSet rs, int rowNum) throws SQLException {
        return InventoryLog.builder()
                .logId(rs.getInt("log_id"))
                .inventoryId(rs.getInt("inventory_id"))
                .actionType(rs.getString("action_type"))
                .quantity(rs.getDouble("quantity"))
                .previousQuantity(rs.getDouble("previous_quantity"))
                .newQuantity(rs.getDouble("new_quantity"))
                .reason(rs.getString("reason"))
                .performedBy(rs.getString("performed_by"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .build();
    }

    public List<Inventory> findAll() {
        return jdbcTemplate.query(InventoryQueries.FIND_ALL, this::mapInventoryRow, SecurityUtil.getCurrentRestaurantId());
    }

    public Inventory findById(int id) {
        List<Inventory> result = jdbcTemplate.query(InventoryQueries.FIND_BY_ID, this::mapInventoryRow, id, SecurityUtil.getCurrentRestaurantId());
        return result.isEmpty() ? null : result.get(0);
    }

    public boolean existsByName(String name) {
        Integer count = jdbcTemplate.queryForObject(InventoryQueries.EXISTS_BY_NAME, Integer.class, name, SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public boolean existsByNameAndNotId(String name, int id) {
        Integer count = jdbcTemplate.queryForObject(InventoryQueries.EXISTS_BY_NAME_AND_NOT_ID, Integer.class, name, id, SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public int save(Inventory item) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                InventoryQueries.INSERT_ITEM,
                resId,
                item.getItemName(),
                item.getCategory(),
                item.getQuantity(),
                item.getUnit(),
                item.getMinThreshold(),
                item.getCostPerUnit(),
                item.getSupplierName(),
                item.getSupplierContact(),
                item.getLastRestocked() != null ? Timestamp.valueOf(item.getLastRestocked()) : null,
                item.getStatus()
        );
    }

    public int update(Inventory item) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                InventoryQueries.UPDATE_ITEM,
                item.getItemName(),
                item.getCategory(),
                item.getQuantity(),
                item.getUnit(),
                item.getMinThreshold(),
                item.getCostPerUnit(),
                item.getSupplierName(),
                item.getSupplierContact(),
                item.getStatus(),
                item.getInventoryId(),
                resId
        );
    }

    public int updateStockAndStatus(int id, double newQuantity, String status, String actionType) {
        return jdbcTemplate.update(
                InventoryQueries.UPDATE_STOCK_AND_STATUS,
                newQuantity,
                status,
                actionType,
                id,
                SecurityUtil.getCurrentRestaurantId()
        );
    }

    public int delete(int id) {
        return jdbcTemplate.update(InventoryQueries.DELETE_ITEM, id, SecurityUtil.getCurrentRestaurantId());
    }

    public List<String> getDistinctCategories() {
        return jdbcTemplate.queryForList(InventoryQueries.GET_DISTINCT_CATEGORIES, String.class, SecurityUtil.getCurrentRestaurantId());
    }

    public int saveLog(InventoryLog log) {
        return jdbcTemplate.update(
                InventoryQueries.INSERT_LOG,
                log.getInventoryId(),
                log.getActionType(),
                log.getQuantity(),
                log.getPreviousQuantity(),
                log.getNewQuantity(),
                log.getReason(),
                log.getPerformedBy()
        );
    }

    public List<InventoryLog> findLogsByInventoryId(int inventoryId) {
        return jdbcTemplate.query(InventoryQueries.FIND_LOGS_BY_INVENTORY_ID, this::mapInventoryLogRow, inventoryId, SecurityUtil.getCurrentRestaurantId());
    }

    public InventorySummaryDTO getSummary() {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        Long total = jdbcTemplate.queryForObject(InventoryQueries.COUNT_TOTAL, Long.class, resId);
        Long inStock = jdbcTemplate.queryForObject(InventoryQueries.COUNT_BY_STATUS, Long.class, "IN_STOCK", resId);
        Long lowStock = jdbcTemplate.queryForObject(InventoryQueries.COUNT_BY_STATUS, Long.class, "LOW_STOCK", resId);
        Long outOfStock = jdbcTemplate.queryForObject(InventoryQueries.COUNT_BY_STATUS, Long.class, "OUT_OF_STOCK", resId);
        Double totalVal = jdbcTemplate.queryForObject(InventoryQueries.SUM_TOTAL_VALUE, Double.class, resId);

        return InventorySummaryDTO.builder()
                .totalItems(total != null ? total : 0)
                .inStockItems(inStock != null ? inStock : 0)
                .lowStockItems(lowStock != null ? lowStock : 0)
                .outOfStockItems(outOfStock != null ? outOfStock : 0)
                .totalInventoryValue(totalVal != null ? Math.round(totalVal * 100.0) / 100.0 : 0.0)
                .build();
    }

    public PageResponse<Inventory> findFilteredPage(PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("SELECT * FROM inventory WHERE restaurant_id = ? ");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM inventory WHERE restaurant_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getSearch() != null && !req.getSearch().trim().isEmpty()) {
            String searchPattern = "%" + req.getSearch().trim().toLowerCase() + "%";
            sql.append("AND (LOWER(item_name) LIKE ? OR LOWER(supplier_name) LIKE ?) ");
            countSql.append("AND (LOWER(item_name) LIKE ? OR LOWER(supplier_name) LIKE ?) ");
            params.add(searchPattern);
            params.add(searchPattern);
        }

        if (req.getCategory() != null && !req.getCategory().trim().isEmpty() && !"ALL".equalsIgnoreCase(req.getCategory())) {
            sql.append("AND category = ? ");
            countSql.append("AND category = ? ");
            params.add(req.getCategory().trim());
        }

        if (req.getStatus() != null && !req.getStatus().trim().isEmpty() && !"ALL".equalsIgnoreCase(req.getStatus())) {
            sql.append("AND status = ? ");
            countSql.append("AND status = ? ");
            params.add(req.getStatus().trim());
        }

        Integer totalElements = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.toArray());
        long total = totalElements != null ? totalElements : 0;

        String sortBy = "item_name";
        if (req.getSortBy() != null && !req.getSortBy().trim().isEmpty()) {
            String col = req.getSortBy().trim().toLowerCase();
            if (col.equals("quantity") || col.equals("costperunit") || col.equals("status") || col.equals("category") || col.equals("updatedat")) {
                sortBy = req.getSortBy().trim();
            }
        }

        String direction = "ASC";
        if ("DESC".equalsIgnoreCase(req.getSortDirection())) {
            direction = "DESC";
        }

        sql.append("ORDER BY ").append(sortBy).append(" ").append(direction).append(" ");
        sql.append("LIMIT ? OFFSET ?");

        List<Object> pageParams = new ArrayList<>(params);
        pageParams.add(req.getSize());
        pageParams.add(req.getPage() * req.getSize());

        List<Inventory> content = jdbcTemplate.query(sql.toString(), this::mapInventoryRow, pageParams.toArray());
        int totalPages = (int) Math.ceil((double) total / req.getSize());

        return PageResponse.<Inventory>builder()
                .content(content)
                .page(req.getPage())
                .size(req.getSize())
                .totalElements(total)
                .totalPages(totalPages)
                .build();
    }
}
