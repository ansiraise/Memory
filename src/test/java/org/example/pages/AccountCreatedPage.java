package org.example.pages;

import org.example.DriverSingleton;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

public class AccountCreatedPage extends BasePage {

    private By continueBtn = By.xpath("//a[text()='Continue']");
    private By accountConfirmText = By.xpath("//b[text()='Account Created!']");

    public String getExpectedCreatedText() {
        return "ACCOUNT CREATED!";
    }

    public HomePage clickContinueBtn() {
        WaitHelper.waitForElementClickable(continueBtn);
        click(continueBtn);
        // Ждем загрузки главной страницы
        WaitHelper.waitForPageLoad();
        return new HomePage();
    }

    public By getAccountConfirm() {return accountConfirmText;}

}
