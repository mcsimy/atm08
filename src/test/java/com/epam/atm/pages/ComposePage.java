package com.epam.atm.pages;

import com.epam.atm.Utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComposePage extends AbstractPage {

    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private WebElement toTextField;

    @FindBy(xpath = "//input[@name='Subject']")
    private WebElement subjectTextField;

    @FindBy(xpath = "//*[@id='tinymce']")
    private WebElement bodyTextArea;

    @FindBy(xpath = "./descendant::span[contains(text(), 'Сохранить')][1]")
    private WebElement saveButton;

    @FindBy(xpath = "//a[@href='/messages/drafts/']")
    private WebElement draftsMenuItem;

    @FindBy(xpath = "./descendant::span[contains(text(), 'max.yermachonak@gmail.com')][2]")
    private WebElement toLabelEmail;

    @FindBy(xpath = "//div[@data-name='send'][1]")
    private WebElement sendButton;

    ComposePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public ComposePage fillToTextField(String toText){
        toTextField.sendKeys(toText);
        return this;
    }

    public ComposePage fillSubjectTextField(String subject){
        subjectTextField.sendKeys(subject);
        return this;
    }

    public ComposePage fillBodyTextArea(String bodyText){
        driver.switchTo().frame(0);
        bodyTextArea.sendKeys(bodyText);
        driver.switchTo().defaultContent();
        return this;
    }

    public ComposePage clickSaveButton(){
        saveButton.click();
        Utils.threadSleep();
        return this;
    }

    public InboxPage clickDraftsButton(){
        draftsMenuItem.click();
//        Assert.assertTrue(Utils.tryToClickNumberOfTimes(driver, 10, DRAFTS_MENU_ITEM_LOCATOR), "Couldn't click Drafts item");
        return new InboxPage(driver);
    }

    public String getToText(){
        return  toLabelEmail.getText();
    }

    public String getSubjectText(){
        return subjectTextField.getAttribute("value");
    }

    public String getBodyText(){
        driver.switchTo().frame(0);
        String bodyText = bodyTextArea.getText().split("\n")[0];
        driver.switchTo().defaultContent();
        return bodyText;
    }

    public ComposePage clickSendButton(){
        sendButton.click();
        return this;
    }
}
