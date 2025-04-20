package com.testinium.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.testinium.selenium.util.Util;

public class ProductPage extends HasHeaderWithSearchBox {
    private By productDetailDivSelector = new By.ByCssSelector("div.o-productDetail");
    private By addToBasketButtonSelector = new By.ByCssSelector("button#addBasket");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void shouldBeOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailDivSelector));
    }

    public String getProductCode() {
        List<WebElement> infoItems = findAll(new By.ByCssSelector("div.m-productDescription__infoItem"));
        WebElement productCodeItem = infoItems.stream().filter(infoItem -> infoItem.getText().startsWith("Ürün Kodu"))
                .findAny().orElse(null);
        if (productCodeItem != null) {
            return productCodeItem.findElement(new By.ByCssSelector("div.m-productDescription__infoDesc")).getText();
        }
        return "";
    }

    private String getProductPriceAsString() {
        // Örn. Sepette %70 indirim ya da "VISA KARTA ÖZEL"
        By campaignTextSelector = new By.ByCssSelector("div.m-price__campaign>span.m-price__campaignDesc");
        if (isDisplayed(campaignTextSelector)) {
            String campaingText = find(campaignTextSelector).getText();
            if (campaingText.startsWith("Sepette %") || campaingText.startsWith("VISA KARTA ÖZEL")) {
                By campaignPriceSelector = new By.ByCssSelector("div.m-price__campaign>span.m-price__campaignPrice");
                return find(campaignPriceSelector).getText();
            }
        }

        // Örn. Eski fiyat üstü çizilmiş altta yeni fiyat yazılmış
        By priceNewSelector = new By.ByCssSelector("div.m-price__list>#priceNew");
        if (isDisplayed(priceNewSelector)) {
            return find(priceNewSelector).getText();
        }

        // İlk bulunan fiyatı dön
        By firstPriceUnderDivSelector = new By.ByCssSelector("div.m-price__list>:nth-child(1)");
        return find(firstPriceUnderDivSelector).getText();
    }

    public Number getProductPrice() {
        String priceStr = getProductPriceAsString();
        return Util.convertFromTextToDouble(priceStr);
    }

    public String selectFirstAvailableSize() {
        By availableSizeSelectorBy = new By.ByCssSelector(
                "div#sizes>div.m-variation>span.m-variation__item:not(.-disabled)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(availableSizeSelectorBy));
        List<WebElement> enabledSizes = findAll(availableSizeSelectorBy);
        WebElement firstAvailableSizeElem = enabledSizes.get(0);
        String selectedSize = firstAvailableSizeElem.getText();
        firstAvailableSizeElem.click();
        return selectedSize;
    }

    public void clickAddToBasket() {
        click(addToBasketButtonSelector);

        By cardITemCountSelector = new By.ByCssSelector(
                "a>span.o-header__userInfo--text>span.o-header__userInfo--count");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardITemCountSelector));
    }

}
