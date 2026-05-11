package org.example.pages;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

import java.util.List;

/**
 * Страница с тестовыми кейсами (Test Cases Page)
 * Содержит методы для получения списка тестовых сценариев.
 */
public class TestCasesPage {

    private final By testCasesHeadingLocator = By.xpath("//div[@class='panel-heading']");

    /**
     * Получение списка всех элементов тестовых кейсов на странице
     * @return список WebElement с заголовками тестовых кейсов
     */
    @Step("Получить список всех тестовых кейсов")
    public List<WebElement> getListTestCases() {
        return DriverSingleton.getDriver().findElements(testCasesHeadingLocator);
    }

    /**
     * Получение количества тестовых кейсов на странице
     * @return количество тестовых кейсов
     */
    @Step("Получить количество тестовых кейсов")
    public int size() {
        return getListTestCases().size();
    }
}