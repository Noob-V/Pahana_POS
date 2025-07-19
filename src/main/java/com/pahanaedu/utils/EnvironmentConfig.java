// src/main/java/com/pahanaedu/utils/EnvironmentConfig.java
package com.pahanaedu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentConfig {
    private static EnvironmentConfig instance;
    private Properties properties;

    private EnvironmentConfig() {
        loadProperties();
    }

    public static synchronized EnvironmentConfig getInstance() {
        if (instance == null) {
            instance = new EnvironmentConfig();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();

        // First try to load from application.properties in resources
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Could not load application.properties: " + e.getMessage());
        }

        // Override with system properties and environment variables
        for (String key : properties.stringPropertyNames()) {
            String envValue = System.getenv(key.toUpperCase().replace(".", "_"));
            if (envValue != null) {
                properties.setProperty(key, envValue);
            }

            String sysValue = System.getProperty(key);
            if (sysValue != null) {
                properties.setProperty(key, sysValue);
            }
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}