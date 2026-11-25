package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.Category;
import com.restaurant.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
        testCategory = new Category();
        testCategory.setCategoryId(1);
        testCategory.setCategoryName("Beverages");
        testCategory.setDescription("Hot and cold drinks");
        testCategory.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Should successfully add a new category")
    void testSaveCategory_Success() throws Exception {
        when(categoryService.addCategory(any(Category.class)))
                .thenReturn("Category Added Successfully");

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Added Successfully"));
    }

    @Test
    @DisplayName("Should return 400 when category add fails")
    void testSaveCategory_Failure() throws Exception {
        when(categoryService.addCategory(any(Category.class)))
                .thenReturn("Category Already Exists");

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Category Already Exists"));
    }

    @Test
    @DisplayName("Should successfully update an existing category")
    void testUpdateCategory_Success() throws Exception {
        when(categoryService.updateCategory(any(Category.class)))
                .thenReturn("Category Updated Successfully");

        mockMvc.perform(put("/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Updated Successfully"));
    }

    @Test
    @DisplayName("Should successfully delete a category")
    void testDeleteCategory_Success() throws Exception {
        when(categoryService.deleteCategory(1))
                .thenReturn("Category Deleted Successfully");

        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Deleted Successfully"));
    }

    @Test
    @DisplayName("Should get category by ID")
    void testGetCategoryById_Found() throws Exception {
        when(categoryService.getCategory(1)).thenReturn(testCategory);

        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Beverages"));
    }

    @Test
    @DisplayName("Should return 404 when category not found")
    void testGetCategoryById_NotFound() throws Exception {
        when(categoryService.getCategory(99)).thenReturn(null);

        mockMvc.perform(get("/category/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get all categories")
    void testGetAllCategory() throws Exception {
        Category cat2 = new Category();
        cat2.setCategoryId(2);
        cat2.setCategoryName("Desserts");

        List<Category> list = Arrays.asList(testCategory, cat2);
        when(categoryService.getAllCategory(any())).thenReturn(list);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].categoryName").value("Beverages"))
                .andExpect(jsonPath("$[1].categoryName").value("Desserts"));
    }

    @Test
    @DisplayName("Should search categories by keyword")
    void testSearchCategory() throws Exception {
        when(categoryService.searchCategory("Bev"))
                .thenReturn(List.of(testCategory));

        mockMvc.perform(get("/category/search/Bev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].categoryName").value("Beverages"));
    }
}
