package com.restaurant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Table Reservation entity in the Restaurant Management System.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    private Integer reservationId;
    private Integer restaurantId;
    private String reservationCode;

    @NotBlank(message = "Guest name is required")
    @Size(min = 2, max = 100, message = "Guest name must be between 2 and 100 characters")
    private String guestName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a valid 10-digit mobile number")
    private String guestPhone;

    private String guestEmail;

    @NotNull(message = "Booking date is required")
    private LocalDate bookingDate;

    @NotBlank(message = "Time slot is required")
    private String bookingTime;

    @NotNull(message = "Guest count is required")
    @Min(value = 1, message = "Guest count must be at least 1")
    private Integer guestCount;

    private String specialRequest;
    private Integer tableId;
    private String tableNumber; // Transformed/joined for convenience in UI display

    @Pattern(regexp = "^(PENDING|CONFIRMED|REJECTED|COMPLETED|CANCELLED)$", message = "Status must be PENDING, CONFIRMED, REJECTED, COMPLETED, or CANCELLED")
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Legacy constructor
    public Reservation(Integer reservationId, String reservationCode, String guestName, String guestPhone,
                       LocalDate bookingDate, String bookingTime, Integer guestCount, String specialRequest,
                       Integer tableId, String tableNumber, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(reservationId, 0, reservationCode, guestName, guestPhone, null, bookingDate, bookingTime, guestCount, specialRequest, tableId, tableNumber, status, createdAt, updatedAt);
    }
}
