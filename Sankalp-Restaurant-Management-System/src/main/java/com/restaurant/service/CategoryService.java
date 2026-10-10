package com.restaurant.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.restaurant.model.Category;
import com.restaurant.repository.CategoryRepository;

@Service
public class CategoryService {

    private static final Logger logger =
            LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // ==========================
    // ADD CATEGORY
    // ==========================
    public String addCategory(Category category) {

        logger.info("Adding Category : {}", category.getCategoryName());

        if (repository.existsByName(category.getCategoryName())) {
            return "Category Already Exists";
        }

        if (category.getStatus() == null || category.getStatus().isEmpty()) {
            category.setStatus("ACTIVE");
        }

        int result = repository.save(category);

        return result > 0
                ? "Category Added Successfully"
                : "Category Not Added";
    }

    // ==========================
    // UPDATE CATEGORY
    // ==========================
    public String updateCategory(Category category) {

        Category old = repository.findById(category.getCategoryId());

        if (old == null) {
            return "Category Not Found";
        }

        if (repository.existsByNameExceptId(
                category.getCategoryName(),
                category.getCategoryId())) {

            return "Category Already Exists";
        }

        int result = repository.update(category);

        return result > 0
                ? "Category Updated Successfully"
                : "Category Not Updated";
    }

    // ==========================
    // DELETE CATEGORY
    // ==========================
    public String deleteCategory(int id) {

        if (repository.isCategoryUsed(id)) {
            return "Category cannot be deleted because it is used in Menu.";
        }

        int result = repository.delete(id);

        return result > 0
                ? "Category Deleted Successfully"
                : "Category Not Found";
    }

    // ==========================
    // GET CATEGORY
    // ==========================
    public Category getCategory(int id) {

        return repository.findById(id);
    }

    // ==========================
    // GET ALL CATEGORY
    // ==========================
    public List<Category> getAllCategory() {
        return getAllCategory(null);
    }

    public List<Category> getAllCategory(Integer restaurantId) {
        return repository.findAll(restaurantId);
    }

    // ==========================
    // SEARCH CATEGORY
    // ==========================
    public List<Category> searchCategory(String keyword) {

        return repository.search(keyword);
    }
}