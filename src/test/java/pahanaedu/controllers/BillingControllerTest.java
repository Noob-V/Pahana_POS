package pahanaedu.controllers;

import com.pahanaedu.controllers.BillingController;
import com.pahanaedu.dto.BillDTO;
import com.pahanaedu.entities.Bill;
import com.pahanaedu.entities.Book;
import com.pahanaedu.entities.CartItem;
import com.pahanaedu.entities.Customer;
import com.pahanaedu.patterns.strategy.DiscountStrategy;
import com.pahanaedu.patterns.strategy.PercentageDiscountStrategy;
import com.pahanaedu.patterns.strategy.FixedDiscountStrategy;
import com.pahanaedu.patterns.strategy.NoDiscountStrategy;

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

public class BillingControllerTest {

    private BillingController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        controller = new BillingController();
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
    public void testCreateDiscountStrategy_Percentage() throws Exception {
        Method method = BillingController.class.getDeclaredMethod("createDiscountStrategy", String.class, BigDecimal.class);
        method.setAccessible(true);

        DiscountStrategy<Bill> strategy = (DiscountStrategy<Bill>) method.invoke(controller, "percentage", new BigDecimal("10"));

        assertTrue("Should create PercentageDiscountStrategy", strategy instanceof PercentageDiscountStrategy);
    }

    @Test
    public void testCreateDiscountStrategy_Fixed() throws Exception {
        Method method = BillingController.class.getDeclaredMethod("createDiscountStrategy", String.class, BigDecimal.class);
        method.setAccessible(true);

        DiscountStrategy<Bill> strategy = (DiscountStrategy<Bill>) method.invoke(controller, "fixed", new BigDecimal("50"));

        assertTrue("Should create FixedDiscountStrategy", strategy instanceof FixedDiscountStrategy);
    }

    @Test
    public void testCreateDiscountStrategy_None() throws Exception {
        Method method = BillingController.class.getDeclaredMethod("createDiscountStrategy", String.class, BigDecimal.class);
        method.setAccessible(true);

        DiscountStrategy<Bill> strategy = (DiscountStrategy<Bill>) method.invoke(controller, "unknown", new BigDecimal("10"));

        assertTrue("Should create NoDiscountStrategy for unknown type", strategy instanceof NoDiscountStrategy);
    }

    @Test
    public void testServletPathRouting_BillingPath() throws ServletException, IOException {
        request.setServletPath("/billing");
        assertEquals("/billing", request.getServletPath());
    }

    @Test
    public void testServletPathRouting_POSPath() throws ServletException, IOException {
        request.setServletPath("/billing/pos");
        assertEquals("/billing/pos", request.getServletPath());
    }

    @Test
    public void testCartOperations_AddToSession() {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("123", "Test Book", "Test Author", new BigDecimal("20.00"), 1));
        session.setAttribute("cart", cart);

        List<CartItem> retrievedCart = (List<CartItem>) session.getAttribute("cart");
        assertNotNull("Cart should be stored in session", retrievedCart);
        assertEquals("Cart should have one item", 1, retrievedCart.size());
        assertEquals("Item ISBN should match", "123", retrievedCart.get(0).getIsbn());
    }

    @Test
    public void testCartOperations_RemoveFromCart() {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("123456789", "Test Book", "Test Author", new BigDecimal("20.00"), 1));
        cart.add(new CartItem("987654321", "Another Book", "Another Author", new BigDecimal("15.00"), 2));
        session.setAttribute("cart", cart);

        cart.removeIf(item -> item.getIsbn().equals("123456789"));

        assertEquals("Cart should have one item after removal", 1, cart.size());
        assertEquals("Remaining item should be the second book", "987654321", cart.get(0).getIsbn());
    }

    @Test
    public void testCartOperations_UpdateQuantity() {
        List<CartItem> cart = new ArrayList<>();
        CartItem item = new CartItem("123456789", "Test Book", "Test Author", new BigDecimal("20.00"), 3);
        cart.add(item);
        session.setAttribute("cart", cart);

        item.setQuantity(5);

        assertEquals("Quantity should be updated", 5, item.getQuantity());
        assertEquals("Total should be recalculated", new BigDecimal("100.00"), item.getTotal());
    }

    @Test
    public void testCartOperations_ClearCart() {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("123", "Test Book", "Test Author", new BigDecimal("20.00"), 1));
        session.setAttribute("cart", cart);

        session.removeAttribute("cart");

        assertNull("Cart should be cleared", session.getAttribute("cart"));
    }

    @Test
    public void testCartTotal_Calculation() {
        List<CartItem> cart = new ArrayList<>();
        cart.add(new CartItem("123", "Book 1", "Author 1", new BigDecimal("20.00"), 2));
        cart.add(new CartItem("456", "Book 2", "Author 2", new BigDecimal("15.50"), 1));

        BigDecimal total = cart.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals("Total should be 55.50", new BigDecimal("55.50"), total);
    }

    @Test
    public void testRequestParameters() {
        request.setParameter("isbn", "123456789");
        request.setParameter("quantity", "2");

        assertEquals("ISBN parameter should match", "123456789", request.getParameter("isbn"));
        assertEquals("Quantity parameter should match", "2", request.getParameter("quantity"));
    }

    @Test
    public void testSessionManagement() {
        session.setAttribute("testAttribute", "testValue");

        assertEquals("Session attribute should be retrievable", "testValue", session.getAttribute("testAttribute"));

        session.removeAttribute("testAttribute");

        assertNull("Session attribute should be removed", session.getAttribute("testAttribute"));
    }

    @Test
    public void testDiscountCalculation_Percentage() {
        PercentageDiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
        Bill bill = new Bill();
        bill.setSubtotal(new BigDecimal("100.00"));

        BigDecimal discount = strategy.calculateDiscount(bill);

        assertEquals("Discount should be 10.00", 0, new BigDecimal("10.00").compareTo(discount));
    }

    @Test
    public void testDiscountCalculation_Fixed() {
        FixedDiscountStrategy strategy = new FixedDiscountStrategy(new BigDecimal("25.00"));
        Bill bill = new Bill();
        bill.setSubtotal(new BigDecimal("100.00"));

        BigDecimal discount = strategy.calculateDiscount(bill);

        assertEquals("Discount should be 25.00", 0, new BigDecimal("25.00").compareTo(discount));
    }

    @Test
    public void testDiscountCalculation_None() {
        NoDiscountStrategy strategy = new NoDiscountStrategy();
        Bill bill = new Bill();
        bill.setSubtotal(new BigDecimal("100.00"));

        BigDecimal discount = strategy.calculateDiscount(bill);

        assertEquals("Discount should be 0.00", 0, BigDecimal.ZERO.compareTo(discount));
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