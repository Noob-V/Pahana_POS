package com.pahanaedu.patterns.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy<T> {
    BigDecimal calculateDiscount(T target);
}