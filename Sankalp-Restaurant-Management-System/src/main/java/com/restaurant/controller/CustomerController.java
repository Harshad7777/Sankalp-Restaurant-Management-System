package com.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Customer;
import com.restaurant.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    /* =====================================================
       ADD CUSTOMER
    ===================================================== */

    @PostMapping
    public ResponseEntity<String> addCustomer(
            @jakarta.validation.Valid @RequestBody Customer customer) {

        try {

            String result =
                    service.addCustomer(customer);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }


    /* =====================================================
       UPDATE CUSTOMER
    ===================================================== */

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable("id") int id,
            @jakarta.validation.Valid @RequestBody Customer customer) {

        try {

            customer.setCustomerId(id);

            String result =
                    service.updateCustomer(customer);

            return ResponseEntity
                    .ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }


    /* =====================================================
       DELETE CUSTOMER
    ===================================================== */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable("id") int id) {

        try {

            String result =
                    service.deleteCustomer(id);

            return ResponseEntity
                    .ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }


    /* =====================================================
       GET CUSTOMER BY ID
    ===================================================== */

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable("id") int id) {

        Customer customer =
                service.getCustomer(id);

        if (customer == null) {

            return ResponseEntity
                    .notFound()
                    .build();

        }

        return ResponseEntity
                .ok(customer);

    }


    /* =====================================================
       GET ALL CUSTOMERS
    ===================================================== */

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {

        List<Customer> customers =
                service.getAllCustomer();

        return ResponseEntity
                .ok(customers);

    }


    /* =====================================================
       SEARCH CUSTOMER
    ===================================================== */

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomer(
            @RequestParam("keyword") String keyword) {

        List<Customer> customers =
                service.searchCustomer(keyword);

        return ResponseEntity
                .ok(customers);

    }

    /* =====================================================
       GET PAGINATED, FILTERED, SORTED CUSTOMERS
       GET /api/customer/page
    ===================================================== */

    @GetMapping("/page")
    public ResponseEntity<com.restaurant.dto.PageResponse<Customer>> getCustomersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {

        com.restaurant.dto.PageRequestDTO req = com.restaurant.dto.PageRequestDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .search(search)
                .status(status)
                .build();

        return ResponseEntity.ok(service.getCustomersFilteredPage(req));
    }

}