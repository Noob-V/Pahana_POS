// src/main/java/com/pahanaedu/patterns/strategy/FixedDiscountStrategy.java
package com.pahanaedu.patterns.strategy;

import com.pahanaedu.entities.Bill;
import java.math.BigDecimal;

public class FixedDiscountStrategy implements DiscountStrategy<Bill> {
    private final BigDecimal fixedAmount;

    public FixedDiscountStrategy(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public BigDecimal calculateDiscount(Bill bill) {
        return fixedAmount;
    }
}