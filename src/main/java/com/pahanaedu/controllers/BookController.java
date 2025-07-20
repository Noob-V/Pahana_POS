// src/main/java/com/pahanaedu/controllers/BookController.java
package com.pahanaedu.controllers;

import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.Category;
import com.pahanaedu.services.BookService;
import com.pahanaedu.services.CategoryService;
import com.pahanaedu.services.impl.BookServiceImpl;
import com.pahanaedu.services.impl.CategoryServiceImpl;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "BookController", urlPatterns = {
        "/books", "/books/add", "/books/edit", "/books/delete",
        "/books/search", "/books/filter", "/books/import",
        "/books/google-search", "/books/suggestions"
})

public class BookController extends HttpServlet {
    private BookService bookService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        bookService = new BookServiceImpl();
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/books/add":
                showAddForm(request, response);
                break;
            case "/books/edit":
                showEditForm(request, response);
                break;
            case "/books/delete":
                deleteBook(request, response);
                break;
            case "/books/search":
                searchBooks(request, response);
                break;
            case "/books/filter":
                filterBooks(request, response);
                break;
            case "/books/google-search":
                searchGoogleBooks(request, response);
                break;
            case "/books/suggestions":
                getSuggestions(request, response);
                break;
            case "/books":
            default:
                listBooks(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/books/add":
                addBook(request, response);
                break;
            case "/books/edit":
                updateBook(request, response);
                break;
            case "/books/import":
                importFromGoogle(request, response);
                break;
            default:
                listBooks(request, response);
                break;
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Book> books = bookService.findAll();
            List<Book> lowStockBooks = bookService.findLowStockBooks();
            List<Category> categories = categoryService.findActive();

            request.setAttribute("books", books);
            request.setAttribute("lowStockBooks", lowStockBooks);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/books/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading books: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.findActive();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/books/add.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading form: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String isbn = request.getParameter("isbn");
            Optional<Book> bookOpt = bookService.findByIsbn(isbn);

            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                List<Category> categories = categoryService.findActive();

                request.setAttribute("book", book);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/views/books/edit.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/books?error=book-not-found");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading book: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Book book = createBookFromRequest(request);
            bookService.save(book);

            response.sendRedirect(request.getContextPath() + "/books?message=book-added");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error adding book: " + e.getMessage());
            showAddForm(request, response);
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Book book = createBookFromRequest(request);
            bookService.update(book);

            response.sendRedirect(request.getContextPath() + "/books?message=book-updated");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
            showEditForm(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String isbn = request.getParameter("isbn");
            bookService.deleteByIsbn(isbn);

            response.sendRedirect(request.getContextPath() + "/books?message=book-deleted");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/books?error=" + e.getMessage());
        }
    }

    private void searchBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String query = request.getParameter("q");
            List<Book> books = bookService.searchBooks(query);

            request.setAttribute("books", books);
            request.setAttribute("searchQuery", query);
            request.getRequestDispatcher("/WEB-INF/views/books/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error searching books: " + e.getMessage());
            listBooks(request, response);
        }
    }

    // Add this method to BookController
    private void filterBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String categoryIdStr = request.getParameter("categoryId");
            List<Book> books;
            List<Category> categories = categoryService.findActive();

            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                Integer categoryId = Integer.parseInt(categoryIdStr);
                books = bookService.findByCategoryId(categoryId);
                request.setAttribute("selectedCategoryId", categoryId);
            } else {
                books = bookService.findAll();
            }

            List<Book> lowStockBooks = bookService.findLowStockBooks();

            request.setAttribute("books", books);
            request.setAttribute("lowStockBooks", lowStockBooks);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/books/list.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error filtering books: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void searchGoogleBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String query = request.getParameter("q");
            String maxResults = request.getParameter("maxResults");

            if (maxResults == null || maxResults.trim().isEmpty()) {
                maxResults = "10";
            }

            // Call Google Books API service
            List<Book> googleBooks = bookService.searchGoogleBooks(query, Integer.parseInt(maxResults));

            // Add categories for import form
            List<Category> categories = categoryService.findActive();

            request.setAttribute("googleBooks", googleBooks);
            request.setAttribute("categories", categories);  // Add this line
            request.setAttribute("searchQuery", query);
            request.getRequestDispatcher("/WEB-INF/views/books/google-search.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error searching Google Books: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/books/google-search.jsp").forward(request, response);
        }
    }

    private void importFromGoogle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String googleId = request.getParameter("googleId");
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String publisher = request.getParameter("publisher");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("categoryId");

            // Create book from Google Books data
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setDescription(description);

            if (priceStr != null && !priceStr.trim().isEmpty()) {
                book.setPrice(new BigDecimal(priceStr));
            } else {
                book.setPrice(BigDecimal.ZERO);
            }

            book.setQuantity(0); // Default quantity
            book.setMinStockLevel(5); // Default min stock level

            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                book.setCategoryId(Integer.parseInt(categoryIdStr));
            }

            book.setActive(true);

            // Save imported book
            bookService.save(book);

            response.sendRedirect(request.getContextPath() + "/books?message=book-imported");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/books?error=import-failed");
        }
    }

    private void getSuggestions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String query = request.getParameter("q");
            List<Book> suggestions = bookService.searchBooks(query);

            // Limit to 5 suggestions
            List<Book> limitedSuggestions = suggestions.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (int i = 0; i < limitedSuggestions.size(); i++) {
                Book book = limitedSuggestions.get(i);
                if (i > 0) json.append(",");
                json.append("{")
                        .append("\"title\":\"").append(escapeJson(book.getTitle())).append("\",")
                        .append("\"author\":\"").append(escapeJson(book.getAuthor())).append("\"")
                        .append("}");
            }

            json.append("]");

            response.getWriter().write(json.toString());
        } catch (Exception e) {
            response.getWriter().write("[]");
        }
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    private Book createBookFromRequest(HttpServletRequest request) {
        Book book = new Book();
        book.setIsbn(request.getParameter("isbn"));
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setPublisher(request.getParameter("publisher"));
        book.setDescription(request.getParameter("description"));

        // Handle price parameter
        String priceStr = request.getParameter("price");
        if (priceStr != null && !priceStr.trim().isEmpty()) {
            book.setPrice(new BigDecimal(priceStr));
        } else {
            book.setPrice(BigDecimal.ZERO);
        }

        // Handle image URL
        String imageUrl = request.getParameter("imageUrl");
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            book.setImageUrl(imageUrl.trim());
        }

        // Handle quantity parameter
        String quantityStr = request.getParameter("quantity");
        if (quantityStr != null && !quantityStr.trim().isEmpty()) {
            book.setQuantity(Integer.parseInt(quantityStr));
        } else {
            book.setQuantity(0);
        }

        // Handle minStockLevel parameter
        String minStockStr = request.getParameter("minStockLevel");
        if (minStockStr != null && !minStockStr.trim().isEmpty()) {
            book.setMinStockLevel(Integer.parseInt(minStockStr));
        } else {
            book.setMinStockLevel(5); // Default value
        }

        // Handle categoryId parameter
        String categoryIdStr = request.getParameter("categoryId");
        if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            book.setCategoryId(Integer.parseInt(categoryIdStr));
        }

        // Handle active parameter - fixed the error here
        String activeStr = request.getParameter("active");
        if (activeStr != null) {
            book.setActive(Boolean.parseBoolean(activeStr));
        } else {
            book.setActive(true); // Default to true if not specified
        }

        return book;
    }
}