package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.Reservation;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.service.mail.JmsMailProducer;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository repository;

    @Mock
    private JmsMailProducer mailProducer;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testReservation = new Reservation();
        testReservation.setReservationId(1);
        testReservation.setReservationCode("RES-12345");
        testReservation.setGuestName("Vikram Singh");
        testReservation.setGuestPhone("9876543210");
        testReservation.setStatus("PENDING");
    }

    @Test
    @DisplayName("Should create reservation successfully")
    void testCreateReservation() {
        when(repository.save(any(Reservation.class))).thenReturn(1);
        when(repository.findByCode(anyString())).thenReturn(testReservation);

        Reservation created = reservationService.createReservation(testReservation);

        assertNotNull(created);
        assertEquals("RES-12345", created.getReservationCode());
        verify(repository).save(any(Reservation.class));
    }

    @Test
    @DisplayName("Should get all reservations")
    void testGetAllReservations() {
        when(repository.findAll()).thenReturn(List.of(testReservation));

        List<Reservation> list = reservationService.getAllReservations(null);
        assertEquals(1, list.size());
        assertEquals("Vikram Singh", list.get(0).getGuestName());
    }

    @Test
    @DisplayName("Should get reservation by ID")
    void testGetReservationById_Found() {
        when(repository.findById(1)).thenReturn(testReservation);

        Reservation result = reservationService.getReservationById(1);
        assertNotNull(result);
        assertEquals(1, result.getReservationId());
    }

    @Test
    @DisplayName("Should update status to CONFIRMED")
    void testUpdateStatus_Confirmed() {
        when(repository.findById(1)).thenReturn(testReservation);
        when(repository.updateStatus(1, "CONFIRMED")).thenReturn(1);

        String result = reservationService.updateStatus(1, "CONFIRMED");
        assertTrue(result.contains("CONFIRMED"));
    }
}
