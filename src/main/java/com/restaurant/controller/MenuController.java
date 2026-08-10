package com.restaurant.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restaurant.model.Menu;
import com.restaurant.service.MenuService;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin("*")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    // ==========================
    // Add Menu
    // ==========================
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addMenu(

            @RequestParam("itemName") String itemName,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        String msg = service.addMenu(
                itemName,
                categoryId,
                description,
                price,
                status,
                image);

        return ResponseEntity.ok(msg);
    }

    // ==========================
    // Update Menu
    // ==========================
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateMenu(

            @PathVariable int id,

            @RequestParam("itemName") String itemName,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        String msg = service.updateMenu(
                id,
                itemName,
                categoryId,
                description,
                price,
                status,
                image);

        return ResponseEntity.ok(msg);
    }

    // ==========================
    // Delete Menu
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable int id) {

        return ResponseEntity.ok(service.deleteMenu(id));
    }

    // ==========================
    // Get Menu By Id
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable int id) {

        return ResponseEntity.ok(service.getMenu(id));
    }

    // ==========================
    // Get All Menu
    // ==========================
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenu() {

        return ResponseEntity.ok(service.getAllMenu());
    }

    // ==========================
    // Get Menu By Category
    // ==========================
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Menu>> getMenuByCategory(
            @PathVariable int categoryId) {

        return ResponseEntity.ok(service.getCategoryMenu(categoryId));
    }

}