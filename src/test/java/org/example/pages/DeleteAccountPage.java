package org.example.pages;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница подтверждения удаления аккаунта (Account Deleted)
 * Содержит методы для завершения процесса удаления аккаунта.
 */
public class DeleteAccountPage extends BasePage {

    private By continueBtn = By.xpath("//a[text()='Continue']");
    private By accountDeletedText = By.xpath("//b[text()='Account Deleted!']");

    /**
     * Нажатие кнопки Continue после удаления аккаунта
     * @return HomePage - главная страница
     */
    @Step("Нажать кнопку Continue на странице удаления аккаунта")
    public HomePage clickContinueBtn() {
        click(continueBtn);
        return new HomePage();
    }

    /**
     * Получение ожидаемого текста об успешном удалении аккаунта
     * @return текст "ACCOUNT DELETED!"
     */
    public String getExpectedDeletedText() {
        return "ACCOUNT DELETED!";
    }

    /**
     * Получение локатора сообщения об удалении аккаунта
     * @return локатор accountDeletedText
     */
    @Step("Получить локатор сообщения об удалении аккаунта")
    public By getAccountDeletedText() {
        return accountDeletedText;
    }
}