package pahanaedu.utils;

import com.pahanaedu.utils.ValidationUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationUtilsTest {

    @Test
    public void testIsValidEmail() {
        // Valid emails
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
        assertTrue(ValidationUtils.isValidEmail("user.name@domain.co.uk"));
        assertTrue(ValidationUtils.isValidEmail("user+tag@example.org"));
        assertTrue(ValidationUtils.isValidEmail("test123@test-domain.com"));

        // Invalid emails
        assertFalse(ValidationUtils.isValidEmail("invalid-email"));
        assertFalse(ValidationUtils.isValidEmail("@example.com"));
        assertFalse(ValidationUtils.isValidEmail("test@"));
        assertFalse(ValidationUtils.isValidEmail("test.example.com"));
        assertFalse(ValidationUtils.isValidEmail(null));
        assertFalse(ValidationUtils.isValidEmail(""));
    }

    @Test
    public void testIsValidPhone() {
        // Valid phone numbers
        assertTrue(ValidationUtils.isValidPhone("1234567890"));
        assertTrue(ValidationUtils.isValidPhone("0987654321"));

        // Invalid phone numbers
        assertFalse(ValidationUtils.isValidPhone("123456789")); // 9 digits
        assertFalse(ValidationUtils.isValidPhone("12345678901")); // 11 digits
        assertFalse(ValidationUtils.isValidPhone("123-456-7890")); // with dashes
        assertFalse(ValidationUtils.isValidPhone("abcdefghij")); // letters
        assertFalse(ValidationUtils.isValidPhone(null));
        assertFalse(ValidationUtils.isValidPhone(""));
    }

    @Test
    public void testIsValidISBN() {
        // Valid ISBNs
        assertTrue(ValidationUtils.isValidISBN("9780123456789"));
        assertTrue(ValidationUtils.isValidISBN("978-0-123-45678-9"));
        assertTrue(ValidationUtils.isValidISBN("0123456789"));
        assertTrue(ValidationUtils.isValidISBN("012345678X"));

        // Invalid ISBNs
        assertFalse(ValidationUtils.isValidISBN("invalid-isbn"));
        assertFalse(ValidationUtils.isValidISBN("123"));
        assertFalse(ValidationUtils.isValidISBN(null));
        assertFalse(ValidationUtils.isValidISBN(""));
    }

    @Test
    public void testIsNotEmpty() {
        // Valid non-empty strings
        assertTrue(ValidationUtils.isNotEmpty("test"));
        assertTrue(ValidationUtils.isNotEmpty("a"));
        assertTrue(ValidationUtils.isNotEmpty("  text  "));

        // Invalid empty strings
        assertFalse(ValidationUtils.isNotEmpty(null));
        assertFalse(ValidationUtils.isNotEmpty(""));
        assertFalse(ValidationUtils.isNotEmpty("   "));
        assertFalse(ValidationUtils.isNotEmpty("\t\n"));
    }

    @Test
    public void testIsValidName() {
        // Valid names
        assertTrue(ValidationUtils.isValidName("John"));
        assertTrue(ValidationUtils.isValidName("Jane Doe"));
        assertTrue(ValidationUtils.isValidName("A".repeat(100))); // exactly 100 chars

        // Invalid names
        assertFalse(ValidationUtils.isValidName("A")); // too short
        assertFalse(ValidationUtils.isValidName("A".repeat(101))); // too long
        assertFalse(ValidationUtils.isValidName(null));
        assertFalse(ValidationUtils.isValidName(""));
        assertFalse(ValidationUtils.isValidName("   "));
    }

    @Test
    public void testIsPositiveNumber() {
        // Valid positive numbers
        assertTrue(ValidationUtils.isPositiveNumber(1));
        assertTrue(ValidationUtils.isPositiveNumber(1.5));
        assertTrue(ValidationUtils.isPositiveNumber(0.001));
        assertTrue(ValidationUtils.isPositiveNumber(Integer.MAX_VALUE));

        // Invalid non-positive numbers
        assertFalse(ValidationUtils.isPositiveNumber(0));
        assertFalse(ValidationUtils.isPositiveNumber(-1));
        assertFalse(ValidationUtils.isPositiveNumber(-0.1));
        assertFalse(ValidationUtils.isPositiveNumber(null));
    }
}