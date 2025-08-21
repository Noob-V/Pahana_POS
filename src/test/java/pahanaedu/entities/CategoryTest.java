package pahanaedu.entities;

import com.pahanaedu.entities.Category;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(category);
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "Fiction";
        String description = "Fiction books category";

        Category testCategory = new Category(name, description);

        assertEquals(name, testCategory.getName());
        assertEquals(description, testCategory.getDescription());
        assertTrue(testCategory.isActive());
        assertNotNull(testCategory.getCreatedDate());
    }

    @Test
    public void testIdGetterSetter() {
        Integer id = 1;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void testNameGetterSetter() {
        String name = "Science";
        category.setName(name);
        assertEquals(name, category.getName());
    }

    @Test
    public void testDescriptionGetterSetter() {
        String description = "Science books and journals";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    public void testActiveGetterSetter() {
        category.setActive(true);
        assertTrue(category.isActive());

        category.setActive(false);
        assertFalse(category.isActive());
    }

    @Test
    public void testCreatedDateGetterSetter() {
        LocalDateTime now = LocalDateTime.now();
        category.setCreatedDate(now);
        assertEquals(now, category.getCreatedDate());
    }

    @Test
    public void testToString() {
        category.setId(1);
        category.setName("Fiction");
        category.setDescription("Fiction books");

        String result = category.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Fiction"));
        assertTrue(result.contains("Fiction books"));
    }
}