package com.pahanaedu.dao;

import com.pahanaedu.entities.Customer;
import com.pahanaedu.entities.Bill;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Optional<Customer> findByAccountNumber(String accountNumber);
    List<Customer> findAll();
    List<Customer> findByName(String name);
    List<Customer> findByPhone(String phone);
    List<Customer> searchByName(String name);
    List<Customer> findByEmail(String email);
    Customer save(Customer customer);
    Customer update(Customer customer);
    boolean deleteByAccountNumber(String accountNumber);
    String generateNextAccountNumber();

    // Bill history methods
    List<Bill> findBillsByCustomerAccountNumber(String accountNumber);
    List<Bill> findRecentBillsByCustomer(String accountNumber, int limit);
}