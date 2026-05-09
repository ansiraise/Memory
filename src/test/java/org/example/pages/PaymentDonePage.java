package org.example.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница подтверждения оплаты (Payment Done)
 * Отображается после успешного оформления заказа.
 * Содержит методы для продолжения покупок и получения текста подтверждения.
 */
public class PaymentDonePage extends BasePage {

    private By continueBtn = By.xpath("//a[text()='Continue']");
    private By downloadInvoiceBtn = By.xpath("//a[text()='Download Invoice']");
    private By congratText = By.xpath("//p[text()='Congratulations! Your order has been confirmed!']");
    private By orderPlacedText = By.xpath("//b[text()='Order Placed!']");

    /**
     * Нажатие кнопки Continue после успешного заказа
     * @return HomePage - главная страница
     */
    @Step("Нажать кнопку Continue на странице подтверждения заказа")
    public HomePage clickContinue() {
        click(continueBtn);
        return new HomePage();
    }

    /**
     * Получение локатора текста поздравления с успешным заказом
     * @return локатор congratText
     */
    @Step("Получить локатор текста подтверждения заказа")
    public By getCongratText() {
        return congratText;
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
        click(downloadInvoiceBtn);
    }

    /**
     * Получение текста "Order Placed!" после успешного заказа
     * @return текст "Order Placed!"
     */
    @Step("Получить текст 'Order Placed!'")
    public String getOrderPlacedText() {
        return getText(orderPlacedText);
    }


}