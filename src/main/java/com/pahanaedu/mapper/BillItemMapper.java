package com.pahanaedu.mapper;

import com.pahanaedu.dto.BillItemDTO;
import com.pahanaedu.entities.BillItem;

public class BillItemMapper {

    public static BillItemDTO toDTO(BillItem entity) {
        if (entity == null) {
            return null;
        }

        BillItemDTO dto = new BillItemDTO();
        dto.setIsbn(entity.getIsbn());
        dto.setBookTitle(entity.getBookTitle());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setDiscountAmount(entity.getDiscountAmount());

        return dto;
    }

    public static BillItem toEntity(BillItemDTO dto) {
        if (dto == null) {
            return null;
        }

        BillItem entity = new BillItem();
        entity.setIsbn(dto.getIsbn());
        entity.setBookTitle(dto.getBookTitle());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setDiscountAmount(dto.getDiscountAmount());

        return entity;
    }
}