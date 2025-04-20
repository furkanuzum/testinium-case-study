package com.testinium.selenium.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.testinium.selenium.util.Util;

public class BasketPage extends BasePage {
    private By myBasketTitleSelector = new By.ByCssSelector("h3.m-basket__header--title");
    private By buyButtonSelector = new By.ByCssSelector("button#nextBtn");
    private By grandTotalAmountSelector = new By.ByCssSelector(
            "li.m-orderSummary__item.-grandTotal>span.m-orderSummary__value");
    private By numberOfProuducSelector = new By.ByCssSelector("select#quantitySelect0-key-0");

    private By emptyBasketSelector = new By.ByCssSelector("div#emtyCart strong.m-empty__messageTitle");

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public void shouldBeOpened(boolean withEmptyBasket) {
        assertEquals(webDriver.getTitle(), "Sepetim | Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu");
        if (withEmptyBasket) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emptyBasketSelector));
            assertEquals("SEPETINIZDE ÜRÜN BULUNMAMAKTADIR", find(emptyBasketSelector).getText());
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(myBasketTitleSelector));
            wait.until(ExpectedConditions.visibilityOfElementLocated(buyButtonSelector));

        }
    }


    public Number getGrandTotalAmount() {
        String amountStr = find(grandTotalAmountSelector).getText();
        return Util.convertFromTextToDouble(amountStr);
    }

    public long getAvailableItemCountForProduct() {
        WebElement selectElement = find(numberOfProuducSelector);
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        return options.stream().filter(option -> option.isEnabled()).count();
    }

    public void setItemCount(int count) {
        WebElement selectElement = find(numberOfProuducSelector);
        Select select = new Select(selectElement);
        select.selectByIndex(count - 1);

        assertEquals("" + (count) + " adet", select.getFirstSelectedOption().getText());
    }

    public void clearBasket() {
        By removeProductFromBasketButtonSelector = new By.ByCssSelector("button.m-basket__remove");
        List<WebElement> buttons = findAll(removeProductFromBasketButtonSelector);
        for (WebElement button : buttons) {
            button.click();
        }
    }
}
