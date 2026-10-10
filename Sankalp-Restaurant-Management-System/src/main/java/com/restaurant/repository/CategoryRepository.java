package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Category;
import com.restaurant.repository.query.CategoryQueries;
import com.restaurant.security.SecurityUtil;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Category category) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                CategoryQueries.INSERT_CATEGORY,
                resId,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus());
    }

    public int update(Category category) {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return jdbcTemplate.update(
                CategoryQueries.UPDATE_CATEGORY,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus(),
                category.getCategoryId(),
                resId);
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                CategoryQueries.DELETE_BY_ID,
                id,
                SecurityUtil.getCurrentRestaurantId());
    }

    public boolean isCategoryUsed(int id) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.IS_CATEGORY_USED,
                Integer.class,
                id,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public List<Category> findAll() {
        return findAll(null);
    }

    public List<Category> findAll(Integer publicRestaurantId) {
        Integer resId = SecurityUtil.getOptionalCurrentRestaurantId();
        if (resId == null && publicRestaurantId != null && publicRestaurantId > 0) {
            resId = publicRestaurantId;
        }
        if (resId == null) {
            resId = 1;
        }
        return jdbcTemplate.query(
                CategoryQueries.FIND_ALL,
                new BeanPropertyRowMapper<>(Category.class),
                resId);
    }

    public Category findById(int id) {
        List<Category> list = jdbcTemplate.query(
                CategoryQueries.FIND_BY_ID,
                new BeanPropertyRowMapper<>(Category.class),
                id,
                SecurityUtil.getCurrentRestaurantId());
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsByName(String name) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.EXISTS_BY_NAME,
                Integer.class,
                name,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public boolean existsByNameExceptId(String name, int id) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.EXISTS_BY_NAME_EXCEPT_ID,
                Integer.class,
                name,
                id,
                SecurityUtil.getCurrentRestaurantId());
        return count != null && count > 0;
    }

    public List<Category> search(String keyword) {
        String key = "%" + keyword + "%";
        return jdbcTemplate.query(
                CategoryQueries.SEARCH_CATEGORY,
                new BeanPropertyRowMapper<>(Category.class),
                SecurityUtil.getCurrentRestaurantId(),
                key,
                key);
    }
}