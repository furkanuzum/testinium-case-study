package com.testinium.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HasHeaderWithSearchBox extends BasePage {
    private By searchBoxInputSelector = new By.ByCssSelector("div>div>input.o-header__search--input");
    private By searchFormInputSelector = new By.ByCssSelector("form>div>input.o-header__search--input");
    private By searchBoxDeleteSelector = new By.ByCssSelector("button.o-header__search--close");
    private By searchFormSelector = new By.ByCssSelector("form.o-header__search");
    private By submitSearchButtonSelector = new By.ByCssSelector("button.o-header__search--btn");

    private By myBasketButtonSelector = new By.ByCssSelector("div.o-header__userInfo>a[title~=\"Sepetim\"]");

    public HasHeaderWithSearchBox(WebDriver driver) {
        super(driver);
    }

    private boolean isSearchFormOpened() {
        return isDisplayed(searchFormSelector);
    }

    public void typeIntoSearchBox(String searchKey) {
        if (!isSearchFormOpened()) {
            click(searchBoxInputSelector);
        }
        type(searchFormInputSelector, searchKey);
    }

    public void clearSearchBox() {
        click(searchBoxDeleteSelector);
    }

    public SearchPage submitSearch(boolean usingEnterKey) {
        if (usingEnterKey) {
            pressEnter(searchFormInputSelector);
        } else {
            click(submitSearchButtonSelector);
        }
        return new SearchPage(webDriver);
    }

    public BasketPage clickOnMyCart() {
        click(myBasketButtonSelector);
        return new BasketPage(webDriver);
    }

    public void waitForSepeteEklendiNotificationToDisappear() {
        By sepeteEklendiNotificationSelector = new By.ByCssSelector("div.m-notification.success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(sepeteEklendiNotificationSelector));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(sepeteEklendiNotificationSelector));
    }

}
