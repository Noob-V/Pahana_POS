// src/main/java/com/pahanaedu/services/impl/CustomerServiceImpl.java
package com.pahanaedu.services.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.impl.CustomerDAOImpl;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.services.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
    }

    @Override
    public Optional<Customer> findByAccountNumber(String accountNumber) {
        return customerDAO.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public List<Customer> searchByName(String name) {
        return customerDAO.findByName(name);
    }

    @Override
    public Customer save(Customer customer) {
        validateCustomer(customer);
        return customerDAO.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        validateCustomer(customer);
        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteByAccountNumber(String accountNumber) {
        return customerDAO.deleteByAccountNumber(accountNumber);
    }

    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty() && !customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email address is required");
        }
    }
}