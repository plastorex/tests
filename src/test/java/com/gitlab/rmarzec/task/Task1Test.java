package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;


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
