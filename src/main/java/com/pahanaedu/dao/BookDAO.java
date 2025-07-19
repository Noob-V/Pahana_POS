// src/main/java/com/pahanaedu/dao/BookDAO.java
package com.pahanaedu.dao;

import com.pahanaedu.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookDAO {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByCategoryId(Integer categoryId);
    List<Book> findLowStockBooks();
    Book save(Book book);
    Book update(Book book);
    boolean deleteByIsbn(String isbn);
    boolean updateQuantity(String isbn, Integer newQuantity);
}