package com.epam.atm.pages;

import com.epam.atm.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ComposePage extends AbstractPage {

    private static final By TO_TEXTFIELD_LOCATOR = By.xpath("//textarea[@data-original-name='To']");
    private static final By SUBJECT_TEXTFILED_LOCATOR = By.xpath("//input[@name='Subject']");
    private static final By BODY_TEXTAREA_LOCATOR = By.xpath("//*[@id='tinymce']");
    private static final By SAVE_BUTTON_LOCATOR = By.xpath("./descendant::span[contains(text(), 'Сохранить')][1]");
    private static final By DRAFTS_MENU_ITEM_LOCATOR = By.xpath("//a[@href='/messages/drafts/']");
    private static final By LABAEL_EMAIL_LOCATOR =  By.xpath("./descendant::span[contains(text(), 'maksim_yermachonak@epam.com')][2]");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send'][1]");

    ComposePage(WebDriver driver){
        super(driver);
    }

    public ComposePage fillToTextfield(String toText){
        driver.findElement(TO_TEXTFIELD_LOCATOR).sendKeys(toText);
        return this;
    }

    public ComposePage fillSubjectTextfield(String subject){
        waitForElementVisisble(SUBJECT_TEXTFILED_LOCATOR);
        driver.findElement(SUBJECT_TEXTFILED_LOCATOR).sendKeys(subject);
        return this;
    }

    public ComposePage fillBodyTextarea(String bodyText){
        driver.switchTo().frame(0);
        waitForElementVisisble(BODY_TEXTAREA_LOCATOR);
        driver.findElement(BODY_TEXTAREA_LOCATOR).sendKeys(bodyText);
        driver.switchTo().defaultContent();
        return this;
    }

    public ComposePage clickSaveButton(){
        waitForElementVisisble(SAVE_BUTTON_LOCATOR);
        driver.findElement(SAVE_BUTTON_LOCATOR).click();
        Utils.threadSleep();
        return this;
    }

    public InboxPage clickDraftsButton(){
        waitForElementVisisble(DRAFTS_MENU_ITEM_LOCATOR);
        Assert.assertTrue(Utils.tryToClickNumberOfTimes(driver, 10, DRAFTS_MENU_ITEM_LOCATOR), "Couldn't click Drafts item");
//        driver.findElement(DRAFTS_MENU_ITEM_LOCATOR).click();
        return new InboxPage(driver);
    }

    public String getToText(){
        waitForElementVisisble(LABAEL_EMAIL_LOCATOR);
        return  driver.findElement(LABAEL_EMAIL_LOCATOR).getText();

    }

    public String getSubjectText(){
        waitForElementVisisble(SUBJECT_TEXTFILED_LOCATOR);
        return driver.findElement(SUBJECT_TEXTFILED_LOCATOR).getAttribute("value");
    }

    public String getBodyText(){
        driver.switchTo().frame(0);
        waitForElementVisisble(BODY_TEXTAREA_LOCATOR);
        String bodyText = driver.findElement(BODY_TEXTAREA_LOCATOR).getText().split("\n")[0];
        driver.switchTo().defaultContent();
        return bodyText;
    }

    public ComposePage clickSendButton(){
        waitForElementPresence(SEND_BUTTON_LOCATOR);
        driver.findElement(SEND_BUTTON_LOCATOR).click();
        return this;
    }
}
