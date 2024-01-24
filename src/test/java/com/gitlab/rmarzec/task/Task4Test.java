package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.model.YTTile;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Task4Test {

    private DriverFactory driverFactory;
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driverFactory = new DriverFactory();
        webDriver = driverFactory.initDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void cleanup() {
        webDriver.quit();
    }

    @Test
    public void Task4Test() {
        List<YTTile> ytTileList = new ArrayList<>();

        //cookies handling on youtube
        webDriver.get("https://www.youtube.com/");
        webDriver.manage().window().maximize();
        WebElement cookiePopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tp-yt-paper-dialog[@id='dialog']")));
        if (cookiePopUp.isDisplayed()) {
            WebElement acceptCookiesButton = cookiePopUp.findElement(By.xpath("//span[text()='Accept all']"));
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", acceptCookiesButton);
        }
        wait.until(ExpectedConditions.invisibilityOf(cookiePopUp));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@id='text' and @class='style-scope ytd-thumbnail-overlay-time-status-renderer']")));
        List<WebElement> videoTiles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("(//div[@id='content' and @class='style-scope ytd-rich-item-renderer'])[position() <= 12]")));

        //adding not live tiles to the list
        for (WebElement videoTile : videoTiles) {

            if (!videoTile.findElements(By.xpath(".//span[@id='text' and @class='style-scope ytd-thumbnail-overlay-time-status-renderer']")).isEmpty()) {

                String title = videoTile.findElement(By.cssSelector("#video-title")).getText();
                String channel = videoTile.findElement(By.cssSelector("yt-formatted-string#text")).getText();
                String length = videoTile.findElement(By.xpath(".//span[@id='text' and @class='style-scope ytd-thumbnail-overlay-time-status-renderer']"))
                        .getAttribute("innerHTML");

                YTTile ytTile = new YTTile();
                ytTile.setTitle(title);
                ytTile.setChannel(channel);
                ytTile.setLength(length);
                ytTileList.add(ytTile);
            }
        }

        //printing out expected tiles to console
        System.out.println("Not live videos data:");
        for (YTTile ytTile : ytTileList) {
            System.out.println("Title: " + ytTile.getTitle());
            System.out.println("Length: " + ytTile.getLength().trim());
            System.out.println("Channel: " + ytTile.getChannel() + '\n');
        }
    }
}

