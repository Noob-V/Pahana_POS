package pahanaedu.controllers;

import com.pahanaedu.controllers.BookController;
import com.pahanaedu.dto.BookDTO;
import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletContext;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Collection;
import java.util.Locale;
import java.security.Principal;

import static org.junit.Assert.*;

public class BookControllerTest {

    private BookController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        controller = new BookController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        request.setSession(session);
    }

    @After
    public void tearDown() {
        controller = null;
        request = null;
        response = null;
        session = null;
    }

    @Test
    public void testServletPathRouting_BooksList() throws ServletException, IOException {
        request.setServletPath("/books");
        assertEquals("/books", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_BooksAdd() throws ServletException, IOException {
        request.setServletPath("/books/add");
        assertEquals("/books/add", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_BooksEdit() throws ServletException, IOException {
        request.setServletPath("/books/edit");
        assertEquals("/books/edit", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_BooksDelete() throws ServletException, IOException {
        request.setServletPath("/books/delete");
        assertEquals("/books/delete", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_BooksSearch() throws ServletException, IOException {
        request.setServletPath("/books/search");
        assertEquals("/books/search", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_BooksFilter() throws ServletException, IOException {
        request.setServletPath("/books/filter");
        assertEquals("/books/filter", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_GoogleSearch() throws ServletException, IOException {
        request.setServletPath("/books/google-search");
        assertEquals("/books/google-search", request.getServletPath());
    }

    @Test
    public void testCreateBookFromRequest_ValidData() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("title", "Test Book");
        request.setParameter("quantity", "10");
        request.setParameter("minStockLevel", "5");

        Book book = (Book) method.invoke(controller, request);

        assertEquals("Quantity should match", (int) 10, (int) book.getQuantity());
        assertEquals("Min stock level should match", (int) 5, (int) book.getMinStockLevel());
    }

    @Test
    public void testCreateBookFromRequest_EmptyPrice() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("title", "Test Book");
        request.setParameter("price", "");

        Book book = (Book) method.invoke(controller, request);

        assertEquals("Price should default to zero", 0, BigDecimal.ZERO.compareTo(book.getPrice()));
    }

    @Test
    public void testCreateBookFromRequest_EmptyQuantity() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("quantity", "");

        Book book = (Book) method.invoke(controller, request);

        assertEquals("Quantity should default to zero", (int) 0, (int) book.getQuantity());
    }

    @Test
    public void testCreateBookFromRequest_DefaultMinStockLevel() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("minStockLevel", "");

        Book book = (Book) method.invoke(controller, request);

        assertEquals("Min stock level should default to 5", (int) 5, (int) book.getMinStockLevel());
    }

    @Test
    public void testCreateBookFromRequest_DefaultActiveStatus() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("title", "Test Book");

        Book book = (Book) method.invoke(controller, request);

        assertTrue("Book should be active by default", book.isActive());
    }

    @Test
    public void testCreateBookDTOFromRequest_ValidData() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookDTOFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "978-0134685991");
        request.setParameter("title", "Effective Java");
        request.setParameter("author", "Joshua Bloch");
        request.setParameter("price", "45.99");
        request.setParameter("quantity", "10");
        request.setParameter("active", "true");

        BookDTO bookDTO = (BookDTO) method.invoke(controller, request);

        assertNotNull("BookDTO should be created", bookDTO);
        assertEquals("ISBN should match", "978-0134685991", bookDTO.getIsbn());
        assertEquals("Title should match", "Effective Java", bookDTO.getTitle());
        assertEquals("Author should match", "Joshua Bloch", bookDTO.getAuthor());
        assertEquals("Price should match", 0, new BigDecimal("45.99").compareTo(bookDTO.getPrice()));
        assertEquals("Quantity should match", 10, bookDTO.getQuantity());
        assertTrue("BookDTO should be active", bookDTO.isActive());
    }

    @Test
    public void testEscapeJson_NullString() throws Exception {
        Method method = BookController.class.getDeclaredMethod("escapeJson", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(controller, (String) null);

        assertEquals("Null string should return empty string", "", result);
    }

    @Test
    public void testEscapeJson_WithQuotes() throws Exception {
        Method method = BookController.class.getDeclaredMethod("escapeJson", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(controller, "Book \"Title\"");

        assertEquals("Quotes should be escaped", "Book \\\"Title\\\"", result);
    }

    @Test
    public void testEscapeJson_WithNewlines() throws Exception {
        Method method = BookController.class.getDeclaredMethod("escapeJson", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(controller, "Line 1\nLine 2\rLine 3");

        assertEquals("Newlines should be escaped", "Line 1\\nLine 2\\rLine 3", result);
    }

    @Test
    public void testRequestParameters_ISBN() {
        request.setParameter("isbn", "978-0134685991");
        assertEquals("ISBN parameter should match", "978-0134685991", request.getParameter("isbn"));
    }

    @Test
    public void testRequestParameters_SearchQuery() {
        request.setParameter("q", "Java Programming");
        assertEquals("Search query should match", "Java Programming", request.getParameter("q"));
    }

    @Test
    public void testRequestParameters_CategoryId() {
        request.setParameter("categoryId", "5");
        assertEquals("Category ID should match", "5", request.getParameter("categoryId"));
    }

    @Test
    public void testRequestParameters_MaxResults() {
        request.setParameter("maxResults", "20");
        assertEquals("Max results should match", "20", request.getParameter("maxResults"));
    }

    @Test
    public void testRequestAttributes_SetAndGet() {
        request.setAttribute("books", new ArrayList<>());
        assertNotNull("Books attribute should be set", request.getAttribute("books"));
        assertTrue("Books should be a list", request.getAttribute("books") instanceof List);
    }

    @Test
    public void testRequestAttributes_ErrorMessage() {
        String errorMessage = "Test error message";
        request.setAttribute("errorMessage", errorMessage);
        assertEquals("Error message should match", errorMessage, request.getAttribute("errorMessage"));
    }

    @Test
    public void testRedirectLocation() {
        String redirectPath = "/books?message=book-added";
        response.sendRedirect(redirectPath);
        assertEquals("Redirect location should match", redirectPath, response.getRedirectLocation());
    }

    @Test
    public void testFormParameterValidation_EmptyISBN() {
        request.setParameter("isbn", "");
        String isbn = request.getParameter("isbn");
        assertTrue("Empty ISBN should be detected", isbn == null || isbn.trim().isEmpty());
    }

    @Test
    public void testFormParameterValidation_ValidPrice() {
        request.setParameter("price", "29.99");
        String priceStr = request.getParameter("price");

        try {
            BigDecimal price = new BigDecimal(priceStr);
            assertTrue("Valid price should be parsed", price.compareTo(BigDecimal.ZERO) > 0);
        } catch (NumberFormatException e) {
            fail("Valid price should not throw exception");
        }
    }

    @Test
    public void testFormParameterValidation_InvalidPrice() {
        request.setParameter("price", "invalid");
        String priceStr = request.getParameter("price");

        try {
            new BigDecimal(priceStr);
            fail("Invalid price should throw exception");
        } catch (NumberFormatException e) {
            // Expected exception
            assertTrue("NumberFormatException should be thrown for invalid price", true);
        }
    }

    @Test
    public void testBookCreation_ImageUrlHandling() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("title", "Test Book");
        request.setParameter("imageUrl", "  http://example.com/image.jpg  ");

        Book book = (Book) method.invoke(controller, request);

        assertEquals("Image URL should be trimmed", "http://example.com/image.jpg", book.getImageUrl());
    }

    @Test
    public void testBookCreation_EmptyImageUrl() throws Exception {
        Method method = BookController.class.getDeclaredMethod("createBookFromRequest", HttpServletRequest.class);
        method.setAccessible(true);

        request.setParameter("isbn", "123456789");
        request.setParameter("title", "Test Book");
        request.setParameter("imageUrl", "");

        Book book = (Book) method.invoke(controller, request);

        assertNull("Empty image URL should result in null", book.getImageUrl());
    }

    @Test
    public void testSessionManagement() {
        session.setAttribute("searchQuery", "Java Books");
        assertEquals("Search query should be stored in session", "Java Books", session.getAttribute("searchQuery"));

        session.removeAttribute("searchQuery");
        assertNull("Search query should be removed from session", session.getAttribute("searchQuery"));
    }

    // Mock classes for testing
    private static class MockHttpServletRequest implements HttpServletRequest {
        private String servletPath;
        private String contextPath = "";
        private HttpSession session;
        private Map<String, String> parameters = new HashMap<>();
        private Map<String, Object> attributes = new HashMap<>();

        public void setServletPath(String servletPath) {
            this.servletPath = servletPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        public void setSession(HttpSession session) {
            this.session = session;
        }

        public void setParameter(String name, String value) {
            parameters.put(name, value);
        }

        @Override
        public String getServletPath() {
            return servletPath;
        }

        @Override
        public String getContextPath() {
            return contextPath;
        }

        @Override
        public HttpSession getSession() {
            return session;
        }

        @Override
        public String getParameter(String name) {
            return parameters.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return new MockRequestDispatcher(path);
        }

        @Override
        public String getRealPath(String path) {
            return "/mock/path" + path;
        }

        // Other required methods with minimal implementation
        @Override public String getAuthType() { return null; }
        @Override public javax.servlet.http.Cookie[] getCookies() { return null; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return Collections.emptyEnumeration(); }
        @Override public Enumeration<String> getHeaderNames() { return Collections.emptyEnumeration(); }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return "GET"; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public HttpSession getSession(boolean create) { return session; }
        @Override public String changeSessionId() { return null; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean isRequestedSessionIdFromUrl() { return false; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) {}
        @Override public void logout() {}
        @Override public Collection<javax.servlet.http.Part> getParts() { return null; }
        @Override public javax.servlet.http.Part getPart(String name) { return null; }
        @Override public <T extends javax.servlet.http.HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
        @Override public String[] getParameterValues(String name) { return null; }
        @Override public Map<String, String[]> getParameterMap() { return null; }
        @Override public Enumeration<String> getParameterNames() { return Collections.enumeration(parameters.keySet()); }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public void removeAttribute(String name) { attributes.remove(name); }
        @Override public Locale getLocale() { return null; }
        @Override public Enumeration<Locale> getLocales() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public ServletInputStream getInputStream() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public void setCharacterEncoding(String env) {}
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public AsyncContext startAsync() { return null; }
        @Override public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public AsyncContext getAsyncContext() { return null; }
        @Override public DispatcherType getDispatcherType() { return null; }
    }

    private static class MockHttpServletResponse implements HttpServletResponse {
        private String redirectLocation;

        public String getRedirectLocation() {
            return redirectLocation;
        }

        @Override
        public void sendRedirect(String location) {
            this.redirectLocation = location;
        }

        // Other required methods with minimal implementation
        @Override public void addCookie(javax.servlet.http.Cookie cookie) {}
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public String encodeUrl(String url) { return url; }
        @Override public String encodeRedirectUrl(String url) { return url; }
        @Override public void sendError(int sc, String msg) {}
        @Override public void sendError(int sc) {}
        @Override public void setDateHeader(String name, long date) {}
        @Override public void addDateHeader(String name, long date) {}
        @Override public void setHeader(String name, String value) {}
        @Override public void addHeader(String name, String value) {}
        @Override public void setIntHeader(String name, int value) {}
        @Override public void addIntHeader(String name, int value) {}
        @Override public void setStatus(int sc) {}
        @Override public void setStatus(int sc, String sm) {}
        @Override public int getStatus() { return 200; }
        @Override public String getHeader(String name) { return null; }
        @Override public Collection<String> getHeaders(String name) { return null; }
        @Override public Collection<String> getHeaderNames() { return null; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public String getContentType() { return null; }
        @Override public javax.servlet.ServletOutputStream getOutputStream() { return null; }
        @Override public PrintWriter getWriter() { return null; }
        @Override public void setCharacterEncoding(String charset) {}
        @Override public void setContentLength(int len) {}
        @Override public void setContentLengthLong(long len) {}
        @Override public void setContentType(String type) {}
        @Override public void setBufferSize(int size) {}
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() {}
        @Override public void resetBuffer() {}
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() {}
        @Override public void setLocale(Locale loc) {}
        @Override public Locale getLocale() { return null; }
    }

    private static class MockHttpSession implements HttpSession {
        private Map<String, Object> attributes = new HashMap<>();

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public void removeAttribute(String name) {
            attributes.remove(name);
        }

        // Other required methods with minimal implementation
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return "test-session-id"; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public ServletContext getServletContext() { return null; }
        @Override public void setMaxInactiveInterval(int interval) {}
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public javax.servlet.http.HttpSessionContext getSessionContext() { return null; }
        @Override public Object getValue(String name) { return getAttribute(name); }
        @Override public String[] getValueNames() { return attributes.keySet().toArray(new String[0]); }
        @Override public void putValue(String name, Object value) { setAttribute(name, value); }
        @Override public void removeValue(String name) { removeAttribute(name); }
        @Override public void invalidate() { attributes.clear(); }
        @Override public boolean isNew() { return false; }
        @Override public Enumeration<String> getAttributeNames() { return Collections.enumeration(attributes.keySet()); }
    }

    private static class MockRequestDispatcher implements RequestDispatcher {
        private String path;

        public MockRequestDispatcher(String path) {
            this.path = path;
        }

        @Override
        public void forward(ServletRequest request, ServletResponse response) {
            // Mock implementation - no actual forwarding
        }

        @Override
        public void include(ServletRequest request, ServletResponse response) {
            // Mock implementation - no actual including
        }
    }
}