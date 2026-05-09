package org.example.tests;

import org.example.DriverSingleton;
import org.example.model.TemporaryUser;
import org.example.pages.*;
import org.example.pages.components.ModalWindow;
import org.example.pages.components.Subcategory;
import org.example.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Регистрация и заказы")
@Feature("Оформление заказа")
@ExtendWith(AllureJunit5.class)
@ExtendWith(TestResultWatcher.class)

public class PatternTest {

    private static final Logger log = LoggerFactory.getLogger(PatternTest.class);

    @BeforeAll
    static void setupAllureEnvironment() {
        AllureEnvironmentLogger.setEnvironmentProperties();
    }

    @BeforeEach
    void starting() {
        Navigation.openMainPage();
    }

    @AfterEach
    void tearDown() {
        DriverSingleton.quitDriver();
    }

    @Test
    @Story("Проверка окружения")
    public void testEnvironment() {
        System.out.println("=== Начало теста ===");
        System.out.println("Окружение: " + ConfigManager.getCurrentEnvironment());
        System.out.println("Base URL: " + ConfigManager.getBaseUrl());
        System.out.println("Login path: " + ConfigManager.getLoginPath());
        System.out.println("Admin email: " + ConfigManager.getAdminEmail());
        System.out.println("Admin login: " + ConfigManager.getAdminLogin());
        System.out.println("=== Конец теста ===");
    }

}