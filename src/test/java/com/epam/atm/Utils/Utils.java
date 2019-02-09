package com.epam.atm.Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String PATH_TO_SCREENSHOT_FOLDER = "/Users/maksim_yermachonak/Desktop/Screenshots";
    static Logger log = Logger.getLogger(Utils.class);

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

    public static boolean tryToClickNumberOfTimes(int clicks, WebElement element){
        boolean result = false;
        int attempts = 0;
        while(attempts < clicks) {
            try {
                element.click();
                result = true;
                break;
            } catch(ElementNotVisibleException e) {
            }
            attempts++;
        }
        return result;
    }

    public static WebElement findVisibleElementFromList(List<WebElement> list){
        for(WebElement element : list){
            if(element.isDisplayed()){
                return element;
            }
        }
        return null;
    }

    public static void highlightElement(WebElement element)
    {
        String bg = element.getCssValue("background");
        JavascriptExecutor js = ((JavascriptExecutor) DriverSingleton.getWebDriverInstance());
        js.executeScript("arguments[0].style.background = '" + "yellow" + "'", element);
        takeScreenshot();
        js.executeScript("arguments[0].style.background = '" + bg + "'", element);
    }

    public static void takeScreenshot(){
        try{
            log.info("Screenshot was taken");

            File screenshot = ((TakesScreenshot) DriverSingleton.getWebDriverInstance())
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFileToDirectory(screenshot, new File(PATH_TO_SCREENSHOT_FOLDER));
        }
        catch (Exception e){
            log.warn(e.getCause());
        }
    }
}
