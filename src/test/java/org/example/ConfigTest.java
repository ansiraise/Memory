package org.example;

import org.example.utils.ConfigManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    void testConfigLoaded() {
        assertTrue(ConfigManager.isLoaded(), "Configuration must be loaded");
        System.out.println("Base URL: " + ConfigManager.getBaseUrl());
        System.out.println("Environment: " + ConfigManager.getCurrentEnvironment());
    }
}
