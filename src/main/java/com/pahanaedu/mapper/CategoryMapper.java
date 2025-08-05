package com.pahanaedu.mapper;

import com.pahanaedu.dto.CategoryDTO;
import com.pahanaedu.entities.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category entity) {
        if (entity == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.isActive());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.isActive());
        entity.setCreatedDate(dto.getCreatedDate());

        return entity;
    }
}