//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.BillDAO;
//import com.pahanaedu.dao.impl.BillDAOImpl;
//import com.pahanaedu.entities.Bill;
//import com.pahanaedu.entities.BillItem;
//import com.pahanaedu.utils.Constants;
//import org.junit.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class BillDAOImplTest {
//
//    private static BillDAO billDAO;
//    private Bill testBill;
//    private String testBillId;
//
//    @BeforeClass
//    public static void setUpClass() {
//        billDAO = new BillDAOImpl();
//    }
//
//    @Before
//    public void setUp() {
//        // Create a test bill for each test
//        testBill = createTestBill();
//        testBillId = null;
//    }
//
//    @After
//    public void tearDown() {
//        // Clean up any created test data
//        if (testBillId != null) {
//            try {
//                billDAO.deleteById(testBillId);
//            } catch (Exception e) {
//                // Ignore cleanup errors
//            }
//        }
//    }
//
//    @Test
//    public void testSaveBill() {
//        // When
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // Then
//        assertNotNull("Saved bill should not be null", savedBill);
//        assertNotNull("Bill ID should be generated", savedBill.getBillId());
//        assertTrue("Bill ID should start with prefix", savedBill.getBillId().startsWith(Constants.BILL_PREFIX));
//        assertEquals("Customer account number should match", testBill.getCustomerAccountNumber(), savedBill.getCustomerAccountNumber());
//        assertEquals("User ID should match", testBill.getUserId(), savedBill.getUserId());
//        assertEquals("Total amount should match", testBill.getTotalAmount(), savedBill.getTotalAmount());
//    }
//
//    @Test
//    public void testFindById() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // When
//        Optional<Bill> foundBill = billDAO.findById(testBillId);
//
//        // Then
//        assertTrue("Bill should be found", foundBill.isPresent());
//        assertEquals("Bill ID should match", testBillId, foundBill.get().getBillId());
//        assertEquals("Customer account should match", testBill.getCustomerAccountNumber(), foundBill.get().getCustomerAccountNumber());
//        assertEquals("Total amount should match", testBill.getTotalAmount(), foundBill.get().getTotalAmount());
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        // When
//        Optional<Bill> foundBill = billDAO.findById("NONEXISTENT_ID");
//
//        // Then
//        assertFalse("Bill should not be found", foundBill.isPresent());
//    }
//
//    @Test
//    public void testFindAll() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // When
//        List<Bill> allBills = billDAO.findAll();
//
//        // Then
//        assertNotNull("Bills list should not be null", allBills);
//        assertTrue("Should contain at least one bill", allBills.size() > 0);
//
//        boolean foundTestBill = false;
//        for (Bill bill : allBills) {
//            if (testBillId.equals(bill.getBillId())) {
//                foundTestBill = true;
//                break;
//            }
//        }
//        assertTrue("Should contain the test bill", foundTestBill);
//    }
//
//    @Test
//    public void testFindByCustomerAccountNumber() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // When
//        List<Bill> customerBills = billDAO.findByCustomerAccountNumber(testBill.getCustomerAccountNumber());
//
//        // Then
//        assertNotNull("Customer bills list should not be null", customerBills);
//        assertTrue("Should contain at least one bill", customerBills.size() > 0);
//
//        boolean foundTestBill = false;
//        for (Bill bill : customerBills) {
//            assertEquals("All bills should belong to the same customer", testBill.getCustomerAccountNumber(), bill.getCustomerAccountNumber());
//            if (testBillId.equals(bill.getBillId())) {
//                foundTestBill = true;
//            }
//        }
//        assertTrue("Should contain the test bill", foundTestBill);
//    }
//
//    @Test
//    public void testFindByUserId() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // When
//        List<Bill> userBills = billDAO.findByUserId(testBill.getUserId());
//
//        // Then
//        assertNotNull("User bills list should not be null", userBills);
//        assertTrue("Should contain at least one bill", userBills.size() > 0);
//
//        boolean foundTestBill = false;
//        for (Bill bill : userBills) {
//            assertEquals("All bills should belong to the same user", testBill.getUserId(), bill.getUserId());
//            if (testBillId.equals(bill.getBillId())) {
//                foundTestBill = true;
//            }
//        }
//        assertTrue("Should contain the test bill", foundTestBill);
//    }
//
//    @Test
//    public void testFindByDate() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//        LocalDate testDate = savedBill.getBillDate().toLocalDate();
//
//        // When
//        List<Bill> dateBills = billDAO.findByDate(testDate);
//
//        // Then
//        assertNotNull("Date bills list should not be null", dateBills);
//
//        boolean foundTestBill = false;
//        for (Bill bill : dateBills) {
//            assertEquals("All bills should be from the same date", testDate, bill.getBillDate().toLocalDate());
//            if (testBillId.equals(bill.getBillId())) {
//                foundTestBill = true;
//            }
//        }
//        assertTrue("Should contain the test bill", foundTestBill);
//    }
//
//    @Test
//    public void testFindByDateRange() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//        LocalDate startDate = LocalDate.now().minusDays(1);
//        LocalDate endDate = LocalDate.now().plusDays(1);
//
//        // When
//        List<Bill> rangeBills = billDAO.findByDateRange(startDate, endDate);
//
//        // Then
//        assertNotNull("Range bills list should not be null", rangeBills);
//
//        boolean foundTestBill = false;
//        for (Bill bill : rangeBills) {
//            LocalDate billDate = bill.getBillDate().toLocalDate();
//            assertTrue("Bill date should be within range",
//                    !billDate.isBefore(startDate) && !billDate.isAfter(endDate));
//            if (testBillId.equals(bill.getBillId())) {
//                foundTestBill = true;
//            }
//        }
//        assertTrue("Should contain the test bill", foundTestBill);
//    }
//
//    @Test
//    public void testDeleteById() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // Verify bill exists
//        Optional<Bill> foundBill = billDAO.findById(testBillId);
//        assertTrue("Bill should exist before deletion", foundBill.isPresent());
//
//        // When
//        boolean deleted = billDAO.deleteById(testBillId);
//        testBillId = null; // Set to null so tearDown doesn't try to delete again
//
//        // Then
//        assertTrue("Delete operation should return true", deleted);
//
//        Optional<Bill> deletedBill = billDAO.findById(savedBill.getBillId());
//        assertFalse("Bill should not exist after deletion", deletedBill.isPresent());
//    }
//
//    @Test
//    public void testDeleteByIdNotFound() {
//        // When
//        boolean deleted = billDAO.deleteById("NONEXISTENT_ID");
//
//        // Then
//        assertFalse("Delete operation should return false for non-existent bill", deleted);
//    }
//
//    @Test
//    public void testGenerateNextBillId() {
//        // When
//        String nextBillId = billDAO.generateNextBillId();
//
//        // Then
//        assertNotNull("Generated bill ID should not be null", nextBillId);
//        assertTrue("Bill ID should start with prefix", nextBillId.startsWith(Constants.BILL_PREFIX));
//        assertTrue("Bill ID should have correct format", nextBillId.matches(Constants.BILL_PREFIX + "\\d{6}"));
//    }
//
//    @Test
//    public void testSaveBillWithItems() {
//        // Given
//        BillItem item1 = createTestBillItem("1234567890123", "Test Book 1", 2, new BigDecimal("15.50"));
//        BillItem item2 = createTestBillItem("9876543210987", "Test Book 2", 1, new BigDecimal("25.00"));
//        testBill.getBillItems().add(item1);
//        testBill.getBillItems().add(item2);
//
//        // When
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // Then
//        assertNotNull("Saved bill should not be null", savedBill);
//
//        // Verify bill items were saved
//        List<BillItem> savedItems = billDAO.findBillItemsByBillId(testBillId);
//        assertEquals("Should have 2 bill items", 2, savedItems.size());
//
//        // Verify item details
//        BillItem savedItem1 = savedItems.get(0);
//        assertEquals("Item 1 ISBN should match", item1.getIsbn(), savedItem1.getIsbn());
//        assertEquals("Item 1 quantity should match", item1.getQuantity(), savedItem1.getQuantity());
//        assertEquals("Item 1 unit price should match", item1.getUnitPrice(), savedItem1.getUnitPrice());
//    }
//
//    @Test
//    public void testFindBillItemsByBillId() {
//        // Given
//        BillItem item = createTestBillItem("1234567890123", "Test Book", 1, new BigDecimal("20.00"));
//        testBill.getBillItems().add(item);
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // When
//        List<BillItem> billItems = billDAO.findBillItemsByBillId(testBillId);
//
//        // Then
//        assertNotNull("Bill items list should not be null", billItems);
//        assertEquals("Should have 1 bill item", 1, billItems.size());
//
//        BillItem savedItem = billItems.get(0);
//        assertEquals("Item ISBN should match", item.getIsbn(), savedItem.getIsbn());
//        assertEquals("Item title should match", item.getBookTitle(), savedItem.getBookTitle());
//        assertEquals("Item quantity should match", item.getQuantity(), savedItem.getQuantity());
//        assertEquals("Item unit price should match", item.getUnitPrice(), savedItem.getUnitPrice());
//    }
//
//    @Test
//    public void testSaveBillItem() {
//        // Given
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        BillItem item = createTestBillItem("1234567890123", "Test Book", 1, new BigDecimal("15.00"));
//        item.setBillId(testBillId);
//
//        // When
//        BillItem savedItem = billDAO.saveBillItem(item);
//
//        // Then
//        assertNotNull("Saved item should not be null", savedItem);
//        assertNotNull("Item ID should be generated", savedItem.getId());
//        assertEquals("Bill ID should match", testBillId, savedItem.getBillId());
//        assertEquals("ISBN should match", item.getIsbn(), savedItem.getIsbn());
//    }
//
//    @Test
//    public void testDeleteBillItemsByBillId() {
//        // Given
//        BillItem item = createTestBillItem("1234567890123", "Test Book", 1, new BigDecimal("20.00"));
//        testBill.getBillItems().add(item);
//        Bill savedBill = billDAO.save(testBill);
//        testBillId = savedBill.getBillId();
//
//        // Verify items exist
//        List<BillItem> itemsBefore = billDAO.findBillItemsByBillId(testBillId);
//        assertTrue("Should have items before deletion", itemsBefore.size() > 0);
//
//        // When
//        boolean deleted = billDAO.deleteBillItemsByBillId(testBillId);
//
//        // Then
//        assertTrue("Delete operation should return true", deleted);
//
//        List<BillItem> itemsAfter = billDAO.findBillItemsByBillId(testBillId);
//        assertEquals("Should have no items after deletion", 0, itemsAfter.size());
//    }
//
//    private Bill createTestBill() {
//        Bill bill = new Bill();
//        bill.setCustomerAccountNumber("PAH000001");
//        bill.setUserId(1);
//        bill.setBillDate(LocalDateTime.now());
//        bill.setSubtotal(new BigDecimal("100.00"));
//        bill.setTaxAmount(new BigDecimal("10.00"));
//        bill.setDiscountAmount(new BigDecimal("5.00"));
//        bill.setTotalAmount(new BigDecimal("105.00"));
//        bill.setPaymentMethod("CASH");
//        bill.setPaymentStatus("PAID");
//        bill.setNotes("Test bill for unit testing");
//        return bill;
//    }
//
//    private BillItem createTestBillItem(String isbn, String title, int quantity, BigDecimal unitPrice) {
//        BillItem item = new BillItem();
//        item.setIsbn(isbn);
//        item.setBookTitle(title);
//        item.setQuantity(quantity);
//        item.setUnitPrice(unitPrice);
//        item.setTotalPrice(unitPrice.multiply(new BigDecimal(quantity)));
//        item.setDiscountAmount(BigDecimal.ZERO);
//        return item;
//    }
//}