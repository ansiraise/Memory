package org.example.utils;

import io.qameta.allure.Attachment;
import org.example.DriverSingleton;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private static final String SCREENSHOT_DIR = "E://IDEA Projects//Screenshots";

    /**
     * Основной метод: делает скриншот, сохраняет на диск и отправляет в Allure
     */
    public static void captureScreenshot(String testName) {
        WebDriver driver = DriverSingleton.getDriver();
        if (driver == null) {
            System.err.println("Драйвер не инициализирован, скриншот невозможен");
            return;
        }

        // 1. Сохраняем на диск (ваш текущий метод)
        takeScreenshotToDisk(driver, testName);

        // 2. Отправляем в Allure
        attachScreenshotToAllure(driver);
    }

    // Метод для Allure (возвращает byte[])
    @Attachment(value = "Screenshot on failure", type = "image/png")
    private static byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    // Вспомогательный метод для сохранения на диск
    private static String takeScreenshotToDisk(WebDriver driver, String testName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = String.format("%s_%s.png", testName, timestamp);

            Path directory = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            Path destination = directory.resolve(fileName);
            Files.copy(screenshot.toPath(), destination);

            System.out.println("✅ Скриншот сохранен на диск: " + destination.toAbsolutePath());
            return destination.toString();
        } catch (Exception e) {
            System.err.println("❌ Ошибка при сохранении на диск: " + e.getMessage());
            return null;
        }
    }

    /**
     * Удаляет старые скриншоты
     */
    public static void cleanOldScreenshots(int daysOld) throws IOException {
        Path dir = Paths.get(SCREENSHOT_DIR);
        if (Files.exists(dir)) {
            long cutoffTime = System.currentTimeMillis() - (daysOld * 24L * 60 * 60 * 1000);
            Files.list(dir)
                    .filter(path -> path.toString().endsWith(".png"))
                    .filter(path -> path.toFile().lastModified() < cutoffTime)
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Удален старый скриншот: " + path.getFileName());
                        } catch (IOException e) {
                            System.err.println("Не удалось удалить: " + path.getFileName());
                        }
                    });
        }
    }
}
