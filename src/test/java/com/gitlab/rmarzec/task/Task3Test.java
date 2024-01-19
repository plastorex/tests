package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.junit.Assert;
import java.time.Duration;
import java.util.ArrayList;


public class Task3Test {

    private DriverFactory driverFactory;
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driverFactory = new DriverFactory();
        webDriver = driverFactory.initDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }

    @AfterMethod
    public void cleanup() {
        webDriver.quit();
    }

    @Test
    public void Task3Test() {
        String testURL = "https://www.google.com/";
        String expectedURL = "https://www.w3schools.com/tags/tag_select.asp";
        String selectOption = "Opel";

        //cookies handling on google
        webDriver.get(testURL);
        WebElement cookiesPopUp1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CXQnmb")));
        if (cookiesPopUp1.isDisplayed()) {
            cookiesPopUp1.findElement(By.id("L2AGLb")).click();
        }

        //steps for lucky search
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class = 'gLFyf']")));
        searchBox.sendKeys("HTML select tag - W3Schools");
        WebElement searchSuggestionBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='lJ9FBc']")));
        WebElement feelingLuckyButton = searchSuggestionBox.findElement(By.xpath("//input[@class='RNmpXc']"));
        feelingLuckyButton.click();

        //assertion
        Assert.assertEquals(expectedURL, webDriver.getCurrentUrl());

        if (!webDriver.getCurrentUrl().equals(expectedURL)) {
            System.out.println("Current site URL: " + webDriver.getCurrentUrl());
            webDriver.get(expectedURL);
        }

        //cookies handling on w3schoools
        WebElement cookiesPopUp2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='sn-inner']")));
        if (cookiesPopUp2.isDisplayed()) {
            webDriver.findElement(By.id("accept-choices")).click();
        }

        //further steps
        WebElement tryItButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Try it Yourself »']")));
        tryItButton.click();
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='runbtn']")));
        WebElement frameElement = webDriver.findElement(By.id("iframeResult"));
        webDriver.switchTo().frame(frameElement);

        //header steps
        WebElement header = webDriver.findElement(By.xpath("//h1[text()='The select element']"));
        System.out.println("Header: " + header.getText());

        //select element steps
        WebElement selectElement = webDriver.findElement(By.xpath("//select[@id='cars']"));
        Select select = new Select(selectElement);
        select.selectByVisibleText(selectOption);
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println("Selected element's text: " + selectedOption.getText());
        System.out.println("Selected element's value: " + selectedOption.getAttribute("value"));
    }
}
