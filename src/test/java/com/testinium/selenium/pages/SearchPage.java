package com.testinium.selenium.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends HasHeaderWithSearchBox{

    private By searchResultTitleSelector = new By.ByCssSelector("div#productListTitle>span");
    private By productCardPhotoSelector = new By.ByCssSelector("div.m-productCard>div.m-productCard__photo");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void shouldBeOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultTitleSelector));
        assertEquals(webDriver.getTitle(), "gömlek | Beymen");
        assertTrue(find(searchResultTitleSelector).getText().startsWith("\"gömlek\" araması için ("));
    }

    public ProductPage clickOnARandomProductPhoto(){
        List<WebElement> productPhotos = findAll(productCardPhotoSelector);
        int randomInt =(int) (Math.random()*productPhotos.size());
        productPhotos.get(randomInt).click();
        return new ProductPage(webDriver);
    }
    
}
