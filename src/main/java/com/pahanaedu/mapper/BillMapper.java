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
        dto.setBillId(entity.getBillId());
        dto.setBillNumber(entity.getBillId());
        dto.setCustomerAccountNumber(entity.getCustomerAccountNumber());
        dto.setUserId(entity.getUserId());
        dto.setSubtotal(entity.getSubtotal());
        dto.setTaxAmount(entity.getTaxAmount());
        dto.setDiscountAmount(entity.getDiscountAmount());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentStatus(entity.getPaymentStatus());
        dto.setNotes(entity.getNotes());

        // Convert LocalDateTime to Date if billDate exists
        if (entity.getBillDate() != null) {
            dto.setBillDate(Date.from(entity.getBillDate().atZone(ZoneId.systemDefault()).toInstant()));
        }

        // Map bill items if they exist
        if (entity.getBillItems() != null) {
            List<BillItemDTO> itemDTOs = entity.getBillItems().stream()
                    .map(BillItemMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setBillItems(itemDTOs);
        }

        return dto;
    }

    public static Bill toEntity(BillDTO dto) {
        if (dto == null) {
            return null;
        }

        Bill entity = new Bill();
        entity.setBillId(dto.getBillId());
        entity.setCustomerAccountNumber(dto.getCustomerAccountNumber());
        entity.setUserId(dto.getUserId());
        entity.setSubtotal(dto.getSubtotal());
        entity.setTaxAmount(dto.getTaxAmount());
        entity.setDiscountAmount(dto.getDiscountAmount());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentStatus(dto.getPaymentStatus());
        entity.setNotes(dto.getNotes());

        // Convert Date to LocalDateTime if billDate exists
        if (dto.getBillDate() != null) {
            entity.setBillDate(dto.getBillDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
        }

        // Map bill items if they exist
        if (dto.getBillItems() != null) {
            List<BillItem> items = dto.getBillItems().stream()
                    .map(BillItemMapper::toEntity)
                    .collect(Collectors.toList());
            entity.setBillItems(items);
        }

        return entity;
    }
}