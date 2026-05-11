package org.example.pages;

import org.example.DriverSingleton;
import org.example.utils.TableHelper;
import org.example.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

import java.util.List;

/**
 * Страница корзины (Cart Page)
 * Содержит методы для работы с товарами в корзине: получение информации,
 * проверка сумм, удаление товаров, переход к оформлению заказа.
 */
public class CartPage extends BasePage {

    private final TableHelper tableHelper;

    // Локаторы для элементов корзины
    private final By cartRowsLocator = By.xpath("//tbody/tr");
    private final By productNameLocator = By.xpath(".//td[@class='cart_description']/h4/a");
    private final By productPriceLocator = By.xpath(".//td[@class='cart_price']/p");
    private final By productQuantityLocator = By.xpath(".//td[@class='cart_quantity']/button");
    private final By productTotalLocator = By.xpath(".//td[@class='cart_total']/p");
    private final By deleteProductLocator = By.xpath(".//td[@class='cart_delete']/a");

    // Иные локаторы
    private final By proceedToCheckoutBtnLocator = By.xpath("//a[text()='Proceed To Checkout']");
    private final By cartEmptyMsgLocator = By.xpath("//span[@id='empty_cart']//b");

    public CartPage() {
        this.tableHelper = new TableHelper(DriverSingleton.getDriver(), cartRowsLocator);
    }

    /**
     * Класс для хранения данных о товаре в корзине
     */
    public static class CartItem {
        private final String name;
        private final int price;
        private final int quantity;
        private final int total;

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
        return tableHelper.parseTable(row -> {
            String name = tableHelper.getCellText(row, productNameLocator);
            int price = extractPrice(tableHelper.getCellText(row, productPriceLocator));
            int quantity = Integer.parseInt(tableHelper.getCellText(row, productQuantityLocator).trim());
            int total = extractPrice(tableHelper.getCellText(row, productTotalLocator));
            return new CartItem(name, price, quantity, total);
        });
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
     * Проверка, что товар с определённым именем есть в корзине
     * @param productName имя товара
     * @return true если товар найден, false если нет
     */
    @Step("Проверить наличие товара '{productName}' в корзине")
    public boolean isProductInCart(String productName) {
        return getAllCartItems().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(productName));
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
        return tableHelper.getRowCount();
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
        try {
            WebElement row = tableHelper.getRow(rowIndex);
            return tableHelper.areCellsDisplayed(row,
                    productPriceLocator,
                    productQuantityLocator,
                    productTotalLocator);
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Проверка, что у ВСЕХ товаров отображаются цена, количество и итог
     * @return true если все элементы отображаются, false если есть проблемы
     */
    @Step("Проверить отображение цены, количества и итога для всех товаров в корзине")
    public boolean allPricesQuantityTotalsDisplayed() {
        if (tableHelper.getRowCount() == 0) {
            System.out.println("Корзина пуста");
            return false;
        }

        return tableHelper.areCellsDisplayedInAllRows(
                productPriceLocator,
                productQuantityLocator,
                productTotalLocator);
    }

    /**
     * Получить количество товара по индексу строки
     * @param rowIndex индекс строки (0, 1, 2...)
     * @return количество товара
     */
    @Step("Получить количество товара в строке #{rowIndex}")
    public int getQuantityByRowIndex(int rowIndex) {
        if (tableHelper.getRowCount() <= rowIndex) {
            throw new RuntimeException("Row with index " + rowIndex + " is not exist");
        }
        String quantityText = tableHelper.getCellValue(rowIndex, productQuantityLocator);
        return Integer.parseInt(quantityText.trim());
    }

    /**
     * Переход к оформлению заказа
     * @return страница оформления заказа CheckOutPage
     */
    @Step("Нажать кнопку 'Proceed To Checkout'")
    public CheckOutPage clickProceedToCheckoutBtn() {
        click(proceedToCheckoutBtnLocator);
        return new CheckOutPage();
    }

    /**
     * Удаление товара по индексу строки
     * @param rowIndex индекс строки (0, 1, 2...)
     */
    @Step("Удалить товар из строки #{rowIndex}")
    public void deleteProductByRowIndex(int rowIndex) {
        tableHelper.deleteRow(rowIndex, deleteProductLocator);
    }

    /**
     * Удаление всех товаров из корзины
     */
    @Step("Удалить все товары из корзины")
    public void deleteAllProducts() {
        tableHelper.deleteAllRows(deleteProductLocator, WaitHelper::waitForPageLoad);
    }

    /**
     * Проверка, пуста ли корзина
     * @return true если корзина пуста, false если нет
     */
    @Step("Проверить, пуста ли корзина")
    public boolean isCartEmpty() {
        return isDisplayed(cartEmptyMsgLocator);
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
        WaitHelper.waitForElementVisible(cartEmptyMsgLocator);
        return cartEmptyMsgLocator;
    }
}