package pahanaedu.patterns.builder;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.patterns.builder.BillBuilder;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BillBuilderTest {

    @Test
    public void testBuildBill() {
        LocalDateTime now = LocalDateTime.now();
        Bill bill = new BillBuilder()
                .billId("B001")
                .customerAccountNumber("C123")
                .userId(10)
                .billDate(now)
                .subtotal(new BigDecimal("100.00"))
                .taxAmount(new BigDecimal("10.00"))
                .discountAmount(new BigDecimal("5.00"))
                .totalAmount(new BigDecimal("105.00"))
                .paymentMethod("CARD")
                .paymentStatus("PAID")
                .notes("Test bill")
                .createdDate(now)
                .customerName("John Doe")
                .userName("admin")
                .build();

        assertEquals("B001", bill.getBillId());
        assertEquals("C123", bill.getCustomerAccountNumber());
        assertEquals(Integer.valueOf(10), bill.getUserId());
        assertEquals(now, bill.getBillDate());
        assertEquals(new BigDecimal("100.00"), bill.getSubtotal());
        assertEquals(new BigDecimal("10.00"), bill.getTaxAmount());
        assertEquals(new BigDecimal("5.00"), bill.getDiscountAmount());
        assertEquals(new BigDecimal("105.00"), bill.getTotalAmount());
        assertEquals("CARD", bill.getPaymentMethod());
        assertEquals("PAID", bill.getPaymentStatus());
        assertEquals("Test bill", bill.getNotes());
        assertEquals(now, bill.getCreatedDate());
        assertEquals("John Doe", bill.getCustomerName());
        assertEquals("admin", bill.getUserName());
    }
}