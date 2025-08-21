package pahanaedu.entities;

import com.pahanaedu.entities.Customer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomerTest {

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(customer);
        assertTrue(customer.isActive());
        assertNotNull(customer.getCreatedDate());
        assertNotNull(customer.getUpdatedDate());
    }

    @Test
    public void testAccountNumberGetterSetter() {
        String accountNumber = "PAH001";
        customer.setAccountNumber(accountNumber);
        assertEquals(accountNumber, customer.getAccountNumber());
    }

    @Test
    public void testNameGetterSetter() {
        String name = "John Doe";
        customer.setName(name);
        assertEquals(name, customer.getName());
    }

    @Test
    public void testAddressGetterSetter() {
        String address = "123 Main Street, City";
        customer.setAddress(address);
        assertEquals(address, customer.getAddress());
    }

    @Test
    public void testPhoneNumberGetterSetter() {
        String phoneNumber = "555-1234";
        customer.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, customer.getPhoneNumber());
    }

    @Test
    public void testEmailGetterSetter() {
        String email = "john.doe@example.com";
        customer.setEmail(email);
        assertEquals(email, customer.getEmail());
    }

    @Test
    public void testActiveGetterSetter() {
        customer.setActive(true);
        assertTrue(customer.isActive());

        customer.setActive(false);
        assertFalse(customer.isActive());
    }

    @Test
    public void testCreatedDateAsDate() {
        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedDate(now);

        Date createdDate = customer.getCreatedDateAsDate();
        assertNotNull(createdDate);
    }

    @Test
    public void testUpdatedDateAsDate() {
        LocalDateTime now = LocalDateTime.now();
        customer.setUpdatedDate(now);

        Date updatedDate = customer.getUpdatedDateAsDate();
        assertNotNull(updatedDate);
    }

    @Test
    public void testCreatedDateGetterSetter() {
        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedDate(now);
        assertEquals(now, customer.getCreatedDate());
    }

    @Test
    public void testUpdatedDateGetterSetter() {
        LocalDateTime now = LocalDateTime.now();
        customer.setUpdatedDate(now);
        assertEquals(now, customer.getUpdatedDate());
    }
}