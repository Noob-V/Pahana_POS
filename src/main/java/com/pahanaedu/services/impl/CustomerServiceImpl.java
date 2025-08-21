package com.pahanaedu.services.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dto.CustomerDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.mapper.CustomerMapper;
import com.pahanaedu.patterns.factory.CustomerFactory;
import com.pahanaedu.patterns.factory.MySQLDAOCreator;
import com.pahanaedu.services.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;
    private final CustomerFactory customerFactory;

    public CustomerServiceImpl() {
        MySQLDAOCreator<CustomerDAO> daoCreator = new MySQLDAOCreator<>();
        this.customerDAO = daoCreator.createDAO("customer").getDAO();
        this.customerFactory = new CustomerFactory(daoCreator);
    }

    // Factory-based customer creation methods
    @Override
    public Customer createSampleCustomer(String name, String phone, String email) {
        return customerFactory.createSampleCustomer(name, phone, email);
    }

    @Override
    public Customer createCustomerFromRequest(String accountNumber, String name,
                                              String address, String phone, String email) {
        return customerFactory.createCustomerFromRequest(accountNumber, name, address, phone, email);
    }

    @Override
    public Customer createPremiumCustomer(String name, String phone, String email, String address) {
        return customerFactory.createPremiumCustomer(name, phone, email, address);
    }

    @Override
    public String generateNextAccountNumber() {
        return customerDAO.generateNextAccountNumber();
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

    @Override
    public CustomerDTO findCustomerDTOByAccountNumber(String accountNumber) {
        Optional<Customer> customerOpt = customerDAO.findByAccountNumber(accountNumber);
        return customerOpt.map(CustomerMapper::toDTO).orElse(null);
    }

    @Override
    public List<CustomerDTO> findAllDTOs() {
        List<Customer> customers = customerDAO.findAll();
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomerDTOsByName(String name) {
        List<Customer> customers = customerDAO.searchByName(name);
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveDTO(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerDAO.save(customer);
        return CustomerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateDTO(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer updatedCustomer = customerDAO.update(customer);
        return CustomerMapper.toDTO(updatedCustomer);
    }
}