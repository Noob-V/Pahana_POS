package com.pahanaedu.patterns.builder;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;

import java.time.LocalDateTime;

public class CustomerBuilder {
    private final Customer customer;

    public CustomerBuilder() {
        customer = new Customer();
    }

    public CustomerBuilder accountNumber(String accountNumber) {
        customer.setAccountNumber(accountNumber);
        return this;
    }

    public CustomerBuilder name(String name) {
        customer.setName(name);
        return this;
    }

    public CustomerBuilder address(String address) {
        customer.setAddress(address);
        return this;
    }

    public CustomerBuilder phoneNumber(String phoneNumber) {
        customer.setPhoneNumber(phoneNumber);
        return this;
    }

    public CustomerBuilder email(String email) {
        customer.setEmail(email);
        return this;
    }

    public CustomerBuilder active(boolean active) {
        customer.setActive(active);
        return this;
    }

    public CustomerBuilder createdDate(LocalDateTime createdDate) {
        customer.setCreatedDate(createdDate);
        return this;
    }

    public CustomerBuilder updatedDate(LocalDateTime updatedDate) {
        customer.setUpdatedDate(updatedDate);
        return this;
    }

    public Customer build() {
        return customer;
    }

    public CustomerBuilder withAutoGeneration(CustomerDAO customerDAO) {
        if (customer.getAccountNumber() == null) {
            customer.setAccountNumber(customerDAO.generateNextAccountNumber());
        }
        if (customer.getCreatedDate() == null) {
            customer.setCreatedDate(LocalDateTime.now());
        }
        if (customer.getUpdatedDate() == null) {
            customer.setUpdatedDate(LocalDateTime.now());
        }
        return this;
    }

    public Customer buildWithValidation() {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getAccountNumber() == null || customer.getAccountNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Account number is required");
        }
        return customer;
    }
}