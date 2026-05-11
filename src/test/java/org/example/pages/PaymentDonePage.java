package org.example.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница подтверждения оплаты (Payment Done)
 * Отображается после успешного оформления заказа.
 * Содержит методы для продолжения покупок и получения текста подтверждения.
 */
public class PaymentDonePage extends BasePage {

    private final By continueBtnLocator = By.xpath("//a[text()='Continue']");
    private final By downloadInvoiceBtnLocator = By.xpath("//a[text()='Download Invoice']");
    private final By orderConfirmedMsgLocator = By.xpath("//p[text()='Congratulations! Your order has been confirmed!']");
    private final By orderPlacedMsgLocator = By.xpath("//b[text()='Order Placed!']");

    /**
     * Нажатие кнопки Continue после успешного заказа
     * @return HomePage - главная страница
     */
    @Step("Нажать кнопку Continue на странице подтверждения заказа")
    public HomePage clickContinue() {
        click(continueBtnLocator);
        return new HomePage();
    }

    /**
     * Получение локатора текста поздравления с успешным заказом
     * @return локатор congratulationText
     */
    @Step("Получить локатор текста подтверждения заказа")
    public By getOrderConfirmedMsgLocator() {
        return orderConfirmedMsgLocator;
    }

    /**
     * Получение ожидаемого текста подтверждения заказа
     * @return текст "Congratulations! Your order has been confirmed!"
     */
    public String getOrderConfirmedText() {
        return "Congratulations! Your order has been confirmed!";
    }


    /**
     * Скачивание инвойса (счета) заказа
     * Нажимает кнопку Download Invoice
     */
    @Step("Скачать инвойс заказа")
    public void downloadInvoice() {
        click(downloadInvoiceBtnLocator);
    }

    /**
     * Получение текста "Order Placed!" после успешного заказа
     * @return текст "Order Placed!"
     */
    @Step("Получить текст 'Order Placed!'")
    public String getOrderPlacedMsgLocator() {
        return getText(orderPlacedMsgLocator);
    }


}