package pahanaedu.entities;

import com.pahanaedu.entities.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testParameterizedConstructor() {
        String username = "testuser";
        String passwordHash = "hashedPassword123";
        String fullName = "Test User";
        String email = "test@example.com";
        String role = "USER";

        User testUser = new User(username, passwordHash, fullName, email, role);

        assertEquals(username, testUser.getUsername());
        assertEquals(passwordHash, testUser.getPasswordHash());
        assertEquals(fullName, testUser.getFullName());
        assertEquals(email, testUser.getEmail());
        assertEquals(role, testUser.getRole());
        assertTrue(testUser.isActive());
        assertNotNull(testUser.getCreatedDate());
    }

    @Test
    public void testIdGetterSetter() {
        Integer id = 1;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testUsernameGetterSetter() {
        String username = "johndoe";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testPasswordHashGetterSetter() {
        String passwordHash = "hashedPassword456";
        user.setPasswordHash(passwordHash);
        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    public void testFullNameGetterSetter() {
        String fullName = "John Doe";
        user.setFullName(fullName);
        assertEquals(fullName, user.getFullName());
    }

    @Test
    public void testEmailGetterSetter() {
        String email = "john.doe@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testRoleGetterSetter() {
        String role = "ADMIN";
        user.setRole(role);
        assertEquals(role, user.getRole());
    }

    @Test
    public void testActiveGetterSetter() {
        user.setActive(true);
        assertTrue(user.isActive());

        user.setActive(false);
        assertFalse(user.isActive());
    }

    @Test
    public void testCreatedDateGetterSetter() {
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedDate(now);
        assertEquals(now, user.getCreatedDate());
    }

    @Test
    public void testLastLoginGetterSetter() {
        LocalDateTime now = LocalDateTime.now();
        user.setLastLogin(now);
        assertEquals(now, user.getLastLogin());
    }

    @Test
    public void testToString() {
        user.setId(1);
        user.setUsername("testuser");
        user.setFullName("Test User");
        user.setRole("USER");

        String result = user.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("testuser"));
        assertTrue(result.contains("Test User"));
        assertTrue(result.contains("USER"));
    }
}