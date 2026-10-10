package com.restaurant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.model.Category;
import com.restaurant.service.CategoryService;

@RestController
@RequestMapping({"/category", "/categories"})
@CrossOrigin("*")
public class CategoryController {

    private static final Logger logger =
            LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    // ==========================
    // ADD CATEGORY
    // ==========================
    @PostMapping
    public ResponseEntity<String> save(
            @jakarta.validation.Valid @RequestBody Category category) {

        logger.info("Add Category Request");

        String result = service.addCategory(category);

        if ("Category Added Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    // ==========================
    // UPDATE CATEGORY
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @jakarta.validation.Valid @RequestBody Category category) {

        category.setCategoryId(id);

        String result = service.updateCategory(category);

        if ("Category Updated Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    // ==========================
    // DELETE CATEGORY
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable int id) {

        String result = service.deleteCategory(id);

        if ("Category Deleted Successfully".equals(result)) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    // ==========================
    // GET CATEGORY BY ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(
            @PathVariable int id) {

        Category category = service.getCategory(id);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category);
    }

    // ==========================
    // GET ALL CATEGORY
    // ==========================
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(
            @RequestParam(value = "restaurantId", required = false) Integer restaurantId) {

        return ResponseEntity.ok(
                service.getAllCategory(restaurantId));
    }

    // ==========================
    // SEARCH CATEGORY
    // ==========================
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Category>> search(
            @PathVariable String keyword) {

        return ResponseEntity.ok(
                service.searchCategory(keyword));
    }
}