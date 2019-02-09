package com.epam.atm.pages;

import com.epam.atm.Utils.Scroller;
import com.epam.atm.Utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.rmi.CORBA.Util;
import java.util.List;

public class InboxPage extends AbstractPage {

    @FindBy(xpath = "./descendant::span[contains(text(), 'Написать письмо')][1]")
    private WebElement composeButton;

    @FindBy(xpath = "//span[@class='b-datalist__item__subj__snippet']")
    private WebElement draftsItem;

    @FindAll(@FindBy(xpath = "//span[@class='b-datalist__item__subj__snippet']"))
    private  List<WebElement> allDraftsOnThePage;

    @FindBy(css = "div[class='b-datalist__empty__block']")
    private WebElement noDraftsMessage;

    @FindBy(xpath = "./descendant::span[contains(text(), 'Отправленные')][1]")
    private WebElement sentMenuItem;

    @FindBy(xpath = "//div[@class='b-datalist__item__panel']//div[@class='b-datalist__item__subj']")
    private WebElement sentItem;

    @FindBy(xpath = "//div[@class='b-datalist__item__panel']//div[@class='b-datalist__item__subj']")
    private List<WebElement> allSentItemsOnthePage;

    @FindBy(id = "PH_logoutLink")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[@data-bem='b-flag']")
    private WebElement flagEmail;

    @FindBy(xpath = "//div[@data-cache-key='500000_undefined_false']" +
            "//div[@class='b-toolbar__group']/div[@data-name='flagged' and @class='b-toolbar__btn b-toolbar__btn_grouped']")
    private WebElement flagFilterButton;

    @FindBy(xpath = "//div[@class='b-datalist__item__flag']/*")
    private WebElement flaggedEmail;

    @FindAll(@FindBy( xpath = "//div[@class='b-datalist__item__flag']/*"))
    private List<WebElement> allFlaggedEmailOnthePage;

    @FindBy(xpath = "(//div[@class='b-datalist__body']//div[@class='js-item-checkbox b-datalist__item__cbx'])[1]")
    private WebElement checkBoxOfEmail;

    @FindBy(xpath = "(//div[@data-group='moveTo']/div[@class='b-dropdown__ctrl js-shortcut']//span[@class='b-dropdown__ctrl__text'])[1]")
    private WebElement moveButton;

    @FindBy(xpath = "(//div[@class='b-dropdown__group b-dropdown__group_']//a[@data-bem='b-dropdown__list__params'])[1]")
    private WebElement moveInboxItem;

    @FindAll(@FindBy(xpath = "./descendant::span[contains(text(), 'Входящие')]"))
    private List<WebElement> inboxMenuItem;

    @FindBy(xpath = "//div[@class='w-portal-footer']")
    private WebElement footer;

    public InboxPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ComposePage clickComposeButton(){
        composeButton.click();
        return new ComposePage(driver);
    }

    public ComposePage clickDraftsItem(){
        driver.navigate().refresh();
        waitForElementClickable(draftsItem);
        draftsItem.click();
        return new ComposePage(driver);
    }

    public boolean isNoDraftsMessageVisible(){
        Utils.threadSleep();
        return noDraftsMessage.isDisplayed();
    }

    public InboxPage clickSentButton(){
        sentMenuItem.click();
        return new InboxPage(driver);
    }

    public List<WebElement> getSentItems(){
        return allSentItemsOnthePage;
    }

    public HomePage logOut(){
        logoutButton.click();
        return new HomePage(driver);
    }

    public InboxPage flagNumberOfEmailFromList(int howMuchEmailsToMark){
        driver.navigate().refresh();
        List<WebElement> listOfEmailFlags = allFlaggedEmailOnthePage;
        for (int i = 0; i < (howMuchEmailsToMark); i++){
            waitForElementClickable(listOfEmailFlags.get(i));
            listOfEmailFlags.get(i).click();
        }
        return this;
    }

    public InboxPage clickFlagsSortButton(){
        flagFilterButton.click();
        return this;
    }

    public int getNumberOfFlagedEmailsAndUnflag(){
        Utils.threadSleep();
        List<WebElement> listOfEmailFlags = allDraftsOnThePage;
        int numberOfFlaggedElements = listOfEmailFlags.size();

        for (int i = 0; i < numberOfFlaggedElements; i++) {
            flaggedEmail.click();
            Utils.threadSleep();
        }
        return numberOfFlaggedElements;
    }

    public String getSubjectOfFirstEmailInList(){
        return sentItem.getAttribute("innerHTML").substring(0, 12);
    }

    public InboxPage clickCheckBoxOfEmail(){
        driver.navigate().refresh();
        waitForElementClickable(checkBoxOfEmail);
        checkBoxOfEmail.click();
        return this;
    }

    public InboxPage clickInboxInMoveOption(){
        waitForElementClickable(moveInboxItem);
        moveInboxItem.click();
        return this;
    }

    public InboxPage clickInboxMenuOption(){
        driver.navigate().refresh();
        WebElement inbox = Utils.findVisibleElementFromList(inboxMenuItem);
        waitForElementClickable(inbox);
//        Utils.tryToClickNumberOfTimes(5, inbox);
        inbox.click();
        return new InboxPage(driver);
    }

    public void dragAndDropElement(WebElement element, WebElement target){
        new Actions(driver).dragAndDrop(element, target).build().perform();
    }
    
    public void scrollDownToToTheFooter(){
        Scroller.ScrollByVisibleElement(footer);
        Utils.highlightElement(footer);
    }

    public WebElement getInboxItem(){
        Utils.highlightElement(draftsItem);
        return draftsItem;
    }

    public WebElement getSentMenuItem(){
        Utils.highlightElement(sentMenuItem);
        return sentMenuItem;
    }
}
