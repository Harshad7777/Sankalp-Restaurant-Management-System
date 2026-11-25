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

import com.restaurant.model.Customer;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should save customer")
    void testSave() {
        Customer customer = new Customer();
        customer.setCustomerName("Pooja");
        customer.setMobile("9876543210");
        customer.setEmail("pooja@example.com");
        customer.setAddress("Pune");
        customer.setStatus("ACTIVE");

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any())).thenReturn(1);

        int result = customerRepository.save(customer);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should update customer")
    void testUpdate() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Pooja Updated");
        customer.setMobile("9876543210");
        customer.setEmail("pooja@example.com");
        customer.setAddress("Pune");
        customer.setStatus("ACTIVE");

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), eq(1), any())).thenReturn(1);

        int result = customerRepository.update(customer);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should delete customer")
    void testDelete() {
        when(jdbcTemplate.update(anyString(), eq(1), any())).thenReturn(1);

        int result = customerRepository.delete(1);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should find customer by ID")
    void testFindById_Found() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Pooja");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(1), any()))
                .thenReturn(Collections.singletonList(customer));

        Customer result = customerRepository.findById(1);
        assertNotNull(result);
        assertEquals("Pooja", result.getCustomerName());
    }

    @Test
    @DisplayName("Should search customers by keyword")
    void testSearch() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Pooja");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any(), any(), any()))
                .thenReturn(Collections.singletonList(customer));

        List<Customer> result = customerRepository.search("Pooja");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pooja", result.get(0).getCustomerName());
    }

    @Test
    @DisplayName("Should check exists by mobile")
    void testExistsByMobile() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("9876543210"), any()))
                .thenReturn(1);

        assertTrue(customerRepository.existsByMobile("9876543210"));
    }

    @Test
    @DisplayName("Should find all customers")
    void testFindAll() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("Pooja");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), any()))
                .thenReturn(Collections.singletonList(customer));

        List<Customer> list = customerRepository.findAll();
        assertEquals(1, list.size());
    }
}
