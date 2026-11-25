package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.restaurant.model.Category;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should save category successfully")
    void testSave() {
        Category category = new Category();
        category.setCategoryName("Starters");
        category.setDescription("Appetizers");
        category.setStatus("ACTIVE");

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any())).thenReturn(1);

        int result = categoryRepository.save(category);
        assertEquals(1, result);
        verify(jdbcTemplate).update(anyString(), any(), eq("Starters"), eq("Appetizers"), eq("ACTIVE"));
    }

    @Test
    @DisplayName("Should update category successfully")
    void testUpdate() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Updated Starters");
        category.setDescription("Updated desc");
        category.setStatus("ACTIVE");

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

        int result = categoryRepository.update(category);
        assertEquals(1, result);
        verify(jdbcTemplate).update(anyString(), eq("Updated Starters"), eq("Updated desc"), eq("ACTIVE"), eq(1), any());
    }

    @Test
    @DisplayName("Should delete category by id")
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1), any())).thenReturn(1);

        int result = categoryRepository.delete(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should check if category is used")
    void testIsCategoryUsed_True() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(1), any())).thenReturn(5);

        boolean used = categoryRepository.isCategoryUsed(1);
        assertTrue(used);
    }

    @Test
    @DisplayName("Should check if category is used - false")
    void testIsCategoryUsed_False() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(1), any())).thenReturn(0);

        boolean used = categoryRepository.isCategoryUsed(1);
        assertFalse(used);
    }

    @Test
    @DisplayName("Should find category by ID")
    void testFindById_Found() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Soups");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(category));

        Category result = categoryRepository.findById(1);
        assertNotNull(result);
        assertEquals("Soups", result.getCategoryName());
    }

    @Test
    @DisplayName("Should return null when category not found by ID")
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(99), any()))
                .thenReturn(Collections.emptyList());

        Category result = categoryRepository.findById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Should find all categories")
    void testFindAll() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Breads");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(Collections.singletonList(category));

        List<Category> list = categoryRepository.findAll();
        assertEquals(1, list.size());
        assertEquals("Breads", list.get(0).getCategoryName());
    }

    @Test
    @DisplayName("Should check exists by name")
    void testExistsByName() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("Breads"), any()))
                .thenReturn(1);

        assertTrue(categoryRepository.existsByName("Breads"));
    }
}
