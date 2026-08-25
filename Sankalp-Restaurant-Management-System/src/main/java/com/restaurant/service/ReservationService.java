package com.restaurant.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.model.Reservation;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.service.mail.JmsMailProducer;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final JmsMailProducer mailProducer;
    private final Random random = new Random();

    public ReservationService(ReservationRepository repository, JmsMailProducer mailProducer) {
        this.repository = repository;
        this.mailProducer = mailProducer;
    }

    // =====================================================
    // CREATE RESERVATION
    // =====================================================

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getStatus() == null || reservation.getStatus().trim().isEmpty()) {
            reservation.setStatus("PENDING");
        }

        // Generate unique reservation code if not provided
        if (reservation.getReservationCode() == null || reservation.getReservationCode().trim().isEmpty()) {
            reservation.setReservationCode(generateUniqueReservationCode());
        }

        int rows = repository.save(reservation);
        if (rows > 0) {
            Reservation created = repository.findByCode(reservation.getReservationCode());
            if (created != null && mailProducer != null) {
                try {
                    mailProducer.sendReservationSubmittedNotification(created);
                } catch (Exception ignored) {}
            }
            return created;
        }

        throw new RuntimeException("Failed to submit reservation request.");
    }

    // =====================================================
    // GET ALL RESERVATIONS
    // =====================================================

    public List<Reservation> getAllReservations(String status) {
        if (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status)) {
            return repository.findByStatus(status.toUpperCase().trim());
        }
        return repository.findAll();
    }

    // =====================================================
    // GET RESERVATION BY ID
    // =====================================================

    public Reservation getReservationById(int id) {
        Reservation r = repository.findById(id);
        if (r == null) {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }
        return r;
    }

    // =====================================================
    // UPDATE STATUS
    // =====================================================

    @Transactional
    public String updateStatus(int id, String status) {
        Reservation r = repository.findById(id);
        if (r == null) {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }

        String formattedStatus = status != null ? status.toUpperCase().trim() : "";
        if (!formattedStatus.matches("^(PENDING|CONFIRMED|REJECTED|COMPLETED|CANCELLED)$")) {
            return "Invalid reservation status: " + status;
        }

        int updated = repository.updateStatus(id, formattedStatus);
        if (updated > 0) {
            Reservation updatedRes = repository.findById(id);
            if (updatedRes != null && mailProducer != null) {
                try {
                    mailProducer.sendReservationStatusNotification(updatedRes);
                } catch (Exception ignored) {}
            }
            return "Reservation status updated to " + formattedStatus;
        }
        return "Failed to update reservation status";
    }

    // =====================================================
    // ASSIGN TABLE
    // =====================================================

    @Transactional
    public String assignTable(int id, Integer tableId) {
        Reservation r = repository.findById(id);
        if (r == null) {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }

        int updated = repository.assignTable(id, tableId);
        if (updated > 0) {
            if (tableId != null && !"CONFIRMED".equalsIgnoreCase(r.getStatus())) {
                repository.updateStatus(id, "CONFIRMED");
            }
            Reservation updatedRes = repository.findById(id);
            if (updatedRes != null && mailProducer != null) {
                try {
                    mailProducer.sendReservationStatusNotification(updatedRes);
                } catch (Exception ignored) {}
            }
            return "Table assigned successfully";
        }
        return "Failed to assign table";
    }

    // =====================================================
    // DELETE RESERVATION
    // =====================================================

    @Transactional
    public String deleteReservation(int id) {
        Reservation r = repository.findById(id);
        if (r == null) {
            throw new RuntimeException("Reservation not found with ID: " + id);
        }

        int rows = repository.delete(id);
        if (rows > 0) {
            return "Reservation deleted successfully";
        }
        return "Failed to delete reservation";
    }

    // =====================================================
    // GENERATE UNIQUE CODE
    // =====================================================

    private String generateUniqueReservationCode() {
        String datePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code;
        int attempts = 0;
        do {
            int randomNum = 1000 + random.nextInt(9000);
            code = "RES-" + datePrefix + "-" + randomNum;
            attempts++;
            if (attempts > 50) {
                code = "RES-" + datePrefix + "-" + System.currentTimeMillis() % 10000;
                break;
            }
        } while (repository.existsByCode(code));

        return code;
    }
}
