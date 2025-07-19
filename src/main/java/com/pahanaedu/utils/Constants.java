package com.pahanaedu.utils;

public class Constants {
    private static final EnvironmentConfig env = EnvironmentConfig.getInstance();

    // Database
    public static final String DB_URL = env.getProperty("db.url",
            "jdbc:sqlserver://localhost:1433;databaseName=PahanaEdu;trustServerCertificate=true;encrypt=false");
    public static final String DB_USERNAME = env.getProperty("db.username", "sa");
    public static final String DB_PASSWORD = env.getProperty("db.password", "your_password");
    public static final String DB_DRIVER = env.getProperty("db.driver",
            "com.microsoft.sqlserver.jdbc.SQLServerDriver");

    // Connection Pool
    public static final int MAX_POOL_SIZE = 20;
    public static final int MIN_POOL_SIZE = 5;
    public static final int CONNECTION_TIMEOUT = 30000;

    // Google Books API
    public static final String GOOGLE_BOOKS_API_URL = env.getProperty("google.books.api.url",
            "https://www.googleapis.com/books/v1/volumes");
    public static final String GOOGLE_BOOKS_API_KEY = env.getProperty("google.books.api.key", "");

    // Application Settings
    public static final String APP_NAME = env.getProperty("app.name", "Pahana Edu Billing System");
    public static final String APP_VERSION = env.getProperty("app.version", "1.0.0");

    // Session Attributes
    public static final String SESSION_USER = "user";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final int SESSION_TIMEOUT = env.getIntProperty("session.timeout", 30);

    // User Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // Account Number Prefix
    public static final String ACCOUNT_PREFIX = env.getProperty("app.account.prefix", "PAH");

    // Bill ID Prefix
    public static final String BILL_PREFIX = env.getProperty("app.bill.prefix", "BILL");

    // Default Tax Rate (10%)
    public static final double DEFAULT_TAX_RATE = env.getProperty("app.default.tax.rate") != null
            ? Double.parseDouble(env.getProperty("app.default.tax.rate")) : 0.10;

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = env.getIntProperty("default.page.size", 10);

    private Constants() {
        // Utility class
    }
}