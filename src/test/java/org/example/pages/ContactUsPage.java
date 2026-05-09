package org.example.pages;

import org.example.DriverSingleton;
import org.example.utils.TestData;
import org.example.utils.WaitHelper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Страница "Contact Us" (Связь с нами)
 * Содержит методы для заполнения формы обратной связи,
 * загрузки файла, отправки сообщения и работы с alert.
 */
public class ContactUsPage extends BasePage {

    private By nameField = By.xpath("//input[@data-qa='name']");
    private By emailField = By.xpath("//input[@data-qa='email']");
    private By subjectField = By.xpath("//input[@data-qa='subject']");
    private By messageField = By.xpath("//textarea[@id='message']");
    private By submitBtn = By.xpath("//input[@type='submit']");
    private By browseBtn = By.xpath("//input[@type='file']");
    private By allertSuccesText = By.xpath("//div[@class='status alert alert-success']");
    private By homeBtn = By.xpath("//span[contains(.,'Home')]");

    /**
     * Ввод имени в поле Name
     * @param name имя пользователя
     */
    @Step("Ввести имя: {name}")
    public void enterName(String name) {
        sendKeys(nameField, name);
    }

    /**
     * Ввод email в поле Email
     * @param email email пользователя
     */
    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        sendKeys(emailField, email);
    }

    /**
     * Ввод темы сообщения
     * @param subject тема сообщения
     */
    @Step("Ввести тему: {subject}")
    public void enterSubject(String subject) {
        sendKeys(subjectField, subject);
    }

    /**
     * Ввод текста сообщения
     * @param message текст сообщения
     */
    @Step("Ввести сообщение: {message}")
    public void enterMessage(String message) {
        sendKeys(messageField, message);
    }

    /**
     * Загрузка файла по указанному пути
     * @param filePath путь к файлу
     */
    @Step("Загрузить файл: {filePath}")
    public void uploadFile(String filePath) {
        WebElement fileInput = DriverSingleton.getDriver().findElement(browseBtn);
        fileInput.sendKeys(filePath);
    }

    /**
     * Загрузка тестового файла (путь из TestData.UPLOAD_FILE_PATH)
     */
    @Step("Загрузить тестовый файл")
    public void uploadTestFile() {
        uploadFile(TestData.UPLOAD_FILE_PATH);
    }

    /**
     * Нажатие кнопки Submit (отправка формы)
     */
    @Step("Нажать кнопку Submit")
    public void clickSubmitBtn() {
        clickWithJs(submitBtn);
    }

    /**
     * Принять alert (нажать OK)
     */
    @Step("Принять alert")
    public void acceptAlert() {
        Alert alert = DriverSingleton.getDriver().switchTo().alert();
        alert.accept();
    }

    /**
     * Отклонить alert (нажать Cancel)
     */
    @Step("Отклонить alert")
    public void dismissAlert() {
        Alert alert = DriverSingleton.getDriver().switchTo().alert();
        alert.dismiss();
    }

    /**
     * Нажатие кнопки Home (возврат на главную страницу)
     * @return HomePage
     */
    @Step("Нажать кнопку Home")
    public HomePage clickHomeBtn() {
        click(homeBtn);
        return new HomePage();
    }

    @Step("Получить текст сообщения об успешной отправке")
    public String getSuccessMessageText() {
        return getText(allertSuccesText);
    }
}