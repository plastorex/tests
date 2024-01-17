package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.tools.ant.taskdefs.SetPermissions.NonPosixMode.fail;


public class Task2Test {

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
    public void Task2Test() {
        String testURL = "https://pl.wikipedia.org/wiki/Wiki";

        //Execution of test
        webDriver.get(testURL);
        waitForLoad(webDriver);

        WebElement langDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p-lang-btn")));
        langDropdown.click();

        WebElement langList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'row uls-language-list uls-lcd']")));
        List<WebElement> languages = langList.findElements(By.xpath(".//a[contains(@class, 'autonym')]"));

        //printing out languages
        System.out.println("List of available languages:");
        for (WebElement lang : languages) {
            String langName = lang.getText();

            // if the current language is English, print it along with its URL
            if (langName.equalsIgnoreCase("english")) {
                String languageUrl = lang.getAttribute("href");
                System.out.println(langName + " - " + languageUrl);
            } else
                System.out.println(langName);
        }
    }
}
