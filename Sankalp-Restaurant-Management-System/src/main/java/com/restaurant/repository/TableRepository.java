package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Table;
import com.restaurant.repository.query.TableQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class TableRepository {

    private final JdbcTemplate jdbcTemplate;

    public TableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Table mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table table = new Table();
        table.setTableId(rs.getInt("table_id"));
        try {
            table.setRestaurantId(rs.getInt("restaurant_id"));
        } catch (SQLException ignored) {}
        table.setTableNumber(rs.getString("table_number"));
        table.setCapacity(rs.getInt("capacity"));
        table.setTableStatus(rs.getString("table_status"));
        try {
            table.setxPos(rs.getInt("x_pos"));
            table.setyPos(rs.getInt("y_pos"));
            table.setShape(rs.getString("shape"));
            table.setWidth(rs.getInt("width"));
            table.setHeight(rs.getInt("height"));
        } catch (SQLException ignored) {}
        return table;
    }

    public int updateFloorPlan(int tableId, int xPos, int yPos, String shape, int width, int height) {
        String sql = "UPDATE restaurant_tables SET x_pos = ?, y_pos = ?, shape = ?, width = ?, height = ? WHERE table_id = ?";
        return jdbcTemplate.update(sql, xPos, yPos, shape != null ? shape : "RECTANGLE", width, height, tableId);
    }

    public List<Table> findAll() {
        return jdbcTemplate.query(
                TableQueries.FIND_ALL,
                this::mapRow,
                SecurityUtil.getCurrentRestaurantId());
    }

    public Table findById(int id) {
        List<Table> result = jdbcTemplate.query(
                TableQueries.FIND_BY_ID,
                this::mapRow,
                id,
                SecurityUtil.getCurrentRestaurantId());
        return result.isEmpty() ? null : result.get(0);
    }

    public boolean existsByTableNumber(String tableNumber) {
        Integer count = jdbcTemplate.queryForObject(
                TableQueries.EXISTS_BY_TABLE_NUMBER,
                Integer.class,
                tableNumber,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public boolean existsByTableNumberAndNotId(String tableNumber, int tableId) {
        Integer count = jdbcTemplate.queryForObject(
                TableQueries.EXISTS_BY_TABLE_NUMBER_AND_NOT_ID,
                Integer.class,
                tableNumber,
                tableId,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public int save(Table table) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                TableQueries.INSERT_TABLE,
                resId,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus());
    }

    public int update(Table table) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                TableQueries.UPDATE_TABLE,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus(),
                table.getTableId(),
                resId);
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                TableQueries.DELETE_TABLE,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }
}