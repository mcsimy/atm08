package com.epam.atm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends AbstractPage {
    private static final String ACCOUNT_NAME = "cfoxtrot";
    private static final String DOMAIN_NAME = "@inbox.ru";
    private static final String PASSWORD = "n5pYZu5dmeqHVzp";

    @FindBy(css = "input[name='login']")
    private WebElement accountNameInput;

    @FindBy(css = "select[id='mailbox:domain']")
    private WebElement domainDropdown;

    @FindBy(css = "input[id='mailbox:password']")
    private WebElement passwordInput;

    @FindBy(css = "label[id='mailbox:submit']")
    private WebElement loginButton;

    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public HomePage fillAccountNameField(String accountName){
        accountNameInput.sendKeys(accountName);
        return this;
    }

    public HomePage fillPasswordField(String password){
        passwordInput.sendKeys(password);
        return this;
    }

    public HomePage selectDomain(String domain){
        new Select(domainDropdown).selectByVisibleText(domain);
        return this;
    }

    public InboxPage clickLoginButton(){
        loginButton.click();
        return new InboxPage(driver);
    }

    public InboxPage logIn(){
        InboxPage inboxPage = new HomePage(driver)
                .fillAccountNameField(ACCOUNT_NAME)
                .selectDomain(DOMAIN_NAME)
                .fillPasswordField(PASSWORD)
                .clickLoginButton();
        return inboxPage;
    }
}
