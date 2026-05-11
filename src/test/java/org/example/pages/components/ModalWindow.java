package org.example.pages.components;

import org.example.DriverSingleton;
import org.example.pages.CartPage;
import org.example.pages.LoginPage;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;

public class ModalWindow {

    private final By modalContinueShopBtnLocator = By.xpath("//div[@class='modal-content']//button[text()='Continue Shopping']");
    private final By modalViewCartBtnLocator = By.xpath("//div[@class='modal-content']//a[@href='/view_cart']");
    private final By continueOnCartLocator = By.xpath("//div[@class='modal-content']//button[text()='Continue On Cart']");
    private final By registerLoginBtnLocator = By.xpath("//div[@class='modal-content']//a[@href='/login']");


    public void clickContinueShopping() {
        WaitHelper.waitForElementVisible(modalContinueShopBtnLocator);
        DriverSingleton.getDriver().findElement(modalContinueShopBtnLocator).click();
    }

    public CartPage clickViewCartBtn() {
        DriverSingleton.getDriver().findElement(modalViewCartBtnLocator).click();
        return new CartPage();
    }

    public void clickContinueOnCartBtn() {
        DriverSingleton.getDriver().findElement(continueOnCartLocator).click();
    }

   public LoginPage clickRegisterLoginBtn() {
        DriverSingleton.getDriver().findElement(registerLoginBtnLocator).click();
        return new LoginPage();
   }

}
