package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import driver.DriverFactory;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        LoginPage loginPage =
                new LoginPage(DriverFactory.getDriver());

        loginPage.enterUsername();
    }
}