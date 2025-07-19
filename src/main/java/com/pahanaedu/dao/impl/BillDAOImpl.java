// src/main/java/com/pahanaedu/dao/impl/BillDAOImpl.java
package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import com.pahanaedu.utils.Constants;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillDAOImpl implements BillDAO {
    private final DatabaseConnection dbConnection;

    public BillDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Optional<Bill> findById(String billId) {
        String sql = "SELECT b.*, c.name as customer_name, u.full_name as user_name " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_account_number = c.account_number " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "WHERE b.bill_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bill bill = mapResultSetToBill(rs);
                    bill.setBillItems(findBillItemsByBillId(billId));
                    return Optional.of(bill);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding bill by id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Bill> findAll() {
        String sql = "SELECT b.*, c.name as customer_name, u.full_name as user_name " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_account_number = c.account_number " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "ORDER BY b.bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all bills", e);
        }

        return bills;
    }

    @Override
    public List<Bill> findByCustomerAccountNumber(String accountNumber) {
        String sql = "SELECT b.*, c.name as customer_name, u.full_name as user_name " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_account_number = c.account_number " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "WHERE b.customer_account_number = ? " +
                "ORDER BY b.bill_date DESC";
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
    public List<Bill> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT b.*, c.name as customer_name, u.full_name as user_name " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_account_number = c.account_number " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "WHERE CAST(b.bill_date AS DATE) BETWEEN ? AND ? " +
                "ORDER BY b.bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding bills by date range", e);
        }

        return bills;
    }

    @Override
    public List<Bill> findByUserId(Integer userId) {
        String sql = "SELECT b.*, c.name as customer_name, u.full_name as user_name " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_account_number = c.account_number " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapResultSetToBill(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding bills by user id", e);
        }

        return bills;
    }

    @Override
    public Bill save(Bill bill) {
        if (bill.getBillId() == null) {
            bill.setBillId(generateNextBillId());
        }

        String sql = "INSERT INTO bills (bill_id, customer_account_number, user_id, bill_date, subtotal, tax_amount, " +
                "discount_amount, total_amount, payment_method, payment_status, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, bill.getBillId());
                stmt.setString(2, bill.getCustomerAccountNumber());
                stmt.setInt(3, bill.getUserId());
                stmt.setTimestamp(4, Timestamp.valueOf(bill.getBillDate()));
                stmt.setBigDecimal(5, bill.getSubtotal());
                stmt.setBigDecimal(6, bill.getTaxAmount());
                stmt.setBigDecimal(7, bill.getDiscountAmount());
                stmt.setBigDecimal(8, bill.getTotalAmount());
                stmt.setString(9, bill.getPaymentMethod());
                stmt.setString(10, bill.getPaymentStatus());
                stmt.setString(11, bill.getNotes());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating bill failed, no rows affected.");
                }

                // Save bill items
                if (bill.getBillItems() != null) {
                    for (BillItem item : bill.getBillItems()) {
                        item.setBillId(bill.getBillId());
                        saveBillItem(conn, item);
                    }
                }

                conn.commit();
                return bill;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving bill", e);
        }
    }

    @Override
    public boolean deleteById(String billId) {
        String sql = "DELETE FROM bills WHERE bill_id = ?";

        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Delete bill items first
                deleteBillItemsByBillId(conn, billId);

                // Delete bill
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, billId);
                    int affectedRows = stmt.executeUpdate();

                    conn.commit();
                    return affectedRows > 0;
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting bill", e);
        }
    }

    @Override
    public String generateNextBillId() {
        String sql = "SELECT TOP 1 bill_id FROM bills WHERE bill_id LIKE ? ORDER BY bill_id DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Constants.BILL_PREFIX + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String lastBillId = rs.getString("bill_id");
                    String numberPart = lastBillId.substring(Constants.BILL_PREFIX.length());
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    return Constants.BILL_PREFIX + String.format("%06d", nextNumber);
                } else {
                    return Constants.BILL_PREFIX + "000001";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error generating next bill id", e);
        }
    }

    @Override
    public List<BillItem> findBillItemsByBillId(String billId) {
        String sql = "SELECT * FROM bill_items WHERE bill_id = ? ORDER BY id";
        List<BillItem> billItems = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, billId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    billItems.add(mapResultSetToBillItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding bill items by bill id", e);
        }

        return billItems;
    }

    @Override
    public BillItem saveBillItem(BillItem billItem) {
        try (Connection conn = dbConnection.getConnection()) {
            return saveBillItem(conn, billItem);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving bill item", e);
        }
    }

    private BillItem saveBillItem(Connection conn, BillItem billItem) throws SQLException {
        String sql = "INSERT INTO bill_items (bill_id, isbn, book_title, quantity, unit_price, total_price, discount_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, billItem.getBillId());
            stmt.setString(2, billItem.getIsbn());
            stmt.setString(3, billItem.getBookTitle());
            stmt.setInt(4, billItem.getQuantity());
            stmt.setBigDecimal(5, billItem.getUnitPrice());
            stmt.setBigDecimal(6, billItem.getTotalPrice());
            stmt.setBigDecimal(7, billItem.getDiscountAmount());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        billItem.setId(generatedKeys.getInt(1));
                    }
                }
            }

            return billItem;
        }
    }

    @Override
    public boolean deleteBillItemsByBillId(String billId) {
        try (Connection conn = dbConnection.getConnection()) {
            return deleteBillItemsByBillId(conn, billId);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting bill items", e);
        }
    }

    private boolean deleteBillItemsByBillId(Connection conn, String billId) throws SQLException {
        String sql = "DELETE FROM bill_items WHERE bill_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, billId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getString("bill_id"));
        bill.setCustomerAccountNumber(rs.getString("customer_account_number"));
        bill.setUserId(rs.getInt("user_id"));

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

        // Set additional fields from JOIN
        bill.setCustomerName(rs.getString("customer_name"));
        bill.setUserName(rs.getString("user_name"));

        return bill;
    }

    private BillItem mapResultSetToBillItem(ResultSet rs) throws SQLException {
        BillItem billItem = new BillItem();
        billItem.setId(rs.getInt("id"));
        billItem.setBillId(rs.getString("bill_id"));
        billItem.setIsbn(rs.getString("isbn"));
        billItem.setBookTitle(rs.getString("book_title"));
        billItem.setQuantity(rs.getInt("quantity"));
        billItem.setUnitPrice(rs.getBigDecimal("unit_price"));
        billItem.setTotalPrice(rs.getBigDecimal("total_price"));
        billItem.setDiscountAmount(rs.getBigDecimal("discount_amount"));

        return billItem;
    }
}