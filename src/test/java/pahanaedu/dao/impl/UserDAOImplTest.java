//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.UserDAO;
//import com.pahanaedu.dao.impl.UserDAOImpl;
//import com.pahanaedu.entities.User;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Optional;
//
//public class UserDAOImplTest {
//
//    private UserDAO userDAO;
//
//    @Before
//    public void setUp() throws SQLException {
//        userDAO = new UserDAOImpl();
//        cleanDatabase();
//    }
//
//    @After
//    public void tearDown() throws SQLException {
//        cleanDatabase();
//    }
//
//    private void cleanDatabase() throws SQLException {
//        try (Connection conn = com.pahanaedu.dao.impl.DatabaseConnection.getInstance().getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.execute("DELETE FROM users WHERE username LIKE 'test%'");
//        }
//    }
//
//    @Test
//    public void testSaveUser() {
//        User user = new User("testuser", "hashedpassword", "Test User", "test@example.com", "USER");
//
//        User savedUser = userDAO.save(user);
//
//        assertNotNull(savedUser);
//        assertNotNull(savedUser.getId());
//        assertEquals("testuser", savedUser.getUsername());
//        assertEquals("Test User", savedUser.getFullName());
//        assertEquals("test@example.com", savedUser.getEmail());
//        assertEquals("USER", savedUser.getRole());
//        assertTrue(savedUser.isActive());
//    }
//
//    @Test
//    public void testFindByUsername() {
//        User user = new User("testuser2", "hashedpassword", "Test User 2", "test2@example.com", "ADMIN");
//        userDAO.save(user);
//
//        Optional<User> foundUser = userDAO.findByUsername("testuser2");
//
//        assertTrue(foundUser.isPresent());
//        assertEquals("testuser2", foundUser.get().getUsername());
//        assertEquals("Test User 2", foundUser.get().getFullName());
//        assertEquals("ADMIN", foundUser.get().getRole());
//    }
//
//    @Test
//    public void testFindByUsernameNotFound() {
//        Optional<User> foundUser = userDAO.findByUsername("nonexistent");
//
//        assertFalse(foundUser.isPresent());
//    }
//
//    @Test
//    public void testFindById() {
//        User user = new User("testuser3", "hashedpassword", "Test User 3", "test3@example.com", "USER");
//        User savedUser = userDAO.save(user);
//
//        Optional<User> foundUser = userDAO.findById(savedUser.getId());
//
//        assertTrue(foundUser.isPresent());
//        assertEquals(savedUser.getId(), foundUser.get().getId());
//        assertEquals("testuser3", foundUser.get().getUsername());
//    }
//
//    @Test
//    public void testFindAll() {
//        User user1 = new User("testuser4", "hashedpassword", "Test User 4", "test4@example.com", "USER");
//        User user2 = new User("testuser5", "hashedpassword", "Test User 5", "test5@example.com", "ADMIN");
//
//        userDAO.save(user1);
//        userDAO.save(user2);
//
//        List<User> allUsers = userDAO.findAll();
//
//        assertNotNull(allUsers);
//        assertTrue(allUsers.size() >= 2);
//
//        boolean foundUser4 = allUsers.stream().anyMatch(u -> "testuser4".equals(u.getUsername()));
//        boolean foundUser5 = allUsers.stream().anyMatch(u -> "testuser5".equals(u.getUsername()));
//
//        assertTrue(foundUser4);
//        assertTrue(foundUser5);
//    }
//
//    @Test
//    public void testUpdateUser() {
//        User user = new User("testuser6", "hashedpassword", "Test User 6", "test6@example.com", "USER");
//        User savedUser = userDAO.save(user);
//
//        savedUser.setFullName("Updated Test User 6");
//        savedUser.setEmail("updated6@example.com");
//        savedUser.setRole("ADMIN");
//
//        User updatedUser = userDAO.update(savedUser);
//
//        assertNotNull(updatedUser);
//        assertEquals("Updated Test User 6", updatedUser.getFullName());
//        assertEquals("updated6@example.com", updatedUser.getEmail());
//        assertEquals("ADMIN", updatedUser.getRole());
//
//        Optional<User> foundUser = userDAO.findById(savedUser.getId());
//        assertTrue(foundUser.isPresent());
//        assertEquals("Updated Test User 6", foundUser.get().getFullName());
//    }
//
//    @Test
//    public void testDeleteById() {
//        User user = new User("testuser7", "hashedpassword", "Test User 7", "test7@example.com", "USER");
//        User savedUser = userDAO.save(user);
//
//        boolean deleted = userDAO.deleteById(savedUser.getId());
//
//        assertTrue(deleted);
//
//        Optional<User> foundUser = userDAO.findByUsername("testuser7");
//        assertFalse(foundUser.isPresent());
//    }
//
//    @Test
//    public void testUpdateLastLogin() {
//        User user = new User("testuser8", "hashedpassword", "Test User 8", "test8@example.com", "USER");
//        userDAO.save(user);
//
//        boolean updated = userDAO.updateLastLogin("testuser8");
//
//        assertTrue(updated);
//
//        Optional<User> foundUser = userDAO.findByUsername("testuser8");
//        assertTrue(foundUser.isPresent());
//        assertNotNull(foundUser.get().getLastLogin());
//    }
//}