package com.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

import com.restaurant.model.Restaurant;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("Should find all restaurants")
    void testFindAll() {
        Restaurant r = new Restaurant();
        r.setRestaurantId(1);
        r.setRestaurantName("Sankalp");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(r));

        List<Restaurant> list = restaurantRepository.findAll();
        assertEquals(1, list.size());
        assertEquals("Sankalp", list.get(0).getRestaurantName());
    }

    @Test
    @DisplayName("Should find restaurant by ID - Found")
    void testFindById_Found() {
        Restaurant r = new Restaurant();
        r.setRestaurantId(1);
        r.setRestaurantName("Sankalp");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1)))
                .thenReturn(Collections.singletonList(r));

        Restaurant result = restaurantRepository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getRestaurantId());
    }

    @Test
    @DisplayName("Should return null when restaurant not found by ID")
    void testFindById_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(99)))
                .thenReturn(Collections.emptyList());

        Restaurant result = restaurantRepository.findById(99);
        assertNull(result);
    }
}
