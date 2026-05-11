package org.example.pages;

import org.example.DriverSingleton;
import org.example.pages.components.Subcategory;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Страница с продуктами (Products Page)
 * Содержит методы для поиска товаров, добавления в корзину,
 * просмотра деталей, фильтрации по категориям и получения информации о товарах.
 */
public class ProductsPage extends BasePage {

    private final By viewProductBtnLocator = By.xpath("//a[contains(@href, '/product_details/')]");
    private final By productNamesLocator = By.xpath("//div[@class='productinfo text-center']//p");
    private final By searchFieldLocator = By.xpath("//input[@id='search_product']");
    private final By searchButtonLocator = By.xpath("//button[@id='submit_search']");
    private final By addToCartBtnLocator = By.xpath("//div[@class='productinfo text-center']//a[text()='Add to cart']");
    private final By titleLocator = By.xpath("//h2[contains(@class,'title')]");

    /**
     * Переход на страницу деталей товара по индексу
     * @param productIndex индекс товара (0, 1, 2...)
     * @return ProductDetailsPage - страница деталей товара
     */
    @Step("Нажать кнопку 'View Product' для товара с индексом {productIndex}")
    public ProductDetailsPage clickViewProductByIndex(int productIndex) {
        List<WebElement> products = DriverSingleton.getDriver().findElements(viewProductBtnLocator);
        if (products.size() > productIndex) {
            products.get(productIndex).click();
        }
        return new ProductDetailsPage();
    }

    /**
     * Добавление товара в корзину по индексу
     * @param productIndex индекс товара (0, 1, 2...)
     */
    @Step("Добавить товар с индексом {productIndex} в корзину")
    public void clickAddToCartBtn(int productIndex) {
        List<WebElement> products = DriverSingleton.getDriver().findElements(addToCartBtnLocator);
        if (products.size() > productIndex) {
            products.get(productIndex).click();
        }
    }

    /**
     * Поиск товара по тексту с предварительной очисткой поля
     * @param searchText искомый текст
     */
    @Step("Выполнить поиск товара по тексту: '{searchText}'")
    public void searchWithClear(String searchText) {
        WebElement searchInput = DriverSingleton.getDriver().findElement(searchFieldLocator);
        searchInput.clear();
        searchInput.sendKeys(searchText);
        DriverSingleton.getDriver().findElement(searchButtonLocator).click();
    }

    /**
     * Получение списка всех названий продуктов на странице
     * @return список названий товаров
     */
    @Step("Получить список всех названий продуктов")
    public List<String> getAllProductNames() {
        List<WebElement> productElements = DriverSingleton.getDriver().findElements(productNamesLocator);
        List<String> names = new ArrayList<>();

        for (WebElement element : productElements) {
            names.add(element.getText());
        }
        return names;
    }

    /**
     * Проверка, что все продукты содержат искомый текст
     * @param searchText искомый текст
     * @return true если все товары содержат текст, false если есть несоответствия
     */
    @Step("Проверить, что все продукты содержат текст '{searchText}'")
    public boolean allProductsContainSearchText(String searchText) {
        List<String> productNames = getAllProductNames();

        for (String name : productNames) {
            if (!name.toLowerCase().contains(searchText.toLowerCase())) {
                System.out.println("❌ Продукт не содержит искомый текст: " + name);
                return false;
            }
        }
        System.out.println("✅ Все " + productNames.size() + " продуктов содержат '" + searchText + "'");
        return true;
    }

    /**
     * Получение количества найденных продуктов
     * @return количество продуктов
     */
    @Step("Получить количество продуктов на странице")
    public int getProductsCount() {
        return DriverSingleton.getDriver().findElements(productNamesLocator).size();
    }

    /**
     * Выбор подкатегории товаров (Women -> Dress, Men -> Tshirts и т.д.)
     * @param subcategory объект подкатегории (enum)
     */
    @Step("Выбрать подкатегорию: {subcategory.categoryName} -> {subcategory.subcategoryName}")
    public void selectSubcategory(Subcategory subcategory) {
        // Кликаем по категории (Women, Men, Kids)
        By categoryLocator = By.xpath("//a[contains(., '" + subcategory.getCategoryName() + "')]");
        click(categoryLocator);

        // Немного ждём, чтобы подкатегории успели раскрыться
        WaitHelper.waitForElementVisible(By.xpath("//div[@id='" + subcategory.getCategoryName() + "']"));

        // Кликаем по подкатегории (Dress, Tops, Saree и т.д.)
        By subcategoryLocator = By.xpath("//div[@id='" + subcategory.getCategoryName() + "']//a[contains(text(), '" + subcategory.getSubcategoryName() + "')]");
        click(subcategoryLocator);
    }

    /**
     * Получение локатора заголовка страницы
     * @return локатор titleLocator
     */
    @Step("Получить локатор заголовка страницы")
    public By getTitle() {
        return titleLocator;
    }

    /**
     * Проверка, что текущая страница является страницей категории
     * @return true если URL содержит "/category_products/", false если нет
     */
    @Step("Проверить, что текущая страница является страницей категории")
    public boolean isCategoryPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl != null && currentUrl.contains("/category_products/");
    }

}