package com.testinium.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.testinium.selenium.pages.BasketPage;
import com.testinium.selenium.pages.HomePage;
import com.testinium.selenium.pages.ProductPage;
import com.testinium.selenium.pages.SearchPage;
import com.testinium.selenium.util.Util;

public class SearchProductAddToBasketTest extends BaseTest {
    static Logger log = LogManager.getLogger();

    @Test
    public void sampleTest() throws IOException {
        String[] inputs = Util.readInputsFromExcel();
        String searchTerm1 = inputs[0];
        String searchTerm2 = inputs[1];

        BasketPage basketPage;

        HomePage homePage = new HomePage(getWebDriver());
        homePage.open(true);
        homePage.shouldBeOpened();

        homePage.typeIntoSearchBox(searchTerm1);
        homePage.clearSearchBox();

        int retryCount = 1;
        long availableItemCountForProduct;
        String selectedProductCode;
        String selectedSize;
        String selectedProductUrl;
        Number productAmount;
        do {
            if (retryCount > 1) {
                log.warn("Üründen stokta sadece 1 adet olduğu için farklı bir ürün seçimi yapılıyor");
            }
            homePage.typeIntoSearchBox(searchTerm2);
            SearchPage searchPage = homePage.submitSearch(true);

            searchPage.shouldBeOpened();
            ProductPage productPage = searchPage.clickOnARandomProductPhoto();

            productPage.shouldBeOpened();
            selectedProductCode = productPage.getProductCode();
            selectedSize = productPage.selectFirstAvailableSize();
            selectedProductUrl = webDriver.getCurrentUrl();
            productAmount = productPage.getProductPrice();
            productPage.clickAddToBasket();
            productPage.waitForSepeteEklendiNotificationToDisappear();

            basketPage = productPage.clickOnMyCart();
            basketPage.shouldBeOpened(false);

            Number basketTotalAmount = basketPage.getGrandTotalAmount();
            log.info("basketTotalAmount:" + basketTotalAmount);
            assertEquals(basketTotalAmount, productAmount);

            availableItemCountForProduct = basketPage.getAvailableItemCountForProduct();
            if (availableItemCountForProduct < 2) {
                basketPage.clearBasket();
                homePage.open(false);
                homePage.shouldBeOpened();
            }
            retryCount++;
        } while (availableItemCountForProduct < 2 && retryCount < 5);

        basketPage.setItemCount(2);
        basketPage.clearBasket();
        basketPage.shouldBeOpened(true);

        Util.writeToOutputFile(String.join("\n",
                "productUrl:" + selectedProductUrl,
                "productCode:" + selectedProductCode,
                "size:" + selectedSize,
                "amount:" + productAmount
                ));
    }
}
