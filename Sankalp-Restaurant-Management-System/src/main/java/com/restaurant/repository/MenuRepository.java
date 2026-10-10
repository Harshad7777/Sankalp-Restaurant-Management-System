package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Menu;
import com.restaurant.repository.query.MenuQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class MenuRepository {

    private final JdbcTemplate jdbcTemplate;

    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Menu menu) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                MenuQueries.INSERT_MENU,
                resId,
                menu.getCategoryId(),
                menu.getItemName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImage(),
                menu.getStatus());
    }

    public int update(Menu menu) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        if (menu.getImage() == null || menu.getImage().isBlank()) {
            List<String> images = jdbcTemplate.query(
                    MenuQueries.GET_OLD_IMAGE,
                    (rs, rowNum) -> rs.getString("image"),
                    menu.getItemId(),
                    resId);
            if (!images.isEmpty()) {
                menu.setImage(images.get(0));
            }
        }

        return jdbcTemplate.update(
                MenuQueries.UPDATE_MENU,
                menu.getCategoryId(),
                menu.getItemName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getImage(),
                menu.getStatus(),
                menu.getItemId(),
                resId);
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                MenuQueries.DELETE_BY_ID,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }

    public Menu findById(int id) {
        List<Menu> list = jdbcTemplate.query(
                MenuQueries.FIND_BY_ID,
                new BeanPropertyRowMapper<>(Menu.class),
                id,
                SecurityUtil.getCurrentRestaurantId());
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Menu> findAll() {
        return findAll(null);
    }

    public List<Menu> findAll(Integer publicRestaurantId) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        if (resId == null && publicRestaurantId != null && publicRestaurantId > 0) {
            resId = publicRestaurantId;
        }
        if (resId == null) {
            resId = 1;
        }
        return jdbcTemplate.query(
                MenuQueries.FIND_ALL,
                new BeanPropertyRowMapper<>(Menu.class),
                resId);
    }

    public List<Menu> findByCategory(int categoryId) {
        return jdbcTemplate.query(
                MenuQueries.FIND_BY_CATEGORY,
                new BeanPropertyRowMapper<>(Menu.class),
                categoryId,
                SecurityUtil.getCurrentRestaurantId());
    }

    public boolean existsByName(String name) {
        Integer count = jdbcTemplate.queryForObject(
                MenuQueries.EXISTS_BY_NAME,
                Integer.class,
                name,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public List<Menu> getMenuFilteredPage(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT m.item_id AS itemId, m.restaurant_id AS restaurantId, m.category_id AS categoryId, c.category_name AS categoryName,
                       m.item_name AS itemName, m.description, m.price, m.image, m.status
                FROM menu m
                LEFT JOIN category c ON m.category_id = c.category_id
                WHERE m.restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getCategoryId() != null && req.getCategoryId() > 0) {
            sql.append(" AND m.category_id = ?");
            params.add(req.getCategoryId());
        }
        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND m.status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getMinPrice() != null) {
            sql.append(" AND m.price >= ?");
            params.add(req.getMinPrice());
        }
        if (req.getMaxPrice() != null) {
            sql.append(" AND m.price <= ?");
            params.add(req.getMaxPrice());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (m.item_name LIKE ? OR m.description LIKE ? OR c.category_name LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
        }

        String sortColumn = "m.item_id";
        if ("itemName".equalsIgnoreCase(req.getSortBy())) sortColumn = "m.item_name";
        else if ("price".equalsIgnoreCase(req.getSortBy())) sortColumn = "m.price";
        else if ("categoryId".equalsIgnoreCase(req.getSortBy())) sortColumn = "m.category_id";
        else if ("status".equalsIgnoreCase(req.getSortBy())) sortColumn = "m.status";

        sql.append(" ORDER BY ").append(sortColumn).append(" ").append(req.getSafeSortDirection());
        sql.append(" LIMIT ? OFFSET ?");
        params.add(req.getLimit());
        params.add(req.getOffset());

        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Menu.class), params.toArray());
    }

    public long countMenuFiltered(com.restaurant.dto.PageRequestDTO req) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM menu m
                LEFT JOIN category c ON m.category_id = c.category_id
                WHERE m.restaurant_id = ?
                """);
        java.util.List<Object> params = new java.util.ArrayList<>();
        params.add(SecurityUtil.getCurrentRestaurantId());

        if (req.getCategoryId() != null && req.getCategoryId() > 0) {
            sql.append(" AND m.category_id = ?");
            params.add(req.getCategoryId());
        }
        if (req.getStatus() != null && !req.getStatus().isBlank()) {
            sql.append(" AND m.status = ?");
            params.add(req.getStatus().trim().toUpperCase());
        }
        if (req.getMinPrice() != null) {
            sql.append(" AND m.price >= ?");
            params.add(req.getMinPrice());
        }
        if (req.getMaxPrice() != null) {
            sql.append(" AND m.price <= ?");
            params.add(req.getMaxPrice());
        }
        if (req.getSearch() != null && !req.getSearch().isBlank()) {
            sql.append(" AND (m.item_name LIKE ? OR m.description LIKE ? OR c.category_name LIKE ?)");
            String term = "%" + req.getSearch().trim() + "%";
            params.add(term);
            params.add(term);
            params.add(term);
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
        return count != null ? count : 0L;
    }
}