package org.example.pages;

import org.example.DriverSingleton;
import org.example.model.TemporaryUser;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import io.qameta.allure.Step;

/**
 * Страница регистрации (Signup Page)
 * Содержит методы для заполнения всех полей регистрационной формы:
 * личная информация, адрес, контакты, выбор страны и даты рождения.
 */
public class SignupPage extends BasePage {

    private final By passwordFieldLocator = By.xpath("//input[@name='password']");
    private final By firstNameFieldLocator = By.xpath("//input[@name='first_name']");
    private final By lastNameFieldLocator = By.xpath("//input[@name='last_name']");
    private final By companyFieldLocator = By.xpath("//input[@name='company']");
    private final By address1FieldLocator = By.xpath("//input[@name='address1']");
    private final By address2FieldLocator = By.xpath("//input[@name='address2']");
    private final By countryDdLocator = By.xpath("//select[@name='country']");
    private final By stateFieldLocator = By.xpath("//input[@name='state']");
    private final By cityFieldLocator = By.xpath("//input[@name='city']");
    private final By zipcodeFieldLocator = By.xpath("//input[@name='zipcode']");
    private final By mobileNumberFieldLocator = By.xpath("//input[@name='mobile_number']");
    private final By newsletterCheckboxLocator = By.xpath("//input[@name='newsletter']");
    private final By specialOfferCheckboxLocator = By.xpath("//input[@name='optin']");
    private final By titleMrRadioBtnLocator = By.xpath("//input[@id='id_gender1']");
    private final By titleMrsRadioBtnLocator = By.xpath("//input[@id='id_gender2']");
    private final By daySelectLocator = By.xpath("//select[@name='days']");
    private final By monthSelectLocator = By.xpath("//select[@name='months']");
    private final By yearSelectLocator = By.xpath("//select[@name='years']");
    private final By createAccountBtnLocator = By.xpath("//button[text()='Create Account']");

    /**
     * Установка пароля
     * @param password пароль
     */
    @Step("Ввести пароль")
    public void setPassword(String password) {
        sendKeys(passwordFieldLocator, password);
    }

    /**
     * Установка имени
     * @param firstName имя
     */
    @Step("Ввести имя: {firstName}")
    public void setFirstName(String firstName) {
        sendKeys(firstNameFieldLocator, firstName);
    }

    /**
     * Установка фамилии
     * @param lastName фамилия
     */
    @Step("Ввести фамилию: {lastName}")
    public void setLastName(String lastName) {
        sendKeys(lastNameFieldLocator, lastName);
    }

    /**
     * Установка названия компании
     * @param company компания
     */
    @Step("Ввести название компании: {company}")
    public void setCompanyField(String company) {
        sendKeys(companyFieldLocator, company);
    }

    /**
     * Установка адреса (строка 1)
     * @param address1 адрес
     */
    @Step("Ввести адрес (строка 1): {address1}")
    public void setAddress1Field(String address1) {
        sendKeys(address1FieldLocator, address1);
    }

    /**
     * Установка адреса (строка 2)
     * @param address2 дополнительный адрес
     */
    @Step("Ввести адрес (строка 2): {address2}")
    public void setAddress2Field(String address2) {
        sendKeys(address2FieldLocator, address2);
    }

    /**
     * Установка штата/области
     * @param state штат
     */
    @Step("Ввести штат/область: {state}")
    public void setStateField(String state) {
        sendKeys(stateFieldLocator, state);
    }

    /**
     * Установка города
     * @param city город
     */
    @Step("Ввести город: {city}")
    public void setCityField(String city) {
        sendKeys(cityFieldLocator, city);
    }

    /**
     * Установка почтового индекса
     * @param zipcode почтовый индекс
     */
    @Step("Ввести почтовый индекс: {zipcode}")
    public void setZipcodeField(String zipcode) {
        sendKeys(zipcodeFieldLocator, zipcode);
    }

    /**
     * Установка номера мобильного телефона
     * @param mobile номер телефона
     */
    @Step("Ввести номер мобильного телефона: {mobile}")
    public void setMobileNumberField(String mobile) {
        sendKeys(mobileNumberFieldLocator, mobile);
    }

