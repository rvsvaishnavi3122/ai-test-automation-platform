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

    public void login(String user) {
        locatorUtils.findElement(By.id("username")).sendKeys(user);
    }
}

