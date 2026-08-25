package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.InventoryRequestDTO;
import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.dto.StockAdjustmentDTO;
import com.restaurant.model.Inventory;
import com.restaurant.model.InventoryLog;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Inventory getInventoryById(int id);

    PageResponse<Inventory> getFilteredInventory(PageRequestDTO req);

    InventorySummaryDTO getInventorySummary();

    List<String> getCategories();

    String addInventory(InventoryRequestDTO dto);

    String updateInventory(int id, InventoryRequestDTO dto);

    String deleteInventory(int id);

    String adjustStock(int id, StockAdjustmentDTO dto);

    List<InventoryLog> getInventoryLogs(int inventoryId);
}
