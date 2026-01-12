package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import driver.DriverFactory;

public class SmokeTest extends BaseTest {

    @Test
    public void openLoginPage() {

        DriverFactory.getDriver()
                     .get("https://the-internet.herokuapp.com/login");

        By username = By.id("username");
        DriverFactory.getDriver().findElement(username).sendKeys("tomsmith");

        Assert.assertTrue(
                DriverFactory.getDriver().getCurrentUrl().contains("login"),
                "Login page not opened"
        );
    }
}
