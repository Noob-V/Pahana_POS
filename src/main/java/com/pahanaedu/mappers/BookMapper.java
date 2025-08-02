package com.pahanaedu.mappers;

import com.pahanaedu.dto.BookDTO;
import com.pahanaedu.entities.Book;

public class BookMapper implements EntityMapper<Book, BookDTO> {

    @Override
    public BookDTO toDto(Book entity) {
        if (entity == null) return null;

        BookDTO dto = new BookDTO();
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public Book toEntity(BookDTO dto) {
        if (dto == null) return null;

        Book entity = new Book();
        entity.setIsbn(dto.getIsbn());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}