package com.restaurant.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.dto.InventoryRequestDTO;
import com.restaurant.dto.InventorySummaryDTO;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.dto.StockAdjustmentDTO;
import com.restaurant.exception.DuplicateResourceException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.exception.ValidationException;
import com.restaurant.model.Inventory;
import com.restaurant.model.InventoryLog;
import com.restaurant.repository.InventoryRepository;
import com.restaurant.service.InventoryService;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    private String calculateStatus(double quantity, double minThreshold) {
        if (quantity <= 0.0) {
            return "OUT_OF_STOCK";
        } else if (quantity <= minThreshold) {
            return "LOW_STOCK";
        } else {
            return "IN_STOCK";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Inventory getInventoryById(int id) {
        Inventory item = repository.findById(id);
        if (item == null) {
            throw new ResourceNotFoundException("Inventory item not found with ID: " + id);
        }
        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<Inventory> getFilteredInventory(PageRequestDTO req) {
        return repository.findFilteredPage(req);
    }

    @Override
    @Transactional(readOnly = true)
    public InventorySummaryDTO getInventorySummary() {
        return repository.getSummary();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCategories() {
        return repository.getDistinctCategories();
    }

    @Override
    public String addInventory(InventoryRequestDTO dto) {
        logger.info("Adding new inventory item: {}", dto.getItemName());

        if (repository.existsByName(dto.getItemName().trim())) {
            throw new DuplicateResourceException("An inventory item with name '" + dto.getItemName().trim() + "' already exists.");
        }

        String calculatedStatus = calculateStatus(dto.getQuantity(), dto.getMinThreshold());

        Inventory item = Inventory.builder()
                .itemName(dto.getItemName().trim())
                .category(dto.getCategory().trim())
                .quantity(dto.getQuantity())
                .unit(dto.getUnit().trim())
                .minThreshold(dto.getMinThreshold())
                .costPerUnit(dto.getCostPerUnit())
                .supplierName(dto.getSupplierName() != null ? dto.getSupplierName().trim() : null)
                .supplierContact(dto.getSupplierContact() != null ? dto.getSupplierContact().trim() : null)
                .lastRestocked(dto.getQuantity() > 0 ? LocalDateTime.now() : null)
                .status(calculatedStatus)
                .build();

        int rows = repository.save(item);
        if (rows > 0) {
            logger.info("Inventory item added successfully: {}", dto.getItemName());
            return "Inventory item added successfully";
        }
        return "Failed to add inventory item";
    }

    @Override
    public String updateInventory(int id, InventoryRequestDTO dto) {
        logger.info("Updating inventory item ID: {}", id);

        Inventory existing = getInventoryById(id);

        if (repository.existsByNameAndNotId(dto.getItemName().trim(), id)) {
            throw new DuplicateResourceException("Another inventory item with name '" + dto.getItemName().trim() + "' already exists.");
        }

        String calculatedStatus = calculateStatus(dto.getQuantity(), dto.getMinThreshold());

        existing.setItemName(dto.getItemName().trim());
        existing.setCategory(dto.getCategory().trim());
        existing.setQuantity(dto.getQuantity());
        existing.setUnit(dto.getUnit().trim());
        existing.setMinThreshold(dto.getMinThreshold());
        existing.setCostPerUnit(dto.getCostPerUnit());
        existing.setSupplierName(dto.getSupplierName() != null ? dto.getSupplierName().trim() : null);
        existing.setSupplierContact(dto.getSupplierContact() != null ? dto.getSupplierContact().trim() : null);
        existing.setStatus(calculatedStatus);

        int rows = repository.update(existing);
        if (rows > 0) {
            logger.info("Inventory item updated successfully: ID {}", id);
            return "Inventory item updated successfully";
        }
        return "Failed to update inventory item";
    }

    @Override
    public String deleteInventory(int id) {
        logger.info("Deleting inventory item ID: {}", id);
        getInventoryById(id); // Ensure exists

        int rows = repository.delete(id);
        if (rows > 0) {
            logger.info("Inventory item deleted: ID {}", id);
            return "Inventory item deleted successfully";
        }
        return "Failed to delete inventory item";
    }

    @Override
    public String adjustStock(int id, StockAdjustmentDTO dto) {
        Inventory item = getInventoryById(id);
        double prevQty = item.getQuantity();
        double changeQty = dto.getQuantity();
        double newQty;
        String actionType = dto.getActionType().toUpperCase();

        switch (actionType) {
            case "RESTOCK":
                newQty = prevQty + changeQty;
                break;
            case "CONSUME":
            case "WASTAGE":
                if (prevQty < changeQty) {
                    throw new ValidationException("Insufficient stock available! Current stock: " + prevQty + " " + item.getUnit() + ", requested: " + changeQty + " " + item.getUnit());
                }
                newQty = prevQty - changeQty;
                break;
            case "ADJUSTMENT":
                newQty = changeQty;
                break;
            default:
                throw new ValidationException("Invalid action type: " + actionType);
        }

        // Round to 2 decimal places
        newQty = Math.round(newQty * 100.0) / 100.0;
        String newStatus = calculateStatus(newQty, item.getMinThreshold());

        repository.updateStockAndStatus(id, newQty, newStatus, actionType);

        // Record Audit Log
        InventoryLog log = InventoryLog.builder()
                .inventoryId(id)
                .actionType(actionType)
                .quantity(changeQty)
                .previousQuantity(prevQty)
                .newQuantity(newQty)
                .reason(dto.getReason() != null ? dto.getReason().trim() : "Standard " + actionType)
                .performedBy(dto.getPerformedBy() != null ? dto.getPerformedBy().trim() : "SYSTEM")
                .build();

        repository.saveLog(log);
        logger.info("Stock adjusted for item '{}' (ID: {}): {} -> {} ({})", item.getItemName(), id, prevQty, newQty, actionType);

        return "Stock " + actionType.toLowerCase() + " updated successfully. New quantity: " + newQty + " " + item.getUnit();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryLog> getInventoryLogs(int inventoryId) {
        getInventoryById(inventoryId); // Ensure exists
        return repository.findLogsByInventoryId(inventoryId);
    }
}
