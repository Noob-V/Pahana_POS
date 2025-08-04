package com.pahanaedu.mapper;

import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.dto.BillItemDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BillMapper {

    public static BillDTO toDTO(Bill entity) {
        if (entity == null) {
            return null;
        }

        BillDTO dto = new BillDTO();
        dto.setBillNumber(entity.getBillId());
        dto.setCustomerAccountNumber(entity.getCustomerAccountNumber());
        dto.setTotalAmount(entity.getTotalAmount());

        // Convert LocalDateTime to Date
        if (entity.getCreatedDate() != null) {
            dto.setCreatedAt(Date.from(entity.getCreatedDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }

        // Map bill items if available
        if (entity.getBillItems() != null) {
            List<BillItemDTO> billItemDTOs = entity.getBillItems().stream()
                    .map(BillItemMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setBillItems(billItemDTOs);
        }

        return dto;
    }

    public static Bill toEntity(BillDTO dto) {
        if (dto == null) {
            return null;
        }

        Bill entity = new Bill();
        entity.setBillId(dto.getBillNumber());
        entity.setCustomerAccountNumber(dto.getCustomerAccountNumber());
        entity.setTotalAmount(dto.getTotalAmount());

        // Convert Date to LocalDateTime
        if (dto.getCreatedAt() != null) {
            entity.setCreatedDate(dto.getCreatedAt().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        // Map bill items if available
        if (dto.getBillItems() != null) {
            List<BillItem> billItems = dto.getBillItems().stream()
                    .map(BillItemMapper::toEntity)
                    .collect(Collectors.toList());
            entity.setBillItems(billItems);
        }

        return entity;
    }
}