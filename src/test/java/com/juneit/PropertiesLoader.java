package com.juneit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public final Properties properties = getResource();
    public final String baseUrl = required("base-url");
    public final String username = required("username");
    public final String password = required("password");
    public final String testEmail = required("test-email");
    public final String testPassword = required("test-password");
    public final String testDisplayName = required("test-display-name");

    private Properties getResource() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (stream == null) {
                throw new IllegalStateException("Missing config.properties: copy src/test/resources/config-template.properties and fill in local test settings.");
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read config.properties", e);
        }
    }

    private String required(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank() || value.startsWith("YOUR_")
                || value.equals("https://training.example.com")) {
            throw new IllegalStateException("Set '" + key + "' in local config.properties before running UI tests.");
        }
        return value;
    }
}
