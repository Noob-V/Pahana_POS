package com.pahanaedu.services;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findByAccountNumber(String accountNumber);
    List<Customer> findAll();
    List<Customer> searchByName(String name);
    List<Customer> findByEmail(String email);
    Customer save(Customer customer);
    Customer update(Customer customer);
    boolean deleteByAccountNumber(String accountNumber);
    List<Bill> findBillsByCustomer(String accountNumber);
    List<Bill> findRecentBillsByCustomer(String accountNumber, int limit);
}