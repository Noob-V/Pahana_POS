package com.pahanaedu.mapper;

import com.pahanaedu.dto.CartItemDTO;
import com.pahanaedu.entities.CartItem;

public class CartItemMapper {

    public static CartItemDTO toDTO(CartItem entity) {
        if (entity == null) {
            return null;
        }

        CartItemDTO dto = new CartItemDTO();
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(entity.getAuthor());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setTotal(entity.getTotal());

        return dto;
    }

    public static CartItem toEntity(CartItemDTO dto) {
        if (dto == null) {
            return null;
        }

        CartItem entity = new CartItem();
        entity.setIsbn(dto.getIsbn());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        // Note: total is calculated in the CartItem entity

        return entity;
    }
}