    /**
     * Выбор страны из выпадающего списка
     * @param country название страны
     */
    @Step("Выбрать страну: {country}")
    public void selectCountry(String country) {
        WebElement element = DriverSingleton.getDriver().findElement(countryDdLocator);
        ((JavascriptExecutor) DriverSingleton.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);

        Select select = new Select(element);
        select.selectByVisibleText(country);
    }

    /**
     * Выбор дня рождения
     * @param day день
     */
    @Step("Выбрать день рождения: {day}")
    public void selectDay(String day) {
        Select daySelect = new Select(DriverSingleton.getDriver().findElement(daySelectLocator));
        daySelect.selectByVisibleText(day);
    }

    /**
     * Выбор месяца рождения
     * @param month месяц
     */
    @Step("Выбрать месяц рождения: {month}")
    public void selectMonth(String month) {
        Select monthSelect = new Select(DriverSingleton.getDriver().findElement(monthSelectLocator));
        monthSelect.selectByVisibleText(month);
    }

    /**
     * Выбор года рождения
     * @param year год
     */
    @Step("Выбрать год рождения: {year}")
    public void selectYear(String year) {
        Select yearSelect = new Select(DriverSingleton.getDriver().findElement(yearSelectLocator));
        yearSelect.selectByVisibleText(year);
    }

    /**
     * Выбор полной даты рождения
     * @param day день
     * @param month месяц
     * @param year год
     */
    @Step("Выбрать дату рождения: {day}.{month}.{year}")
    public void selectDate(String day, String month, String year) {
        selectDay(day);
        selectMonth(month);
        selectYear(year);
    }

    /**
     * Отметить чекбокс подписки на новости
     */
    @Step("Отметить чекбокс подписки на новости")
    public void checkNewsletter() {
        WebElement checkbox = DriverSingleton.getDriver().findElement(newsletterCheckboxLocator);
        ((JavascriptExecutor) DriverSingleton.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", checkbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    /**
     * Отметить чекбокс специальных предложений
     */
    @Step("Отметить чекбокс получения специальных предложений")
    public void setSpecialOfferCheckbox() {
        WebElement checkbox = DriverSingleton.getDriver().findElement(specialOfferCheckboxLocator);
        ((JavascriptExecutor) DriverSingleton.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", checkbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    /**
     * Выбрать обращение "Mr."
     */
    @Step("Выбрать обращение Mr.")
    public void selectMrTitle() {
        WebElement radioBtn = DriverSingleton.getDriver().findElement(titleMrRadioBtnLocator);
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
    }

    /**
     * Выбрать обращение "Mrs."
     */
    @Step("Выбрать обращение Mrs.")
    public void selectMrsTitle() {
        WebElement radioBtn = DriverSingleton.getDriver().findElement(titleMrsRadioBtnLocator);
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
    }

    /**
     * Нажатие кнопки создания аккаунта
     * @return AccountCreatedPage - страница подтверждения создания аккаунта
     */
    @Step("Нажать кнопку 'Create Account'")
    public AccountCreatedPage clickCreateAccountBtn() {
        clickWithJs(createAccountBtnLocator);
        WaitHelper.waitForPageLoad();
        return new AccountCreatedPage();
    }

    /**
     * Заполнение всей регистрационной формы данными пользователя
     * @param user объект TemporaryUser с данными для регистрации
     */
    @Step("Заполнить всю регистрационную форму данными пользователя: {user.name}")
    public void fillRegistrationForm(TemporaryUser user) {
        selectMrTitle();
        setPassword(user.getPassword());
        selectDate(user.getBirthDay(), user.getBirthMonth(), user.getBirthYear());
        checkNewsletter();
        setSpecialOfferCheckbox();
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setCompanyField(user.getCompany());
        setAddress1Field(user.getAddress1());
        setAddress2Field(user.getAddress2());
        selectCountry(user.getCountry());
        setStateField(user.getState());
        setCityField(user.getCity());
        setZipcodeField(user.getZipcode());
        setMobileNumberField(user.getMobile());
    }
}