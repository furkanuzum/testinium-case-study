package com.testinium.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {
    static WebDriver webDriver = null;

    @BeforeAll
    public static void setUp() {
        String operatingSystem = System.getProperty("os.name");
        System.out.println("operatingSystem:" + operatingSystem);
        if(operatingSystem.startsWith("Windows")){
            System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\driver\\chromedriver.exe");
        }else{
            System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-notifications");
        options.addArguments("disable-popup-blocking");
        setWebDriver(new ChromeDriver(options));
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }

    private static void setWebDriver(WebDriver webDriver) {
        BaseTest.webDriver = webDriver;
    }

    @AfterAll
    public static void tearDown() {
        getWebDriver().quit();
    }

}
