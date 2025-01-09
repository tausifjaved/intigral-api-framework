package org.intigral.assignment.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to manage application configuration by reading values from a properties file.
 */
public class ConfigManager {

    /**
     * Properties object to hold configuration key-value pairs.
     */
    private static Properties properties;

    // Static block to initialize and load the properties file at class loading time
    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }

    /**
     * Retrieves the value of a given key from the loaded properties file.
     *
     * @param key The key whose value needs to be retrieved.
     * @return The value associated with the key, or {@code null} if the key is not found.
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
