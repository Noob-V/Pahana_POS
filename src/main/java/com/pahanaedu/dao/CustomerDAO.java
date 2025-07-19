// src/main/java/com/pahanaedu/dao/CustomerDAO.java
package com.pahanaedu.dao;

import com.pahanaedu.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Optional<Customer> findByAccountNumber(String accountNumber);
    List<Customer> findAll();
    List<Customer> findByName(String name);
    Customer save(Customer customer);
    Customer update(Customer customer);
    boolean deleteByAccountNumber(String accountNumber);
    String generateNextAccountNumber();
}