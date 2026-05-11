package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By emailLoginFieldLocator = By.xpath("//input[@data-qa='login-email']");
    private final By passwordLoginFieldLocator = By.xpath("//input[@data-qa='login-password']");
    private final By nameSignupFieldLocator = By.xpath("//input[@data-qa='signup-name']");
    private final By emailSignupFieldLocator = By.xpath("//input[contains(@data-qa,'signup-email')]");
    private final By loginBtnLocator = By.xpath("//button[text()='Login']");
    private final By signupBtnLocator = By.xpath("//button[text()='Signup']");
    private final By errorCredentialsLocator = By.xpath("//p[text()='Your email or password is incorrect!']");
    private final By errorSignupLocator = By.xpath("//p[text()='Email Address already exist!']");



    // LoginPage.java
// Страница логина и регистрации

    /**
     * Ввод email в поле авторизации
     * @param email email пользователя
     */
    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        sendKeys(emailLoginFieldLocator, email);
    }

    /**
     * Ввод пароля в поле авторизации
     * @param password пароль пользователя
     */
    @Step("Ввести пароль")
    public void enterPassword(String password) {
        sendKeys(passwordLoginFieldLocator, password);
    }

    /**
     * Нажатие кнопки Login
     * @return HomePage - главная страница после успешного входа
     */
    @Step("Нажать кнопку Login")
    public HomePage clickLogin() {
        click(loginBtnLocator);
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
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return new HomePage();
    }

    /**
     * Ввод имени в поле регистрации
     * @param name имя нового пользователя
     */
    @Step("Ввести имя для регистрации: {name}")
    public void enterName(String name) {
        sendKeys(nameSignupFieldLocator, name);
    }

    /**
     * Ввод email в поле регистрации
     * @param email email нового пользователя
     */
    @Step("Ввести email для регистрации: {email}")
    public void enterEmailSignup(String email) {
        sendKeys(emailSignupFieldLocator, email);
    }

    /**
     * Нажатие кнопки Signup на странице логина
     * @return SignupPage - страница заполнения регистрационной формы
     */
    @Step("Нажать кнопку Signup")
    public SignupPage clickSignup() {
        click(signupBtnLocator);
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
        enterName(name);
        enterEmailSignup(email);
        return clickSignup();
    }

    /**
     * Получение локатора сообщения об ошибке авторизации
     * @return локатор errorCredentials
     */
    public By getErrorCredentials() {
        return errorCredentialsLocator;
    }

    /**
     * Получение локатора сообщения об ошибке регистрации
     * @return локатор errorSignup
     */
    public By getErrorSignup() {
        return errorSignupLocator;
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
