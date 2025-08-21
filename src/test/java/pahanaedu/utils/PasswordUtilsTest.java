package pahanaedu.utils;

import com.pahanaedu.utils.PasswordUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordUtilsTest {

    @Test
    public void testHashPassword() {
        String plainPassword = "testPassword123";
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(plainPassword, hashedPassword);
        assertTrue(hashedPassword.startsWith("$2a$"));
        assertTrue(hashedPassword.length() > 50);
    }

    @Test
    public void testHashPasswordDifferentResults() {
        String plainPassword = "testPassword123";
        String hash1 = PasswordUtils.hashPassword(plainPassword);
        String hash2 = PasswordUtils.hashPassword(plainPassword);

        assertNotEquals("Two hashes of the same password should be different due to salt", hash1, hash2);
    }

    @Test
    public void testVerifyPasswordCorrect() {
        String plainPassword = "testPassword123";
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        assertTrue(PasswordUtils.verifyPassword(plainPassword, hashedPassword));
    }

    @Test
    public void testVerifyPasswordIncorrect() {
        String plainPassword = "testPassword123";
        String wrongPassword = "wrongPassword";
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);

        assertFalse(PasswordUtils.verifyPassword(wrongPassword, hashedPassword));
    }

    @Test
    public void testVerifyPasswordWithNullValues() {
        String hashedPassword = PasswordUtils.hashPassword("test");

        assertFalse(PasswordUtils.verifyPassword(null, hashedPassword));
        assertFalse(PasswordUtils.verifyPassword("test", null));
        assertFalse(PasswordUtils.verifyPassword(null, null));
    }

    @Test
    public void testVerifyPasswordWithEmptyValues() {
        String hashedPassword = PasswordUtils.hashPassword("test");

        assertFalse(PasswordUtils.verifyPassword("", hashedPassword));
        assertFalse(PasswordUtils.verifyPassword("test", ""));
    }

    @Test
    public void testVerifyPasswordWithInvalidHash() {
        assertFalse(PasswordUtils.verifyPassword("test", "invalid-hash"));
        assertFalse(PasswordUtils.verifyPassword("test", "not-bcrypt-hash"));
    }
}