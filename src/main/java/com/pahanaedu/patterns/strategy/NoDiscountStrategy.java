package com.pahanaedu.patterns.strategy;

import com.pahanaedu.entities.Bill;
import java.math.BigDecimal;

public class NoDiscountStrategy implements DiscountStrategy<Bill> {
    @Override
    public BigDecimal calculateDiscount(Bill bill) {
        return BigDecimal.ZERO;
    }
}