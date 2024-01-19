package com.gitlab.rmarzec.framework.utils;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DriverFactory {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
    public WebDriver initDriver(){
        WebDriverManager.getInstance(FirefoxDriver.class).driverVersion("0.30.0").setup();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages", "en-GB");
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        WebDriver webDriver = new FirefoxDriver(options);
        tlDriver.set(webDriver);
        return getDriver();
    }
}
