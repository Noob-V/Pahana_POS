package pahanaedu.entities;

import com.pahanaedu.entities.Book;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(book);
    }

    @Test
    public void testParameterizedConstructor() {
        String isbn = "9781234567890";
        String title = "Test Book";
        String author = "Test Author";
        String publisher = "Test Publisher";
        BigDecimal price = new BigDecimal("29.99");
        Integer quantity = 10;

        Book testBook = new Book(isbn, title, author, publisher, price, quantity);

        assertEquals(isbn, testBook.getIsbn());
        assertEquals(title, testBook.getTitle());
        assertEquals(author, testBook.getAuthor());
        assertEquals(publisher, testBook.getPublisher());
        assertEquals(price, testBook.getPrice());
        assertEquals(quantity, testBook.getQuantity());
        assertEquals(Integer.valueOf(5), testBook.getMinStockLevel());
        assertTrue(testBook.isActive());
        assertNotNull(testBook.getCreatedDate());
        assertNotNull(testBook.getUpdatedDate());
    }

    @Test
    public void testIsbnGetterSetter() {
        String isbn = "9781234567890";
        book.setIsbn(isbn);
        assertEquals(isbn, book.getIsbn());
    }

    @Test
    public void testTitleGetterSetter() {
        String title = "Sample Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testPriceGetterSetter() {
        BigDecimal price = new BigDecimal("19.99");
        book.setPrice(price);
        assertEquals(price, book.getPrice());
    }

    @Test
    public void testQuantityGetterSetter() {
        Integer quantity = 15;
        book.setQuantity(quantity);
        assertEquals(quantity, book.getQuantity());
    }

    @Test
    public void testIsLowStock() {
        book.setMinStockLevel(10);
        book.setQuantity(5);
        assertTrue(book.isLowStock());

        book.setQuantity(15);
        assertFalse(book.isLowStock());

        book.setQuantity(10);
        assertTrue(book.isLowStock()); // Equal to min stock level
    }

    @Test
    public void testActiveGetterSetter() {
        book.setActive(true);
        assertTrue(book.isActive());

        book.setActive(false);
        assertFalse(book.isActive());
    }

    @Test
    public void testToString() {
        book.setIsbn("9781234567890");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(new BigDecimal("29.99"));
        book.setQuantity(10);

        String result = book.toString();
        assertTrue(result.contains("9781234567890"));
        assertTrue(result.contains("Test Book"));
        assertTrue(result.contains("Test Author"));
    }
}