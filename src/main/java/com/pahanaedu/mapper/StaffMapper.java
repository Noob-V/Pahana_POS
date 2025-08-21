package com.pahanaedu.mapper;

import com.pahanaedu.dto.StaffDTO;
import com.pahanaedu.entities.Staff;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class StaffMapper {

    public static StaffDTO toDTO(Staff entity) {
        if (entity == null) {
            return null;
        }

        StaffDTO dto = new StaffDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        // We don't map the password hash for security reasons
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.isActive());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastLogin(entity.getLastLogin());

        return dto;
    }

    public static Staff toEntity(StaffDTO dto) {
        if (dto == null) {
            return null;
        }

        Staff entity = new Staff();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        // Only set password if it's being updated
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPasswordHash(dto.getPassword()); // This might need encryption before setting
        }
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.isActive());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setLastLogin(dto.getLastLogin());
        // Role is automatically set in the Staff constructor

        return entity;
    }
}