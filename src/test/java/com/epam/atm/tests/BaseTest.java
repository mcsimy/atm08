package com.epam.atm.tests;

import com.epam.atm.drivers.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected WebDriver driver;
    private static final String URL = "https://mail.ru/";


    @BeforeMethod(description = "Open mail.ru")
    public void openWebSite() {
        driver = DriverSingleton.getWebDriverInstance();

        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod(description = "Close browser")
    public void closeBrowser() {
        DriverSingleton.kill();
    }
}
