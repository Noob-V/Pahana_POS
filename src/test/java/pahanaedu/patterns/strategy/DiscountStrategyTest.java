package pahanaedu.patterns.strategy;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.patterns.strategy.FixedDiscountStrategy;
import com.pahanaedu.patterns.strategy.NoDiscountStrategy;
import com.pahanaedu.patterns.strategy.PercentageDiscountStrategy;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DiscountStrategyTest {

    @Test
    public void testFixedDiscountStrategy() {
        FixedDiscountStrategy strategy = new FixedDiscountStrategy(new BigDecimal("15.00"));
        Bill bill = new Bill();
        assertEquals(new BigDecimal("15.00"), strategy.calculateDiscount(bill));
    }

    @Test
    public void testNoDiscountStrategy() {
        NoDiscountStrategy strategy = new NoDiscountStrategy();
        Bill bill = new Bill();
        assertEquals(BigDecimal.ZERO, strategy.calculateDiscount(bill));
    }

    @Test
    public void testPercentageDiscountStrategy() {
        PercentageDiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10")); // 10%
        Bill bill = new Bill();
        bill.setSubtotal(new BigDecimal("200.00"));
        BigDecimal expected = new BigDecimal("20.00");
        BigDecimal actual = strategy.calculateDiscount(bill);
        assertTrue("Expected: " + expected + ", Actual: " + actual, expected.compareTo(actual) == 0);
    }

    @Test
    public void testPercentageDiscountStrategyWithNullSubtotal() {
        PercentageDiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
        Bill bill = new Bill();
        bill.setSubtotal(null);
        assertEquals(BigDecimal.ZERO, strategy.calculateDiscount(bill));
    }
}