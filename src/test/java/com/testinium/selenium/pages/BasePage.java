package com.testinium.selenium.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected WebDriver webDriver = null;
    protected WebDriverWait wait = null;

    public BasePage(WebDriver driver) {
        this.webDriver = driver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(30L));
    }

    public WebElement find(By locator) {
        return webDriver.findElement(locator);
    }

    public List<WebElement> findAll(By locator) {
        return webDriver.findElements(locator);
    }

    public void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    public void click(By locator) {
        find(locator).click();
    }

    public void pressEnter(By locator) {
        find(locator).sendKeys(Keys.RETURN);
    }

    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

}
