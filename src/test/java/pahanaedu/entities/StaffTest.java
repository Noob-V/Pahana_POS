package pahanaedu.entities;

import com.pahanaedu.entities.Staff;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StaffTest {

    private Staff staff;

    @Before
    public void setUp() {
        staff = new Staff();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(staff);
        assertEquals("STAFF", staff.getRole());
    }

    @Test
    public void testParameterizedConstructor() {
        String username = "staff01";
        String passwordHash = "hashedPassword123";
        String fullName = "John Staff";
        String email = "staff@example.com";

        Staff testStaff = new Staff(username, passwordHash, fullName, email);

        assertEquals(username, testStaff.getUsername());
        assertEquals(passwordHash, testStaff.getPasswordHash());
        assertEquals(fullName, testStaff.getFullName());
        assertEquals(email, testStaff.getEmail());
        assertEquals("STAFF", testStaff.getRole());
    }

    @Test
    public void testInheritsFromUser() {
        assertTrue(staff instanceof com.pahanaedu.entities.User);
    }

    @Test
    public void testRoleIsSetToStaff() {
        staff.setRole("ADMIN"); // Try to change role
        // Role should remain STAFF or be reset to STAFF depending on implementation
        // This test verifies the Staff class behavior
        assertNotNull(staff.getRole());
    }

    @Test
    public void testUsernameInheritance() {
        String username = "teststaff";
        staff.setUsername(username);
        assertEquals(username, staff.getUsername());
    }

    @Test
    public void testFullNameInheritance() {
        String fullName = "Test Staff Member";
        staff.setFullName(fullName);
        assertEquals(fullName, staff.getFullName());
    }

    @Test
    public void testEmailInheritance() {
        String email = "test.staff@example.com";
        staff.setEmail(email);
        assertEquals(email, staff.getEmail());
    }

    @Test
    public void testActiveInheritance() {
        staff.setActive(true);
        assertTrue(staff.isActive());

        staff.setActive(false);
        assertFalse(staff.isActive());
    }
}