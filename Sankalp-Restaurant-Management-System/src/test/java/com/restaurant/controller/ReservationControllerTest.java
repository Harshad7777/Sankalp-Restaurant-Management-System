package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.model.Reservation;
import com.restaurant.service.ReservationService;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private ObjectMapper objectMapper;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testReservation = new Reservation();
        testReservation.setReservationId(1);
        testReservation.setReservationCode("RES-2026-999");
        testReservation.setGuestName("Amit Patel");
        testReservation.setGuestPhone("9876543210");
        testReservation.setGuestEmail("amit@example.com");
        testReservation.setBookingDate(LocalDate.now().plusDays(1));
        testReservation.setBookingTime("19:30");
        testReservation.setGuestCount(4);
        testReservation.setStatus("PENDING");
    }

    @Test
    @DisplayName("Should create reservation successfully")
    void testCreateReservation_Success() throws Exception {
        when(reservationService.createReservation(any(Reservation.class))).thenReturn(testReservation);

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testReservation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reservationCode").value("RES-2026-999"))
                .andExpect(jsonPath("$.guestName").value("Amit Patel"));
    }

    @Test
    @DisplayName("Should get all reservations")
    void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservations(any())).thenReturn(List.of(testReservation));

        mockMvc.perform(get("/reservation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].guestName").value("Amit Patel"));
    }

    @Test
    @DisplayName("Should get reservation by ID - Found")
    void testGetReservationById_Found() throws Exception {
        when(reservationService.getReservationById(1)).thenReturn(testReservation);

        mockMvc.perform(get("/reservation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(1))
                .andExpect(jsonPath("$.guestName").value("Amit Patel"));
    }

    @Test
    @DisplayName("Should return 404 when reservation not found by ID")
    void testGetReservationById_NotFound() throws Exception {
        when(reservationService.getReservationById(99)).thenThrow(new RuntimeException("Reservation not found"));

        mockMvc.perform(get("/reservation/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update reservation status")
    void testUpdateStatus_Success() throws Exception {
        when(reservationService.updateStatus(1, "CONFIRMED")).thenReturn("Reservation status updated to CONFIRMED");

        mockMvc.perform(put("/reservation/1/status")
                .param("status", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }
}
