package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Category;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==========================
    // ADD CATEGORY
    // ==========================
    public int save(Category category) {

        String sql = """
                INSERT INTO category
                (category_name,description,status)
                VALUES(?,?,?)
                """;

        return jdbcTemplate.update(
                sql,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus());
    }

    // ==========================
    // UPDATE CATEGORY
    // ==========================
    public int update(Category category) {

        String sql = """
                UPDATE category
                SET
                    category_name=?,
                    description=?,
                    status=?
                WHERE category_id=?
                """;

        return jdbcTemplate.update(
                sql,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus(),
                category.getCategoryId());
    }

    // ==========================
    // DELETE CATEGORY
    // ==========================
    public int delete(int id) {

        return jdbcTemplate.update(
                "DELETE FROM category WHERE category_id=?",
                id);
    }

    // ==========================
    // CATEGORY USED IN MENU
    // ==========================
    public boolean isCategoryUsed(int id) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM menu WHERE category_id=?",
                Integer.class,
                id);

        return count != null && count > 0;
    }

    // ==========================
    // GET ALL CATEGORY
    // ==========================
    public List<Category> findAll() {

        String sql = """
                SELECT
                    category_id AS categoryId,
                    category_name AS categoryName,
                    description,
                    status
                FROM category
                ORDER BY category_id DESC
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Category.class));
    }

    // ==========================
    // GET CATEGORY BY ID
    // ==========================
    public Category findById(int id) {

        String sql = """
                SELECT
                    category_id AS categoryId,
                    category_name AS categoryName,
                    description,
                    status
                FROM category
                WHERE category_id=?
                """;

        List<Category> list = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Category.class),
                id);

        return list.isEmpty() ? null : list.get(0);
    }

    // ==========================
    // DUPLICATE NAME
    // ==========================
    public boolean existsByName(String name) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM category WHERE category_name=?",
                Integer.class,
                name);

        return count != null && count > 0;
    }

    // ==========================
    // DUPLICATE NAME EXCEPT ID
    // ==========================
    public boolean existsByNameExceptId(String name, int id) {

        String sql = """
                SELECT COUNT(*)
                FROM category
                WHERE category_name=?
                AND category_id<>?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                name,
                id);

        return count != null && count > 0;
    }

    // ==========================
    // SEARCH CATEGORY
    // ==========================
    public List<Category> search(String keyword) {

        String sql = """
                SELECT
                    category_id AS categoryId,
                    category_name AS categoryName,
                    description,
                    status
                FROM category
                WHERE
                    category_name LIKE ?
                    OR description LIKE ?
                ORDER BY category_name
                """;

        String key = "%" + keyword + "%";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Category.class),
                key,
                key);
    }
}