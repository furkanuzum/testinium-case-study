package com.testinium.selenium.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends HasHeaderWithSearchBox {
    By homeDivSelector = new By.ByCssSelector("div.o-home");
    By acceptCookiesButtonSelector = new By.ByCssSelector("button#onetrust-accept-btn-handler");
    By genderManButtonSelector = new By.ByCssSelector("button#genderManButton");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(boolean isFirstTime) {
        webDriver.navigate().to("https://www.beymen.com/tr");
        if (isFirstTime) {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButtonSelector));
            click(acceptCookiesButtonSelector);
            wait.until(ExpectedConditions.elementToBeClickable(genderManButtonSelector));
            click(genderManButtonSelector);
        }
    }

    public void shouldBeOpened() {
        assertEquals(webDriver.getTitle(), "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu");
        assertTrue(isDisplayed(homeDivSelector));
    }

    public void submitSearch() {
        WebElement searchBox = webDriver.findElement(By.cssSelector("input[id='o-searchSuggestion__input']"));
        searchBox.sendKeys(Keys.RETURN);

    }
}
