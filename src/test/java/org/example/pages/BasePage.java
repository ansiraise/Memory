package org.example.pages;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.example.utils.WaitHelper;
import io.qameta.allure.Step;

/**
 * Абстрактный базовый класс для всех страниц.
 * Содержит универсальные методы взаимодействия с элементами.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;

    public BasePage() {
        this.driver = DriverSingleton.getDriver();
        this.waitHelper = new WaitHelper();
    }

    /**
     * Получение текста элемента по локатору
     * @param locator локатор элемента
     * @return текст элемента
     */
    @Step("Получить текст элемента: {locator}")
    public String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    /**
     * Ввод текста в элемент по локатору
     * @param locator локатор элемента
     * @param text вводимый текст
     */
    @Step("Ввести текст '{text}' в поле {locator}")
    protected void sendKeys(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    /**
     * Клик по элементу по локатору
     * @param locator локатор элемента
     */
    @Step("Кликнуть по элементу {locator}")
    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    /**
     * Получение аттрибута элемента
     * @param locator локатор элемента
     */
    @Step("Получить аттрибут элемента по {locator} и {attributeName}")
    public String getAttribute(By locator, String attributeName) {
        return WaitHelper.waitForElementVisible(locator).getAttribute(attributeName);
    }

    /**
     * Получение текущего URL страницы
     * @return текущий URL
     */
    @Step("Получить текущий URL страницы")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Клик по элементу с прокруткой (JavaScript)
     * Используется, когда обычный клик недоступен из-за перекрытия
     * @param locator локатор элемента
     */
    @Step("Скролл к элементу {locator} и клик (JavaScript)")
    protected void clickWithJs(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                element
        );
        element.click();
    }

    /**
     * Проверка, отображается ли элемент на странице
     * @param locator локатор элемента
     * @return true - если элемент отображается, false - если нет
     */
    @Step("Проверить отображение элемента {locator}")
    public boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}