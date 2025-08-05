package com.pahanaedu.mapper;

import com.pahanaedu.dto.BookDTO;
import com.pahanaedu.entities.Book;

public class BookMapper {

    public static BookDTO toDTO(Book entity) {
        if (entity == null) {
            return null;
        }
        BookDTO dto = new BookDTO();
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setDescription(entity.getDescription()); // Add this line
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setActive(entity.isActive());
        dto.setCategoryName(entity.getCategoryName());
        dto.setImageUrl(entity.getImageUrl());
        dto.setPublisher(entity.getPublisher());
        dto.setMinStockLevel(entity.getMinStockLevel());

        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }

        Book entity = new Book();
        entity.setIsbn(dto.getIsbn());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setActive(dto.isActive());        // Map the active property

        // Default values for required fields not in DTO
        entity.setMinStockLevel(5);

        return entity;
    }
}