package com.pahanaedu.services.impl;

import com.pahanaedu.dao.BookDAO;
import com.pahanaedu.dao.impl.BookDAOImpl;
import com.pahanaedu.entities.Book;
import com.pahanaedu.services.BookService;
import com.pahanaedu.services.GoogleBooksService;
import com.pahanaedu.dto.BookDTO;
import com.pahanaedu.mapper.BookMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final GoogleBooksService googleBooksService;

    public BookServiceImpl() {
        this.bookDAO = new BookDAOImpl();
        this.googleBooksService = GoogleBooksService.getInstance();
    }

    @Override
    public List<BookDTO> findByCategoryIdDTOs(Integer categoryId) {
        List<Book> books = bookDAO.findByCategoryId(categoryId);
        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByCategoryId(Integer categoryId) {
        return bookDAO.findByCategoryId(categoryId);
    }
    public List<Book> getLowStockBooks(int limit) {
        List<Book> lowStockBooks = findLowStockBooks();
        if (lowStockBooks.size() > limit) {
            return lowStockBooks.subList(0, limit);
        }
        return lowStockBooks;
    }
    @Override
    public BookDTO findBookDTOByIsbn(String isbn) {
        Optional<Book> bookOpt = bookDAO.findByIsbn(isbn);
        return bookOpt.map(BookMapper::toDTO).orElse(null);
    }

    @Override
    public List<BookDTO> findAllDTOs() {
        List<Book> books = bookDAO.findAll(); // or similar
        return books.stream()
                .map(BookMapper::toDTO)
                .filter(Objects::nonNull)  // ✅ This is essential!
                .collect(Collectors.toList());
    }


    @Override
    public List<BookDTO> searchBookDTOs(String query) {
        List<Book> books = searchBooks(query);
        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO saveDTO(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        Book savedBook = save(book);
        return BookMapper.toDTO(savedBook);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookDAO.findByIsbn(isbn);
    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> localResults = new ArrayList<>();

        // Search by title
        localResults.addAll(bookDAO.findByTitle(query));

        // Search by author
        localResults.addAll(bookDAO.findByAuthor(query));

        // Remove duplicates
        return localResults.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findLowStockBooks() {
        return bookDAO.findLowStockBooks();
    }

    @Override
    public Book save(Book book) {
        validateBook(book);
        return bookDAO.save(book);
    }

    @Override
    public Book update(Book book) {
        validateBook(book);
        return bookDAO.update(book);
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        return bookDAO.deleteByIsbn(isbn);
    }

    @Override
    public boolean updateQuantity(String isbn, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return bookDAO.updateQuantity(isbn, newQuantity);
    }

    @Override
    public List<Book> searchGoogleBooks(String query, int maxResults) {
        return googleBooksService.searchBooks(query, maxResults);
    }

    @Override
    public Book importFromGoogle(String googleBookId) {
        Book googleBook = googleBooksService.getBookByGoogleId(googleBookId);
        if (googleBook != null) {
            // Check if book already exists
            if (googleBook.getIsbn() != null && bookDAO.findByIsbn(googleBook.getIsbn()).isPresent()) {
                throw new IllegalArgumentException("Book with ISBN " + googleBook.getIsbn() + " already exists");
            }
            return bookDAO.save(googleBook);
        }
        return null;
    }

    @Override
    public Book findOrImportByIsbn(String isbn) {
        Optional<Book> localBook = bookDAO.findByIsbn(isbn);
        if (localBook.isPresent()) {
            return localBook.get();
        }

        Book googleBook = googleBooksService.getBookByIsbn(isbn);
        if (googleBook != null) {
            return bookDAO.save(googleBook);
        }

        return null;
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title is required");
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN is required");
        }
        if (book.getPrice() == null || book.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Book price must be non-negative");
        }
        if (book.getQuantity() == null || book.getQuantity() < 0) {
            throw new IllegalArgumentException("Book quantity must be non-negative");
        }
    }
}