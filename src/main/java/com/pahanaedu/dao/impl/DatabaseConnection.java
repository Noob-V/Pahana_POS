package com.pahanaedu.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private HikariDataSource dataSource;
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("C:/Users/ASUS TUF/IdeaProjects/PahanaEdu-POS")
            .ignoreIfMissing()
            .load();

    private DatabaseConnection() {
        initializeDataSource();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void initializeDataSource() {
        try {
            // Get database configuration from environment variables
            String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String dbUrl = dotenv.get("DB_URL");
            String dbUsername = dotenv.get("DB_USERNAME");
            String dbPassword = dotenv.get("DB_PASSWORD", "");

            // Fix the truncated URL issue
            if (dbUrl == null || dbUrl.trim().isEmpty() || !dbUrl.contains("databaseName=")) {
                // Use fallback connection string since dotenv is truncating at semicolon
                dbUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=PahanaEdu;trustServerCertificate=true;encrypt=false";
                System.out.println("Using fallback DB_URL due to parsing issue");
            }

            // Debug output to verify values are loaded
            System.out.println("DEBUG - Current working directory: " + System.getProperty("user.dir"));
            System.out.println("DEBUG - DB_URL: '" + dbUrl + "'");
            System.out.println("DEBUG - DB_USERNAME: '" + dbUsername + "'");
            System.out.println("DEBUG - DB_PASSWORD: '" + (dbPassword != null && !dbPassword.isEmpty() ? "[SET]" : "[EMPTY]") + "'");

            // Validate required environment variables
            if (dbUrl == null || dbUrl.trim().isEmpty()) {
                throw new RuntimeException("DB_URL is missing or empty. Check your .env file at: C:/Users/ASUS TUF/IdeaProjects/PahanaEdu-POS/.env");
            }

            if (dbUsername == null || dbUsername.trim().isEmpty()) {
                throw new RuntimeException("DB_USERNAME is missing or empty. Check your .env file at: C:/Users/ASUS TUF/IdeaProjects/PahanaEdu-POS/.env");
            }

            // Load the database driver
            Class.forName(dbDriver);

            // Configure HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);

            // Connection pool settings
            config.setMaximumPoolSize(20);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);  // 30 seconds
            config.setIdleTimeout(600000);       // 10 minutes
            config.setMaxLifetime(1800000);      // 30 minutes

            // Connection validation
            config.setConnectionTestQuery("SELECT 1");
            config.setLeakDetectionThreshold(60000); // 1 minute

            // Create the data source
            this.dataSource = new HikariDataSource(config);

            System.out.println("Database connection pool initialized successfully");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        } catch (Exception e) {
            System.err.println("Failed to initialize database connection pool: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized");
        }
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            dataSource = null;
        }
    }

    public boolean isRunning() {
        return dataSource != null && !dataSource.isClosed();
    }

    public void printPoolStats() {
        if (dataSource != null) {
            System.out.println("Active Connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
            System.out.println("Idle Connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
            System.out.println("Total Connections: " + dataSource.getHikariPoolMXBean().getTotalConnections());
        }
    }
}