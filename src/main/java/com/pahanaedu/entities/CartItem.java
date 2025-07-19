// src/main/java/com/pahanaedu/entities/CartItem.java
package com.pahanaedu.entities;

import java.math.BigDecimal;

public class CartItem {
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer quantity;

    public CartItem() {}

    public CartItem(String isbn, String title, String author, BigDecimal price, Integer quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}