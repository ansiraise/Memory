package org.example.utils;
import io.qameta.allure.Step;
import org.example.DriverSingleton;

public class Navigation {
    @Step("Open Main page")
    public static void openMainPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl());
    }

    public static void openLoginPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl() + ConfigManager.getLoginPath());
    }

    public static void openContactUsPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl() + ConfigManager.getContactUsPath());
    }
}