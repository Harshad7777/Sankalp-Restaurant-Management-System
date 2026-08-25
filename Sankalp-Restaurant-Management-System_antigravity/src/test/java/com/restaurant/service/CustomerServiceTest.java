package com.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.model.Customer;
import com.restaurant.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .customerId(1)
                .customerName("Bob Martin")
                .mobile("9876543210")
                .email("bob@example.com")
                .address("123 Main St")
                .status("ACTIVE")
                .build();
    }

    @Test
    @DisplayName("Should successfully add a customer")
    void testAddCustomer_Success() {
        when(repository.existsByMobile("9876543210")).thenReturn(false);
        when(repository.save(any(Customer.class))).thenReturn(1);

        String result = customerService.addCustomer(testCustomer);

        assertEquals("Customer Added Successfully", result);
        verify(repository).save(testCustomer);
    }

    @Test
    @DisplayName("Should reject customer when mobile already exists")
    void testAddCustomer_DuplicateMobile() {
        when(repository.existsByMobile("9876543210")).thenReturn(true);

        String result = customerService.addCustomer(testCustomer);

        assertEquals("Customer Already Exists", result);
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should update customer profile")
    void testUpdateCustomer_Success() {
        when(repository.update(any(Customer.class))).thenReturn(1);

        String result = customerService.updateCustomer(testCustomer);

        assertEquals("Customer Updated Successfully", result);
        verify(repository).update(testCustomer);
    }

    @Test
    @DisplayName("Should delete customer by ID")
    void testDeleteCustomer_Success() {
        when(repository.delete(1)).thenReturn(1);

        String result = customerService.deleteCustomer(1);

        assertEquals("Customer Deleted Successfully", result);
        verify(repository).delete(1);
    }

    @Test
    @DisplayName("Should search customers by keyword")
    void testSearchCustomer() {
        when(repository.search("Bob")).thenReturn(List.of(testCustomer));

        List<Customer> list = customerService.searchCustomer("Bob");

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Bob Martin", list.get(0).getCustomerName());
    }
}
