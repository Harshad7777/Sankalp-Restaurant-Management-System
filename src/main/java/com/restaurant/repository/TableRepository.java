package com.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Table;

@Repository
public class TableRepository {

    private final JdbcTemplate jdbcTemplate;

    public TableRepository(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate =
                jdbcTemplate;
    }

    private Table mapRow(
            ResultSet rs,
            int rowNum)
            throws SQLException {

        Table table =
                new Table();

        table.setTableId(
                rs.getInt("table_id")
        );

        table.setTableNumber(
                rs.getString("table_number")
        );

        table.setCapacity(
                rs.getInt("capacity")
        );

        table.setTableStatus(
                rs.getString("table_status")
        );

        return table;
    }

    public List<Table> findAll() {

        String sql = """
            SELECT
                table_id,
                table_number,
                capacity,
                table_status
            FROM restaurant_tables
            ORDER BY table_id ASC
            """;

        return jdbcTemplate.query(
                sql,
                this::mapRow
        );
    }

    public Table findById(int id) {

        String sql = """
            SELECT
                table_id,
                table_number,
                capacity,
                table_status
            FROM restaurant_tables
            WHERE table_id = ?
            """;

        List<Table> result =
                jdbcTemplate.query(
                        sql,
                        this::mapRow,
                        id
                );

        return result.isEmpty()
                ? null
                : result.get(0);
    }

    public boolean existsByTableNumber(
            String tableNumber) {

        String sql = """
            SELECT COUNT(*)
            FROM restaurant_tables
            WHERE table_number = ?
            """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        tableNumber
                );

        return count != null && count > 0;
    }

    public boolean existsByTableNumberAndNotId(
            String tableNumber,
            int tableId) {

        String sql = """
            SELECT COUNT(*)
            FROM restaurant_tables
            WHERE table_number = ?
            AND table_id <> ?
            """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        tableNumber,
                        tableId
                );

        return count != null && count > 0;
    }

    public int save(Table table) {

        String sql = """
            INSERT INTO restaurant_tables
            (
                table_number,
                capacity,
                table_status
            )
            VALUES (?, ?, ?)
            """;

        return jdbcTemplate.update(
                sql,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus()
        );
    }

    public int update(Table table) {

        String sql = """
            UPDATE restaurant_tables
            SET
                table_number = ?,
                capacity = ?,
                table_status = ?
            WHERE table_id = ?
            """;

        return jdbcTemplate.update(
                sql,
                table.getTableNumber(),
                table.getCapacity(),
                table.getTableStatus(),
                table.getTableId()
        );
    }

    public int delete(int id) {

        String sql = """
            DELETE FROM restaurant_tables
            WHERE table_id = ?
            """;

        return jdbcTemplate.update(
                sql,
                id
        );
    }
}