package com.restaurant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.InventoryRequestDTO;
import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.dto.StockAdjustmentDTO;
import com.restaurant.model.Inventory;
import com.restaurant.model.InventoryLog;
import com.restaurant.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public ResponseEntity<InventorySummaryDTO> getSummary() {
        return ResponseEntity.ok(service.getInventorySummary());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        return ResponseEntity.ok(service.getAllInventory());
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<Inventory>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        PageRequestDTO req = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .category(category)
                .status(status)
                .search(search)
                .build();

        return ResponseEntity.ok(service.getFilteredInventory(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getInventoryById(id));
    }

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(service.addInventory(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(service.updateInventory(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteInventory(id));
    }

    @PostMapping("/{id}/adjust-stock")
    public ResponseEntity<String> adjustStock(@PathVariable int id, @Valid @RequestBody StockAdjustmentDTO dto) {
        return ResponseEntity.ok(service.adjustStock(id, dto));
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<InventoryLog>> getLogs(@PathVariable int id) {
        return ResponseEntity.ok(service.getInventoryLogs(id));
    }
}
