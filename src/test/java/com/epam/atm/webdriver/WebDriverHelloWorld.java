package com.epam.atm.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class WebDriverHelloWorld {
    private WebDriver webDriver;
    private final String accountName = "cfoxtrot";
    private final String domainName = "@inbox.ru";
    private final String password = "n5pYZu5dmeqHVzp";
    private final String toText = "maksim_yermachonak@epam.com";
    private String subjectText;
    private final String bodyText = "Hi, I'm testing auto tests";
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @BeforeClass(description = "Browser start")
    public void startBrowser() {
        //System.setProperty("webdriver.chrome.driver", "/Users/maksim_yermachonak/Documents/EPAM/ATM2018/05WebDriver/chromedriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        webDriver = new ChromeDriver(chromeOptions);
    }

    @BeforeMethod(description = "Open mail.ru")
    public void openWebSite() {
        webDriver.get("https://mail.ru/");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod(description = "Close browser")
    public void closeBrowser() {
        webDriver.quit();
    }

    @Test
    public void emailFlowTest() throws InterruptedException {

        webDriver.manage().window().maximize();
        WebElement accountNameField = webDriver.findElement(By.cssSelector("input[name='login']"));
        Select domainNameField = new Select(webDriver.findElement(By.cssSelector("select[id='mailbox:domain']")));
        WebElement passwordTextField = webDriver.findElement(By.cssSelector("input[id='mailbox:password']"));
        WebElement loginButton = webDriver.findElement(By.cssSelector("label[id='mailbox:submit']"));

        accountNameField.sendKeys(accountName);
        passwordTextField.sendKeys(password);
        domainNameField.selectByVisibleText(domainName);
        loginButton.click();

        WebElement composeEmailButton = (new WebDriverWait(webDriver, 30))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(By
                        //.cssSelector("a[data-title='Написать письмо (N)']")));
                        .xpath("./descendant::span[contains(text(), 'Написать письмо')][1]")));
                        //span[contains(text(), 'Написать письмо')]
                        //.xpath("//*[@id='ry9HKWr']/div/div/div[2]/div/a")));
        composeEmailButton.click();

        WebElement toTextField = webDriver.findElement(By.xpath("//textarea[@data-original-name='To']"));
        toTextField.sendKeys(toText);
        WebElement subjectTextField = webDriver.findElement(By.xpath("//input[@name='Subject']"));
        subjectTextField.sendKeys(generateRandomString(12));
        webDriver.switchTo().frame(0);
        WebElement bodyTextArea = webDriver.findElement(By.xpath("//*[@id='tinymce']"));
        bodyTextArea.sendKeys(bodyText);
        webDriver.switchTo().defaultContent();
        Thread.sleep(2000);
        WebElement saveDraft = webDriver.findElement(By.xpath("./descendant::span[contains(text(), 'Сохранить')][1]"));
        saveDraft.click();
        Thread.sleep(2000);
        WebElement draftsButton = webDriver.findElement(By.xpath("./descendant::span[contains(text(), 'Черновики')][1]"));
        draftsButton.click();

        WebElement draftEmailItem = new WebDriverWait(webDriver, 10)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='b-datalist__item__subj__snippet']")));
        draftEmailItem.click();

        WebElement emailLabel = new WebDriverWait(webDriver, 10)
        .ignoring(NoSuchElementException.class)
        .until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("./descendant::span[contains(text(), 'maksim_yermachonak@epam.com')][2]")));
        Assert.assertEquals(emailLabel.getText(), toText, "saved email is incorrect");

        Assert.assertEquals(subjectTextField.getAttribute("value"), subjectText, "saved subject is incorrect");

        webDriver.switchTo().frame(0);
        Assert.assertEquals(bodyTextArea.getText().split("\n")[0], bodyText, "saved email is incorrect");
        webDriver.switchTo().defaultContent();

        WebElement sendButton = webDriver.findElement(By.xpath("//div[@data-name='send']"));
        sendButton.click();

        WebElement draftButton = webDriver.findElement(By.xpath("./descendant::span[contains(text(), 'Черновики')][1]"));
        draftButton.click();
        WebElement noItemsElement = webDriver.findElement(By.xpath("//span[@class= 'b-datalist__empty__text' " +
                "and contains(text(), 'У вас нет незаконченных или неотправленных писем.')][1]"));
        Assert.assertTrue(noItemsElement.isDisplayed());

        WebElement sentButton = new WebDriverWait(webDriver, 10)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("./descendant::span[contains(text(), 'Отправленные')][1]")));
        retryingFindClick(sentButton);
        List<WebElement> sentItems = webDriver.findElements(By.xpath("//div[@class='b-datalist__item__panel']//div[@class='b-datalist__item__subj']"));

        Assert.assertTrue(isElementWithTextInList(subjectText, sentItems));

        WebElement logOffButton = webDriver.findElement(By.id("PH_logoutLink"));
        logOffButton.click();
    }

    private boolean retryingFindClick(WebElement webElement) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                webElement.click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    private String generateRandomString(int count){
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return subjectText = builder.toString();
    }

    private boolean isElementWithTextInList(String text, List<WebElement> list){
        for (WebElement item : list){
            if (item.getText().substring(0, 12).equals(text)){
                return true;
            }
        }
        return false;
    }
}
