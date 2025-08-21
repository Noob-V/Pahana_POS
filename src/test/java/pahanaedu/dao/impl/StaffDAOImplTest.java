//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.impl.DatabaseConnection;
//import com.pahanaedu.dao.impl.StaffDAOImpl;
//import com.pahanaedu.entities.Staff;
//import org.junit.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class StaffDAOImplTest {
//    private StaffDAOImpl staffDAO;
//    private DatabaseConnection dbConnection;
//    private static final String TEST_USERNAME = "test_staff_user";
//    private static final String TEST_EMAIL = "test@example.com";
//    private static final String TEST_FULL_NAME = "Test Staff User";
//    private static final String TEST_PASSWORD_HASH = "hashedpassword123";
//
//    @Before
//    public void setUp() {
//        staffDAO = new StaffDAOImpl();
//        dbConnection = DatabaseConnection.getInstance();
//        cleanupTestData();
//    }
//
//    @After
//    public void tearDown() {
//        cleanupTestData();
//    }
//
//    @Test
//    public void testSaveStaff() {
//        // Given
//        Staff staff = new Staff();
//        staff.setUsername(TEST_USERNAME);
//        staff.setPasswordHash(TEST_PASSWORD_HASH);
//        staff.setFullName(TEST_FULL_NAME);
//        staff.setEmail(TEST_EMAIL);
//        staff.setActive(true);
//
//        // When
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        // Then
//        assertNotNull("Saved staff should not be null", savedStaff);
//        assertNotNull("Staff ID should be generated", savedStaff.getId());
//        assertEquals("Username should match", TEST_USERNAME, savedStaff.getUsername());
//        assertEquals("Full name should match", TEST_FULL_NAME, savedStaff.getFullName());
//        assertEquals("Email should match", TEST_EMAIL, savedStaff.getEmail());
//        assertEquals("Role should be STAFF", "STAFF", savedStaff.getRole());
//        assertTrue("Staff should be active", savedStaff.isActive());
//    }
//
//    @Test
//    public void testFindStaffByUsername() {
//        // Given
//        Staff staff = createTestStaff();
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        // When
//        Optional<Staff> foundStaff = staffDAO.findStaffByUsername(TEST_USERNAME);
//
//        // Then
//        assertTrue("Staff should be found", foundStaff.isPresent());
//        assertEquals("Username should match", TEST_USERNAME, foundStaff.get().getUsername());
//        assertEquals("Full name should match", TEST_FULL_NAME, foundStaff.get().getFullName());
//        assertEquals("Email should match", TEST_EMAIL, foundStaff.get().getEmail());
//    }
//
//    @Test
//    public void testFindStaffByUsername_NotFound() {
//        // When
//        Optional<Staff> foundStaff = staffDAO.findStaffByUsername("nonexistent_user");
//
//        // Then
//        assertFalse("Staff should not be found", foundStaff.isPresent());
//    }
//
//    @Test
//    public void testFindStaffById() {
//        // Given
//        Staff staff = createTestStaff();
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        // When
//        Optional<Staff> foundStaff = staffDAO.findStaffById(savedStaff.getId());
//
//        // Then
//        assertTrue("Staff should be found", foundStaff.isPresent());
//        assertEquals("ID should match", savedStaff.getId(), foundStaff.get().getId());
//        assertEquals("Username should match", TEST_USERNAME, foundStaff.get().getUsername());
//        assertEquals("Full name should match", TEST_FULL_NAME, foundStaff.get().getFullName());
//    }
//
//    @Test
//    public void testFindStaffById_NotFound() {
//        // When
//        Optional<Staff> foundStaff = staffDAO.findStaffById(99999);
//
//        // Then
//        assertFalse("Staff should not be found", foundStaff.isPresent());
//    }
//
//    @Test
//    public void testFindAllStaff() {
//        // Given
//        Staff staff1 = createTestStaff();
//        Staff staff2 = createTestStaff();
//        staff2.setUsername(TEST_USERNAME + "_2");
//        staff2.setEmail("test2@example.com");
//
//        staffDAO.saveStaff(staff1);
//        staffDAO.saveStaff(staff2);
//
//        // When
//        List<Staff> allStaff = staffDAO.findAllStaff();
//
//        // Then
//        assertNotNull("Staff list should not be null", allStaff);
//        assertTrue("Should have at least 2 staff members", allStaff.size() >= 2);
//
//        boolean foundStaff1 = allStaff.stream().anyMatch(s -> TEST_USERNAME.equals(s.getUsername()));
//        boolean foundStaff2 = allStaff.stream().anyMatch(s -> (TEST_USERNAME + "_2").equals(s.getUsername()));
//
//        assertTrue("Should find first test staff", foundStaff1);
//        assertTrue("Should find second test staff", foundStaff2);
//    }
//
//    @Test
//    public void testUpdateStaff() {
//        // Given
//        Staff staff = createTestStaff();
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        String updatedName = "Updated Staff Name";
//        String updatedEmail = "updated@example.com";
//        savedStaff.setFullName(updatedName);
//        savedStaff.setEmail(updatedEmail);
//        savedStaff.setActive(false);
//
//        // When
//        Staff updatedStaff = staffDAO.updateStaff(savedStaff);
//
//        // Then
//        assertNotNull("Updated staff should not be null", updatedStaff);
//        assertEquals("Full name should be updated", updatedName, updatedStaff.getFullName());
//        assertEquals("Email should be updated", updatedEmail, updatedStaff.getEmail());
//        assertFalse("Staff should be inactive", updatedStaff.isActive());
//
//        // Verify the update persisted
//        Optional<Staff> foundStaff = staffDAO.findStaffById(savedStaff.getId());
//        assertTrue("Staff should still exist", foundStaff.isPresent());
//        assertEquals("Persisted name should match", updatedName, foundStaff.get().getFullName());
//        assertEquals("Persisted email should match", updatedEmail, foundStaff.get().getEmail());
//        assertFalse("Persisted active status should be false", foundStaff.get().isActive());
//    }
//
//    @Test
//    public void testDeleteStaff() {
//        // Given
//        Staff staff = createTestStaff();
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        // When
//        boolean deleteResult = staffDAO.deleteStaff(TEST_USERNAME);
//
//        // Then
//        assertTrue("Delete should return true", deleteResult);
//
//        // Verify staff is marked as inactive
//        Optional<Staff> foundStaff = staffDAO.findStaffByUsername(TEST_USERNAME);
//        assertTrue("Staff should still exist in database", foundStaff.isPresent());
//        assertFalse("Staff should be marked as inactive", foundStaff.get().isActive());
//    }
//
//    @Test
//    public void testDeleteStaff_NotFound() {
//        // When
//        boolean deleteResult = staffDAO.deleteStaff("nonexistent_user");
//
//        // Then
//        assertFalse("Delete should return false for non-existent user", deleteResult);
//    }
//
//    @Test
//    public void testSaveStaff_WithNullValues() {
//        // Given
//        Staff staff = new Staff();
//        staff.setUsername(TEST_USERNAME);
//        staff.setPasswordHash(TEST_PASSWORD_HASH);
//        staff.setFullName(null);
//        staff.setEmail(null);
//        staff.setActive(true);
//
//        // When
//        Staff savedStaff = staffDAO.saveStaff(staff);
//
//        // Then
//        assertNotNull("Saved staff should not be null", savedStaff);
//        assertNotNull("Staff ID should be generated", savedStaff.getId());
//        assertEquals("Username should match", TEST_USERNAME, savedStaff.getUsername());
//        assertNull("Full name should be null", savedStaff.getFullName());
//        assertNull("Email should be null", savedStaff.getEmail());
//    }
//
//    private Staff createTestStaff() {
//        Staff staff = new Staff();
//        staff.setUsername(TEST_USERNAME);
//        staff.setPasswordHash(TEST_PASSWORD_HASH);
//        staff.setFullName(TEST_FULL_NAME);
//        staff.setEmail(TEST_EMAIL);
//        staff.setActive(true);
//        return staff;
//    }
//
//    private void cleanupTestData() {
//        try (Connection conn = dbConnection.getConnection()) {
//            // Delete test staff users
//            String deleteSQL = "DELETE FROM users WHERE username LIKE ? AND role = 'STAFF'";
//            try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
//                stmt.setString(1, TEST_USERNAME + "%");
//                stmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            System.err.println("Error cleaning up test data: " + e.getMessage());
//        }
//    }
//}