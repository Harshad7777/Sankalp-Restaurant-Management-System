package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.restaurant.model.Reservation;

@ExtendWith(MockitoExtension.class)
class ReservationRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("Should save reservation")
    void testSave() {
        Reservation r = new Reservation();
        r.setReservationCode("RES-1234");
        r.setGuestName("Karan");
        r.setGuestPhone("9876543210");
        r.setGuestEmail("karan@example.com");
        r.setBookingDate(LocalDate.now());
        r.setBookingTime("20:00");
        r.setGuestCount(2);
        r.setStatus("PENDING");

        when(jdbcTemplate.update(anyString(), any(), eq("RES-1234"), eq("Karan"), eq("9876543210"), eq("karan@example.com"), any(), eq("20:00"), eq(2), any(), any(), eq("PENDING")))
                .thenReturn(1);

        int result = reservationRepository.save(r);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should find reservation by ID")
    void testFindById_Found() {
        Reservation r = new Reservation();
        r.setReservationId(1);
        r.setGuestName("Karan");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(r));

        Reservation result = reservationRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getReservationId());
    }

    @Test
    @DisplayName("Should return null when reservation not found by ID")
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(99), any()))
                .thenReturn(Collections.emptyList());

        Reservation result = reservationRepository.findById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Should update reservation status")
    void testUpdateStatus() {
        when(jdbcTemplate.update(anyString(), eq("SEATED"), eq(1), any()))
                .thenReturn(1);

        int result = reservationRepository.updateStatus(1, "SEATED");
        assertEquals(1, result);
    }
}
