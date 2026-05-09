package org.example.pages;

import org.example.DriverSingleton;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Страница корзины (Cart Page)
 * Содержит методы для работы с товарами в корзине: получение информации,
 * проверка сумм, удаление товаров, переход к оформлению заказа.
 */
public class CartPage extends BasePage {

    // Локаторы для элементов корзины
    private By cartRows = By.xpath("//tbody/tr");
    private By productName = By.xpath(".//td[@class='cart_description']/h4/a");
    private By productPrice = By.xpath(".//td[@class='cart_price']/p");
    private By productQuantity = By.xpath(".//td[@class='cart_quantity']/button");
    private By productTotal = By.xpath(".//td[@class='cart_total']/p");
    private By deleteProduct = By.xpath(".//td[@class='cart_delete']/a");

    // Иные локаторы
    private By proceedToCheckoutBtn = By.xpath("//a[text()='Proceed To Checkout']");
    private By emptyCartMessage = By.xpath("//span[@id='empty_cart']//b");

    /**
     * Класс для хранения данных о товаре в корзине
     */
    public static class CartItem {
        private String name;
        private int price;
        private int quantity;
        private int total;

        public CartItem(String name, int price, int quantity, int total) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.total = total;
        }

        public String getName() { return name; }
        public int getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public int getTotal() { return total; }

        /**
         * Проверка, что итоговая сумма = цена * количество
         * @return true если корректно, false если нет
         */
        @Step("Проверить корректность итоговой суммы для товара: {name}")
        public boolean isTotalCorrect() {
            return total == (price * quantity);
        }

