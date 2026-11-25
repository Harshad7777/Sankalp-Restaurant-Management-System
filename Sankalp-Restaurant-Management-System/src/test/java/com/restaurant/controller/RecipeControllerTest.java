package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import com.restaurant.model.Recipe;
import com.restaurant.service.RecipeService;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should create recipe ingredient")
    void testCreateRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setItemId(5);
        recipe.setInventoryId(2);
        recipe.setQuantityRequired(BigDecimal.valueOf(0.25));
        recipe.setUnit("KG");

        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(10);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.recipeId").value(10));
    }

    @Test
    @DisplayName("Should get recipes by item ID")
    void testGetRecipesByItemId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setItemId(5);
        recipe.setItemName("Paneer Butter Masala");

        when(recipeService.getRecipesByItemId(5)).thenReturn(List.of(recipe));

        mockMvc.perform(get("/recipe/item/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].itemName").value("Paneer Butter Masala"));
    }

    @Test
    @DisplayName("Should delete recipe")
    void testDeleteRecipe() throws Exception {
        when(recipeService.deleteRecipe(1)).thenReturn(true);

        mockMvc.perform(delete("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
