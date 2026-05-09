package org.example.pages;

import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

import java.time.Duration;

/**
 * Страница ввода платёжных данных (Payment Page)
 * Содержит методы для заполнения информации о карте и подтверждения заказа.
 */
public class PaymentPage extends BasePage {

    private By nameOfCardField = By.xpath("//input[@name='name_on_card']");
    private By cardNumberField = By.xpath("//input[@name='card_number']");
    private By cvcField = By.xpath("//input[@name='cvc']");
    private By expirationMonthField = By.xpath("//input[@name='expiry_month']");
    private By expiratonYearField = By.xpath("//input[@name='expiry_year']");
    private By confirmOrderBtn = By.xpath("//button[@id='submit']");
    private By successAlert = By.xpath("//div[@id='success_message']//*[contains(text(), 'Your order has been placed successfully!')]");

    /**
     * Ввод имени владельца карты
     * @param name имя на карте
     */
    @Step("Ввести имя владельца карты: {name}")
    public void enterCardName(String name) {
        sendKeys(nameOfCardField, name);
    }

    /**
     * Ввод номера карты
     * @param number номер карты
     */
    @Step("Ввести номер карты: {number}")
    public void enterCardNumber(String number) {
        sendKeys(cardNumberField, number);
    }

    /**
     * Ввод CVC/CVV кода карты
     * @param cvc код CVC
     */
    @Step("Ввести CVC код: {cvc}")
    public void enterCVC(String cvc) {
        sendKeys(cvcField, cvc);
    }

    /**
     * Ввод месяца истечения срока действия карты
     * @param month в формате MM
     */
    @Step("Ввести месяц истечения срока: {month}")
    public void enterMonth(String month) {
        sendKeys(expirationMonthField, month);
    }

    /**
     * Ввод года истечения срока действия карты
     * @param year в формате YYYY
     */
    @Step("Ввести год истечения срока: {year}")
    public void enterYear(String year) {
        sendKeys(expiratonYearField, year);
    }

    /**
     * Нажатие кнопки подтверждения заказа
     * @return PaymentDonePage - страница подтверждения
     */
    @Step("Нажать кнопку подтверждения заказа")
    public PaymentDonePage clickConfirmOrder() {
        click(confirmOrderBtn);
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