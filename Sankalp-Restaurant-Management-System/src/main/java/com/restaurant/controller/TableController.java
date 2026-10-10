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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Table;
import com.restaurant.service.TableService;

@RestController
@RequestMapping({"/table", "/tables"})
@CrossOrigin(origins = "*")
public class TableController {

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    // =====================================================
    // GET ALL TABLES
    // GET /api/table
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {

        return ResponseEntity.ok(
                service.getAllTables()
        );
    }

    // =====================================================
    // GET TABLE BY ID
    // GET /api/table/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getTable(
            @PathVariable int id) {

        try {

            Table table =
                    service.getTable(id);

            return ResponseEntity.ok(table);

        }
        catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    // =====================================================
    // ADD TABLE
    // POST /api/table
    // =====================================================

    @PostMapping
    public ResponseEntity<String> addTable(
            @jakarta.validation.Valid @RequestBody Table table) {

        String result =
                service.addTable(table);

        if (
                "Table Added Successfully"
                        .equals(result)
        ) {

            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }

    // =====================================================
    // UPDATE TABLE
    // PUT /api/table/{id}
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTable(
            @PathVariable int id,
            @jakarta.validation.Valid @RequestBody Table table) {

        table.setTableId(id);

        String result =
                service.updateTable(table);

        if (
                "Table Updated Successfully"
                        .equals(result)
        ) {

            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }

    // =====================================================
    // DELETE TABLE
    // DELETE /api/table/{id}
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTable(
            @PathVariable int id) {

        String result =
                service.deleteTable(id);

        if (
                "Table Deleted Successfully"
                        .equals(result)
        ) {

            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .badRequest()
                .body(result);
    }

    @PutMapping("/floor-plan/layout")
    public ResponseEntity<?> updateFloorPlanLayout(@RequestBody java.util.List<Table> tables) {
        if (tables != null) {
            for (Table t : tables) {
                if (t.getTableId() != null) {
                    service.updateFloorPlan(t.getTableId(), 
                            t.getxPos() != null ? t.getxPos() : 0, 
                            t.getyPos() != null ? t.getyPos() : 0, 
                            t.getShape() != null ? t.getShape() : "RECTANGLE", 
                            t.getWidth() != null ? t.getWidth() : 100, 
                            t.getHeight() != null ? t.getHeight() : 100);
                }
            }
        }
        return ResponseEntity.ok(java.util.Map.of("success", true, "message", "Floor plan visual layout updated successfully"));
    }
}