package org.example.pages.components;

import org.example.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class Table {
    private final By tableLocator;
    private final By rowLocator = By.xpath(".//tr");
    private final By cellLocator = By.xpath(".//td");

    public Table(By tableLocator) {
        this.tableLocator = tableLocator;
    }

    public List<WebElement> getRows() {
        WebElement table = DriverSingleton.getDriver().findElement(tableLocator);
        return table.findElements(rowLocator);
    }

    public WebElement getCell(int rowIndex, int colIndex) {
        List<WebElement> rows = getRows();
        if (rowIndex >= rows.size()) throw new RuntimeException("Row not found");
        List<WebElement> cells = rows.get(rowIndex).findElements(cellLocator);
        if (colIndex >= cells.size()) throw new RuntimeException("Cell not found");
        return cells.get(colIndex);
    }

    public String getCellText(int rowIndex, int colIndex) {
        return getCell(rowIndex, colIndex).getText();
    }

    public int getRowCount() {
        return getRows().size();
    }


}