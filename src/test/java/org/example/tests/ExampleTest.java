package org.example.tests;

import org.example.DriverSingleton;
import org.example.model.TemporaryUser;
import org.example.pages.*;
import org.example.pages.components.ModalWindow;
import org.example.pages.components.Subcategory;
import org.example.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Регистрация и заказы")
@Feature("Оформление заказа")
@ExtendWith(AllureJunit5.class)
@ExtendWith(TestResultWatcher.class)

public class ExampleTest {

    private static final Logger log = LoggerFactory.getLogger(ExampleTest.class);

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
    @Story("Проверка окружения")
    public void testEnvironment() {
        System.out.println("=== Test started ===");
        System.out.println("Environment: " + ConfigManager.getCurrentEnvironment());
        System.out.println("Base URL: " + ConfigManager.getBaseUrl());
        System.out.println("Login path: " + ConfigManager.getLoginPath());
        System.out.println("Admin email: " + ConfigManager.getAdminEmail());
        System.out.println("Admin login: " + ConfigManager.getAdminLogin());
        System.out.println("=== Test finished ===");
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
        loginPage.login(TestData.INCORRECT_EMAIL, TestData.INCORRECT_PASSWORD);
        assertEquals(loginPage.getCredsIncorrectText(), loginPage.getText(loginPage.getErrorCredentials()));
        log.info("=== Test LoginIncorrectCredentials completed successfully ===");
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

    @Test
    public void ContactUsForm() {
        log.info("=== Test started: ContactUsForm ===");
        NavigationBar navigationBar = new NavigationBar();
        ContactUsPage contactUsPage = navigationBar.clickContactUsBtn();
        contactUsPage.enterName("any");
        contactUsPage.enterEmail("any@test.come");
        contactUsPage.enterSubject("any subject");
        contactUsPage.enterMessage("any text here");
        contactUsPage.uploadTestFile();
        contactUsPage.clickSubmitBtn();
        contactUsPage.acceptAlert();
        HomePage homePage = contactUsPage.clickHomeBtn();
        WaitHelper.waitForPageLoad();
        assertEquals(ConfigManager.getBaseUrl() + "/", homePage.getCurrentUrl());
        log.info("=== Test ContactUsForm completed successfully ===");
    }

    @Test
    public void ListTestCases() {
        log.info("=== Test started: ListTestCases ===");
        NavigationBar navigationBar = new NavigationBar();
        TestCasesPage testCases = navigationBar.clickTestCasesBtn();
        testCases.getListTestCases();
        assertEquals(27, testCases.size());
        log.info("=== Test ListTestCases completed successfully ===");
    }

    @Test
    public void ProductDetailPage() {
        log.info("=== Test started: ProductDetailPage ===");
        NavigationBar navigationBar = new NavigationBar();
        ProductsPage productsPage = navigationBar.clickProductsBtn();
        ProductDetailsPage productDetailsPage = productsPage.clickViewProductByIndex(0);
        productDetailsPage.isDisplayed(productDetailsPage.getProductName());
        assertTrue(productDetailsPage.isAllProductDetailsDisplayed());
        log.info("=== Test ProductDetailPage completed successfully ===");
    }

    @Test
    public void SearchProduct() {
        log.info("=== Test started: SearchProduct ===");
        NavigationBar navigationBar = new NavigationBar();
        ProductsPage productsPage = navigationBar.clickProductsBtn();
        String searchText = "jeans";
        productsPage.searchWithClear(searchText);
        assertTrue(productsPage.allProductsContainSearchText(searchText));
        log.info("=== Test SearchProduct completed successfully ===");
    }

    @Test
    @Story("Пользователь подписывается на рассылку")
    @Severity(SeverityLevel.MINOR)
    public void Subscription() {
        log.info("=== Test started: Subscription ===");
        log.info("Opening main page");
        NavigationBar navigationBar = new NavigationBar();
        log.info("Entering admin email");
        navigationBar.fillEmailSubscription(ConfigManager.getAdminEmail());
        log.info("Clicking subscribe button");
        navigationBar.clickSubscriptionBtn();

        String expected = navigationBar.getSuccessSubscribedText();
        String actual = navigationBar.getText(navigationBar.getAlertText());

        log.info("Checking subscription message. Expected: {}", expected);
        assertEquals(expected, actual);
        log.info("Subscription successful: {}", expected);
    }

    @Test
    public void SubscriptionInCart() {
        log.info("=== Test started: SubscriptionInCart ===");
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.clickCartBtn();
        navigationBar.fillEmailSubscription(ConfigManager.getAdminEmail());
        navigationBar.clickSubscriptionBtn();
        assertEquals(navigationBar.getSuccessSubscribedText(), navigationBar.getText(navigationBar.getAlertText()));
        log.info("=== Test SubscriptionInCart completed successfully ===");
    }

    @Test
    public void AddProductsInCart() {
        log.info("=== Test started: AddProductsInCart ===");
        NavigationBar navigationBar = new NavigationBar();
        ProductsPage productsPage = navigationBar.clickProductsBtn();
        productsPage.clickAddToCartBtn(0);
        ModalWindow modalWindow = new ModalWindow();
        modalWindow.clickContinueShopping();
        productsPage.clickAddToCartBtn(1);
        CartPage cartPage = modalWindow.clickViewCartBtn();
        assertTrue(cartPage.allPricesQuantityTotalsDisplayed());
        log.info("=== Test AddProductsInCart completed successfully ===");
    }

    @Test
    public void ProductQuantityInCart() {
        log.info("=== Test started: ProductQuantityInCart ===");
        NavigationBar navigationBar = new NavigationBar();
        ProductsPage productsPage = navigationBar.clickProductsBtn();
        ProductDetailsPage productDetailsPage = productsPage.clickViewProductByIndex(0);
        productDetailsPage.setQuantity(4);
        productDetailsPage.clickAddToCartBtn();
        ModalWindow modalWindow = new ModalWindow();
        CartPage cartPage = modalWindow.clickViewCartBtn();
        int quantity = cartPage.getQuantityByRowIndex(0);
        assertEquals(4, quantity);
        log.info("=== Test ProductQuantityInCart completed successfully ===");
    }

    @Test
    public void Test14_Order_Reg_Checkout() {
        log.info("=== Test started: Test14_Order_Reg_Checkout ===");
        HomePage homePage = new HomePage();
        homePage.clickAddToCartBtn(0);
        ModalWindow modalWindow = new ModalWindow();
        CartPage cartPage = modalWindow.clickViewCartBtn();
        cartPage.clickProceedToCheckoutBtn();
        LoginPage loginPage = modalWindow.clickRegisterLoginBtn();
        TemporaryUser user = TemporaryUser.createRandom();
        user.accountFields();
        SignupPage signupPage = loginPage.signup(user.getName(),user.getEmail());
        signupPage.fillRegistrationForm(user);
        AccountCreatedPage accountCreated = signupPage.clickCreateAccountBtn();
        assertEquals(accountCreated.getExpectedCreatedText(), accountCreated.getText(accountCreated.getAccountConfirm()));
        accountCreated.clickContinueBtn();
        NavigationBar navigationBar = new NavigationBar();
        String actualUserName = navigationBar.getLoggedInUserName();
        assertEquals(user.getName(), actualUserName);
        navigationBar.clickCartBtn();
        CheckOutPage checkOutPage = cartPage.clickProceedToCheckoutBtn();
        checkOutPage.addComment("any text");
        PaymentPage paymentPage = checkOutPage.clickPlaceOrderBtn();
        paymentPage.fillPaymentDetails(TestData.CARD_NAME, TestData.CARD_NUMBER, TestData.CARD_CVC,
                TestData.EXPIRATION_MONTH, TestData.EXPIRATION_YEAR);
        PaymentDonePage paymentDonePage = paymentPage.clickConfirmOrder();
        assertEquals("Congratulations! Your order has been confirmed!", paymentDonePage.getText(paymentDonePage.getOrderConfirmedMsgLocator()));
        homePage = paymentDonePage.clickContinue();
        DeleteAccountPage deleteAccount = navigationBar.clickDeleteAccountBtn();
        assertEquals(deleteAccount.getExpectedDeletedText(), deleteAccount.getText(deleteAccount.getAccountDeletedText()));
        deleteAccount.clickContinueBtn();
        assertEquals(ConfigManager.getBaseUrl() + "/", homePage.getCurrentUrl());
        log.info("=== Test Test14_Order_Reg_Checkout completed successfully ===");
    }

    @Test
    public void Test15_Reg_Order_Checkout() {
        log.info("=== Test started: Test15_Reg_Order_Checkout ===");
        NavigationBar navigationBar = new NavigationBar();
        LoginPage loginPage = navigationBar.clickSignupLoginBtn();
        TemporaryUser user = TemporaryUser.createRandom();
        user.accountFields();
        SignupPage signupPage = loginPage.signup(user.getName(),user.getEmail());
        signupPage.fillRegistrationForm(user);
        AccountCreatedPage accountCreated = signupPage.clickCreateAccountBtn();
        assertEquals(accountCreated.getExpectedCreatedText(), accountCreated.getText(accountCreated.getAccountConfirm()));
        HomePage homePage = accountCreated.clickContinueBtn();
        String actualUserName = navigationBar.getLoggedInUserName();
        assertEquals(user.getName(), actualUserName);
        homePage.clickAddToCartBtn(0);
        ModalWindow modalWindow = new ModalWindow();
        modalWindow.clickContinueShopping();
        CartPage cartPage = navigationBar.clickCartBtn();
        CheckOutPage checkOutPage = cartPage.clickProceedToCheckoutBtn();
        checkOutPage.addComment("any text");
        PaymentPage paymentPage = checkOutPage.clickPlaceOrderBtn();
        paymentPage.fillPaymentDetails(TestData.CARD_NAME, TestData.CARD_NUMBER, TestData.CARD_CVC,
                TestData.EXPIRATION_MONTH, TestData.EXPIRATION_YEAR);
        PaymentDonePage paymentDonePage = paymentPage.clickConfirmOrder();
        assertEquals(paymentDonePage.getOrderConfirmedText(), paymentDonePage.getText(paymentDonePage.getOrderConfirmedMsgLocator()));
        DeleteAccountPage deleteAccount = navigationBar.clickDeleteAccountBtn();
        assertEquals(deleteAccount.getExpectedDeletedText(), deleteAccount.getText(deleteAccount.getAccountDeletedText()));
        deleteAccount.clickContinueBtn();
        assertEquals(ConfigManager.getBaseUrl() + "/", homePage.getCurrentUrl());
        log.info("=== Test Test15_Reg_Order_Checkout completed successfully ===");
    }

    @Test
    public void Test17_Remove_Product() {
        log.info("=== Test started: Test17_Remove_Product ===");
        HomePage homePage = new HomePage();
        ModalWindow modalWindow = new ModalWindow();
        homePage.clickAddToCartBtn(0);
        CartPage cartPage = modalWindow.clickViewCartBtn();
        cartPage.deleteProductByRowIndex(0);
        assertEquals(cartPage.getExpectedCartIsEmpty(), cartPage.getText(cartPage.getEmptyCartMessage()));
        log.info("=== Test Test17_Remove_Product completed successfully ===");
    }

    @Test
    public void Test18_ViewCategory() {
        log.info("=== Test started: Test18_ViewCategory ===");
        HomePage homePage = new HomePage();
        ProductsPage productsPage = homePage.selectSubcategory(Subcategory.WOMEN_DRESS);
        // Сравниваем без учёта регистра
        String expectedTitle = Subcategory.WOMEN_DRESS.getExpectedTitle();
        assertTrue(expectedTitle.equalsIgnoreCase(productsPage.getText(productsPage.getTitle())));

        productsPage.selectSubcategory(Subcategory.MEN_TSHIRTS);
        String expectedNewTitle = Subcategory.MEN_TSHIRTS.getExpectedTitle();
        assertTrue(expectedNewTitle.equalsIgnoreCase(productsPage.getText(productsPage.getTitle())));
        // Проверяем, что мы на странице Категорий
        assertTrue(productsPage.isCategoryPage());
        log.info("=== Test Test18_ViewCategory completed successfully ===");
    }

}