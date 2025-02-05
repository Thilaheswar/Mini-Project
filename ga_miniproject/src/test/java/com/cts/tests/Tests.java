package com.cts.tests;

import com.cts.ga.Util.Utility;
import com.cts.pla.factory.DriverSetupFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Tests {
    private WebDriver driver;
    private Utility utility;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = DriverSetupFactory.getDriver("chrome");
        driver.manage().window().maximize();
        utility = new Utility(driver, "https://www.snapdeal.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSearchFunctionality() {
        WebElement searchBox = utility.getWebElement1("name", "keyword");
        utility.setValues(searchBox, "Bluetooth headphone");

        WebElement searchButton = utility.getWebElement1("className", "searchTextSpan");
        utility.clickEvent(searchButton);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sort-drop")));
        WebElement sortBy = utility.getWebElement1("cssSelector", ".sort-drop");
        Assert.assertTrue(sortBy.isDisplayed(), "Search functionality failed.");
    }

    @Test(dependsOnMethods = "testSearchFunctionality")
    public void testPriceFilterFunctionality() throws InterruptedException {
        WebElement minPrice = utility.getWebElement1("name", "fromVal");
        WebElement maxPrice = utility.getWebElement1("name", "toVal");

        utility.clearEvent(minPrice);
        utility.setValues(minPrice, "700");
        utility.clearEvent(maxPrice);
        utility.setValues(maxPrice, "1400");
        WebElement goButton = utility.getWebElement1("className", "price-go-arrow");
        utility.clickEvent(goButton);

        Thread.sleep(3000);

        List<WebElement> productPrices = utility.getWebElement2("cssSelector", ".lfloat.product-price");
        for (WebElement priceElement : productPrices) {
            String priceText = priceElement.getText().replaceAll("[^0-9]", "");
            int price = Integer.parseInt(priceText);
            Assert.assertTrue(price >= 700 && price <= 1400, "Price filter functionality failed.");
        }
    }

    @Test(dependsOnMethods = "testPriceFilterFunctionality")
    public void testProductDataExtraction() {
        List<WebElement> productNames = utility.getWebElement2("cssSelector", ".product-title");
        List<WebElement> productPrices = utility.getWebElement2("cssSelector", ".lfloat.product-price");

        Assert.assertTrue(productNames.size() > 0, "No products found.");
        Assert.assertTrue(productPrices.size() > 0, "No product prices found.");

        for (int i = 0; i < 5 && i < productNames.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            Assert.assertNotNull(name, "Product name is null.");
            Assert.assertNotNull(price, "Product price is null.");
        }
    }

    @AfterClass
    public void tearDown() {
        DriverSetupFactory.quitDriver();
    }
}
