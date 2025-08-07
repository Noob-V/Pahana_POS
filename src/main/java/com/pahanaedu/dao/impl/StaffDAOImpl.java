package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.entities.Staff;
import com.pahanaedu.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StaffDAOImpl implements StaffDAO {
    private final DatabaseConnection dbConnection;

    public StaffDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public List<Staff> findAllStaff() {
        String sql = "SELECT * FROM users WHERE role = 'STAFF' ORDER BY created_date DESC";
        List<Staff> staffList = new ArrayList<>(); // Changed from List<User> to List<Staff>

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                staffList.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all staff", e);
        }

        return staffList;
    }

    @Override
    public Optional<Staff> findStaffByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND role = 'STAFF'";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding staff by username", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Staff> findStaffById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ? AND role = 'STAFF'";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding staff by id", e);
        }

        return Optional.empty();
    }

    @Override
    public Staff saveStaff(Staff staff) { // Changed return type from User to Staff
        String sql = "INSERT INTO users (username, password_hash, full_name, email, role, is_active) VALUES (?, ?, ?, ?, 'STAFF', ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, staff.getUsername());
            stmt.setString(2, staff.getPasswordHash());
            stmt.setString(3, staff.getFullName());
            stmt.setString(4, staff.getEmail());
            stmt.setBoolean(5, staff.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating staff failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    staff.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating staff failed, no ID obtained.");
                }
            }

            return staff; // Now returns Staff instead of User
        } catch (SQLException e) {
            throw new RuntimeException("Error saving staff", e);
        }
    }

    @Override
    public Staff updateStaff(Staff staff) {
        String sql = "UPDATE users SET full_name = ?, email = ?, is_active = ? WHERE id = ? AND role = 'STAFF'";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, staff.getFullName());
            stmt.setString(2, staff.getEmail());
            stmt.setBoolean(3, staff.isActive());
            stmt.setInt(4, staff.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating staff failed, no rows affected.");
            }

            return staff;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating staff", e);
        }
    }

    @Override
    public boolean deleteStaff(String username) {
        String sql = "UPDATE users SET is_active = 0 WHERE username = ? AND role = 'STAFF'";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting staff", e);
        }
    }

    private Staff mapResultSetToUser(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setId(rs.getInt("id"));
        staff.setUsername(rs.getString("username"));
        staff.setPasswordHash(rs.getString("password_hash"));
        staff.setFullName(rs.getString("full_name"));
        staff.setEmail(rs.getString("email"));
        staff.setActive(rs.getBoolean("is_active"));

        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            staff.setCreatedDate(createdDate.toLocalDateTime());
        }

        Timestamp lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) {
            staff.setLastLogin(lastLogin.toLocalDateTime());
        }

        return staff;
    }
}