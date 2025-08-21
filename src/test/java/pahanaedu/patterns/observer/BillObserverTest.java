package pahanaedu.patterns.observer;

import com.pahanaedu.entities.Bill;
import com.pahanaedu.patterns.observer.BillAuditObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.*;

public class BillObserverTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testBillAuditObserver_onBillCreated_printsAuditLog() {
        Bill bill = new Bill();
        bill.setBillId("B123");
        bill.setCustomerAccountNumber("C456");
        bill.setUserId(789);
        bill.setTotalAmount(new BigDecimal("99.99"));
        bill.setPaymentMethod("CARD");
        bill.setBillItems(Collections.emptyList());

        BillAuditObserver observer = new BillAuditObserver();
        observer.onBillCreated(bill);

        String output = outContent.toString();
        assertTrue(output.contains("AUDIT LOG SYSTEM"));
        assertTrue(output.contains("Event Type: BILL_CREATED"));
        assertTrue(output.contains("Bill ID: B123"));
        assertTrue(output.contains("Customer: C456"));
        assertTrue(output.contains("User ID: 789"));
        assertTrue(output.contains("Amount: $99.99"));
        assertTrue(output.contains("Payment Method: CARD"));
        assertTrue(output.contains("Items Count: 0"));
        assertTrue(output.contains("Audit trail recorded"));
        assertTrue(output.contains("Thank you for using our service!"));
    }
}