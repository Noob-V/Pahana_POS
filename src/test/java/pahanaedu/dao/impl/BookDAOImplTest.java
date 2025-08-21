//package pahanaedu.dao.impl;
//
//import com.pahanaedu.dao.BookDAO;
//import com.pahanaedu.dao.impl.BookDAOImpl;
//import com.pahanaedu.entities.Book;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//public class BookDAOImplTest {
//
//    private BookDAO bookDAO;
//    private Book testBook;
//
//    @Before
//    public void setUp() {
//        bookDAO = new BookDAOImpl();
//
//        // Create a test book
//        testBook = new Book();
//        testBook.setIsbn("9781234567890");
//        testBook.setTitle("Test Book");
//        testBook.setAuthor("Test Author");
//        testBook.setPublisher("Test Publisher");
//        testBook.setDescription("Test Description");
//        testBook.setPrice(new BigDecimal("29.99"));
//        testBook.setQuantity(10);
//        testBook.setMinStockLevel(5);
//        testBook.setActive(true);
//    }
//
//    @After
//    public void tearDown() {
//        // Clean up test data
//        if (testBook != null && testBook.getIsbn() != null) {
//            bookDAO.deleteByIsbn(testBook.getIsbn());
//        }
//    }
//
//    @Test
//    public void testSaveBook() {
//        Book savedBook = bookDAO.save(testBook);
//        assertNotNull("Saved book should not be null", savedBook);
//        assertEquals("ISBN should match", testBook.getIsbn(), savedBook.getIsbn());
//        assertEquals("Title should match", testBook.getTitle(), savedBook.getTitle());
//    }
//
//    @Test
//    public void testFindByIsbn() {
//        // First save the book
//        bookDAO.save(testBook);
//
//        // Then find it
//        Optional<Book> foundBook = bookDAO.findByIsbn(testBook.getIsbn());
//        assertTrue("Book should be found", foundBook.isPresent());
//        assertEquals("ISBN should match", testBook.getIsbn(), foundBook.get().getIsbn());
//    }
//
//    @Test
//    public void testFindByIsbnNotFound() {
//        Optional<Book> foundBook = bookDAO.findByIsbn("9999999999999");
//        assertFalse("Book should not be found", foundBook.isPresent());
//    }
//
//    @Test
//    public void testUpdateBook() {
//        // First save the book
//        bookDAO.save(testBook);
//
//        // Update the book
//        testBook.setTitle("Updated Test Book");
//        testBook.setPrice(new BigDecimal("39.99"));
//
//        Book updatedBook = bookDAO.update(testBook);
//        assertNotNull("Updated book should not be null", updatedBook);
//        assertEquals("Title should be updated", "Updated Test Book", updatedBook.getTitle());
//        assertEquals("Price should be updated", new BigDecimal("39.99"), updatedBook.getPrice());
//    }
//
//    @Test
//    public void testDeleteByIsbn() {
//        // First save the book
//        bookDAO.save(testBook);
//
//        // Verify it exists
//        Optional<Book> foundBook = bookDAO.findByIsbn(testBook.getIsbn());
//        assertTrue("Book should exist before deletion", foundBook.isPresent());
//
//        // Delete the book
//        boolean deleted = bookDAO.deleteByIsbn(testBook.getIsbn());
//        assertTrue("Delete operation should return true", deleted);
//
//        // Verify it's deleted
//        Optional<Book> deletedBook = bookDAO.findByIsbn(testBook.getIsbn());
//        assertFalse("Book should not exist after deletion", deletedBook.isPresent());
//
//        // Set to null to prevent cleanup in tearDown
//        testBook = null;
//    }
//
//    @Test
//    public void testFindAll() {
//        // Save the test book
//        bookDAO.save(testBook);
//
//        List<Book> allBooks = bookDAO.findAll();
//        assertNotNull("Book list should not be null", allBooks);
//        assertTrue("Book list should contain at least one book", allBooks.size() > 0);
//
//        // Check if our test book is in the list
//        boolean found = allBooks.stream()
//                .anyMatch(book -> testBook.getIsbn().equals(book.getIsbn()));
//        assertTrue("Test book should be in the list", found);
//    }
//
//    @Test
//    public void testFindByTitle() {
//        // Save the test book
//        bookDAO.save(testBook);
//
//        List<Book> books = bookDAO.findByTitle("Test");
//        assertNotNull("Book list should not be null", books);
//        assertTrue("Should find at least one book", books.size() > 0);
//
//        boolean found = books.stream()
//                .anyMatch(book -> testBook.getIsbn().equals(book.getIsbn()));
//        assertTrue("Test book should be found by title", found);
//    }
//
//    @Test
//    public void testFindByAuthor() {
//        // Save the test book
//        bookDAO.save(testBook);
//
//        List<Book> books = bookDAO.findByAuthor("Test Author");
//        assertNotNull("Book list should not be null", books);
//        assertTrue("Should find at least one book", books.size() > 0);
//
//        boolean found = books.stream()
//                .anyMatch(book -> testBook.getIsbn().equals(book.getIsbn()));
//        assertTrue("Test book should be found by author", found);
//    }
//
//    @Test
//    public void testUpdateQuantity() {
//        // Save the test book
//        bookDAO.save(testBook);
//
//        // Update quantity
//        boolean updated = bookDAO.updateQuantity(testBook.getIsbn(), 15);
//        assertTrue("Update quantity should return true", updated);
//
//        // Verify the quantity was updated
//        Optional<Book> updatedBook = bookDAO.findByIsbn(testBook.getIsbn());
//        assertTrue("Book should still exist", updatedBook.isPresent());
//        assertEquals("Quantity should be updated", Integer.valueOf(15), updatedBook.get().getQuantity());
//    }
//
//    @Test
//    public void testFindLowStockBooks() {
//        // Create a low stock book
//        Book lowStockBook = new Book();
//        lowStockBook.setIsbn("9781234567891");
//        lowStockBook.setTitle("Low Stock Book");
//        lowStockBook.setAuthor("Test Author");
//        lowStockBook.setPublisher("Test Publisher");
//        lowStockBook.setPrice(new BigDecimal("19.99"));
//        lowStockBook.setQuantity(2); // Below min stock level
//        lowStockBook.setMinStockLevel(5);
//        lowStockBook.setActive(true);
//
//        try {
//            // Save the low stock book
//            bookDAO.save(lowStockBook);
//
//            List<Book> lowStockBooks = bookDAO.findLowStockBooks();
//            assertNotNull("Low stock books list should not be null", lowStockBooks);
//
//            boolean found = lowStockBooks.stream()
//                    .anyMatch(book -> lowStockBook.getIsbn().equals(book.getIsbn()));
//            assertTrue("Low stock book should be found", found);
//
//        } finally {
//            // Clean up
//            bookDAO.deleteByIsbn(lowStockBook.getIsbn());
//        }
//    }
//}