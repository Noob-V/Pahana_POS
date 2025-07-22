// src/main/java/com/pahanaedu/services/BookService.java
package com.pahanaedu.services;

import com.pahanaedu.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    List<Book> searchBooks(String query);
    List<Book> findLowStockBooks();
    List<Book> findByCategoryId(Integer categoryId);
    List<Book> getLowStockBooks(int limit);
    Book save(Book book);
    Book update(Book book);
    boolean deleteByIsbn(String isbn);
    boolean updateQuantity(String isbn, Integer newQuantity);

    // Google Books integration
    List<Book> searchGoogleBooks(String query, int maxResults);
    Book importFromGoogle(String googleBookId);
    Book findOrImportByIsbn(String isbn);
}