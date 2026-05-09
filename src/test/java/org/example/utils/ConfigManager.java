package org.example.utils;

import java.util.Properties;
import java.io.InputStream;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static String environment;

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
                // Если файл окружения не найден, пробуем загрузить dev как fallback
                System.err.println("❌ Файл не найден: " + configFile);
                System.err.println("⚠️ Пробуем загрузить dev.properties как fallback...");

                input = ConfigManager.class.getClassLoader()
                        .getResourceAsStream("config/dev.properties");

                if (input == null) {
                    // Если и dev не найден - это критическая ошибка
                    throw new RuntimeException(
                            "❌ НЕ НАЙДЕН НИ ОДИН ФАЙЛ КОНФИГУРАЦИИ!\n" +
                                    "Убедитесь, что файлы существуют в src/test/resources/config/\n" +
                                    "Необходим хотя бы dev.properties"
                    );
                }

                System.out.println("✅ Используем dev.properties как fallback");
            }

            properties.load(input);
            input.close();
            System.out.println("✅ Успешно загружено свойств: " + properties.size());

        } catch (Exception e) {
            throw new RuntimeException("Критическая ошибка загрузки конфигурации", e);
        }
    }

    // Общий метод для любого ключа
    public static String get(String key) {
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

    public static String getContactUsPath() {return get("pages.contacts"); }

    public static String getTestCasesPath() {return get("pages.testCases"); }

    public static String getAdminEmail() {
        return get("admin.email");
    }

    public static String getAdminLogin() { return get("admin.login"); }

    public static String getAdminPassword() {
        return get("admin.password");
    }

    public static String getCurrentEnvironment() {
        return environment;
    }

    // Для отладки - показать все загруженные свойства
    public static void printAllProperties() {
        System.out.println("=== Все свойства для окружения: " + environment + " ===");
        properties.forEach((key, value) ->
                System.out.println(key + " = " + value)
        );
    }
}