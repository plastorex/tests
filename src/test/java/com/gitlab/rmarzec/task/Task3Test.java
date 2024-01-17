package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.junit.Assert;

import java.time.Duration;

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

    void waitForLoad(WebDriver driver) {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    @Test
    public void Task3Test(){
        String testURL = "https://www.google.com/";
        String expectedURL = "https://www.w3schools.com/tags/tag_select.asp";

        //Execution of test
        webDriver.get(testURL);
        waitForLoad(webDriver);

        WebElement cookiesPopUp = webDriver.findElement(By.id("CXQnmb"));
        if (cookiesPopUp.isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("L2AGLb"))).click();
        }

        //steps for lucky search
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@class = 'gLFyf']")));
        searchBox.sendKeys("HTML select tag - W3Schools");

        WebElement inputSuggestionButtonsDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='lJ9FBc']")));
        WebElement feelingLuckyButton = inputSuggestionButtonsDiv.findElement(By.xpath("//input[@class='RNmpXc']"));
        feelingLuckyButton.click();

        //Assertion
                Assert.assertEquals(expectedURL, webDriver.getCurrentUrl());

        if (!webDriver.getCurrentUrl().equals("https://www.w3schools.com/tags/tag_select.asp")) {
            System.out.println("Current site URL: " + webDriver.getCurrentUrl());
            webDriver.get("https://www.w3schools.com/tags/tag_select.asp");
            waitForLoad(webDriver);
        }


        try {
            WebElement w3schoolsCookieBanner = webDriver.findElement(By.id("accept-choices"));
            if (w3schoolsCookieBanner.isDisplayed()) {
                w3schoolsCookieBanner.click();
            }
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
        }


                WebElement tryItButton = webDriver.findElement(By.xpath("//a[text()='Try it Yourself »']"));
        tryItButton.click();
        waitForLoad(webDriver);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("iframeResult"));


                WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='The select element']")));

        webDriver.switchTo().defaultContent();




    }
}
