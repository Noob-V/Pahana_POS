package com.pahanaedu.mappers;

import com.pahanaedu.dto.CustomerDTO;
import com.pahanaedu.entities.Customer;

public class CustomerMapper implements EntityMapper<Customer, CustomerDTO> {

    @Override
    public CustomerDTO toDto(Customer entity) {
        if (entity == null) return null;

        CustomerDTO dto = new CustomerDTO();
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setTelephone(entity.getPhoneNumber());
        return dto;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;

        Customer entity = new Customer();
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getTelephone());
        return entity;
    }
}