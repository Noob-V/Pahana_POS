package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.entities.Bill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

public class CustomerDAOImpl implements CustomerDAO {
    private final DatabaseConnection dbConnection;

    public CustomerDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Optional<Customer> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM customers WHERE account_number = ? AND is_active = 1";

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
    public List<Customer> searchByName(String name) {
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
            throw new RuntimeException("Error searching customers by name", e);
        }

        return customers;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers WHERE is_active = 1 ORDER BY created_date DESC";
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
    public List<Customer> findByPhone(String phone) {
        String sql = "SELECT * FROM customers WHERE phone_number LIKE ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + phone + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding customers by phone", e);
        }

        return customers;
    }

    @Override
    public List<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email LIKE ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + email + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding customers by email", e);
        }

        return customers;
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getAccountNumber() == null) {
            customer.setAccountNumber(generateNextAccountNumber());
        }

        String sql = "INSERT INTO customers (account_number, name, address, phone_number, email, is_active, created_date, updated_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getEmail());
            stmt.setBoolean(6, customer.isActive());
            stmt.setTimestamp(7, Timestamp.valueOf(customer.getCreatedDate()));
            stmt.setTimestamp(8, Timestamp.valueOf(customer.getUpdatedDate()));

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
        customer.setUpdatedDate(LocalDateTime.now());
        String sql = "UPDATE customers SET name = ?, address = ?, phone_number = ?, email = ?, is_active = ?, updated_date = ? WHERE account_number = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setString(4, customer.getEmail());
            stmt.setBoolean(5, customer.isActive());
            stmt.setTimestamp(6, Timestamp.valueOf(customer.getUpdatedDate()));
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
        String sql = "UPDATE customers SET is_active = 0, updated_date = ? WHERE account_number = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, accountNumber);
            int rowsAffected = stmt.executeUpdate();

            System.out.println("Soft delete operation - Account: " + accountNumber + ", Rows affected: " + rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error soft deleting customer " + accountNumber + ": " + e.getMessage());
            throw new RuntimeException("Error deactivating customer: " + e.getMessage(), e);
        }
    }

    @Override
    public String generateNextAccountNumber() {
        String sql = "SELECT TOP 1 account_number FROM customers WHERE account_number LIKE 'CUST%' ORDER BY account_number DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastAccountNumber = rs.getString("account_number");
                    String numberPart = lastAccountNumber.substring(4);
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    return "CUST" + String.format("%06d", nextNumber);
                } else {
                    return "CUST000001";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating next account number", e);
        }
    }

    @Override
    public List<Bill> findBillsByCustomerAccountNumber(String accountNumber) {
        String sql = "SELECT b.*, u.full_name as user_name FROM bills b LEFT JOIN users u ON b.user_id = u.id WHERE b.customer_account_number = ? ORDER BY b.bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding bills by customer account number", e);
        }

        return bills;
    }

    @Override
    public List<Bill> findRecentBillsByCustomer(String accountNumber, int limit) {
        String sql = "SELECT TOP (?) b.*, u.full_name as user_name FROM bills b LEFT JOIN users u ON b.user_id = u.id WHERE b.customer_account_number = ? ORDER BY b.bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setString(2, accountNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding recent bills by customer", e);
        }

        return bills;
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setAccountNumber(rs.getString("account_number"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        customer.setEmail(rs.getString("email"));
        customer.setActive(rs.getBoolean("is_active"));

        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            customer.setCreatedDate(createdDate.toLocalDateTime());
        }

        Timestamp updatedDate = rs.getTimestamp("updated_date");
        if (updatedDate != null) {
            customer.setUpdatedDate(updatedDate.toLocalDateTime());
        }

        return customer;
    }

    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getString("bill_id"));
        bill.setCustomerAccountNumber(rs.getString("customer_account_number"));

        Integer userId = rs.getObject("user_id", Integer.class);
        bill.setUserId(userId);

        Timestamp billDate = rs.getTimestamp("bill_date");
        if (billDate != null) {
            bill.setBillDate(billDate.toLocalDateTime());
        }

        bill.setSubtotal(rs.getBigDecimal("subtotal"));
        bill.setTaxAmount(rs.getBigDecimal("tax_amount"));
        bill.setDiscountAmount(rs.getBigDecimal("discount_amount"));
        bill.setTotalAmount(rs.getBigDecimal("total_amount"));
        bill.setPaymentMethod(rs.getString("payment_method"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        bill.setNotes(rs.getString("notes"));
        bill.setUserName(rs.getString("user_name"));

        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            bill.setCreatedDate(createdDate.toLocalDateTime());
        }

        return bill;
    }
}