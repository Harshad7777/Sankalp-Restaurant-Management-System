package com.restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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

import com.restaurant.model.Reservation;
import com.restaurant.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // =====================================================
    // SUBMIT NEW RESERVATION (PUBLIC ACCESSIBLE)
    // POST /api/reservation
    // =====================================================

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody Reservation reservation) {
        try {
            Reservation created = service.createReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // =====================================================
    // GET ALL RESERVATIONS (WITH OPTIONAL STATUS FILTER)
    // GET /api/reservation?status=PENDING
    // =====================================================

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations(
            @RequestParam(required = false) String status) {
        List<Reservation> reservations = service.getAllReservations(status);
        return ResponseEntity.ok(reservations);
    }

    // =====================================================
    // GET RESERVATION BY ID
    // GET /api/reservation/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable int id) {
        try {
            Reservation reservation = service.getReservationById(id);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =====================================================
    // UPDATE STATUS
    // PUT /api/reservation/{id}/status?status=CONFIRMED
    // or JSON body { "status": "CONFIRMED" }
    // =====================================================

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestParam(required = false) String status,
            @RequestBody(required = false) Map<String, String> body) {

        String newStatus = status;
        if ((newStatus == null || newStatus.trim().isEmpty()) && body != null && body.containsKey("status")) {
            newStatus = body.get("status");
        }

        if (newStatus == null || newStatus.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Status parameter or body field is required."));
        }

        try {
            String result = service.updateStatus(id, newStatus);
            if (result.startsWith("Reservation status updated")) {
                return ResponseEntity.ok(Map.of("message", result, "status", newStatus.toUpperCase().trim()));
            }
            return ResponseEntity.badRequest().body(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // =====================================================
    // ASSIGN TABLE
    // PUT /api/reservation/{id}/assign-table?tableId=2
    // or JSON body { "tableId": 2 }
    // =====================================================

    @PutMapping("/{id}/assign-table")
    public ResponseEntity<?> assignTable(
            @PathVariable int id,
            @RequestParam(required = false) Integer tableId,
            @RequestBody(required = false) Map<String, Object> body) {

        Integer assignedTableId = tableId;
        if (assignedTableId == null && body != null && body.containsKey("tableId")) {
            Object val = body.get("tableId");
            if (val instanceof Number) {
                assignedTableId = ((Number) val).intValue();
            } else if (val != null) {
                try {
                    assignedTableId = Integer.parseInt(val.toString());
                } catch (NumberFormatException ignored) {}
            }
        }

        try {
            String result = service.assignTable(id, assignedTableId);
            if ("Table assigned successfully".equals(result)) {
                return ResponseEntity.ok(Map.of("message", result, "tableId", assignedTableId != null ? assignedTableId : 0));
            }
            return ResponseEntity.badRequest().body(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // =====================================================
    // DELETE RESERVATION
    // DELETE /api/reservation/{id}
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable int id) {
        try {
            String result = service.deleteReservation(id);
            if ("Reservation deleted successfully".equals(result)) {
                return ResponseEntity.ok(Map.of("message", result));
            }
            return ResponseEntity.badRequest().body(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
