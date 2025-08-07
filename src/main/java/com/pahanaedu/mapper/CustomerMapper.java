package com.pahanaedu.mapper;

import com.pahanaedu.dto.CustomerDTO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.patterns.builder.CustomerBuilder;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setTelephone(entity.getPhoneNumber());
        dto.setActive(entity.isActive());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());

        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        CustomerBuilder builder = new CustomerBuilder()
                .accountNumber(dto.getAccountNumber())
                .name(dto.getName())
                .address(dto.getAddress())
                .phoneNumber(dto.getTelephone())
                .email(dto.getEmail())
                .active(dto.isActive());

        if (dto.getCreatedDate() != null) {
            builder.createdDate(dto.getCreatedDate());
        }
        if (dto.getUpdatedDate() != null) {
            builder.updatedDate(dto.getUpdatedDate());
        }

        return builder.build();
    }
}