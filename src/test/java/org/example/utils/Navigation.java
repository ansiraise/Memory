package org.example.utils;
import io.qameta.allure.Step;
import org.example.DriverSingleton;

public class Navigation {
    @Step("Open Main page")
    public static void openMainPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl());
    }

    @Step("Open Login page")
    public static void openLoginPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl() + ConfigManager.getLoginPath());
    }

    @Step("Open ContactUs page")
    public static void openContactUsPage() {
        DriverSingleton.getDriver().get(ConfigManager.getBaseUrl() + ConfigManager.getContactUsPath());
    }
}