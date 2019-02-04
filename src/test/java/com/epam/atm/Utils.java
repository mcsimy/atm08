package com.epam.atm;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;


import java.util.List;

public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int count){
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static void threadSleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    public static boolean isElementWithTextInList(List<WebElement> list, String text){
        for (WebElement item : list){
            if (item.getAttribute("innerText").substring(0, 12).equals(text)){
                return true;
            }
        }
        return false;
    }

    public static boolean tryToClickNumberOfTimes(WebDriver driver, int clicks, By locator){
        boolean result = false;
        int attempts = 0;
        while(attempts < clicks) {
            try {
                driver.findElement(locator).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }
}
