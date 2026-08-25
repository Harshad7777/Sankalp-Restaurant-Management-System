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
@RequestMapping("/api/table")
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
}