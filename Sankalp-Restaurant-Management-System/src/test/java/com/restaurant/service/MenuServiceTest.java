package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Menu;
import com.restaurant.repository.MenuRepository;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository repository;

    @InjectMocks
    private MenuService menuService;

    private Menu testMenu;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(menuService, "uploadDir", "C:/restaurant/uploads");
        testMenu = new Menu();
        testMenu.setItemId(1);
        testMenu.setItemName("Paneer Tikka");
        testMenu.setCategoryId(2);
        testMenu.setPrice(220.0);
        testMenu.setDescription("Grilled cottage cheese");
        testMenu.setStatus("AVAILABLE");
    }

    @Test
    @DisplayName("Should successfully add menu item without image")
    void testAddMenu_SuccessWithoutImage() {
        when(repository.existsByName("Paneer Tikka")).thenReturn(false);
        when(repository.save(any(Menu.class))).thenReturn(1);

        String result = menuService.addMenu("Paneer Tikka", 2, "Grilled cottage cheese", 220.0, "AVAILABLE", null);

        assertEquals("Menu Added Successfully", result);
        verify(repository).save(any(Menu.class));
    }

    @Test
    @DisplayName("Should reject adding menu item if name already exists")
    void testAddMenu_DuplicateName() {
        when(repository.existsByName("Paneer Tikka")).thenReturn(true);

        String result = menuService.addMenu("Paneer Tikka", 2, "Grilled cottage cheese", 220.0, "AVAILABLE", null);

        assertEquals("Menu Item Already Exists", result);
        verify(repository, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Should successfully update menu item")
    void testUpdateMenu_Success() {
        when(repository.findById(1)).thenReturn(testMenu);
        when(repository.update(any(Menu.class))).thenReturn(1);

        String result = menuService.updateMenu(1, "Paneer Tikka Special", 2, "Updated desc", 240.0, "AVAILABLE", null);

        assertEquals("Menu Updated Successfully", result);
        verify(repository).update(any(Menu.class));
    }

    @Test
    @DisplayName("Should return Menu Not Found when updating non-existent item")
    void testUpdateMenu_NotFound() {
        when(repository.findById(99)).thenReturn(null);

        String result = menuService.updateMenu(99, "Unknown", 2, "Desc", 100.0, "AVAILABLE", null);

        assertEquals("Menu Not Found", result);
        verify(repository, never()).update(any(Menu.class));
    }

    @Test
    @DisplayName("Should delete menu item")
    void testDeleteMenu_Success() {
        when(repository.delete(1)).thenReturn(1);

        String result = menuService.deleteMenu(1);

        assertEquals("Menu Deleted Successfully", result);
    }

    @Test
    @DisplayName("Should get menu item by ID")
    void testGetMenu_Found() {
        when(repository.findById(1)).thenReturn(testMenu);

        Menu result = menuService.getMenu(1);

        assertNotNull(result);
        assertEquals("Paneer Tikka", result.getItemName());
    }

    @Test
    @DisplayName("Should get all menu items")
    void testGetAllMenu() {
        when(repository.findAll(null)).thenReturn(List.of(testMenu));

        List<Menu> list = menuService.getAllMenu();

        assertEquals(1, list.size());
        assertEquals("Paneer Tikka", list.get(0).getItemName());
    }

    @Test
    @DisplayName("Should get menu by category")
    void testGetCategoryMenu() {
        when(repository.findByCategory(2)).thenReturn(List.of(testMenu));

        List<Menu> list = menuService.getCategoryMenu(2);

        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Should get menu filtered page")
    void testGetMenuFilteredPage() {
        PageRequestDTO req = PageRequestDTO.builder().page(0).size(10).build();
        when(repository.getMenuFilteredPage(req)).thenReturn(List.of(testMenu));
        when(repository.countMenuFiltered(req)).thenReturn(1L);

        PageResponse<Menu> response = menuService.getMenuFilteredPage(req);

        assertEquals(1, response.getContent().size());
        assertEquals(1L, response.getTotalElements());
    }
}
