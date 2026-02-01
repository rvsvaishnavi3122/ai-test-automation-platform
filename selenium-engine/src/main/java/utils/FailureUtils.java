package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class FailureUtils {

    private static String classifyFailure(Throwable error) {
        String msg = error.getMessage() == null ? "" : error.getMessage().toLowerCase();

        if (msg.contains("no such element") || msg.contains("timeout")) {
            return "LOCATOR";
        }
        if (msg.contains("assert")) {
            return "ASSERTION";
        }
        if (msg.contains("connection") || msg.contains("refused")) {
            return "ENVIRONMENT";
        }
        return "UNKNOWN";
    }

    public static void captureFailure(
            WebDriver driver,
            String testName,
            Throwable error
    ) {
        try {
            System.out.println(">>> captureFailure triggered for: " + testName);

            String timestamp = LocalDateTime.now().toString().replace(":", "-");

            // Screenshot
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path screenshotPath = Path.of(
                    "failure-artifacts",
                    testName + "_" + timestamp + ".png"
            );

            Files.createDirectories(screenshotPath.getParent());
            Files.copy(screenshot.toPath(), screenshotPath);

            // Log
            Path logPath = Path.of(
                    "failure-artifacts",
                    testName + "_" + timestamp + ".log"
            );

            String log = "Error: " + error.getMessage();
            Files.writeString(logPath, log);

            //  ANALYTICS MUST BE HERE
            String failureType = classifyFailure(error);

            FailureAnalyticsStore.recordFailure(
                    testName,
                    failureType,
                    error.getMessage()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
