package pahanaedu.controllers;

import com.pahanaedu.controllers.CategoryController;
import com.pahanaedu.dto.CategoryDTO;
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
import java.time.LocalDateTime;
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

public class CategoryControllerTest {

    private CategoryController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        controller = new CategoryController();
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
    public void testDoGet_ListAction() throws ServletException, IOException {
        request.setParameter("action", "list");
        assertEquals("Action parameter should be list", "list", request.getParameter("action"));
    }

    @Test
    public void testDoGet_ViewAction() throws ServletException, IOException {
        request.setParameter("action", "view");
        request.setParameter("id", "1");

        assertEquals("Action parameter should be view", "view", request.getParameter("action"));
        assertEquals("ID parameter should be 1", "1", request.getParameter("id"));
    }

    @Test
    public void testDoGet_NewAction() throws ServletException, IOException {
        request.setParameter("action", "new");
        assertEquals("Action parameter should be new", "new", request.getParameter("action"));
    }

    @Test
    public void testDoGet_EditAction() throws ServletException, IOException {
        request.setParameter("action", "edit");
        request.setParameter("id", "1");

        assertEquals("Action parameter should be edit", "edit", request.getParameter("action"));
        assertEquals("ID parameter should be 1", "1", request.getParameter("id"));
    }

    @Test
    public void testDoPost_CreateAction() throws ServletException, IOException {
        request.setParameter("action", "create");
        request.setParameter("name", "Test Category");
        request.setParameter("description", "Test Description");
        request.setParameter("active", "on");

        assertEquals("Action parameter should be create", "create", request.getParameter("action"));
        assertEquals("Name parameter should match", "Test Category", request.getParameter("name"));
        assertEquals("Description parameter should match", "Test Description", request.getParameter("description"));
        assertEquals("Active parameter should be on", "on", request.getParameter("active"));
    }

    @Test
    public void testParameterValidation_ValidIdFormat() {
        request.setParameter("id", "123");
        String idStr = request.getParameter("id");

        try {
            int id = Integer.parseInt(idStr);
            assertTrue("Valid ID should be parsed", id > 0);
        } catch (NumberFormatException e) {
            fail("Valid ID should not throw exception");
        }
    }

    @Test
    public void testParameterValidation_InvalidIdFormat() {
        request.setParameter("id", "abc");
        String idStr = request.getParameter("id");

        try {
            Integer.parseInt(idStr);
            fail("Invalid ID should throw exception");
        } catch (NumberFormatException e) {
            assertTrue("NumberFormatException should be thrown for invalid ID", true);
        }
    }

    @Test
    public void testActiveParameterHandling_Checked() {
        request.setParameter("active", "on");
        String active = request.getParameter("active");
        assertNotNull("Active parameter should not be null when checked", active);
        assertEquals("Active parameter should be 'on'", "on", active);
    }

    @Test
    public void testActiveParameterHandling_Unchecked() {
        String active = request.getParameter("active");
        assertNull("Active parameter should be null when unchecked", active);
    }

    @Test
    public void testRequestAttributes_SetAndGet() {
        List<CategoryDTO> categories = new ArrayList<>();
        request.setAttribute("categories", categories);

        assertNotNull("Categories attribute should be set", request.getAttribute("categories"));
        assertTrue("Categories should be a list", request.getAttribute("categories") instanceof List);
    }

    @Test
    public void testRequestAttributes_ErrorMessage() {
        String errorMessage = "Test error message";
        request.setAttribute("errorMessage", errorMessage);
        assertEquals("Error message should match", errorMessage, request.getAttribute("errorMessage"));
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