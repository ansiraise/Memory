package org.example.pages.components;

public enum Category {
    WOMEN("Women"),
    MEN("Men"),
    KIDS("Kids");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}