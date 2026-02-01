package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class locatorUtils {

    private WebDriver driver;

    public locatorUtils(WebDriver driver) {
        this.driver = driver;
    }

    // Primary entry point (USED BY PAGES)
    public WebElement findElement(By primaryLocator) {
        try {
            return waitFor(primaryLocator);
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println(">>> PRIMARY LOCATOR FAILED, ENTERING SELF-HEALING");
            return healAndFind(primaryLocator);
        }
    }

    private WebElement waitFor(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement healAndFind(By failedLocator) {

        System.out.println(">>> HEALING METHOD ENTERED");

        // Strategy 1: Any element with id
        List<WebElement> byId = driver.findElements(By.xpath("//*[@id]"));
        if (!byId.isEmpty()) {

            System.out.println(">>> HEALING SUCCESS: ID_FALLBACK");

            HealingMetricsStore.recordHealing(
                    failedLocator.toString(),
                    true,
                    "ID_FALLBACK"
            );

            return byId.get(0);
        }

        // Strategy 2: Button tag
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        if (!buttons.isEmpty()) {

            System.out.println(">>> HEALING SUCCESS: BUTTON_TAG");

            HealingMetricsStore.recordHealing(
                    failedLocator.toString(),
                    true,
                    "BUTTON_TAG"
            );

            return buttons.get(0);
        }

        // Strategy 3: Input tag
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        if (!inputs.isEmpty()) {

            System.out.println(">>> HEALING SUCCESS: INPUT_TAG");

            HealingMetricsStore.recordHealing(
                    failedLocator.toString(),
                    true,
                    "INPUT_TAG"
            );

            return inputs.get(0);
        }

        // Healing failed
        System.out.println(">>> HEALING FAILED: NO_MATCH_FOUND");

        HealingMetricsStore.recordHealing(
                failedLocator.toString(),
                false,
                "NO_MATCH_FOUND"
        );

        throw new NoSuchElementException(
                "Self-healing failed for locator: " + failedLocator
        );
    }
}
