package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.DriverSingleton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class WaitHelper {

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int LONG_TIMEOUT = 20;
    private static final int SHORT_TIMEOUT = 3;

    private static WebDriver getDriver() {
        return DriverSingleton.getDriver();
    }

    private static WebDriverWait getWait(int seconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
    }

    // ----- Ожидания элементов -----

    public static WebElement waitForElementVisible(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverSingleton.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementVisible(By locator, int timeout) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementClickable(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementClickable(WebElement element) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForElementInvisible(By locator) {
        return getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ----- Действия с ожиданием -----

    public static void click(By locator) {
        waitForElementClickable(locator).click();
    }

    public static void click(WebElement element) {
        waitForElementClickable(element).click();
    }

    public static void sendKeys(By locator, String text) {
        waitForElementVisible(locator).sendKeys(text);
    }

    public static String getText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    public static boolean isDisplayed(By locator) {
        try {
            return getDriver().findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ----- Специальные ожидания -----

    public static void waitForPageLoad() {
        getWait(LONG_TIMEOUT).until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }

    public static void waitForAttribute(By locator, String attribute, String value) {
        getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    public static void waitForUrlContains(String text) {
        getWait(DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(text));
    }

    // ----- Для кастомных списков (как ты говорил) -----

    public static void waitForAnimationEnd(By dropdownLocator, String animationClass) {
        WebDriverWait wait = getWait(DEFAULT_TIMEOUT);
        wait.until(driver -> {
            WebElement element = driver.findElement(dropdownLocator);
            String classes = element.getAttribute("class");
            return !classes.contains(animationClass);
        });
    }

    public static WebElement waitForDropdownOption(By dropdownLocator, String optionText) {
        // Сначала открываем дропдаун
        click(dropdownLocator);

        // Ждем появления опции
        By optionLocator = By.xpath("//li[contains(text(),'" + optionText + "')]");
        return waitForElementClickable(optionLocator);
    }

    // В WaitHelper.java
    public static void dismissSaveAddressPopup() {
        try {
            WebDriver driver = DriverSingleton.getDriver();
            // Пробуем нажать "Нет, спасибо"
            WebElement noThanks = driver.findElement(By.xpath("//button[contains(text(), 'Нет, спасибо')]"));
            noThanks.click();
        } catch (Exception e) {
            // Попапа нет - игнорируем
        }
    }


    public static void dismissBrowserPopup() {
        try {
            Robot robot = new Robot();
            Thread.sleep(1000); // ждем появления попапа

            // Нажимаем Tab (переключение на кнопку "Нет, спасибо")
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);

            Thread.sleep(500);

            // Нажимаем Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            System.out.println("The pop-up is closed via Robot");
        } catch (Exception e) {
            System.out.println("Couldn't close the pop-up: " + e.getMessage());
        }
    }

}