// src/main/java/com/pahanaedu/dao/com.pahanaedu.services.impl/CustomerDAOImpl.java
package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    private final DatabaseConnection dbConnection;

    public CustomerDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Optional<Customer> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM customers WHERE account_number = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding customer by account number", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers ORDER BY created_date DESC";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all customers", e);
        }

        return customers;
    }

    @Override
    public List<Customer> findByName(String name) {
        String sql = "SELECT * FROM customers WHERE name LIKE ? ORDER BY name";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding customers by name", e);
        }

        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getAccountNumber() == null) {
            customer.setAccountNumber(generateNextAccountNumber());
        }

        String sql = "INSERT INTO customers (account_number, name, email, phone, address, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getCity());
            stmt.setString(7, customer.getPostalCode());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            return customer;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving customer", e);
        }
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ?, city = ?, postal_code = ? WHERE account_number = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getCity());
            stmt.setString(6, customer.getPostalCode());
            stmt.setString(7, customer.getAccountNumber());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating customer failed, no rows affected.");
            }

            return customer;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }

    @Override
    public boolean deleteByAccountNumber(String accountNumber) {
        String sql = "DELETE FROM customers WHERE account_number = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }

    @Override
    public String generateNextAccountNumber() {
        String sql = "SELECT TOP 1 account_number FROM customers WHERE account_number LIKE ? ORDER BY account_number DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Constants.ACCOUNT_PREFIX + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastAccountNumber = rs.getString("account_number");
                    String numberPart = lastAccountNumber.substring(Constants.ACCOUNT_PREFIX.length());
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    return Constants.ACCOUNT_PREFIX + String.format("%06d", nextNumber);
                } else {
                    return Constants.ACCOUNT_PREFIX + "000001";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating next account number", e);
        }
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setAccountNumber(rs.getString("account_number"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setPostalCode(rs.getString("postal_code"));

        // Fix: Use setCreatedAt instead of setCreatedDate
        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            customer.setCreatedAt(createdDate.toLocalDateTime());
        }

        return customer;
    }
}