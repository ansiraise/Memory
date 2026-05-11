package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Универсальный помощник для работы с HTML-таблицами.
 * Позволяет получать строки, ячейки, проверять отображение,
 * парсить таблицу в список объектов и удалять строки.
 */
public class TableHelper {

    private final WebDriver driver;
    private final By rowsLocator;

    /**
     * Конструктор
     * @param driver экземпляр WebDriver
     * @param rowsLocator локатор строк таблицы (например, By.xpath("//tbody/tr"))
     */
    public TableHelper(WebDriver driver, By rowsLocator) {
        this.driver = driver;
        this.rowsLocator = rowsLocator;
    }

    // ==================== БАЗОВЫЕ МЕТОДЫ ====================

    /**
     * Получить все строки таблицы
     * @return список WebElement строк
     */
    public List<WebElement> getRows() {
        return driver.findElements(rowsLocator);
    }

    /**
     * Получить количество строк в таблице
     * @return количество строк
     */
    public int getRowCount() {
        return getRows().size();
    }

    /**
     * Получить строку по индексу
     * @param index индекс строки (начиная с 0)
     * @return WebElement строки
     * @throws RuntimeException если индекс выходит за пределы
     */
    public WebElement getRow(int index) {
        List<WebElement> rows = getRows();
        if (index >= rows.size()) {
            throw new RuntimeException("Строка с индексом " + index + " не существует. Всего строк: " + rows.size());
        }
        return rows.get(index);
    }

    /**
     * Получить ячейку из строки по локатору
     * @param row строка
     * @param cellLocator локатор ячейки внутри строки
     * @return WebElement ячейки
     */
    public WebElement getCell(WebElement row, By cellLocator) {
        return row.findElement(cellLocator);
    }

    /**
     * Получить текст ячейки по индексу строки и локатору
     * @param rowIndex индекс строки (начиная с 0)
     * @param cellLocator локатор ячейки внутри строки
     * @return текст ячейки
     */
    public String getCellValue(int rowIndex, By cellLocator) {
        WebElement row = getRow(rowIndex);
        return getCellText(row, cellLocator);
    }

    /**
     * Получить текст ячейки
     * @param row строка
     * @param cellLocator локатор ячейки внутри строки
     * @return текст ячейки
     */
    public String getCellText(WebElement row, By cellLocator) {
        return getCell(row, cellLocator).getText();
    }

    /**
     * Проверить, отображается ли ячейка в строке
     * @param row строка
     * @param cellLocator локатор ячейки внутри строки
     * @return true если отображается, false если нет или элемент не найден
     */
    public boolean isCellDisplayed(WebElement row, By cellLocator) {
        try {
            return row.findElement(cellLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== МЕТОДЫ ДЛЯ ПАРСИНГА ====================

    /**
     * Универсальный метод для парсинга всей таблицы в список объектов
     * @param mapper функция, преобразующая строку в объект типа T
     * @return список объектов типа T
     * @param <T> тип объектов в результирующем списке
     */
    public <T> List<T> parseTable(Function<WebElement, T> mapper) {
        List<WebElement> rows = getRows();
        List<T> result = new ArrayList<>();
        for (WebElement row : rows) {
            result.add(mapper.apply(row));
        }
        return result;
    }

    /**
     * Получить значения из определённой колонки для всех строк
     * @param columnLocator локатор ячейки внутри строки
     * @return список текстов из колонки
     */
    public List<String> getColumnValues(By columnLocator) {
        List<WebElement> rows = getRows();
        List<String> values = new ArrayList<>();
        for (WebElement row : rows) {
            values.add(getCellText(row, columnLocator));
        }
        return values;
    }

    /**
     * Преобразовать строку в Map (локатор → текст)
     * @param row строка
     * @param cellLocators массив локаторов ячеек
     * @return Map с текстами ячеек
     */
    public Map<By, String> getRowAsMap(WebElement row, By... cellLocators) {
        Map<By, String> rowData = new HashMap<>();
        for (By locator : cellLocators) {
            rowData.put(locator, getCellText(row, locator));
        }
        return rowData;
    }

    // ==================== МЕТОДЫ ДЛЯ ПРОВЕРКИ ОТОБРАЖЕНИЯ ====================

    /**
     * Проверить, что все указанные ячейки отображаются в строке
     * @param row строка
     * @param cellLocators список локаторов для проверки
     * @return true если все отображаются
     */
    public boolean areCellsDisplayed(WebElement row, By... cellLocators) {
        for (By locator : cellLocators) {
            if (!isCellDisplayed(row, locator)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверить, что во всех строках отображаются указанные ячейки
     * @param cellLocators список локаторов для проверки в каждой строке
     * @return true если во всех строках все ячейки отображаются
     */
    public boolean areCellsDisplayedInAllRows(By... cellLocators) {
        List<WebElement> rows = getRows();
        if (rows.isEmpty()) {
            return false;
        }
        for (WebElement row : rows) {
            if (!areCellsDisplayed(row, cellLocators)) {
                return false;
            }
        }
        return true;
    }

    // ==================== МЕТОДЫ ДЛЯ УДАЛЕНИЯ ====================

    /**
     * Удалить строку по индексу
     * @param rowIndex индекс строки
     * @param deleteButtonLocator локатор кнопки удаления внутри строки
     */
    public void deleteRow(int rowIndex, By deleteButtonLocator) {
        WebElement row = getRow(rowIndex);
        WebElement deleteBtn = row.findElement(deleteButtonLocator);
        deleteBtn.click();
    }

    /**
     * Удалить все строки из таблицы
     * @param deleteButtonLocator локатор кнопки удаления внутри строки
     * @param waitAfterDelete действие после удаления (например, ожидание обновления страницы)
     */
    public void deleteAllRows(By deleteButtonLocator, Runnable waitAfterDelete) {
        int rowCount = getRowCount();
        for (int i = 0; i < rowCount; i++) {
            WebElement row = getRow(0);
            WebElement deleteBtn = row.findElement(deleteButtonLocator);
            deleteBtn.click();
            if (waitAfterDelete != null) {
                waitAfterDelete.run();
            }
        }
    }
}