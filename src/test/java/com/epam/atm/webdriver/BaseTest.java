package com.epam.atm.webdriver;

import com.epam.atm.Utils.DriverSingleton;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