        @Override
        public String toString() {
            return String.format("Товар: %s, Цена: %d, Количество: %d, Итого: %d",
                    name, price, quantity, total);
        }
    }

    /**
     * Получить все товары из корзины
     * @return список CartItem со всеми товарами
     */
    @Step("Получить все товары из корзины")
    public List<CartItem> getAllCartItems() {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);
        List<CartItem> items = new ArrayList<>();

        for (WebElement row : rows) {
            String name = row.findElement(productName).getText();

            // Извлекаем числовые значения из текста
            int price = extractPrice(row.findElement(productPrice).getText());
            int quantity = extractQuantity(row.findElement(productQuantity).getText());
            int total = extractPrice(row.findElement(productTotal).getText());

            items.add(new CartItem(name, price, quantity, total));
        }

        return items;
    }

    /**
     * Вспомогательный метод для извлечения цены из строки (например "Rs. 500" -> 500)
     * @param priceText текст с ценой
     * @return числовое значение цены
     */
    private int extractPrice(String priceText) {
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }

    /**
     * Вспомогательный метод для извлечения количества из строки
     * @param quantityText текст с количеством
     * @return числовое значение количества
     */
    private int extractQuantity(String quantityText) {
        return Integer.parseInt(quantityText.trim());
    }

    /**
     * Проверка, что товар с определённым именем есть в корзине
     * @param productName имя товара
     * @return true если товар найден, false если нет
     */
    @Step("Проверить наличие товара '{productName}' в корзине")
    public boolean isProductInCart(String productName) {
        List<CartItem> items = getAllCartItems();
        for (CartItem item : items) {
            if (item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получить товар по имени
     * @param productName имя товара
     * @return CartItem или null если не найден
     */
    @Step("Получить товар '{productName}' из корзины")
    public CartItem getCartItemByName(String productName) {
        List<CartItem> items = getAllCartItems();
        for (CartItem item : items) {
            if (item.getName().equalsIgnoreCase(productName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Проверить, что все итоговые суммы корректны (цена * количество)
     * @return true если все суммы корректны, false если есть ошибки
     */
    @Step("Проверить корректность всех итоговых сумм в корзине")
    public boolean areAllTotalsCorrect() {
        List<CartItem> items = getAllCartItems();
        for (CartItem item : items) {
            if (!item.isTotalCorrect()) {
                System.out.println("Неверная итоговая сумма для: " + item);
                return false;
            }
        }
        return true;
    }

    /**
     * Получить общее количество товаров в корзине
     * @return количество товаров
     */
    @Step("Получить количество товаров в корзине")
    public int getCartItemCount() {
        return DriverSingleton.getDriver().findElements(cartRows).size();
    }

    /**
     * Вспомогательный метод для проверки отображения элемента внутри строки
     * @param row строка-контейнер
     * @param locator локатор элемента внутри строки
     * @return true если элемент отображается, false если нет
     */
    private boolean isElementDisplayedInRow(WebElement row, By locator) {
        try {
            WebElement element = row.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Проверка, что у конкретного товара (по индексу строки) цена, количество и итог отображаются
     * @param rowIndex индекс строки (0, 1, 2...)
     * @return true если все элементы отображаются, false если нет
     */
    @Step("Проверить отображение цены, количества и итога для товара #{rowIndex}")
    public boolean arePriceQuantityTotalDisplayed(int rowIndex) {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);

        if (rows.size() <= rowIndex) {
            return false;
        }

        WebElement row = rows.get(rowIndex);

        boolean priceDisplayed = isElementDisplayedInRow(row, productPrice);
        boolean quantityDisplayed = isElementDisplayedInRow(row, productQuantity);
        boolean totalDisplayed = isElementDisplayedInRow(row, productTotal);

        return priceDisplayed && quantityDisplayed && totalDisplayed;
    }

    /**
     * Проверка, что у ВСЕХ товаров отображаются цена, количество и итог
     * @return true если все элементы отображаются, false если есть проблемы
     */
    @Step("Проверить отображение цены, количества и итога для всех товаров в корзине")
    public boolean allPricesQuantityTotalsDisplayed() {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);

        if (rows.isEmpty()) {
            System.out.println("Корзина пуста");
            return false;
        }

        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            boolean priceDisplayed = isElementDisplayedInRow(row, productPrice);
            boolean quantityDisplayed = isElementDisplayedInRow(row, productQuantity);
            boolean totalDisplayed = isElementDisplayedInRow(row, productTotal);

            if (!priceDisplayed || !quantityDisplayed || !totalDisplayed) {
                System.out.println("Проблема в товаре #" + (i + 1));
                return false;
            }
        }
        return true;
    }

    /**
     * Получить количество товара по индексу строки
     * @param rowIndex индекс строки (0, 1, 2...)
     * @return количество товара
     */
    @Step("Получить количество товара в строке #{rowIndex}")
    public int getQuantityByRowIndex(int rowIndex) {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);

        if (rows.size() <= rowIndex) {
            throw new RuntimeException("Строка с индексом " + rowIndex + " не существует");
        }

        WebElement row = rows.get(rowIndex);
        String quantityText = row.findElement(productQuantity).getText();
        return extractQuantity(quantityText);
    }

    /**
     * Переход к оформлению заказа
     * @return страница оформления заказа CheckOutPage
     */
    @Step("Нажать кнопку 'Proceed To Checkout'")
    public CheckOutPage clickProceedToCheckoutBtn() {
        click(proceedToCheckoutBtn);
        return new CheckOutPage();
    }

    /**
     * Удаление товара по индексу строки
     * @param rowIndex индекс строки (0, 1, 2...)
     */
    @Step("Удалить товар из строки #{rowIndex}")
    public void deleteProductByRowIndex(int rowIndex) {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);

        if (rows.size() > rowIndex) {
            WebElement row = rows.get(rowIndex);
            WebElement deleteBtn = row.findElement(deleteProduct);
            deleteBtn.click();
        } else {
            throw new RuntimeException("Строка с индексом " + rowIndex + " не существует");
        }
    }

    /**
     * Удаление всех товаров из корзины
     */
    @Step("Удалить все товары из корзины")
    public void deleteAllProducts() {
        List<WebElement> rows = DriverSingleton.getDriver().findElements(cartRows);

        for (WebElement row : rows) {
            WebElement deleteBtn = row.findElement(deleteProduct);
            deleteBtn.click();
            WaitHelper.waitForPageLoad();
            rows = DriverSingleton.getDriver().findElements(cartRows);
        }
    }

    /**
     * Проверка, пуста ли корзина
     * @return true если корзина пуста, false если нет
     */
    @Step("Проверить, пуста ли корзина")
    public boolean isCartEmpty() {
        return isDisplayed(emptyCartMessage);
    }

    /**
     * Получение ожидаемого текста для пустой корзины
     * @return текст "Cart is empty!"
     */
    public String getExpectedCartIsEmpty() {
        return "Cart is empty!";
    }

    /**
     * Получение локатора сообщения о пустой корзине
     * @return локатор emptyCartMessage
     */
    public By getEmptyCartMessage() {
        WaitHelper.waitForElementVisible(emptyCartMessage);
        return emptyCartMessage;
    }
}