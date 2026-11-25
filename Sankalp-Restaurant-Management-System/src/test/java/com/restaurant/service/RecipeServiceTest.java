package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.restaurant.model.Recipe;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    @DisplayName("Should save recipe successfully")
    void testSaveRecipe() {
        Recipe recipe = new Recipe();
        recipe.setItemId(1);
        recipe.setInventoryId(2);
        recipe.setQuantityRequired(BigDecimal.valueOf(1.5));
        recipe.setUnit("KG");

        when(jdbcTemplate.update(anyString(), eq(1), eq(2), eq(BigDecimal.valueOf(1.5)), eq("KG"))).thenReturn(1);
        when(jdbcTemplate.queryForObject(eq("SELECT LAST_INSERT_ID()"), eq(Integer.class))).thenReturn(9);

        int result = recipeService.saveRecipe(recipe);
        assertEquals(9, result);
    }

    @Test
    @DisplayName("Should get recipes by item ID")
    void testGetRecipesByItemId() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(9);
        recipe.setItemId(1);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
                .thenReturn(Collections.singletonList(recipe));

        List<Recipe> list = recipeService.getRecipesByItemId(1);
        assertEquals(1, list.size());
        assertEquals(9, list.get(0).getRecipeId());
    }

    @Test
    @DisplayName("Should delete recipe")
    void testDeleteRecipe() {
        when(jdbcTemplate.update(anyString(), eq(9))).thenReturn(1);

        boolean result = recipeService.deleteRecipe(9);
        assertTrue(result);
    }
}
