package org.example.pages;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Страница деталей товара (Product Details Page)
 * Содержит методы для получения информации о товаре,
 * управления количеством и добавления в корзину.
 */
public class ProductDetailsPage extends BasePage {

    private final By productNameLocator = By.xpath("//div[@class='product-information']/h2");
    private final By productCategoryLocator = By.xpath("//p[contains(.,'Category:')]");
    private final By productPriceLocator = By.xpath("//div[@class='product-information']//span[contains(text(), 'Rs.')]");
    private final By productAvailabilityLocator = By.xpath("//p[contains(.,'Availability:')]");
    private final By productConditionLocator = By.xpath("//p[contains(.,'Condition:')]");
    private final By productBrandLocator = By.xpath("//p[contains(.,'Brand:')]");
    private final By productQuantityLocator = By.xpath("//input[@id='quantity']");
    private final By addToCartBtnLocator = By.xpath("//button[contains(normalize-space(), 'Add to cart')]"); // normalize-space() убирает лишние пробелы и переносы

    /**
     * Получение локатора названия товара
     * @return локатор productName
     */
    @Step("Получить локатор названия товара")
    public By getProductName() {
        return productNameLocator;
    }

    /**
     * Получение локатора категории товара
     * @return локатор productCategory
     */
    @Step("Получить локатор категории товара")
    public By getProductCategory() {
        return productCategoryLocator;
    }

    /**
     * Получение локатора цены товара
     * @return локатор productPrice
     */
    @Step("Получить локатор цены товара")
    public By getProductPrice() {
        return productPriceLocator;
    }

    /**
     * Получение локатора информации о наличии товара
     * @return локатор productAvailability
     */
    @Step("Получить локатор информации о наличии товара")
    public By getProductAvailability() {
        return productAvailabilityLocator;
    }

    /**
     * Получение локатора состояния товара (Condition)
     * @return локатор productCondition
     */
    @Step("Получить локатор состояния товара")
    public By getProductCondition() {
        return productConditionLocator;
    }

    /**
     * Получение локатора бренда товара
     * @return локатор productBrand
     */
    @Step("Получить локатор бренда товара")
    public By getProductBrand() {
        return productBrandLocator;
    }

    /**
     * Проверка, что все детали товара отображаются на странице
     * @return true если все элементы отображаются, false если нет
     */
    @Step("Проверить отображение всех деталей товара")
    public boolean isAllProductDetailsDisplayed() {
        return isDisplayed(productNameLocator) &&
                isDisplayed(productCategoryLocator) &&
                isDisplayed(productPriceLocator) &&
                isDisplayed(productAvailabilityLocator) &&
                isDisplayed(productConditionLocator) &&
                isDisplayed(productBrandLocator);
    }

    /**
     * Получение числового значения цены (без "Rs. ")
     * @return цена как int
     */
    @Step("Получить числовое значение цены товара")
    public int getPriceValue() {
        String priceText = getText(getProductPrice());
        // Убираем "Rs. " и преобразуем в число
        return Integer.parseInt(priceText.replace("Rs. ", ""));
    }

    /**
     * Получение текущего значения счётчика количества
     * @return количество как int
     */
    @Step("Получить текущее количество товара")
    public int getCurrentQuantity() {
        String value = DriverSingleton.getDriver().findElement(productQuantityLocator)
                .getAttribute("value");
        return Integer.parseInt(value);
    }

    /**
     * Установка конкретного количества товара
     * @param quantity желаемое количество
     */
    @Step("Установить количество товара: {quantity}")
    public void setQuantity(int quantity) {
        WebElement quantityInput = DriverSingleton.getDriver().findElement(productQuantityLocator);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
    }

    /**
     * Нажатие кнопки добавления товара в корзину
     */
    @Step("Нажать кнопку 'Add to cart' на странице товара")
    public void clickAddToCartBtn() {
        click(addToCartBtnLocator);
    }
}