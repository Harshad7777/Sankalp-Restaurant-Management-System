package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.dto.PageRequestDTO;
import com.restaurant.dto.PageResponse;
import com.restaurant.model.Customer;
import com.restaurant.service.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
        testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testCustomer.setCustomerName("Rahul Sharma");
        testCustomer.setMobile("9876543210");
        testCustomer.setEmail("rahul@example.com");
        testCustomer.setAddress("Mumbai");
        testCustomer.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Should successfully add a new customer")
    void testAddCustomer_Success() throws Exception {
        when(customerService.addCustomer(any(Customer.class)))
                .thenReturn("Customer Added Successfully");

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Customer Added Successfully"));
    }

    @Test
    @DisplayName("Should return 400 when adding customer throws exception")
    void testAddCustomer_Exception() throws Exception {
        when(customerService.addCustomer(any(Customer.class)))
                .thenThrow(new RuntimeException("Mobile already exists"));

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Mobile already exists"));
    }

    @Test
    @DisplayName("Should update customer")
    void testUpdateCustomer_Success() throws Exception {
        when(customerService.updateCustomer(any(Customer.class)))
                .thenReturn("Customer Updated Successfully");

        mockMvc.perform(put("/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer Updated Successfully"));
    }

    @Test
    @DisplayName("Should delete customer")
    void testDeleteCustomer_Success() throws Exception {
        when(customerService.deleteCustomer(1))
                .thenReturn("Customer Deleted Successfully");

        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer Deleted Successfully"));
    }

    @Test
    @DisplayName("Should get customer by ID - Found")
    void testGetCustomerById_Found() throws Exception {
        when(customerService.getCustomer(1)).thenReturn(testCustomer);

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("Rahul Sharma"));
    }

    @Test
    @DisplayName("Should get customer by ID - Not Found")
    void testGetCustomerById_NotFound() throws Exception {
        when(customerService.getCustomer(99)).thenReturn(null);

        mockMvc.perform(get("/customer/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get all customers")
    void testGetAllCustomers() throws Exception {
        when(customerService.getAllCustomer()).thenReturn(List.of(testCustomer));

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("Rahul Sharma"));
    }

    @Test
    @DisplayName("Should search customer by keyword")
    void testSearchCustomer() throws Exception {
        when(customerService.searchCustomer("Rahul")).thenReturn(List.of(testCustomer));

        mockMvc.perform(get("/customer/search").param("keyword", "Rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerName").value("Rahul Sharma"));
    }

    @Test
    @DisplayName("Should get paginated customers")
    void testGetCustomersPage() throws Exception {
        PageResponse<Customer> pageResponse = PageResponse.<Customer>builder()
                .content(List.of(testCustomer))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .build();

        when(customerService.getCustomersFilteredPage(any(PageRequestDTO.class))).thenReturn(pageResponse);

        mockMvc.perform(get("/customer/page")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].customerName").value("Rahul Sharma"));
    }
}
