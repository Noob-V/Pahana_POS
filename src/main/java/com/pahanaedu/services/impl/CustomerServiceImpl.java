package com.pahanaedu.services.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.impl.CustomerDAOImpl;
import com.pahanaedu.entities.Bill;
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
        return customerDAO.searchByName(name);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteByAccountNumber(String accountNumber) {
        return customerDAO.deleteByAccountNumber(accountNumber);
    }

    @Override
    public List<Bill> findBillsByCustomer(String accountNumber) {
        return customerDAO.findBillsByCustomerAccountNumber(accountNumber);
    }

    @Override
    public List<Bill> findRecentBillsByCustomer(String accountNumber, int limit) {
        return customerDAO.findRecentBillsByCustomer(accountNumber, limit);
    }
}