package com.restaurant.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Category;
import com.restaurant.repository.query.CategoryQueries;

@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Category category) {
        return jdbcTemplate.update(
                CategoryQueries.INSERT_CATEGORY,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus());
    }

    public int update(Category category) {
        return jdbcTemplate.update(
                CategoryQueries.UPDATE_CATEGORY,
                category.getCategoryName(),
                category.getDescription(),
                category.getStatus(),
                category.getCategoryId());
    }

    public int delete(int id) {
        return jdbcTemplate.update(
                CategoryQueries.DELETE_BY_ID,
                id);
    }

    public boolean isCategoryUsed(int id) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.IS_CATEGORY_USED,
                Integer.class,
                id);
        return count != null && count > 0;
    }

    public List<Category> findAll() {
        return jdbcTemplate.query(
                CategoryQueries.FIND_ALL,
                new BeanPropertyRowMapper<>(Category.class));
    }

    public Category findById(int id) {
        List<Category> list = jdbcTemplate.query(
                CategoryQueries.FIND_BY_ID,
                new BeanPropertyRowMapper<>(Category.class),
                id);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsByName(String name) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.EXISTS_BY_NAME,
                Integer.class,
                name);
        return count != null && count > 0;
    }

    public boolean existsByNameExceptId(String name, int id) {
        Integer count = jdbcTemplate.queryForObject(
                CategoryQueries.EXISTS_BY_NAME_EXCEPT_ID,
                Integer.class,
                name,
                id);
        return count != null && count > 0;
    }

    public List<Category> search(String keyword) {
        String key = "%" + keyword + "%";
        return jdbcTemplate.query(
                CategoryQueries.SEARCH_CATEGORY,
                new BeanPropertyRowMapper<>(Category.class),
                key,
                key);
    }
}