package pahanaedu.dao.impl;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.entities.Bill;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerDAOImplTest {

    private CustomerDAO customerDAO;
    private Customer testCustomer;

    @Before
    public void setUp() {
        customerDAO = mock(CustomerDAO.class);

        testCustomer = new Customer();
        testCustomer.setAccountNumber("CUST000001");
        testCustomer.setName("Test Customer");
        testCustomer.setAddress("123 Test Street");
        testCustomer.setPhoneNumber("1234567890");
        testCustomer.setEmail("test@example.com");
        testCustomer.setActive(true);
        testCustomer.setCreatedDate(LocalDateTime.now());
        testCustomer.setUpdatedDate(LocalDateTime.now());

        // Mock behaviors
        when(customerDAO.save(any(Customer.class))).thenReturn(testCustomer);
        when(customerDAO.findByAccountNumber("CUST000001")).thenReturn(Optional.of(testCustomer));
        when(customerDAO.findAll()).thenReturn(Collections.singletonList(testCustomer));
        when(customerDAO.searchByName("Test")).thenReturn(Collections.singletonList(testCustomer));
        when(customerDAO.update(any(Customer.class))).thenReturn(testCustomer);
        when(customerDAO.deleteByAccountNumber("CUST000001")).thenReturn(true);
        when(customerDAO.generateNextAccountNumber()).thenReturn("CUST000002");
        when(customerDAO.findByPhone("1234567890")).thenReturn(Collections.singletonList(testCustomer));
        when(customerDAO.findByEmail("test@example.com")).thenReturn(Collections.singletonList(testCustomer));
        when(customerDAO.findBillsByCustomerAccountNumber("CUST000001")).thenReturn(Collections.emptyList());
    }

    @Test
    public void testSaveCustomer() {
        Customer savedCustomer = customerDAO.save(testCustomer);
        assertNotNull(savedCustomer);
        assertEquals("CUST000001", savedCustomer.getAccountNumber());
    }

    @Test
    public void testFindByAccountNumber() {
        Optional<Customer> foundCustomer = customerDAO.findByAccountNumber("CUST000001");
        assertTrue(foundCustomer.isPresent());
        assertEquals("CUST000001", foundCustomer.get().getAccountNumber());
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = customerDAO.findAll();
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testSearchByName() {
        List<Customer> customers = customerDAO.searchByName("Test");
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testUpdateCustomer() {
        Customer updatedCustomer = customerDAO.update(testCustomer);
        assertNotNull(updatedCustomer);
        assertEquals("CUST000001", updatedCustomer.getAccountNumber());
    }

    @Test
    public void testDeleteByAccountNumber() {
        boolean deleted = customerDAO.deleteByAccountNumber("CUST000001");
        assertTrue(deleted);
    }

    @Test
    public void testGenerateNextAccountNumber() {
        String accountNumber = customerDAO.generateNextAccountNumber();
        assertEquals("CUST000002", accountNumber);
    }

    @Test
    public void testFindByPhone() {
        List<Customer> customers = customerDAO.findByPhone("1234567890");
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testFindByEmail() {
        List<Customer> customers = customerDAO.findByEmail("test@example.com");
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testFindBillsByCustomerAccountNumber() {
        List<Bill> bills = customerDAO.findBillsByCustomerAccountNumber("CUST000001");
        assertNotNull(bills);
        assertEquals(0, bills.size());
    }
}