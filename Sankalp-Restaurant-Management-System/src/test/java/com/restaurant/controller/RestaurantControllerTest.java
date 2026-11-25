package com.restaurant.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
        testRestaurant = new Restaurant();
        testRestaurant.setRestaurantId(1);
        testRestaurant.setRestaurantName("Sankalp Restaurant");
        testRestaurant.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Should get all restaurants")
    void testGetAllRestaurants() throws Exception {
        when(restaurantService.getAllRestaurants()).thenReturn(List.of(testRestaurant));

        mockMvc.perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].restaurantName").value("Sankalp Restaurant"));
    }

    @Test
    @DisplayName("Should get current restaurant")
    void testGetCurrentRestaurant_Found() throws Exception {
        when(restaurantService.getCurrentRestaurant()).thenReturn(testRestaurant);

        mockMvc.perform(get("/restaurant/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value("Sankalp Restaurant"));
    }

    @Test
    @DisplayName("Should return 404 when current restaurant not found")
    void testGetCurrentRestaurant_NotFound() throws Exception {
        when(restaurantService.getCurrentRestaurant()).thenReturn(null);

        mockMvc.perform(get("/restaurant/current"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get restaurant by ID")
    void testGetRestaurantById_Found() throws Exception {
        when(restaurantService.getRestaurantById(1)).thenReturn(testRestaurant);

        mockMvc.perform(get("/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(1));
    }
}
