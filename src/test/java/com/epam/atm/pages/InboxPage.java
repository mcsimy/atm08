package com.epam.atm.pages;

import com.epam.atm.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InboxPage extends AbstractPage {

    private static final By COMPOSE_BUTTON_LOCATOR = By.xpath("./descendant::span[contains(text(), 'Написать письмо')][1]");
    private static final By DRAFTS_ITEM_LOCATOR = By.xpath("//span[@class='b-datalist__item__subj__snippet']");
    private static final By NO_DRAFTS_MESSAGE_LOCATOR = By.cssSelector("div[class='b-datalist__empty__block']");
    private static final By SENT_BUTTON_LOCATOR = By.xpath("./descendant::span[contains(text(), 'Отправленные')][1]");
    private static final By SENT_ITEM_LOCATOR = By.xpath("//div[@class='b-datalist__item__panel']//div[@class='b-datalist__item__subj']");
    private static final By LOGOUT_LOCATOR = By.id("PH_logoutLink");
    private static final By FLAG_EMAIL_LOCATOR = By.xpath("//div[@data-bem='b-flag']");
    private static final By FLAG_SORT_BUTTON_LOCATOR = By.xpath("//div[@data-cache-key='500000_undefined_false']" +
            "//div[@class='b-toolbar__group']/div[@data-name='flagged' and @class='b-toolbar__btn b-toolbar__btn_grouped']");
    private static final By FLAGGED_EMAIL_LOCATOR = By.xpath("//div[@class='b-datalist__item__flag']/*");
    private static final By CHECKBOX_OF_EMAIL_ITEM_LOCATOR = By.xpath("(//div[@class='js-item-checkbox b-datalist__item__cbx']//div[@class='b-checkbox__box'])[2]");
    private static final By MOVE_BUTTON = By.xpath("(//div[@data-group='moveTo']/div[@class='b-dropdown__ctrl js-shortcut']//span[@class='b-dropdown__ctrl__text'])[1]");
    private static final By MOVE_INBOX_ITEM_LOCATOR = By.xpath("(//div[@class='b-dropdown__group b-dropdown__group_']//a[@data-bem='b-dropdown__list__params'])[1]");

    public InboxPage(WebDriver driver){
        super(driver);
    }

    public ComposePage clickComposeButton(){
        (new WebDriverWait(driver, 30))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(COMPOSE_BUTTON_LOCATOR))
                .click();
        return new ComposePage(driver);
    }

    public ComposePage clickDraftsItem(){
        waitForElementVisisble(DRAFTS_ITEM_LOCATOR);
        driver.findElement(DRAFTS_ITEM_LOCATOR).click();
        return new ComposePage(driver);
    }

    public boolean isNoDraftsMessageVisible(){
        Utils.threadSleep();
        waitForElementPresence(NO_DRAFTS_MESSAGE_LOCATOR);
        return driver.findElement(NO_DRAFTS_MESSAGE_LOCATOR).isDisplayed();
    }

    public InboxPage clickSentButton(){
        waitForElementVisisble(SENT_BUTTON_LOCATOR);
        driver.findElement(SENT_BUTTON_LOCATOR).click();
        return this;
    }

    public List<WebElement> getSentItems(){
        waitForElementPresence(SENT_ITEM_LOCATOR);
        List<WebElement> sentItems = driver.findElements(SENT_ITEM_LOCATOR);
        return sentItems;
    }

    public HomePage logOut(){
        waitForElementVisisble(LOGOUT_LOCATOR);
        driver.findElement(LOGOUT_LOCATOR).click();
        return new HomePage(driver);
    }

    public InboxPage flagNumberOfEmailFromList(int howMuchEmailsToMark){
        waitForElementPresence(FLAG_EMAIL_LOCATOR);
        List<WebElement> listOfEmailFlags = driver.findElements(FLAG_EMAIL_LOCATOR);
        for (int i = 0; i < (howMuchEmailsToMark); i++){
            listOfEmailFlags.get(i).click();
        }
        return this;
    }

    public InboxPage clickFlagsSortButton(){
        waitForElementPresence(FLAG_SORT_BUTTON_LOCATOR);
        driver.findElement(FLAG_SORT_BUTTON_LOCATOR).click();
        return this;
    }

    public int getNumberOfFlagedEmailsAndUnflag(){
        Utils.threadSleep();
        List<WebElement> listOfEmailFlags = driver.findElements(DRAFTS_ITEM_LOCATOR);
        int numberOfFlaggedElements = listOfEmailFlags.size();

        for (int i = 0; i < numberOfFlaggedElements; i++) {
            waitForElementPresence(FLAGGED_EMAIL_LOCATOR);
            driver.findElement(FLAGGED_EMAIL_LOCATOR).click();
            Utils.threadSleep();
        }
        return numberOfFlaggedElements;
    }

    public String getSubjectOfFirstEmailInList(){
        waitForElementPresence(SENT_ITEM_LOCATOR);
        String subject = driver.findElement(SENT_ITEM_LOCATOR).getAttribute("innerHTML").substring(0, 12);

        return subject;
    }

    public InboxPage clickCheckBoxOfEmail(){
        waitForElementPresence(CHECKBOX_OF_EMAIL_ITEM_LOCATOR);
        driver.findElement(CHECKBOX_OF_EMAIL_ITEM_LOCATOR).click();
        return this;
    }

    public InboxPage clickMoveButtonAndSelectInbox(){
        waitForElementPresence(MOVE_BUTTON);
        driver.findElement(MOVE_BUTTON).click();
        waitForElementPresence(MOVE_INBOX_ITEM_LOCATOR);
        driver.findElement(MOVE_INBOX_ITEM_LOCATOR).click();
        return this;
    }
}
