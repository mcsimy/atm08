package com.epam.atm.webdriver;

import com.epam.atm.Utils;
import com.epam.atm.pages.ComposePage;
import com.epam.atm.pages.HomePage;
import com.epam.atm.pages.InboxPage;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DraftAndSendEmailTest extends BaseTest {
    private static final String ACCOUNT_NAME = "cfoxtrot";
    private static final String DOMAIN_NAME = "@inbox.ru";
    private static final String PASSWORD = "n5pYZu5dmeqHVzp";
    private static final String TO_TEXT = "max.yermachonak@gmail.com";
    private static final String BODY_TEXT = "Hi, I'm testing auto tests";
    private String subjectText;


    @Test
    public void createDraftAndSendItTest(){
        ComposePage composePage = logIn().clickComposeButton();

        subjectText = Utils.generateRandomString(12);
        composePage.fillToTextField(TO_TEXT)
                .fillSubjectTextField(subjectText)
                .fillBodyTextArea(BODY_TEXT)
                .clickSaveButton()
                .clickDraftsButton()
                .clickDraftsItem();

        Assert.assertEquals(composePage.getToText(), TO_TEXT, "recipient email didn't match");
        Assert.assertEquals(composePage.getSubjectText(), subjectText, "subject text didn't match");
        Assert.assertEquals(composePage.getBodyText(), BODY_TEXT, "Body text didn't match");

        InboxPage inboxPage = composePage.clickSendButton()
                .clickDraftsButton();

        Assert.assertTrue(inboxPage.isNoDraftsMessageVisible(), "No drafts message was not displayed");
        inboxPage.clickSentButton();

        Assert.assertTrue(Utils.isElementWithTextInList(inboxPage.getSentItems(), subjectText));
        inboxPage.logOut();
    }

    @Test
    public void flagEmailsAndFilterByIt(){
        int numberOfEmailsToFlag = 5;
        InboxPage inboxPage = logIn()
                .clickSentButton()
                .flagNumberOfEmailFromList(numberOfEmailsToFlag)
                .clickFlagsSortButton();
        Assert.assertEquals(numberOfEmailsToFlag, inboxPage.getNumberOfFlagedEmailsAndUnflag(),
                "number of flagged emails and number of sorted emails is not equal");
    }

    //test is not complete
    @Test
    public void moveSentEmailToInboxAndBackToSent(){
        InboxPage inboxPage = logIn()
                .clickSentButton()
                .clickCheckBoxOfEmail();

        subjectText = inboxPage.getSubjectOfFirstEmailInList();
        new Actions(driver).sendKeys("v").build().perform();
        inboxPage.clickInboxInMoveOption();
        inboxPage = inboxPage.clickInboxMenuOption();
        inboxPage.clickCheckBoxOfEmail()
                .dragAndDropElement(inboxPage.getInboxItem(), inboxPage.getSentMenuItem());
    }

    private InboxPage logIn(){
        InboxPage inboxPage = new HomePage(driver)
                .fillAccountNameField(ACCOUNT_NAME)
                .selectDomain(DOMAIN_NAME)
                .fillPasswordField(PASSWORD)
                .clickLoginButton();
        return inboxPage;
    }
}
