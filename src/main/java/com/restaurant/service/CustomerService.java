package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.model.Customer;
import com.restaurant.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public String addCustomer(Customer customer) {

        if(repository.existsByMobile(customer.getMobile()))
            return "Customer Already Exists";

        customer.setStatus("ACTIVE");

        int value = repository.save(customer);

        return value > 0 ?
                "Customer Added Successfully"
                :
                "Customer Not Added";
    }

    public String updateCustomer(Customer customer) {

        int value = repository.update(customer);

        return value > 0 ?
                "Customer Updated Successfully"
                :
                "Customer Not Updated";
    }

    public String deleteCustomer(int id) {

        int value = repository.delete(id);

        return value > 0 ?
                "Customer Deleted Successfully"
                :
                "Customer Not Deleted";
    }

    public Customer getCustomer(int id) {
        return repository.findById(id);
    }

    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }

    public List<Customer> searchCustomer(String keyword) {
        return repository.search(keyword);
    }
}