// src/main/java/com/pahanaedu/dao/com.pahanaedu.services.impl/CategoryDAOImpl.java
package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.CategoryDAO;
import com.pahanaedu.entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    private final DatabaseConnection dbConnection;

    public CategoryDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        String sql = "SELECT * FROM categories WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding category by id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM categories ORDER BY name";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all categories", e);
        }

        return categories;
    }

    @Override
    public List<Category> findActive() {
        String sql = "SELECT * FROM categories WHERE is_active = 1 ORDER BY name";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active categories", e);
        }

        return categories;
    }

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO categories (name, description, is_active) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setBoolean(3, category.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }

            return category;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving category", e);
        }
    }

    @Override
    public Category update(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ?, is_active = ? WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setBoolean(3, category.isActive());
            stmt.setInt(4, category.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating category failed, no rows affected.");
            }

            return category;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating category", e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "UPDATE categories SET is_active = 0 WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category", e);
        }
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setActive(rs.getBoolean("is_active"));

        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            category.setCreatedDate(createdDate.toLocalDateTime());
        }

        return category;
    }
}