package org.example.pages;

import io.qameta.allure.Step;
import org.example.DriverSingleton;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private By emailLoginField = By.xpath("//input[@data-qa='login-email']");
    private By passwordLoginField = By.xpath("//input[@data-qa='login-password']");
    private By nameSignupField = By.xpath("//input[@data-qa='signup-name']");
    private By emailSignupField = By.xpath("//input[contains(@data-qa,'signup-email')]");
    private By loginBtn = By.xpath("//button[text()='Login']");
    private By signupBtn = By.xpath("//button[text()='Signup']");
    private By errorCredentials = By.xpath("//p[text()='Your email or password is incorrect!']");
    private By errorSignup = By.xpath("//p[text()='Email Address already exist!']");



    // LoginPage.java
// Страница логина и регистрации

    /**
     * Ввод email в поле авторизации
     * @param email email пользователя
     */
    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        sendKeys(emailLoginField, email);
    }

    /**
     * Ввод пароля в поле авторизации
     * @param password пароль пользователя
     */
    @Step("Ввести пароль")
    public void enterPassword(String password) {
        sendKeys(passwordLoginField, password);
    }

    /**
     * Нажатие кнопки Login
     * @return HomePage - главная страница после успешного входа
     */
    @Step("Нажать кнопку Login")
    public HomePage clickLogin() {
        click(loginBtn);
        WaitHelper.waitForPageLoad();
        return new HomePage();
    }

    /**
     * Выполнение авторизации с заполнением полей (комбинированный метод)
     * @param email email пользователя
     * @param password пароль пользователя
     * @return HomePage - главная страница после успешного входа
     */
    @Step("Выполнить логин с email: {email}")
    public HomePage login(String email, String password) {
        sendKeys(emailLoginField, email);
        sendKeys(passwordLoginField, password);
        return clickLogin();
    }

    /**
     * Ввод имени в поле регистрации
     * @param name имя нового пользователя
     */
    @Step("Ввести имя для регистрации: {name}")
    public void enterName(String name) {
        sendKeys(nameSignupField, name);
    }

    /**
     * Ввод email в поле регистрации
     * @param email email нового пользователя
     */
    @Step("Ввести email для регистрации: {email}")
    public void enterEmailSignup(String email) {
        sendKeys(emailSignupField, email);
    }

    /**
     * Нажатие кнопки Signup на странице логина
     * @return SignupPage - страница заполнения регистрационной формы
     */
    @Step("Нажать кнопку Signup")
    public SignupPage clickSignup() {
        click(signupBtn);
        return new SignupPage();
    }

    /**
     * Полная регистрация (заполнение полей и переход на страницу Signup)
     * @param name имя нового пользователя
     * @param email email нового пользователя
     * @return SignupPage - страница заполнения регистрационной формы
     */
    @Step("Заполнить форму регистрации (имя: {name}, email: {email})")
    public SignupPage signup(String name, String email) {
        sendKeys(nameSignupField, name);
        sendKeys(emailSignupField, email);
        return clickSignup();
    }

    /**
     * Получение локатора сообщения об ошибке авторизации
     * @return локатор errorCredentials
     */
    public By getErrorCredentials() {
        return errorCredentials;
    }

    /**
     * Получение локатора сообщения об ошибке регистрации
     * @return локатор errorSignup
     */
    public By getErrorSignup() {
        return errorSignup;
    }

    /**
     * Текст сообщения при неверных учетных данных
     * @return текст ошибки
     */
    public String getCredsIncorrectText() {
        return "Your email or password is incorrect!";
    }

    /**
     * Текст сообщения при попытке регистрации с существующим email
     * @return текст ошибки
     */
    public String getCredsExistText() {
        return "Email Address already exist!";
    }

}
