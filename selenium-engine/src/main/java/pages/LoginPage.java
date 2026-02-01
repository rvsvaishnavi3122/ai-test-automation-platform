package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.locatorUtils;

public class LoginPage {

    private WebDriver driver;
    private locatorUtils locatorUtils;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.locatorUtils = new locatorUtils(driver);
    }

    public void enterUsername() {
        // INTENTIONALLY BROKEN LOCATOR (FOR PHASE 3E)
        locatorUtils
            .findElement(By.id("THIS_ID_DOES_NOT_EXIST"))
            .sendKeys("admin");
    }
}
