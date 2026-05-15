package org.example.tests;

import org.example.DriverSingleton;
import org.example.model.TemporaryUser;
import org.example.pages.*;
import org.example.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("User Management")
@Feature("Login & Authentication")
@ExtendWith(AllureJunit5.class)
@ExtendWith(TestResultWatcher.class)

public class LoginTests {

    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);

    @BeforeAll
    static void setupAllureEnvironment() {
        AllureEnvironmentLogger.setEnvironmentProperties();
    }

    @BeforeEach
    void starting() {
        Navigation.openMainPage();
    }

    @AfterEach
    void tearDown() {
        DriverSingleton.quitDriver();
    }

    @Test
    public void LoginCorrectCredentials() {
        log.info("=== Test started: LoginCorrectCredentials ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.login(ConfigManager.getAdminEmail(), ConfigManager.getAdminPassword());
        String actualUserName = navigationBar.getLoggedInUserName();
        assertEquals(ConfigManager.getAdminLogin(), actualUserName);
        log.info("=== Test LoginCorrectCredentials completed successfully ===");
    }

    @Test
    public void LoginIncorrectCredentials() {
        log.info("=== Test started: LoginIncorrectCredentials ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.login(TestData.WRONG_EMAIL, TestData.MIN_PASSWORD_LENGTH);
        assertEquals(loginPage.getCredsIncorrectText(), loginPage.getText(loginPage.getErrorCredentials()));
        log.info("=== Test LoginIncorrectCredentials completed successfully ===");
    }

    @Test
    public void LoginWithEmptyEmailField() {
        log.info("=== Test started: LoginWithEmptyEmailField ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.enterEmail(TestData.EMPTY_EMAIL);
        loginPage.clickLogin();
        //Получаем текст подсказки прямо из DOM-свойства
        String validationMessage = loginPage.getEmailValidationMessage();
        assert validationMessage.equals("Please fill out this field.");
        log.info("=== Test LoginWithEmptyEmailField completed successfully ===");
    }

    @Test
    public void LoginWithSpacesOnlyInEmailField() {
        log.info("=== Test started: LoginWithSpacesOnlyInEmailField ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.enterEmail(TestData.SINGLE_SPACE);
        loginPage.clickLogin();
        //Получаем текст подсказки прямо из DOM-свойства
        String validationMessage = loginPage.getEmailValidationMessage();
        System.out.println(validationMessage);
        assert validationMessage.equals("Please fill out this field.");
        log.info("=== Test LoginWithSpacesOnlyInEmailField completed successfully ===");
    }

    @Test
    public void LoginWithInvalidEmailFormat() {
        log.info("=== Test started: LoginWithInvalidEmailFormat ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.enterEmail(TestData.INCORRECT_EMAIL);
        loginPage.clickLogin();
        String invalidEmail = TestData.INCORRECT_EMAIL;
        //Получаем текст подсказки прямо из DOM-свойства
        String validationMessage = loginPage.getEmailValidationMessage();
        // Динамическое сообщение '%s', чтобы не указывать значение из INCORRECT_PASSWORD
        String expectedMessage = String.format(
                "Please include an '@' in the email address. '%s' is missing an '@'.",
                invalidEmail);
        assert validationMessage.equals(expectedMessage);
        log.info("=== Test LoginWithInvalidEmailFormat completed successfully ===");
    }

    @Test
    public void RegistrationDeleteRandomUser() {
        log.info("=== Test started: RegistrationDeleteRandomUser ===");

        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();

        TemporaryUser user = TemporaryUser.createRandom();
        user.accountFields();
        log.info("Random user created: {} / {}", user.getEmail(), user.getPassword());

        SignupPage signupPage = loginPage.signup(user.getName(), user.getEmail());
        signupPage.fillRegistrationForm(user);
        AccountCreatedPage accountCreated = signupPage.clickCreateAccountBtn();

        log.info("Checking account creation success message");
        assertEquals(accountCreated.getExpectedCreatedText(), accountCreated.getText(accountCreated.getAccountConfirm()));

        HomePage homePage = accountCreated.clickContinueBtn();
        DeleteAccountPage deleteAccount = navigationBar.clickDeleteAccountBtn();

        log.info("Checking account deletion success message");
        assertEquals(deleteAccount.getExpectedDeletedText(), deleteAccount.getText(deleteAccount.getAccountDeletedText()));

        deleteAccount.clickContinueBtn();
        assertEquals(ConfigManager.getBaseUrl() + "/", homePage.getCurrentUrl());

        log.info("=== Test RegistrationDeleteRandomUser completed successfully ===");
    }

    @Test
    public void SignupWithExistingCredentials() {
        log.info("=== Test started: SignupWithExistingCredentials ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        loginPage.signup(ConfigManager.getAdminLogin(), ConfigManager.getAdminEmail());
        assertEquals(loginPage.getCredsExistText(), loginPage.getText(loginPage.getErrorSignup()));
        log.info("=== Test SignupWithExistingCredentials completed successfully ===");
    }


}
