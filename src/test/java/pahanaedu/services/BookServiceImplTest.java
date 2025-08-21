//package pahanaedu.services;
//
//import com.pahanaedu.entities.Book;
//import com.pahanaedu.dto.BookDTO;
//import com.pahanaedu.services.impl.BookServiceImpl;
//import com.pahanaedu.dao.BookDAO;
//import com.pahanaedu.mapper.BookMapper;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//public class BookServiceImplTest {
//
//    private BookServiceImpl bookService;
//    private Book testBook;
//
//    // Stub BookDAO
//    private class StubBookDAO implements BookDAO {
//        private Map<String, Book> books = new HashMap<>();
//
//        @Override
//        public List<Book> findByCategoryId(Integer categoryId) {
//            return new ArrayList<>(books.values());
//        }
//
//        @Override
//        public Optional<Book> findByIsbn(String isbn) {
//            return Optional.ofNullable(books.get(isbn));
//        }
//
//        @Override
//        public List<Book> findAll() {
//            return new ArrayList<>(books.values());
//        }
//
//        @Override
//        public List<Book> findByTitle(String title) {
//            return new ArrayList<>(books.values());
//        }
//
//        @Override
//        public List<Book> findByAuthor(String author) {
//            return new ArrayList<>(books.values());
//        }
//
//        @Override
//        public List<Book> findLowStockBooks() {
//            return new ArrayList<>();
//        }
//
//        @Override
//        public Book save(Book book) {
//            books.put(book.getIsbn(), book);
//            return book;
//        }
//
//        @Override
//        public Book update(Book book) {
//            books.put(book.getIsbn(), book);
//            return book;
//        }
//
//        @Override
//        public boolean deleteByIsbn(String isbn) {
//            return books.remove(isbn) != null;
//        }
//
//        @Override
//        public boolean updateQuantity(String isbn, Integer newQuantity) {
//            Book book = books.get(isbn);
//            if (book != null) {
//                book.setQuantity(newQuantity);
//                return true;
//            }
//            return false;
//        }
//    }
//
//    @Before
//    public void setUp() {
//        testBook = new Book();
//        testBook.setIsbn("1234567890");
//        testBook.setTitle("JUnit Book");
//        testBook.setPrice(BigDecimal.valueOf(10));
//        testBook.setQuantity(5);
//
//        // Use an anonymous subclass to inject stubs
//        bookService = new BookServiceImpl() {
//            private final StubBookDAO stubBookDAO = new StubBookDAO();
//
//            @Override
//            public Optional<Book> findByIsbn(String isbn) {
//                return stubBookDAO.findByIsbn(isbn);
//            }
//
//            @Override
//            public Book save(Book book) {
//                return stubBookDAO.save(book);
//            }
//
//            @Override
//            public boolean updateQuantity(String isbn, Integer newQuantity) {
//                if (newQuantity < 0) {
//                    throw new IllegalArgumentException("Quantity cannot be negative");
//                }
//                return stubBookDAO.updateQuantity(isbn, newQuantity);
//            }
//
//            @Override
//            public boolean deleteByIsbn(String isbn) {
//                return stubBookDAO.deleteByIsbn(isbn);
//            }
//        };
//        // Save test book
//        bookService.save(testBook);
//    }
//
//    @Test
//    public void testFindByIsbn() {
//        Optional<Book> found = bookService.findByIsbn("1234567890");
//        assertTrue(found.isPresent());
//        assertEquals("JUnit Book", found.get().getTitle());
//    }
//
//    @Test
//    public void testSaveDTO() {
//        BookDTO dto = BookMapper.toDTO(testBook);
//        BookDTO saved = bookService.saveDTO(dto);
//        assertNotNull(saved);
//        assertEquals("1234567890", saved.getIsbn());
//    }
//
//    @Test
//    public void testUpdateQuantity() {
//        boolean updated = bookService.updateQuantity("1234567890", 10);
//        assertTrue(updated);
//        Optional<Book> found = bookService.findByIsbn("1234567890");
//        assertEquals(Integer.valueOf(10), found.get().getQuantity());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testUpdateQuantityNegative() {
//        bookService.updateQuantity("1234567890", -1);
//    }
//
//    @Test
//    public void testDeleteByIsbn() {
//        boolean deleted = bookService.deleteByIsbn("1234567890");
//        assertTrue(deleted);
//        Optional<Book> found = bookService.findByIsbn("1234567890");
//        assertFalse(found.isPresent());
//    }
//}