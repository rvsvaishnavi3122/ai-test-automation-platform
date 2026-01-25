package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class FailureUtils {

    public static void captureFailure(
            WebDriver driver,
            String testName,
            Throwable error
    ) {
        try {
            String timestamp = LocalDateTime.now().toString().replace(":", "-");

            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path screenshotPath = Path.of(
                    "failure-artifacts",
                    testName + "_" + timestamp + ".png"
            );

            Files.createDirectories(screenshotPath.getParent());
            Files.copy(screenshot.toPath(), screenshotPath);

            Path logPath = Path.of(
                    "failure-artifacts",
                    testName + "_" + timestamp + ".log"
            );

            String log = "Error: " + error.getMessage();
            Files.writeString(logPath, log);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
