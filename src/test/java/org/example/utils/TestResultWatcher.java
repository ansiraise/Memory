package org.example.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.util.Optional;

public class TestResultWatcher implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Вызываем ваш помощник, передавая имя упавшего теста
        String testName = context.getDisplayName();
        ScreenshotHelper.captureScreenshot(testName);
    }

    // Остальные методы (testSuccessful, testAborted) можно не переопределять
}
