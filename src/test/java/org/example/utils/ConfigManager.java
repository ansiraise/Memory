package org.example.utils;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static String environment;
    private static boolean isLoaded = false;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            environment = System.getProperty("test.env", "dev");
            System.out.println("=== Loading environment: " + environment + " ===");

            // Try to load configuration file for the specified environment
            String configFile = "config/" + environment + ".properties";
            InputStream input = ConfigManager.class.getClassLoader()
                    .getResourceAsStream(configFile);

            if (input == null) {
                // Environment file not found, try dev as fallback
                System.err.println("❌ File not found: " + configFile);
                System.err.println("⚠️ Trying to load config/dev.properties as fallback...");

                input = ConfigManager.class.getClassLoader()
                        .getResourceAsStream("config/dev.properties");

                if (input == null) {
                    // Last attempt: try to find in root
                    System.err.println("⚠️ Trying to load dev.properties from root...");
                    input = ConfigManager.class.getClassLoader()
                            .getResourceAsStream("dev.properties");
                }

                if (input == null) {
                    System.err.println("❌ NO CONFIGURATION FILE FOUND!");
                    System.err.println("=== Classpath contents ===");
                    java.io.InputStream classpathCheck = ConfigManager.class.getClassLoader()
                            .getResourceAsStream(".");
                    if (classpathCheck == null) {
                        System.err.println("classpath is empty or inaccessible");
                    }
                    isLoaded = false;
                    return;
                }

                System.out.println("✅ Using dev.properties as fallback");
            }

            properties.load(input);
            input.close();
            System.out.println("✅ Successfully loaded properties: " + properties.size());
            isLoaded = true;

        } catch (IOException e) {
            System.err.println("❌ Error loading configuration: " + e.getMessage());
            isLoaded = false;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            isLoaded = false;
        }
    }

    // Common method for any key
    public static String get(String key) {
        if (!isLoaded) {
            throw new RuntimeException(
                    "❌ ConfigManager not loaded! Check configuration files in src/test/resources/config/"
            );
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    "❌ Key '" + key + "' not found in configuration for environment: " + environment
            );
        }
        return value;
    }

    // Convenience methods for specific settings
    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getLoginPath() {
        return get("pages.login");
    }

    public static String getProfilePath() {
        return get("pages.profile");
    }

    public static String getContactUsPath() {
        return get("pages.contacts");
    }

    public static String getTestCasesPath() {
        return get("pages.testCases");
    }

    public static String getAdminEmail() {
        return get("admin.email");
    }

    public static String getAdminLogin() {
        return get("admin.login");
    }

    public static String getAdminPassword() {
        return get("admin.password");
    }

    public static String getCurrentEnvironment() {
        return environment;
    }

    public static boolean isLoaded() {
        return isLoaded;
    }

    // For debugging - show all loaded properties
    public static void printAllProperties() {
        if (!isLoaded) {
            System.err.println("Configuration not loaded, cannot display");
            return;
        }
        System.out.println("=== All properties for environment: " + environment + " ===");
        properties.forEach((key, value) ->
                System.out.println(key + " = " + value)
        );
    }
}