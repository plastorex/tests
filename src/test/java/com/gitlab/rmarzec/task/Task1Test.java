package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;


public class Task1Test {

    @Test
    public void Task1Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();

        //Navigate to webpage
        webDriver.get("https://www.onet.pl/");

        //cleanup
        webDriver.quit();
    }
}
