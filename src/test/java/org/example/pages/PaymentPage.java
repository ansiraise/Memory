package org.example.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница ввода платёжных данных (Payment Page)
 * Содержит методы для заполнения информации о карте и подтверждения заказа.
 */
public class PaymentPage extends BasePage {

    private final By cardNameFieldLocator = By.xpath("//input[@name='name_on_card']");
    private final By cardNumberFieldLocator = By.xpath("//input[@name='card_number']");
    private final By cvcFieldLocator = By.xpath("//input[@name='cvc']");
    private final By expirationMonthFieldLocator = By.xpath("//input[@name='expiry_month']");
    private final By expirationYearFieldLocator = By.xpath("//input[@name='expiry_year']");
    private final By confirmOrderBtnLocator = By.xpath("//button[@id='submit']");
    private final By orderSuccessAlertLocator = By.xpath("//div[@id='success_message']//*[contains(text(), 'Your order has been placed successfully!')]");

    /**
     * Ввод имени владельца карты
     * @param name имя на карте
     */
    @Step("Ввести имя владельца карты: {name}")
    public void enterCardName(String name) {
        sendKeys(cardNameFieldLocator, name);
    }

    /**
     * Ввод номера карты
     * @param number номер карты
     */
    @Step("Ввести номер карты: {number}")
    public void enterCardNumber(String number) {
        sendKeys(cardNumberFieldLocator, number);
    }

    /**
     * Ввод CVC/CVV кода карты
     * @param cvc код CVC
     */
    @Step("Ввести CVC код: {cvc}")
    public void enterCVC(String cvc) {
        sendKeys(cvcFieldLocator, cvc);
    }

    /**
     * Ввод месяца, когда истечет срока действия карты
     * @param month в формате MM
     */
    @Step("Ввести месяц истечения срока: {month}")
    public void enterMonth(String month) {
        sendKeys(expirationMonthFieldLocator, month);
    }

    /**
     * Ввод года истечения срока действия карты
     * @param year в формате YYYY
     */
    @Step("Ввести год истечения срока: {year}")
    public void enterYear(String year) {
        sendKeys(expirationYearFieldLocator, year);
    }

    /**
     * Нажатие кнопки подтверждения заказа
     * @return PaymentDonePage - страница подтверждения
     */
    @Step("Нажать кнопку подтверждения заказа")
    public PaymentDonePage clickConfirmOrder() {
        click(confirmOrderBtnLocator);
        return new PaymentDonePage();
    }

    /**
     * Заполнение всех платёжных данных (комбинированный метод)
     * @param cardName имя владельца карты
     * @param cardNumber номер карты
     * @param cvc CVC код
     * @param month месяц истечения
     * @param year год истечения
     */
    @Step("Заполнить все платёжные данные")
    public void fillPaymentDetails(String cardName, String cardNumber,
                                   String cvc, String month, String year) {
        enterCardName(cardName);
        enterCardNumber(cardNumber);
        enterCVC(cvc);
        enterMonth(month);
        enterYear(year);
    }
}