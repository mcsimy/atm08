package com.epam.atm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AbstractPage {
    protected WebDriver driver;
    private static final int WAIT_FOR_ELEMENT_SECONDS = 10;

    public AbstractPage(WebDriver driver){
        this.driver = driver;
    }

    protected void waitForElementVisisble(By locator){
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementPresence(By locator){
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_SECONDS).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForElementClickable(By locator){
        new WebDriverWait(driver, WAIT_FOR_ELEMENT_SECONDS).until(ExpectedConditions.elementToBeClickable(locator));
    }
}
