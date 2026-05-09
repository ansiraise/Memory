package org.example.pages.components;

public enum Subcategory {
    // Women
    WOMEN_DRESS("Women", "Dress"),
    WOMEN_TOPS("Women", "Tops"),
    WOMEN_SAREE("Women", "Saree"),

    // Men
    MEN_TSHIRTS("Men", "Tshirts"),
    MEN_JEANS("Men", "Jeans"),

    // Kids
    KIDS_DRESS("Kids", "Dress"),
    KIDS_TOPS("Kids", "Tops");

    private final String categoryName;
    private final String subcategoryName;

    Subcategory(String categoryName, String subcategoryName) {
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    // Автоматически формируем ожидаемый заголовок
    public String getExpectedTitle() {
        return categoryName + " - " + subcategoryName + " Products";
    }

}