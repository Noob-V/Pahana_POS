// src/main/java/com/pahanaedu/patterns/strategy/DiscountStrategy.java
package com.pahanaedu.patterns.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy<T> {
    BigDecimal calculateDiscount(T target);
}