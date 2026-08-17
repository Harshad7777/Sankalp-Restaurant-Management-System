package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.model.Table;
import com.restaurant.repository.TableRepository;

@Service
public class TableService {

    private final TableRepository repository;

    public TableService(TableRepository repository) {
        this.repository = repository;
    }

    public List<Table> getAllTables() {

        return repository.findAll();
    }

    public Table getTable(int id) {

        Table table =
                repository.findById(id);

        if (table == null) {
            throw new RuntimeException(
                    "Table not found"
            );
        }

        return table;
    }

    public String addTable(Table table) {

        if (table == null) {
            return "Table data is required";
        }

        if (
                table.getTableNumber() == null ||
                table.getTableNumber()
                        .trim()
                        .isEmpty()
        ) {
            return "Table number is required";
        }

        if (
                table.getCapacity() == null ||
                table.getCapacity() <= 0
        ) {
            return "Capacity must be greater than 0";
        }

        String tableNumber =
                table.getTableNumber()
                        .trim();

        if (
                repository.existsByTableNumber(
                        tableNumber
                )
        ) {
            return "Table number already exists";
        }

        table.setTableNumber(tableNumber);

        if (
                table.getTableStatus() == null ||
                table.getTableStatus()
                        .trim()
                        .isEmpty()
        ) {
            table.setTableStatus("AVAILABLE");
        }
        else {
            table.setTableStatus(
                    table.getTableStatus()
                            .trim()
                            .toUpperCase()
            );
        }

        if (!isValidStatus(
                table.getTableStatus()
        )) {
            return "Invalid table status";
        }

        int result =
                repository.save(table);

        return result > 0
                ? "Table Added Successfully"
                : "Unable to add table";
    }

    public String updateTable(Table table) {

        if (
                table == null ||
                table.getTableId() == null ||
                table.getTableId() <= 0
        ) {
            return "Invalid table ID";
        }

        if (
                table.getTableNumber() == null ||
                table.getTableNumber()
                        .trim()
                        .isEmpty()
        ) {
            return "Table number is required";
        }

        if (
                table.getCapacity() == null ||
                table.getCapacity() <= 0
        ) {
            return "Capacity must be greater than 0";
        }

        Table existing =
                repository.findById(
                        table.getTableId()
                );

        if (existing == null) {
            return "Table not found";
        }

        String tableNumber =
                table.getTableNumber()
                        .trim();

        if (
                repository.existsByTableNumberAndNotId(
                        tableNumber,
                        table.getTableId()
                )
        ) {
            return "Table number already exists";
        }

        table.setTableNumber(tableNumber);

        if (
                table.getTableStatus() == null ||
                table.getTableStatus()
                        .trim()
                        .isEmpty()
        ) {
            table.setTableStatus("AVAILABLE");
        }
        else {
            table.setTableStatus(
                    table.getTableStatus()
                            .trim()
                            .toUpperCase()
            );
        }

        if (!isValidStatus(
                table.getTableStatus()
        )) {
            return "Invalid table status";
        }

        int result =
                repository.update(table);

        return result > 0
                ? "Table Updated Successfully"
                : "Unable to update table";
    }

    public String deleteTable(int id) {

        Table existing =
                repository.findById(id);

        if (existing == null) {
            return "Table not found";
        }

        int result =
                repository.delete(id);

        return result > 0
                ? "Table Deleted Successfully"
                : "Unable to delete table";
    }

    private boolean isValidStatus(
            String status) {

        return "AVAILABLE".equals(status)
                || "OCCUPIED".equals(status)
                || "RESERVED".equals(status)
                || "CLEANING".equals(status)
                || "BILLED".equals(status);
    }
}