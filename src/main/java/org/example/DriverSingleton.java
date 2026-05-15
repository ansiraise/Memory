package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;

public class DriverSingleton {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverSingleton() {}

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();

            // Добавляем настройки браузера
            ChromeOptions options = new ChromeOptions();

            // Переключатель: true для headless, false для обычного режима
            boolean headlessMode = true;  // или false когда нужно видеть

            if (headlessMode) {
                options.addArguments("--headless=new");
                //options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
            }

            options.addArguments("--lang=en-US");                        // устанавливаем английский язык интерфейса
            //позиция и размер окна
            options.addArguments("--window-position=0,0");
            //options.addArguments("--start-maximized");                   // устанавливает размер окна
            //options.addArguments("--incognito");                         // режим инкогнито
            options.addArguments("--disable-save-password-bubble");      // для паролей
            options.addArguments("--disable-notifications");             // для уведомлений
            options.addArguments("--disable-autofill");                  // ОТКЛЮЧАЕТ АВТОЗАПОЛНЕНИЕ АДРЕСОВ!
            options.addArguments("--disable-autofill-keyboard-accessory-view"); // отключает автозаполнение
            options.addArguments("--disable-save-password-bubble");      // отключает сохранение паролей

            options.addArguments("--block-new-web-contents");             // блокирует новые окна
            options.addArguments("--disable-default-apps");              // блокируе дефолтные приложения браузера
            options.addArguments("--popup-blocking");                     // блокирует всплывающие окна

// Блокируем рекламу через настройки
            HashMap<String, Object> prefs = new HashMap<>();

            // Отключаем сохранение паролей
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

// Отключаем автозаполнение форм
            prefs.put("autofill.profile_enabled", false);
            prefs.put("autofill.credit_card_enabled", false);
            prefs.put("autofill.payment_method_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.default_content_setting_values.popups", 2);
            prefs.put("profile.default_content_setting_values.images", 2); // блокируем картинки
            options.setExperimentalOption("prefs", prefs);

// Запрещаем запуск внешних процессов
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

// Блокируем рекламные скрипты
            options.addArguments("--host-resolver-rules=MAP *ad* 127.0.0.1");
            options.addArguments("--host-resolver-rules=MAP *doubleclick* 127.0.0.1");
            options.addArguments("--host-resolver-rules=MAP *googlead* 127.0.0.1");

            WebDriver instance = new ChromeDriver(options);
            instance.manage().window().maximize();
            instance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Дополнительно можно установить размер через manage()
            //instance.manage().window().setSize(new Dimension(1920, 1080));

            driver.set(instance);
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
