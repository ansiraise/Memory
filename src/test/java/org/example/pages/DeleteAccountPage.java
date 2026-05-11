package org.example.pages;

import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * Страница подтверждения удаления аккаунта (Account Deleted)
 * Содержит методы для завершения процесса удаления аккаунта.
 */
public class DeleteAccountPage extends BasePage {

    private final By continueBtnLocator = By.xpath("//a[text()='Continue']");
    private final By accountDeletedMsgLocator = By.xpath("//b[text()='Account Deleted!']");

    /**
     * Нажатие кнопки Continue после удаления аккаунта
     * @return HomePage - главная страница
     */
    @Step("Нажать кнопку Continue на странице удаления аккаунта")
    public HomePage clickContinueBtn() {
        click(continueBtnLocator);
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
        return accountDeletedMsgLocator;
    }
}