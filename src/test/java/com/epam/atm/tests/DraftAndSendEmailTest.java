package com.epam.atm.tests;

import com.epam.atm.models.Email;
import com.epam.atm.utils.Utils;
import com.epam.atm.pages.ComposePage;
import com.epam.atm.pages.HomePage;
import com.epam.atm.pages.InboxPage;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DraftAndSendEmailTest extends BaseTest {

    @Test
    public void createDraftAndSendItTest(){
        HomePage homePage = new HomePage(driver);
        ComposePage composePage = homePage.logIn().clickComposeButton();
        Email email = new Email();

        composePage.composeEmailAndSave(email.getToText(), email.getSubjectText(), email.getBodyText())
                .clickDraftsButton()
                .clickDraftsItem();

        Assert.assertEquals(composePage.getToText(), email.getToText(), "recipient email didn't match");
        Assert.assertEquals(composePage.getSubjectText(), email.getSubjectText(), "subject text didn't match");
        Assert.assertEquals(composePage.getBodyText(), email.getSubjectText(), "Body text didn't match");

        InboxPage inboxPage = composePage.clickSendButton()
                .clickDraftsButton();

        Assert.assertTrue(inboxPage.isNoDraftsMessageVisible(), "No drafts message was not displayed");
        inboxPage.clickSentButton();

        Assert.assertTrue(Utils.isElementWithTextInList(inboxPage.getSentItems(), email.getSubjectText()));
        inboxPage.logOut();
    }

    @Test
    public void flagEmailsAndFilterByIt(){
        int numberOfEmailsToFlag = 5;
        HomePage homePage = new HomePage(driver);

        InboxPage inboxPage = homePage.logIn()
                .clickSentButton()
                .flagNumberOfEmailFromList(numberOfEmailsToFlag)
                .clickFlagsSortButton();
        Assert.assertEquals(numberOfEmailsToFlag, inboxPage.getNumberOfFlagedEmailsAndUnflag(),
                "number of flagged emails and number of sorted emails is not equal");
    }

    @Test
    public void moveSentEmailToInboxAndBackToSent(){
        HomePage homePage = new HomePage(driver);

        InboxPage inboxPage = homePage.logIn()
                .clickSentButton()
                .clickCheckBoxOfEmail();

        String subjectText = inboxPage.getSubjectOfFirstEmailInList();
        new Actions(driver).sendKeys("v").build().perform();
        inboxPage.clickInboxInMoveOption();
        inboxPage = inboxPage.clickInboxMenuOption();
        inboxPage.clickCheckBoxOfEmail()
                .dragAndDropElement(inboxPage.getInboxItem(), inboxPage.getSentMenuItem());
        inboxPage.scrollDownToToTheFooter();
    }
}
