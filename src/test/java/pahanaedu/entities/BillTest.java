package pahanaedu.entities;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.BillItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillTest {

    private Bill bill;

    @Before
    public void setUp() {
        bill = new Bill();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(bill.getBillItems());
        assertEquals(BigDecimal.ZERO, bill.getDiscountAmount());
        assertEquals("CASH", bill.getPaymentMethod());
        assertEquals("PAID", bill.getPaymentStatus());
        assertNotNull(bill.getBillDate());
        assertNotNull(bill.getCreatedDate());
    }

    @Test
    public void testBillIdGetterSetter() {
        String billId = "BILL001";
        bill.setBillId(billId);
        assertEquals(billId, bill.getBillId());
    }

    @Test
    public void testCustomerAccountNumberGetterSetter() {
        String accountNumber = "PAH001";
        bill.setCustomerAccountNumber(accountNumber);
        assertEquals(accountNumber, bill.getCustomerAccountNumber());
    }

    @Test
    public void testTotalAmountGetterSetter() {
        BigDecimal total = new BigDecimal("100.50");
        bill.setTotalAmount(total);
        assertEquals(total, bill.getTotalAmount());
    }

    @Test
    public void testBillItemsGetterSetter() {
        List<BillItem> items = new ArrayList<>();
        BillItem item = new BillItem();
        item.setIsbn("123456789");
        items.add(item);

        bill.setBillItems(items);
        assertEquals(1, bill.getBillItems().size());
        assertEquals("123456789", bill.getBillItems().get(0).getIsbn());
    }

    @Test
    public void testBillDateAsDate() {
        LocalDateTime now = LocalDateTime.now();
        bill.setBillDate(now);
        assertNotNull(bill.getBillDateAsDate());
    }

    @Test
    public void testToString() {
        bill.setBillId("BILL001");
        bill.setCustomerAccountNumber("PAH001");
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setPaymentStatus("PAID");

        String result = bill.toString();
        assertTrue(result.contains("BILL001"));
        assertTrue(result.contains("PAH001"));
        assertTrue(result.contains("PAID"));
    }
}