//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.CustomerDAO;
//import com.pahanaedu.dao.impl.CustomerDAOImpl;
//import com.pahanaedu.entities.Customer;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//public class CustomerDAOImplTest {
//
//    private CustomerDAO customerDAO;
//    private Customer testCustomer;
//
//    @Before
//    public void setUp() {
//        customerDAO = new CustomerDAOImpl();
//
//        // Create a test customer
//        testCustomer = new Customer();
//        testCustomer.setName("Test Customer");
//        testCustomer.setAddress("123 Test Street");
//        testCustomer.setPhoneNumber("1234567890");
//        testCustomer.setEmail("test@example.com");
//        testCustomer.setActive(true);
//        testCustomer.setCreatedDate(LocalDateTime.now());
//        testCustomer.setUpdatedDate(LocalDateTime.now());
//    }
//
//    @After
//    public void tearDown() {
//        // Clean up test data if needed
//        if (testCustomer != null && testCustomer.getAccountNumber() != null) {
//            try {
//                customerDAO.deleteByAccountNumber(testCustomer.getAccountNumber());
//            } catch (Exception e) {
//                // Ignore cleanup errors
//            }
//        }
//    }
//
//    @Test
//    public void testSaveCustomer() {
//        Customer savedCustomer = customerDAO.save(testCustomer);
//
//        assertNotNull("Saved customer should not be null", savedCustomer);
//        assertNotNull("Account number should be generated", savedCustomer.getAccountNumber());
//        assertEquals("Name should match", testCustomer.getName(), savedCustomer.getName());
//        assertEquals("Email should match", testCustomer.getEmail(), savedCustomer.getEmail());
//    }
//
//    @Test
//    public void testFindByAccountNumber() {
//        // First save the customer
//        Customer savedCustomer = customerDAO.save(testCustomer);
//        String accountNumber = savedCustomer.getAccountNumber();
//
//        // Then find by account number
//        Optional<Customer> foundCustomer = customerDAO.findByAccountNumber(accountNumber);
//
//        assertTrue("Customer should be found", foundCustomer.isPresent());
//        assertEquals("Account numbers should match", accountNumber, foundCustomer.get().getAccountNumber());
//        assertEquals("Names should match", testCustomer.getName(), foundCustomer.get().getName());
//    }
//
//    @Test
//    public void testFindAll() {
//        // Save test customer first
//        customerDAO.save(testCustomer);
//
//        List<Customer> customers = customerDAO.findAll();
//
//        assertNotNull("Customers list should not be null", customers);
//        assertTrue("Should have at least one customer", customers.size() > 0);
//    }
//
//    @Test
//    public void testSearchByName() {
//        // Save test customer first
//        customerDAO.save(testCustomer);
//
//        List<Customer> customers = customerDAO.searchByName("Test");
//
//        assertNotNull("Search result should not be null", customers);
//        assertTrue("Should find at least one customer", customers.size() > 0);
//
//        boolean found = customers.stream()
//                .anyMatch(c -> c.getName().contains("Test"));
//        assertTrue("Should find customer with 'Test' in name", found);
//    }
//
//    @Test
//    public void testUpdateCustomer() {
//        // Save customer first
//        Customer savedCustomer = customerDAO.save(testCustomer);
//
//        // Update customer details
//        savedCustomer.setName("Updated Test Customer");
//        savedCustomer.setEmail("updated@example.com");
//
//        Customer updatedCustomer = customerDAO.update(savedCustomer);
//
//        assertNotNull("Updated customer should not be null", updatedCustomer);
//        assertEquals("Name should be updated", "Updated Test Customer", updatedCustomer.getName());
//        assertEquals("Email should be updated", "updated@example.com", updatedCustomer.getEmail());
//    }
//
//    @Test
//    public void testDeleteByAccountNumber() {
//        // Save customer first
//        Customer savedCustomer = customerDAO.save(testCustomer);
//        String accountNumber = savedCustomer.getAccountNumber();
//
//        // Verify customer exists
//        Optional<Customer> beforeDelete = customerDAO.findByAccountNumber(accountNumber);
//        assertTrue("Customer should exist before delete", beforeDelete.isPresent());
//
//        // Delete customer (soft delete)
//        boolean deleted = customerDAO.deleteByAccountNumber(accountNumber);
//
//        assertTrue("Delete operation should return true", deleted);
//
//        // Verify customer is no longer found (due to soft delete)
//        Optional<Customer> afterDelete = customerDAO.findByAccountNumber(accountNumber);
//        assertFalse("Customer should not be found after soft delete", afterDelete.isPresent());
//    }
//
//    @Test
//    public void testGenerateNextAccountNumber() {
//        String accountNumber = customerDAO.generateNextAccountNumber();
//
//        assertNotNull("Account number should not be null", accountNumber);
//        assertTrue("Account number should start with CUST", accountNumber.startsWith("CUST"));
//        assertEquals("Account number should be 10 characters long", 10, accountNumber.length());
//    }
//
//    @Test
//    public void testFindByPhone() {
//        // Save customer first
//        customerDAO.save(testCustomer);
//
//        List<Customer> customers = customerDAO.findByPhone("1234567890");
//
//        assertNotNull("Search result should not be null", customers);
//        assertTrue("Should find at least one customer", customers.size() > 0);
//
//        boolean found = customers.stream()
//                .anyMatch(c -> c.getPhoneNumber().equals("1234567890"));
//        assertTrue("Should find customer with matching phone", found);
//    }
//
//    @Test
//    public void testFindByEmail() {
//        // Save customer first
//        customerDAO.save(testCustomer);
//
//        List<Customer> customers = customerDAO.findByEmail("test@example.com");
//
//        assertNotNull("Search result should not be null", customers);
//        assertTrue("Should find at least one customer", customers.size() > 0);
//
//        boolean found = customers.stream()
//                .anyMatch(c -> c.getEmail().equals("test@example.com"));
//        assertTrue("Should find customer with matching email", found);
//    }
//
//    @Test
//    public void testFindBillsByCustomerAccountNumber() {
//        // Save customer first
//        Customer savedCustomer = customerDAO.save(testCustomer);
//
//        // Test finding bills (should return empty list for new customer)
//        List<com.pahanaedu.entities.Bill> bills = customerDAO.findBillsByCustomerAccountNumber(savedCustomer.getAccountNumber());
//
//        assertNotNull("Bills list should not be null", bills);
//        // For a new customer, should have no bills
//        assertEquals("New customer should have no bills", 0, bills.size());
//    }
//}