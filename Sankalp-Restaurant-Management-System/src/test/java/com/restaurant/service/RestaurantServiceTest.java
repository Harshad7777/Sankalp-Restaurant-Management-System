package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    @DisplayName("Should get all restaurants")
    void testGetAllRestaurants() {
        Restaurant r = new Restaurant();
        r.setRestaurantId(1);
        r.setRestaurantName("Sankalp");

        when(repository.findAll()).thenReturn(List.of(r));

        List<Restaurant> list = restaurantService.getAllRestaurants();
        assertEquals(1, list.size());
        assertEquals("Sankalp", list.get(0).getRestaurantName());
    }

    @Test
    @DisplayName("Should get restaurant by ID")
    void testGetRestaurantById() {
        Restaurant r = new Restaurant();
        r.setRestaurantId(1);

        when(repository.findById(1)).thenReturn(r);

        Restaurant result = restaurantService.getRestaurantById(1);
        assertNotNull(result);
        assertEquals(1, result.getRestaurantId());
    }

    @Test
    @DisplayName("Should get current restaurant")
    void testGetCurrentRestaurant() {
        Restaurant r = new Restaurant();
        r.setRestaurantId(1);

        when(repository.findById(anyInt())).thenReturn(r);

        Restaurant result = restaurantService.getCurrentRestaurant();
        assertNotNull(result);
    }
}
