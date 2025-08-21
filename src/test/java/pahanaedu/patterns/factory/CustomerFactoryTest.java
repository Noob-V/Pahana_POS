package pahanaedu.patterns.factory;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.patterns.factory.CustomerFactory;
import com.pahanaedu.patterns.factory.DAOCreator;
import com.pahanaedu.patterns.factory.CustomerDAOProduct;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerFactoryTest {

    private CustomerFactory factory;

    // Simple mock CustomerDAO
    private static class MockCustomerDAO implements CustomerDAO {
        @Override public String generateNextAccountNumber() { return "0001"; }
        // Other methods can throw UnsupportedOperationException
        @Override public java.util.Optional<Customer> findByAccountNumber(String accountNumber) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<Customer> findAll() { throw new UnsupportedOperationException(); }
        @Override public java.util.List<Customer> findByName(String name) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<Customer> findByPhone(String phone) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<Customer> searchByName(String name) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<Customer> findByEmail(String email) { throw new UnsupportedOperationException(); }
        @Override public Customer save(Customer customer) { throw new UnsupportedOperationException(); }
        @Override public Customer update(Customer customer) { throw new UnsupportedOperationException(); }
        @Override public boolean deleteByAccountNumber(String accountNumber) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<com.pahanaedu.entities.Bill> findBillsByCustomerAccountNumber(String accountNumber) { throw new UnsupportedOperationException(); }
        @Override public java.util.List<com.pahanaedu.entities.Bill> findRecentBillsByCustomer(String accountNumber, int limit) { throw new UnsupportedOperationException(); }
    }

    // Simple mock DAOCreator
    private static class MockDAOCreator extends DAOCreator<CustomerDAO> {
        @Override
        public CustomerDAOProduct createDAO(String type) {
            return new CustomerDAOProduct(new MockCustomerDAO());
        }
    }

    @Before
    public void setUp() {
        factory = new CustomerFactory(new MockDAOCreator());
    }

    @Test
    public void testCreateSampleCustomer() {
        Customer customer = factory.createSampleCustomer("Alice", "1234567890", "alice@example.com");
        assertEquals("Alice", customer.getName());
        assertEquals("1234567890", customer.getPhoneNumber());
        assertEquals("alice@example.com", customer.getEmail());
        assertEquals("Sample Address", customer.getAddress());
        assertTrue(customer.isActive());
    }

    @Test
    public void testCreateCustomerFromRequestWithAccountNumber() {
        Customer customer = factory.createCustomerFromRequest("C100", "Bob", "Addr", "9876543210", "bob@example.com");
        assertEquals("C100", customer.getAccountNumber());
        assertEquals("Bob", customer.getName());
        assertEquals("Addr", customer.getAddress());
        assertEquals("9876543210", customer.getPhoneNumber());
        assertEquals("bob@example.com", customer.getEmail());
        assertTrue(customer.isActive());
    }

    @Test
    public void testCreateCustomerFromRequestWithoutAccountNumber() {
        Customer customer = factory.createCustomerFromRequest(null, "Carol", "Addr2", "5555555555", "carol@example.com");
        assertEquals("0001", customer.getAccountNumber()); // Expect auto-generated value
        assertEquals("Carol", customer.getName());
        assertEquals("Addr2", customer.getAddress());
        assertEquals("5555555555", customer.getPhoneNumber());
        assertEquals("carol@example.com", customer.getEmail());
        assertTrue(customer.isActive());
    }

    @Test
    public void testCreatePremiumCustomer() {
        Customer customer = factory.createPremiumCustomer("Dave", "1112223333", "dave@example.com", "Premium Addr");
        assertEquals("PREM0001", customer.getAccountNumber());
        assertEquals("Dave", customer.getName());
        assertEquals("Premium Addr", customer.getAddress());
        assertEquals("1112223333", customer.getPhoneNumber());
        assertEquals("dave@example.com", customer.getEmail());
        assertTrue(customer.isActive());
    }
}
