package pahanaedu.patterns.builder;

import com.pahanaedu.entities.Customer;
import com.pahanaedu.patterns.builder.CustomerBuilder;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CustomerBuilderTest {

    @Test
    public void testBuildCustomer() {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new CustomerBuilder()
                .accountNumber("C123")
                .name("Jane Doe")
                .address("123 Main St")
                .phoneNumber("555-1234")
                .email("jane@example.com")
                .active(true)
                .createdDate(now)
                .updatedDate(now)
                .build();

        assertEquals("C123", customer.getAccountNumber());
        assertEquals("Jane Doe", customer.getName());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals("555-1234", customer.getPhoneNumber());
        assertEquals("jane@example.com", customer.getEmail());
        assertTrue(customer.isActive());
        assertEquals(now, customer.getCreatedDate());
        assertEquals(now, customer.getUpdatedDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithValidationMissingName() {
        new CustomerBuilder()
                .accountNumber("C123")
                .buildWithValidation();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildWithValidationMissingAccountNumber() {
        new CustomerBuilder()
                .name("Jane Doe")
                .buildWithValidation();
    }
}