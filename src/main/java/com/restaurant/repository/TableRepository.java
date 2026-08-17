package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Table;
import com.restaurant.repository.query.TableQueries;

@Repository
public class TableRepository {

    private final JdbcTemplate jdbcTemplate;

    public TableRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Table mapRow(ResultSet rs, int rowNum) throws SQLException {
        Table table = new Table();
        table.setTableId(rs.getInt("table_id"));
        table.setTableNumber(rs.getString("table_number"));
        table.setCapacity(rs.getInt("capacity"));
        table.setTableStatus(rs.getString("table_status"));
        return table;
    }

    public List<Table> findAll() {
        return jdbcTemplate.query(
                TableQueries.FIND_ALL,
                this::mapRow);
    }

    public Table findById(int id) {
        List<Table> result = jdbcTemplate.query(
                TableQueries.FIND_BY_ID,
                this::mapRow,
                id);
        return result.isEmpty() ? null : result.get(0);
    }

    public boolean existsByTableNumber(String tableNumber) {
        Integer count = jdbcTemplate.queryForObject(
                TableQueries.EXISTS_BY_TABLE_NUMBER,
                Integer.class,
                tableNumber);
        return count != null && count > 0;
    }

    public boolean existsByTableNumberAndNotId(String tableNumber, int tableId) {
        Integer count = jdbcTemplate.queryForObject(
                TableQueries.EXISTS_BY_TABLE_NUMBER_AND_NOT_ID,
                Integer.class,
                tableNumber,
                tableId);
        return count != null && count > 0;
    }

    public int save(Table table) {
        return jdbcTemplate.update(
                TableQueries.INSERT_TABLE,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus());
    }

    public int update(Table table) {
        return jdbcTemplate.update(
                TableQueries.UPDATE_TABLE,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus(),
                table.getTableId());
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                TableQueries.DELETE_TABLE,
                id);
    }
}