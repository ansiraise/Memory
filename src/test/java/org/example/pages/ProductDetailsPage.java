package org.example.pages;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Страница деталей товара (Product Details Page)
 * Содержит методы для получения информации о товаре,
 * управления количеством и добавления в корзину.
 */
public class ProductDetailsPage extends BasePage {

    private By productName = By.xpath("//div[@class='product-information']/h2");
    private By productCategory = By.xpath("//p[contains(.,'Category:')]");
    private By productPrice = By.xpath("//div[@class='product-information']//span[contains(text(), 'Rs.')]");
    private By productAvailability = By.xpath("//p[contains(.,'Availability:')]");
    private By productCondition = By.xpath("//p[contains(.,'Condition:')]");
    private By productBrand = By.xpath("//p[contains(.,'Brand:')]");
    private By productQuantity = By.xpath("//input[@id='quantity']");
    private By addToCartBtn = By.xpath("//button[contains(normalize-space(), 'Add to cart')]"); // normalize-space() убирает лишние пробелы и переносы

    /**
     * Получение локатора названия товара
     * @return локатор productName
     */
    @Step("Получить локатор названия товара")
    public By getProductName() {
        return productName;
    }

    /**
     * Получение локатора категории товара
     * @return локатор productCategory
     */
    @Step("Получить локатор категории товара")
    public By getProductCategory() {
        return productCategory;
    }

    /**
     * Получение локатора цены товара
     * @return локатор productPrice
     */
    @Step("Получить локатор цены товара")
    public By getProductPrice() {
        return productPrice;
    }

    /**
     * Получение локатора информации о наличии товара
     * @return локатор productAvailability
     */
    @Step("Получить локатор информации о наличии товара")
    public By getProductAvailability() {
        return productAvailability;
    }

    /**
     * Получение локатора состояния товара (Condition)
     * @return локатор productCondition
     */
    @Step("Получить локатор состояния товара")
    public By getProductCondition() {
        return productCondition;
    }

    /**
     * Получение локатора бренда товара
     * @return локатор productBrand
     */
    @Step("Получить локатор бренда товара")
    public By getProductBrand() {
        return productBrand;
    }

    /**
     * Проверка, что все детали товара отображаются на странице
     * @return true если все элементы отображаются, false если нет
     */
    @Step("Проверить отображение всех деталей товара")
    public boolean isAllProductDetailsDisplayed() {
        return isDisplayed(productName) &&
                isDisplayed(productCategory) &&
                isDisplayed(productPrice) &&
                isDisplayed(productAvailability) &&
                isDisplayed(productCondition) &&
                isDisplayed(productBrand);
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
        String value = DriverSingleton.getDriver().findElement(productQuantity)
                .getAttribute("value");
        return Integer.parseInt(value);
    }

    /**
     * Установка конкретного количества товара
     * @param quantity желаемое количество
     */
    @Step("Установить количество товара: {quantity}")
    public void setQuantity(int quantity) {
        WebElement quantityInput = DriverSingleton.getDriver().findElement(productQuantity);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
    }

    /**
     * Нажатие кнопки добавления товара в корзину
     */
    @Step("Нажать кнопку 'Add to cart' на странице товара")
    public void clickAddToCartBtn() {
        click(addToCartBtn);
    }
}