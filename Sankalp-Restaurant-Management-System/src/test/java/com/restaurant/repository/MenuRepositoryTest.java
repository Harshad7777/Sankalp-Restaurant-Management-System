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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.restaurant.model.Menu;

@ExtendWith(MockitoExtension.class)
class MenuRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MenuRepository menuRepository;

    @Test
    @DisplayName("Should save menu item")
    void testSave() {
        Menu menu = new Menu();
        menu.setItemName("Dal Makhani");
        menu.setCategoryId(1);
        menu.setDescription("Creamy black lentils");
        menu.setPrice(180.0);
        menu.setImage("dal.jpg");
        menu.setStatus("AVAILABLE");

        when(jdbcTemplate.update(anyString(), any(), eq(1), eq("Dal Makhani"), eq("Creamy black lentils"), eq(180.0), eq("dal.jpg"), eq("AVAILABLE")))
                .thenReturn(1);

        int result = menuRepository.save(menu);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should update menu item")
    void testUpdate() {
        Menu menu = new Menu();
        menu.setItemId(1);
        menu.setItemName("Dal Makhani");
        menu.setCategoryId(1);
        menu.setDescription("Creamy black lentils");
        menu.setPrice(190.0);
        menu.setImage("dal.jpg");
        menu.setStatus("AVAILABLE");

        when(jdbcTemplate.update(anyString(), eq(1), eq("Dal Makhani"), eq("Creamy black lentils"), eq(190.0), eq("dal.jpg"), eq("AVAILABLE"), eq(1), any()))
                .thenReturn(1);

        int result = menuRepository.update(menu);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should delete menu item")
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1), any())).thenReturn(1);

        int result = menuRepository.delete(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should find menu item by ID")
    void testFindById_Found() {
        Menu menu = new Menu();
        menu.setItemId(1);
        menu.setItemName("Dal Makhani");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(menu));

        Menu result = menuRepository.findById(1);
        assertNotNull(result);
        assertEquals("Dal Makhani", result.getItemName());
    }

    @Test
    @DisplayName("Should find menu by category")
    void testFindByCategory() {
        Menu menu = new Menu();
        menu.setItemId(1);
        menu.setCategoryId(2);

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(2), any()))
                .thenReturn(Collections.singletonList(menu));

        List<Menu> list = menuRepository.findByCategory(2);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should check exists by name")
    void testExistsByName() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("Dal Makhani"), any()))
                .thenReturn(1);

        assertTrue(menuRepository.existsByName("Dal Makhani"));
    }
}
