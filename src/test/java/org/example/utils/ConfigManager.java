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
            System.out.println("=== Загружаем окружение: " + environment + " ===");

            // Пробуем загрузить файл указанного окружения
            String configFile = "config/" + environment + ".properties";
            InputStream input = ConfigManager.class.getClassLoader()
                    .getResourceAsStream(configFile);

            if (input == null) {
                // Если файл окружения не найден, пробуем dev
                System.err.println("❌ Файл не найден: " + configFile);
                System.err.println("⚠️ Пробуем загрузить config/dev.properties...");

                input = ConfigManager.class.getClassLoader()
                        .getResourceAsStream("config/dev.properties");

                if (input == null) {
                    // Последняя попытка — искать в корне
                    System.err.println("⚠️ Пробуем загрузить dev.properties из корня...");
                    input = ConfigManager.class.getClassLoader()
                            .getResourceAsStream("dev.properties");
                }

                if (input == null) {
                    System.err.println("❌ НЕ НАЙДЕН НИ ОДИН ФАЙЛ КОНФИГУРАЦИИ!");
                    System.err.println("=== Содержимое classpath ===");
                    // Выводим, что есть в classpath
                    java.io.InputStream classpathCheck = ConfigManager.class.getClassLoader()
                            .getResourceAsStream(".");
                    if (classpathCheck == null) {
                        System.err.println("classpath пуст или недоступен");
                    }
                    isLoaded = false;
                    return;
                }

                System.out.println("✅ Используем dev.properties как fallback");
            }

            properties.load(input);
            input.close();
            System.out.println("✅ Успешно загружено свойств: " + properties.size());
            isLoaded = true;

        } catch (IOException e) {
            System.err.println("❌ Ошибка при загрузке конфигурации: " + e.getMessage());
            isLoaded = false;
        } catch (Exception e) {
            System.err.println("❌ Неожиданная ошибка: " + e.getMessage());
            isLoaded = false;
        }
    }

    // Общий метод для любого ключа
    public static String get(String key) {
        if (!isLoaded) {
            throw new RuntimeException(
                    "❌ ConfigManager не загружен! Проверьте наличие файлов конфигурации в src/test/resources/config/"
            );
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(
                    "❌ Ключ '" + key + "' не найден в конфигурации для окружения: " + environment
            );
        }
        return value;
    }

    // Удобные методы для конкретных настроек
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

    // Для отладки - показать все загруженные свойства
    public static void printAllProperties() {
        if (!isLoaded) {
            System.err.println("Конфигурация не загружена, вывод невозможен");
            return;
        }
        System.out.println("=== Все свойства для окружения: " + environment + " ===");
        properties.forEach((key, value) ->
                System.out.println(key + " = " + value)
        );
    }
}