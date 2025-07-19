// src/main/java/com/pahanaedu/services/GoogleBooksService.java
package com.pahanaedu.services;

import com.pahanaedu.entities.Book;
import com.pahanaedu.utils.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoogleBooksService {
    private static GoogleBooksService instance;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private GoogleBooksService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public static synchronized GoogleBooksService getInstance() {
        if (instance == null) {
            instance = new GoogleBooksService();
        }
        return instance;
    }

    public List<Book> searchBooks(String query, int maxResults) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = Constants.GOOGLE_BOOKS_API_URL + "?q=" + encodedQuery +
                    "&maxResults=" + maxResults + "&key=" + Constants.GOOGLE_BOOKS_API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseSearchResults(response.body());
            } else {
                System.err.println("Google Books API error: " + response.statusCode() + " - " + response.body());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error searching Google Books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Book getBookByGoogleId(String googleBookId) {
        try {
            String url = Constants.GOOGLE_BOOKS_API_URL + "/" + googleBookId + "?key=" + Constants.GOOGLE_BOOKS_API_KEY;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseBookFromJson(objectMapper.readTree(response.body()));
            } else {
                System.err.println("Google Books API error: " + response.statusCode() + " - " + response.body());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error getting book by Google ID: " + e.getMessage());
            return null;
        }
    }

    public Book getBookByIsbn(String isbn) {
        return searchBooks("isbn:" + isbn, 1).stream().findFirst().orElse(null);
    }

    private List<Book> parseSearchResults(String jsonResponse) throws IOException {
        List<Book> books = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        JsonNode itemsNode = rootNode.get("items");

        if (itemsNode != null && itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                Book book = parseBookFromJson(itemNode);
                if (book != null) {
                    books.add(book);
                }
            }
        }

        return books;
    }

    private Book parseBookFromJson(JsonNode itemNode) {
        try {
            Book book = new Book();

            // Google Book ID
            String googleBookId = itemNode.get("id").asText();
            book.setGoogleBookId(googleBookId);

            JsonNode volumeInfo = itemNode.get("volumeInfo");
            if (volumeInfo == null) return null;

            // Title
            JsonNode titleNode = volumeInfo.get("title");
            if (titleNode != null) {
                book.setTitle(titleNode.asText());
            }

            // Authors
            JsonNode authorsNode = volumeInfo.get("authors");
            if (authorsNode != null && authorsNode.isArray()) {
                StringBuilder authors = new StringBuilder();
                for (JsonNode authorNode : authorsNode) {
                    if (authors.length() > 0) {
                        authors.append(", ");
                    }
                    authors.append(authorNode.asText());
                }
                book.setAuthor(authors.toString());
            }

            // Publisher
            JsonNode publisherNode = volumeInfo.get("publisher");
            if (publisherNode != null) {
                book.setPublisher(publisherNode.asText());
            }

            // Description
            JsonNode descriptionNode = volumeInfo.get("description");
            if (descriptionNode != null) {
                String description = descriptionNode.asText();
                // Limit description length for database
                if (description.length() > 1000) {
                    description = description.substring(0, 997) + "...";
                }
                book.setDescription(description);
            }

            // ISBN
            JsonNode industryIdentifiersNode = volumeInfo.get("industryIdentifiers");
            if (industryIdentifiersNode != null && industryIdentifiersNode.isArray()) {
                for (JsonNode identifierNode : industryIdentifiersNode) {
                    String type = identifierNode.get("type").asText();
                    if ("ISBN_13".equals(type) || "ISBN_10".equals(type)) {
                        book.setIsbn(identifierNode.get("identifier").asText());
                        break;
                    }
                }
            }

            // Set default values for required fields
            if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
                return null; // Skip books without title
            }

            if (book.getAuthor() == null) {
                book.setAuthor("Unknown Author");
            }

            if (book.getPrice() == null) {
                book.setPrice(BigDecimal.ZERO); // Will be set manually
            }

            // Default inventory values
            book.setQuantity(0);
            book.setMinStockLevel(5);
            book.setActive(true);

            return book;

        } catch (Exception e) {
            System.err.println("Error parsing book from JSON: " + e.getMessage());
            return null;
        }
    }

    // Search by different criteria
    public List<Book> searchByTitle(String title, int maxResults) {
        return searchBooks("intitle:" + title, maxResults);
    }

    public List<Book> searchByAuthor(String author, int maxResults) {
        return searchBooks("inauthor:" + author, maxResults);
    }

    public List<Book> searchBySubject(String subject, int maxResults) {
        return searchBooks("subject:" + subject, maxResults);
    }

    public List<Book> searchByPublisher(String publisher, int maxResults) {
        return searchBooks("inpublisher:" + publisher, maxResults);
    }
}