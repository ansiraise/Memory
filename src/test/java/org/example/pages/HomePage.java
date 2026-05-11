package org.example.pages;

import org.example.DriverSingleton;
import org.example.pages.components.Subcategory;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

import java.util.List;

/**
 * Главная страница (Home Page)
 * Содержит методы для добавления товаров в корзину и навигации по категориям.
 */
public class HomePage extends BasePage {

    private final By addToCartBtnLocator = By.xpath("//div[contains(@class, 'productinfo')]//a[text()='Add to cart']");

    /**
     * Добавление товара в корзину по индексу
     * @param productIndex индекс товара на странице (0, 1, 2...)
     */
    @Step("Добавить товар в корзину (индекс: {productIndex})")
    public void clickAddToCartBtn(int productIndex) {
        List<WebElement> products = DriverSingleton.getDriver().findElements(addToCartBtnLocator);
        if (products.size() > productIndex) {
            products.get(productIndex).click();
        }
    }

    /**
     * Выбор подкатегории товаров (Women -> Dress, Men -> Tshirts и т.д.)
     * @param subcategory объект подкатегории (enum)
     * @return ProductsPage - страница с товарами выбранной подкатегории
     */
    @Step("Выбрать подкатегорию: {subcategory.categoryName} -> {subcategory.subcategoryName}")
    public ProductsPage selectSubcategory(Subcategory subcategory) {
        // Кликаем по категории (Women, Men, Kids)
        By categoryLocator = By.xpath("//a[contains(., '" + subcategory.getCategoryName() + "')]");
        click(categoryLocator);

        // Немного ждём, чтобы подкатегории успели раскрыться
        WaitHelper.waitForElementVisible(By.xpath("//div[@id='" + subcategory.getCategoryName() + "']"));

        // Кликаем по подкатегории (Dress, Tops, Saree и т.д.)
        By subcategoryLocator = By.xpath("//div[@id='" + subcategory.getCategoryName() + "']//a[contains(text(), '" + subcategory.getSubcategoryName() + "')]");
        click(subcategoryLocator);
        return new ProductsPage();
    }
}