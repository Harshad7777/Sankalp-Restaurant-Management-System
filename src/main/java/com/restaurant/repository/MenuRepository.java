package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Menu;

@Repository
public class MenuRepository {

    private final JdbcTemplate jdbcTemplate;

    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Menu menu) {

        String sql = """
                INSERT INTO menu
                (category_id,item_name,description,price,image,status)
                VALUES(?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                menu.getCategoryId(),
                menu.getItemName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImage(),
                menu.getStatus());
    }

    public int update(Menu menu) {

        if (menu.getImage() == null || menu.getImage().isBlank()) {

            String oldImage = jdbcTemplate.queryForObject(
                    "SELECT image FROM menu WHERE item_id=?",
                    String.class,
                    menu.getItemId());

            menu.setImage(oldImage);
        }

        String sql = """
                UPDATE menu
                SET category_id=?,
                    item_name=?,
                    description=?,
                    price=?,
                    image=?,
                    status=?
                WHERE item_id=?
                """;

        return jdbcTemplate.update(sql,
                menu.getCategoryId(),
                menu.getItemName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImage(),
                menu.getStatus(),
                menu.getItemId());
    }
    public int delete(int id) {

        return jdbcTemplate.update(
                "DELETE FROM menu WHERE item_id=?",
                id);
    }

    public Menu findById(int id) {

        String sql = """
            SELECT
                m.item_id AS itemId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            WHERE m.item_id = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(Menu.class),
                id);
    }

    public List<Menu> findAll() {

        String sql = """
            SELECT
                m.item_id AS itemId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            ORDER BY m.item_id DESC
            """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Menu.class));
    }

    public List<Menu> findByCategory(int categoryId) {

        String sql = """
            SELECT
                m.item_id AS itemId,
                m.category_id AS categoryId,
                c.category_name AS categoryName,
                m.item_name AS itemName,
                m.description,
                m.price,
                m.image,
                m.status
            FROM menu m
            INNER JOIN category c
                ON m.category_id = c.category_id
            WHERE m.category_id = ?
            """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Menu.class),
                categoryId);
    }
    public boolean existsByName(String name) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM menu WHERE item_name=?",
                Integer.class,
                name);

        return count != null && count > 0;
    }
}