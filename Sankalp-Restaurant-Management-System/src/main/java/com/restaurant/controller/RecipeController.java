package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Recipe;
import com.restaurant.service.RecipeService;

@RestController
@RequestMapping({"/recipe", "/api/recipe", "/recipes", "/api/recipes"})
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        int id = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(Map.of("success", true, "message", "Recipe ingredient saved", "recipeId", id));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Recipe>> getRecipesByItemId(@PathVariable int itemId) {
        return ResponseEntity.ok(recipeService.getRecipesByItemId(itemId));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable int recipeId) {
        boolean deleted = recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok(Map.of("success", deleted, "message", deleted ? "Ingredient removed from recipe" : "Recipe item not found"));
    }
}
