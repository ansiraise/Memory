package org.example.pages.components;

import org.example.DriverSingleton;
import org.example.pages.CartPage;
import org.example.pages.LoginPage;
import org.openqa.selenium.By;

public class ModalWindow {

    private By modalContinueShopBtn = By.xpath("//div[@class='modal-content']//button[text()='Continue Shopping']");
    private By modalViewCartBtn = By.xpath("//div[@class='modal-content']//a[@href='/view_cart']");
    private By continueOnCart = By.xpath("//div[@class='modal-content']//button[text()='Continue On Cart']");
    private By registerLoginBtn = By.xpath("//div[@class='modal-content']//a[@href='/login']");


    public void clickContinueShopping() {
        DriverSingleton.getDriver().findElement(modalContinueShopBtn).click();
    }

    public CartPage clickViewCartBtn() {
        DriverSingleton.getDriver().findElement(modalViewCartBtn).click();
        return new CartPage();
    }

    public void clickContinueOnCartBtn() {
        DriverSingleton.getDriver().findElement(continueOnCart).click();
    }

   public LoginPage clickRegisterLoginBtn() {
        DriverSingleton.getDriver().findElement(registerLoginBtn).click();
        return new LoginPage();
   }




}
