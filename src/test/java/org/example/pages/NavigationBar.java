package org.example.pages;

import io.qameta.allure.Step;
import org.example.DriverSingleton;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

/**
 * Панель навигации (Navigation Bar)
 * Содержит методы для навигации по основным разделам сайта:
 * главная страница, продукты, корзина, логин/регистрация,
 * контакты, тест-кейсы, удаление аккаунта, подписка.
 */
public class NavigationBar extends BasePage {

    private final By homeBtnLocator = By.xpath("//a[contains(.,'Home')]");
    private final By productsBtnLocator = By.xpath("//a[contains(.,'Products')]");
    private final By cartBtnLocator = By.xpath("//a[@href='/view_cart']");
    private final By signupLoginBtnLocator = By.xpath("//a[@href='/login']");
    private final By contactUsBtnLocator = By.xpath("//a[@href='/contact_us']");
    private final By logoutBtnLocator = By.xpath("//a[@href='/logout']");
    private final By deleteAccountBtnLocator = By.xpath("//a[@href='/delete_account']");
    private final By loggedInAsLocator = By.xpath("//a[contains(text(), 'Logged in as')]");
    private final By testCasesBtnLocator = By.xpath("//a[@href='/test_cases']");
    private final By subscribeBtnLocator = By.xpath("//button[@id='subscribe']");
    private final By subscribeEmailFieldLocator = By.xpath("//input[@id='susbscribe_email']");
    private final By successAlertSubscriptionLocator = By.xpath("//div[@class='alert-success alert']");

    /**
     * Клик по кнопке Signup / Login
     * @return LoginPage - страница входа/регистрации
     */
    @Step("Нажать кнопку 'Signup / Login'")
    public LoginPage clickSignupLoginBtn() {
        WaitHelper.waitForElementVisible(signupLoginBtnLocator).click();
        return new LoginPage();
    }

    /**
     * Клик по кнопке корзины (Cart)
     * @return CartPage - страница корзины
     */
    @Step("Нажать кнопку 'Cart'")
    public CartPage clickCartBtn() {
        click(cartBtnLocator);
        return new CartPage();
    }

    /**
     * Клик по кнопке удаления аккаунта
     * @return DeleteAccountPage - страница подтверждения удаления
     */
    @Step("Нажать кнопку 'Delete Account'")
    public DeleteAccountPage clickDeleteAccountBtn() {
        click(deleteAccountBtnLocator);
        return new DeleteAccountPage();
    }

    /**
     * Клик по кнопке Contact Us
     * @return ContactUsPage - страница обратной связи
     */
    @Step("Нажать кнопку 'Contact Us'")
    public ContactUsPage clickContactUsBtn() {
        click(contactUsBtnLocator);
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
        click(testCasesBtnLocator);
        return new TestCasesPage();
    }

    /**
     * Клик по кнопке Products
     * @return ProductsPage - страница с продуктами
     */
    @Step("Нажать кнопку 'Products'")
    public ProductsPage clickProductsBtn() {
        click(productsBtnLocator);
        return new ProductsPage();
    }

    /**
     * Ввод email в поле подписки
     * @param email email для подписки
     */
    @Step("Ввести email в поле подписки: {email}")
    public void fillEmailSubscription(String email) {
        sendKeys(subscribeEmailFieldLocator, email);
    }

    /**
     * Клик по кнопке подписки
     */
    @Step("Нажать кнопку подписки")
    public void clickSubscriptionBtn() {
        click(subscribeBtnLocator);
    }

    /**
     * Получение локатора сообщения об успешной подписке
     * @return локатор alertSubscription
     */
    @Step("Получить локатор сообщения об успешной подписке")
    public By getAlertText() {
        return successAlertSubscriptionLocator;
    }

    /**
     * Ожидаемый текст успешной подписки
     * @return текст "You have been successfully subscribed!"
     */
    public String getSuccessSubscribedText() {
        return "You have been successfully subscribed!";
    }
}