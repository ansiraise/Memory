package org.example.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница оформления заказа (Checkout Page)
 * Содержит методы для добавления комментария к заказу и перехода к оплате.
 */
public class CheckOutPage extends BasePage {

    private final By placeOrderBtnLocator = By.xpath("//a[@href='/payment']");
    private final By commentAreaLocator = By.xpath("//textarea[@name='message']");

    /**
     * Добавление комментария к заказу
     * @param text текст комментария
     */
    @Step("Добавить комментарий к заказу: '{text}'")
    public void addComment(String text) {
        sendKeys(commentAreaLocator, text);
    }

    /**
     * Нажатие кнопки Place Order (переход к оплате)
     * @return страница оплаты PaymentPage
     */
    @Step("Нажать кнопку 'Place Order'")
    public PaymentPage clickPlaceOrderBtn() {
        click(placeOrderBtnLocator);
        return new PaymentPage();
    }
}