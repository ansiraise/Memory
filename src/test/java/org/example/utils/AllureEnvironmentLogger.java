package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentLogger {

    private static final Logger log = LoggerFactory.getLogger(AllureEnvironmentLogger.class);

    public static void setEnvironmentProperties() {
        try {
            // 1. Создаём папку
            java.io.File resultsDir = new java.io.File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }

            // 2. Создаём файл allure.properties (если его нет)
            java.io.File propsFile = new java.io.File(resultsDir, "allure.properties");
            if (!propsFile.exists()) {
                try (java.io.FileWriter fw = new java.io.FileWriter(propsFile)) {
                    fw.write("allure.results.directory=target/allure-results\n");
                }
            }

            // 3. Создаём environment.properties
            Properties props = new Properties();
            props.setProperty("OS", System.getProperty("os.name"));
            props.setProperty("Java Version", System.getProperty("java.version"));
            props.setProperty("Environment", ConfigManager.getCurrentEnvironment());
            props.setProperty("Base URL", ConfigManager.getBaseUrl());
            props.setProperty("Browser", "Chrome");

            try (FileOutputStream out = new FileOutputStream(new java.io.File(resultsDir, "environment.properties"))) {
                props.store(out, "Allure Environment Properties");
                log.info("Файлы allure.properties и environment.properties успешно созданы");
            }
        } catch (IOException e) {
            log.error("Couldn't create files for Allure", e);
        }
    }
}