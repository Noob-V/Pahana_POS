//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.CategoryDAO;
//import com.pahanaedu.dao.impl.CategoryDAOImpl;
//import com.pahanaedu.dao.impl.DatabaseConnection;
//import com.pahanaedu.entities.Category;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class CategoryDAOImplTest {
//
//    private CategoryDAO categoryDAO;
//    private DatabaseConnection dbConnection;
//
//    @Before
//    public void setUp() throws Exception {
//        categoryDAO = new CategoryDAOImpl();
//        dbConnection = DatabaseConnection.getInstance();
//
//        // Clean up any existing test data
//        cleanUpTestData();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        cleanUpTestData();
//    }
//
//    private void cleanUpTestData() {
//        String sql = "DELETE FROM categories WHERE name LIKE 'Test%'";
//        try (Connection conn = dbConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.err.println("Error cleaning up test data: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testSaveCategory() {
//        // Given
//        Category category = new Category();
//        category.setName("Test Category");
//        category.setDescription("Test Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        // When
//        Category savedCategory = categoryDAO.save(category);
//
//        // Then
//        assertNotNull("Saved category should not be null", savedCategory);
//        assertNotNull("Category ID should be generated", savedCategory.getId());
//        assertEquals("Category name should match", "Test Category", savedCategory.getName());
//        assertEquals("Category description should match", "Test Description", savedCategory.getDescription());
//        assertTrue("Category should be active", savedCategory.isActive());
//    }
//
//    @Test
//    public void testFindById() {
//        // Given - save a category first
//        Category category = new Category();
//        category.setName("Test FindById Category");
//        category.setDescription("Test FindById Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        Category savedCategory = categoryDAO.save(category);
//
//        // When
//        Optional<Category> foundCategory = categoryDAO.findById(savedCategory.getId());
//
//        // Then
//        assertTrue("Category should be found", foundCategory.isPresent());
//        assertEquals("Category ID should match", savedCategory.getId(), foundCategory.get().getId());
//        assertEquals("Category name should match", "Test FindById Category", foundCategory.get().getName());
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        // When
//        Optional<Category> foundCategory = categoryDAO.findById(99999);
//
//        // Then
//        assertFalse("Category should not be found", foundCategory.isPresent());
//    }
//
//    @Test
//    public void testFindAll() {
//        // Given - save multiple categories
//        Category category1 = new Category();
//        category1.setName("Test Category 1");
//        category1.setDescription("Test Description 1");
//        category1.setActive(true);
//        category1.setCreatedDate(LocalDateTime.now());
//
//        Category category2 = new Category();
//        category2.setName("Test Category 2");
//        category2.setDescription("Test Description 2");
//        category2.setActive(true);
//        category2.setCreatedDate(LocalDateTime.now());
//
//        categoryDAO.save(category1);
//        categoryDAO.save(category2);
//
//        // When
//        List<Category> allCategories = categoryDAO.findAll();
//
//        // Then
//        assertNotNull("Categories list should not be null", allCategories);
//        assertTrue("Should have at least 2 categories", allCategories.size() >= 2);
//
//        // Check if our test categories are in the list
//        boolean found1 = allCategories.stream().anyMatch(c -> "Test Category 1".equals(c.getName()));
//        boolean found2 = allCategories.stream().anyMatch(c -> "Test Category 2".equals(c.getName()));
//
//        assertTrue("Test Category 1 should be found", found1);
//        assertTrue("Test Category 2 should be found", found2);
//    }
//
//    @Test
//    public void testFindActive() {
//        // Given - save active and inactive categories
//        Category activeCategory = new Category();
//        activeCategory.setName("Test Active Category");
//        activeCategory.setDescription("Test Active Description");
//        activeCategory.setActive(true);
//        activeCategory.setCreatedDate(LocalDateTime.now());
//
//        Category inactiveCategory = new Category();
//        inactiveCategory.setName("Test Inactive Category");
//        inactiveCategory.setDescription("Test Inactive Description");
//        inactiveCategory.setActive(false);
//        inactiveCategory.setCreatedDate(LocalDateTime.now());
//
//        categoryDAO.save(activeCategory);
//        categoryDAO.save(inactiveCategory);
//
//        // When
//        List<Category> activeCategories = categoryDAO.findActive();
//
//        // Then
//        assertNotNull("Active categories list should not be null", activeCategories);
//
//        // Check that only active categories are returned
//        boolean foundActive = activeCategories.stream().anyMatch(c -> "Test Active Category".equals(c.getName()));
//        boolean foundInactive = activeCategories.stream().anyMatch(c -> "Test Inactive Category".equals(c.getName()));
//
//        assertTrue("Active category should be found", foundActive);
//        assertFalse("Inactive category should not be found", foundInactive);
//
//        // All returned categories should be active
//        for (Category category : activeCategories) {
//            assertTrue("All categories should be active", category.isActive());
//        }
//    }
//
//    @Test
//    public void testUpdateCategory() {
//        // Given - save a category first
//        Category category = new Category();
//        category.setName("Test Update Category");
//        category.setDescription("Original Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        Category savedCategory = categoryDAO.save(category);
//
//        // When - update the category
//        savedCategory.setName("Updated Category Name");
//        savedCategory.setDescription("Updated Description");
//        savedCategory.setActive(false);
//
//        Category updatedCategory = categoryDAO.update(savedCategory);
//
//        // Then
//        assertNotNull("Updated category should not be null", updatedCategory);
//        assertEquals("Category name should be updated", "Updated Category Name", updatedCategory.getName());
//        assertEquals("Category description should be updated", "Updated Description", updatedCategory.getDescription());
//        assertFalse("Category should be inactive", updatedCategory.isActive());
//
//        // Verify in database
//        Optional<Category> foundCategory = categoryDAO.findById(savedCategory.getId());
//        assertTrue("Category should still exist", foundCategory.isPresent());
//        assertEquals("Database should reflect updated name", "Updated Category Name", foundCategory.get().getName());
//    }
//
//    @Test
//    public void testDeleteById() {
//        // Given - save a category first
//        Category category = new Category();
//        category.setName("Test Delete Category");
//        category.setDescription("Test Delete Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        Category savedCategory = categoryDAO.save(category);
//
//        // Verify it exists
//        Optional<Category> foundBefore = categoryDAO.findById(savedCategory.getId());
//        assertTrue("Category should exist before deletion", foundBefore.isPresent());
//        assertTrue("Category should be active before deletion", foundBefore.get().isActive());
//
//        // When
//        boolean deleted = categoryDAO.deleteById(savedCategory.getId());
//
//        // Then
//        assertTrue("Delete operation should return true", deleted);
//
//        // Verify it's marked as inactive (soft delete)
//        Optional<Category> foundAfter = categoryDAO.findById(savedCategory.getId());
//        assertTrue("Category should still exist after soft delete", foundAfter.isPresent());
//        assertFalse("Category should be inactive after deletion", foundAfter.get().isActive());
//    }
//
//    @Test
//    public void testDeleteByIdNotFound() {
//        // When
//        boolean deleted = categoryDAO.deleteById(99999);
//
//        // Then
//        assertFalse("Delete operation should return false for non-existent category", deleted);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testSaveCategoryWithNullName() {
//        // Given
//        Category category = new Category();
//        category.setName(null); // This should cause an error
//        category.setDescription("Test Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        // When - this should throw an exception
//        categoryDAO.save(category);
//    }
//
//    @Test
//    public void testSaveCategoryWithLongName() {
//        // Given - assuming name field has a reasonable length limit
//        StringBuilder longName = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            longName.append("Test Very Long Category Name ");
//        }
//
//        Category category = new Category();
//        category.setName(longName.toString());
//        category.setDescription("Test Description");
//        category.setActive(true);
//        category.setCreatedDate(LocalDateTime.now());
//
//        try {
//            // When
//            Category savedCategory = categoryDAO.save(category);
//
//            // Then
//            assertNotNull("Category should be saved", savedCategory);
//            assertNotNull("Category ID should be generated", savedCategory.getId());
//        } catch (RuntimeException e) {
//            // If database has length constraints, this is expected
//            assertTrue("Exception should be about data length",
//                    e.getMessage().contains("truncated") ||
//                            e.getMessage().contains("too long") ||
//                            e.getMessage().contains("length"));
//        }
//    }
//}