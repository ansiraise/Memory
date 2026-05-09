package org.example.pages;

import org.openqa.selenium.By;
import org.example.DriverSingleton;
import io.qameta.allure.Step;

/**
 * Страница оформления заказа (Checkout Page)
 * Содержит методы для добавления комментария к заказу и перехода к оплате.
 */
public class CheckOutPage extends BasePage {

    private By placeOrderBtn = By.xpath("//a[@href='/payment']");
    private By commentArea = By.xpath("//textarea[@name='message']");

    /**
     * Добавление комментария к заказу
     * @param text текст комментария
     */
    @Step("Добавить комментарий к заказу: '{text}'")
    public void addComment(String text) {
        sendKeys(commentArea, text);
    }

    /**
     * Нажатие кнопки Place Order (переход к оплате)
     * @return страница оплаты PaymentPage
     */
    @Step("Нажать кнопку 'Place Order'")
    public PaymentPage clickPlaceOrderBtn() {
        click(placeOrderBtn);
        return new PaymentPage();
    }
}