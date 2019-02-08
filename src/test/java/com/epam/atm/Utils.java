package com.epam.atm;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;


import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import static okhttp3.internal.Internal.instance;

public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String PATH_TO_SCREENSHOT_FOLDER = ".//screenshots";
//    private Logger log = (Logger) Logger.getLogger(Utils.class);

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

//
//    public static void takeScreenshot(){
//        try{
//            File screenshot= ((TakesScreenshot)instance).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFileToDirectory(screenshot, new File(PATH_TO_SCREENSHOT_FOLDER));
//            Logger.html
//        ("Screenshot was taken <a href='screenshot/" + screenshot.getName() + "'>"
//            + screenshot.getName() + "</a>");
//        }
//        catch (Exception e){
//            Logger.getErr
//        }
//    }
}
