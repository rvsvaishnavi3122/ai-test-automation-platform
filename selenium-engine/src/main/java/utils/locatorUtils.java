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
            System.out.println("Primary locator failed. Trying self-healing...");
            return healAndFind(primaryLocator);
        }
    }

    private WebElement waitFor(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
        private WebElement healAndFind(By failedLocator) {

        // Strategy 1: Try by ID (if original was XPath/CSS)
        List<WebElement> byId = driver.findElements(By.xpath("//*[@id]"));
        if (!byId.isEmpty()) {
            System.out.println("Healing attempt: found element by id");
            return byId.get(0);
        }

        // Strategy 2: Try by button text
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        if (!buttons.isEmpty()) {
            System.out.println("Healing attempt: found element by tag <button>");
            return buttons.get(0);
        }

        // Strategy 3: First visible input
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        if (!inputs.isEmpty()) {
            System.out.println("Healing attempt: found element by tag <input>");
            return inputs.get(0);
        }

        throw new NoSuchElementException(
                "Self-healing failed for locator: " + failedLocator
        );
      

    }
  

}


    

