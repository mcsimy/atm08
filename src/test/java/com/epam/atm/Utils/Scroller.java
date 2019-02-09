package com.epam.atm.Utils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
public class Scroller {
    public static void ScrollByVisibleElement (WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)DriverSingleton.getWebDriverInstance();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }
}
