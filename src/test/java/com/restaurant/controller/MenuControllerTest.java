package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.model.Menu;
import com.restaurant.service.MenuService;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    void testGetAllMenu() throws Exception {
        Menu item1 = new Menu();
        item1.setItemId(1);
        item1.setItemName("Paneer Butter Masala");
        item1.setPrice(250.0);

        Menu item2 = new Menu();
        item2.setItemId(2);
        item2.setItemName("Butter Naan");
        item2.setPrice(40.0);

        List<Menu> menuList = Arrays.asList(item1, item2);
        when(menuService.getAllMenu()).thenReturn(menuList);

        mockMvc.perform(get("/api/menu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].itemName").value("Paneer Butter Masala"))
                .andExpect(jsonPath("$[1].itemName").value("Butter Naan"));
    }

    @Test
    void testGetMenuById() throws Exception {
        Menu item = new Menu();
        item.setItemId(1);
        item.setItemName("Paneer Butter Masala");
        item.setPrice(250.0);

        when(menuService.getMenu(1)).thenReturn(item);

        mockMvc.perform(get("/api/menu/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(1))
                .andExpect(jsonPath("$.itemName").value("Paneer Butter Masala"));
    }

    @Test
    void testDeleteMenu() throws Exception {
        when(menuService.deleteMenu(anyInt())).thenReturn("Menu deleted successfully");

        mockMvc.perform(delete("/api/menu/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Menu deleted successfully"));
    }
}
