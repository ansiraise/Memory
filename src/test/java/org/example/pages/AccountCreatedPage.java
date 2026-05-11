package org.example.pages;

import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

public class AccountCreatedPage extends BasePage {

    private final By continueBtnLocator = By.xpath("//a[contains(text(), 'Continue')]");
    private final By accountCreatedMsgLocator = By.xpath("//b[contains(text(), 'Account Created')]");

    public String getExpectedCreatedText() {
        return "ACCOUNT CREATED!";
    }

    public HomePage clickContinueBtn() {
        WaitHelper.waitForElementClickable(continueBtnLocator);
        click(continueBtnLocator);
        // Ждем загрузки главной страницы
        WaitHelper.waitForPageLoad();
        return new HomePage();
    }

    public By getAccountConfirm() {return accountCreatedMsgLocator;}

}
