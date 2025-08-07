// src/main/java/com/pahanaedu/dao/impl/BookDAOImpl.java
package com.pahanaedu.dao.impl;

import com.pahanaedu.dao.BookDAO;
import com.pahanaedu.entities.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAOImpl implements BookDAO {
    private final DatabaseConnection dbConnection;

    public BookDAOImpl() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id WHERE b.isbn = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding book by ISBN: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id ORDER BY b.title";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding all books: " + e.getMessage());
        }

        return books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id WHERE b.title LIKE ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding books by title: " + e.getMessage());
        }

        return books;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id WHERE b.author LIKE ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding books by author: " + e.getMessage());
        }

        return books;
    }

    @Override
    public List<Book> findByCategoryId(Integer categoryId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id WHERE b.category_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding books by category: " + e.getMessage());
        }

        return books;
    }

    @Override
    public List<Book> findLowStockBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b LEFT JOIN categories c ON b.category_id = c.id WHERE b.quantity <= b.min_stock_level AND b.is_active = 1";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error finding low stock books: " + e.getMessage());
        }

        return books;
    }

    @Override
    public Book save(Book book) {
        // Check if book already exists
        Optional<Book> existingBook = findByIsbn(book.getIsbn());

        if (existingBook.isPresent()) {
            // Book exists, perform UPDATE
            return update(book);
        } else {
            // Book doesn't exist, perform INSERT
            return insertNewBook(book);
        }
    }

    private Book insertNewBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, publisher, description, price, quantity, min_stock_level, category_id, google_book_id, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getPublisher());
            stmt.setString(5, book.getDescription());
            stmt.setBigDecimal(6, book.getPrice());
            stmt.setInt(7, book.getQuantity());
            stmt.setInt(8, book.getMinStockLevel());

            if (book.getCategoryId() != null) {
                stmt.setInt(9, book.getCategoryId());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            stmt.setString(10, book.getGoogleBookId());
            stmt.setString(11, book.getImageUrl());
            stmt.setBoolean(12, book.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            return book;
        } catch (SQLException e) {
            System.err.println("SQL Error inserting book: " + e.getMessage());
            throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, description = ?, price = ?, quantity = ?, min_stock_level = ?, category_id = ?, google_book_id = ?, image_url = ?, is_active = ?, updated_date = GETDATE() WHERE isbn = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, book.getDescription());
            stmt.setBigDecimal(5, book.getPrice());
            stmt.setInt(6, book.getQuantity());
            stmt.setInt(7, book.getMinStockLevel());

            if (book.getCategoryId() != null) {
                stmt.setInt(8, book.getCategoryId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.setString(9, book.getGoogleBookId());
            stmt.setString(10, book.getImageUrl());
            stmt.setBoolean(11, book.isActive());
            stmt.setString(12, book.getIsbn());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }

            return book;
        } catch (SQLException e) {
            System.err.println("SQL Error updating book: " + e.getMessage());
            throw new RuntimeException("Error updating book: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error deleting book: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateQuantity(String isbn, Integer newQuantity) {
        String sql = "UPDATE books SET quantity = ? WHERE isbn = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setString(2, isbn);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error updating quantity: " + e.getMessage());
            return false;
        }
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublisher(rs.getString("publisher"));
        book.setDescription(rs.getString("description"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setQuantity(rs.getInt("quantity"));
        book.setMinStockLevel(rs.getInt("min_stock_level"));
        book.setCategoryId(rs.getObject("category_id", Integer.class));
        book.setCategoryName(rs.getString("category_name"));
        book.setGoogleBookId(rs.getString("google_book_id"));
        book.setImageUrl(rs.getString("image_url"));
        book.setActive(rs.getBoolean("is_active"));

        Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            book.setCreatedDate(createdDate.toLocalDateTime());
        }

        Timestamp updatedDate = rs.getTimestamp("updated_date");
        if (updatedDate != null) {
            book.setUpdatedDate(updatedDate.toLocalDateTime());
        }

        return book;
    }
}