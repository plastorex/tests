package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


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

    @Test
    public void Task2Test() {
        String testURL = "https://pl.wikipedia.org/wiki/Wiki";

        //wikipedia language dropdown steps
        webDriver.get(testURL);
        WebElement languageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p-lang-btn")));
        languageButton.click();
        WebElement languagesList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'row uls-language-list uls-lcd']")));
        List<WebElement> languages = languagesList.findElements(By.xpath("//a[contains(@class, 'autonym')]"));
        System.out.println("List of available languages:");
        List<String> duplicateLanguages = new ArrayList<>();

        for (WebElement language : languages) {
            String languageName = language.getText();

            if (!duplicateLanguages.contains(languageName)) {
                duplicateLanguages.add(languageName);

                //English language case handling
                if (languageName.equalsIgnoreCase("english")) {
                    String languageUrl = language.getAttribute("href");
                    System.out.println(languageName + " - " + languageUrl);
                } else
                    System.out.println(languageName);
            }
        }
    }
}
