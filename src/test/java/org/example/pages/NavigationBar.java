package org.example.pages;

import io.qameta.allure.Step;
import org.example.DriverSingleton;
import org.openqa.selenium.By;

/**
 * Панель навигации (Navigation Bar)
 * Содержит методы для навигации по основным разделам сайта:
 * главная страница, продукты, корзина, логин/регистрация,
 * контакты, тест-кейсы, удаление аккаунта, подписка.
 */
public class NavigationBar extends BasePage {

    private By homeBtn = By.xpath("//a[contains(.,'Home')]");
    private By productsBtn = By.xpath("//a[contains(.,'Products')]");
    private By cartBtn = By.xpath("//a[@href='/view_cart']");
    private By signupLoginBtn = By.xpath("//a[contains(.,'Signup / Login')]");
    private By contactUsBtn = By.xpath("//a[@href='/contact_us']");
    private By logoutBtn = By.xpath("//a[@href='/logout']");
    private By deleteAccountBtn = By.xpath("//a[@href='/delete_account']");
    private By loggedInAsElement = By.xpath("//a[contains(text(), 'Logged in as')]");
    private By testCasesBtn = By.xpath("//a[@href='/test_cases']");
    private By subscriptionBtn = By.xpath("//button[@id='subscribe']");
    private By subscriptionEmailField = By.xpath("//input[@id='susbscribe_email']");
    private By alertSubscription = By.xpath("//div[@class='alert-success alert']");

    /**
     * Клик по кнопке Signup / Login
     * @return LoginPage - страница входа/регистрации
     */
    @Step("Нажать кнопку 'Signup / Login'")
    public LoginPage clickSignupLoginBtn() {
        click(signupLoginBtn);
        return new LoginPage();
    }

    /**
     * Клик по кнопке корзины (Cart)
     * @return CartPage - страница корзины
     */
    @Step("Нажать кнопку 'Cart'")
    public CartPage clickCartBtn() {
        click(cartBtn);
        return new CartPage();
    }

    /**
     * Клик по кнопке удаления аккаунта
     * @return DeleteAccountPage - страница подтверждения удаления
     */
    @Step("Нажать кнопку 'Delete Account'")
    public DeleteAccountPage clickDeleteAccountBtn() {
        click(deleteAccountBtn);
        return new DeleteAccountPage();
    }

    /**
     * Клик по кнопке Contact Us
     * @return ContactUsPage - страница обратной связи
     */
    @Step("Нажать кнопку 'Contact Us'")
    public ContactUsPage clickContactUsBtn() {
        click(contactUsBtn);
        return new ContactUsPage();
    }

    /**
     * Получение имени залогиненного пользователя
     * @return имя пользователя
     */
    @Step("Получить имя залогиненного пользователя")
    public String getLoggedInUserName() {
        // Ищем тег <b> внутри элемента с текстом "Logged in as"
        By userNameLocator = By.xpath("//a[contains(text(), 'Logged in as')]/b");
        return DriverSingleton.getDriver().findElement(userNameLocator).getText();
    }

    /**
     * Клик по кнопке Test Cases
     * @return TestCasesPage - страница с тест-кейсами
     */
    @Step("Нажать кнопку 'Test Cases'")
    public TestCasesPage clickTestCasesBtn() {
        click(testCasesBtn);
        return new TestCasesPage();
    }

    /**
     * Клик по кнопке Products
     * @return ProductsPage - страница с продуктами
     */
    @Step("Нажать кнопку 'Products'")
    public ProductsPage clickProductsBtn() {
        click(productsBtn);
        return new ProductsPage();
    }

    /**
     * Ввод email в поле подписки
     * @param email email для подписки
     */
    @Step("Ввести email в поле подписки: {email}")
    public void fillEmailSubscription(String email) {
        sendKeys(subscriptionEmailField, email);
    }

    /**
     * Клик по кнопке подписки
     */
    @Step("Нажать кнопку подписки")
    public void clickSubscriptionBtn() {
        click(subscriptionBtn);
    }

    /**
     * Получение локатора сообщения об успешной подписке
     * @return локатор alertSubscription
     */
    @Step("Получить локатор сообщения об успешной подписке")
    public By getAlertText() {
        return alertSubscription;
    }

    /**
     * Ожидаемый текст успешной подписки
     * @return текст "You have been successfully subscribed!"
     */
    public String getSuccessSubscribedText() {
        return "You have been successfully subscribed!";
    }
}