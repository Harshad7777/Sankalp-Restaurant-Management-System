package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.Category;
import com.restaurant.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .categoryId(1)
                .categoryName("Beverages")
                .description("Hot and cold drinks")
                .status("ACTIVE")
                .build();
    }

    @Test
    @DisplayName("Should successfully add a new category")
    void testAddCategory_Success() {
        when(repository.existsByName("Beverages")).thenReturn(false);
        when(repository.save(any(Category.class))).thenReturn(1);

        String result = categoryService.addCategory(testCategory);

        assertEquals("Category Added Successfully", result);
        verify(repository).save(testCategory);
    }

    @Test
    @DisplayName("Should reject category if name already exists")
    void testAddCategory_Duplicate() {
        when(repository.existsByName("Beverages")).thenReturn(true);

        String result = categoryService.addCategory(testCategory);

        assertEquals("Category Already Exists", result);
        verify(repository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should update category")
    void testUpdateCategory_Success() {
        when(repository.findById(1)).thenReturn(testCategory);
        when(repository.existsByNameExceptId("Beverages", 1)).thenReturn(false);
        when(repository.update(any(Category.class))).thenReturn(1);

        String result = categoryService.updateCategory(testCategory);

        assertEquals("Category Updated Successfully", result);
        verify(repository).update(testCategory);
    }

    @Test
    @DisplayName("Should prevent deleting category if used in menu")
    void testDeleteCategory_InUse() {
        when(repository.isCategoryUsed(1)).thenReturn(true);

        String result = categoryService.deleteCategory(1);

        assertEquals("Category cannot be deleted because it is used in Menu.", result);
        verify(repository, never()).delete(anyInt());
    }

    @Test
    @DisplayName("Should delete category if not in use")
    void testDeleteCategory_Success() {
        when(repository.isCategoryUsed(1)).thenReturn(false);
        when(repository.delete(1)).thenReturn(1);

        String result = categoryService.deleteCategory(1);

        assertEquals("Category Deleted Successfully", result);
        verify(repository).delete(1);
    }

    @Test
    @DisplayName("Should get all categories")
    void testGetAllCategory() {
        when(repository.findAll()).thenReturn(List.of(testCategory));

        List<Category> list = categoryService.getAllCategory();

        assertNotNull(list);
        assertEquals(1, list.size());
    }
}
