package org.example.utils;

public class TestData {

        // Валидные
        public static final String VALID_EMAIL = "valid@test.com";
        public static final String VALID_PASSWORD = "valid123";

        // Невалидные
        public static final String INCORRECT_EMAIL = "wrong@test.com";
        public static final String INCORRECT_PASSWORD = "wrong123";
        public static final String EMPTY_EMAIL = "";
        public static final String EMPTY_PASSWORD = "";
        public static final String LONG_NAME = "ThisIsAVeryLongNameThatExceedsNormalLength";
        public static final int MIN_PASSWORD_LENGTH = 123;
        public static final long MAX_PASSWORD_LENGTH = 12345678901654613L;


    // Путь к файлу для загрузки
    public static final String UPLOAD_FILE_PATH = "E://IDEA Projects/Memory/src/test/resources/testdata/text.txt";

    // Для разных типов файлов
    public static final String IMAGE_PATH = "src/test/resources/testdata/___.jpg";
    public static final String PDF_PATH = "src/test/resources/testdata/____.pdf";
    public static final String TEXT_PATH = "src/test/resources/testdata/____.txt";

    // Данные для карты
    public static final String CARD_NAME = "American Express";
    public static final String CARD_NUMBER = "371449635398431";
    public static final String CARD_CVC = "123";
    public static final String EXPIRATION_MONTH = "04";
    public static final String EXPIRATION_YEAR = "27";


    }
