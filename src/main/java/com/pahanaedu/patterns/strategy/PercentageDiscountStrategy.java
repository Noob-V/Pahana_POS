package com.pahanaedu.patterns.strategy;

import com.pahanaedu.entities.Bill;
import java.math.BigDecimal;

public class PercentageDiscountStrategy implements DiscountStrategy<Bill> {
    private final BigDecimal percentage; // e.g., 0.10 for 10%

    public PercentageDiscountStrategy(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public BigDecimal calculateDiscount(Bill bill) {
        if (bill.getSubtotal() == null) return BigDecimal.ZERO;
        return bill.getSubtotal().multiply(percentage);
    }
}