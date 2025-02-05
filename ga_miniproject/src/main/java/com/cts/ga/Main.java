package com.cts.ga;
import com.cts.ga.Util.*;
import com.cts.pla.factory.DriverSetupFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;


import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main{
    public static void main(String[] args) {
        WebDriver driver = DriverSetupFactory.getDriver("chrome");
        driver.manage().window().maximize();

        String baseUrl = "https://www.snapdeal.com/";
        Utility utility = new Utility(driver, baseUrl);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement searchBox = utility.getWebElement1("name", "keyword");
            utility.setValues(searchBox, "Bluetooth headphone");

            WebElement searchButton = utility.getWebElement1("className", "searchTextSpan");
            utility.clickEvent(searchButton);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".sort-drop")));

            WebElement sortBy = utility.getWebElement1("cssSelector", ".sort-drop");
            utility.clickEvent(sortBy);

            WebElement popularityOption = utility.getWebElement1("cssSelector", "ul.sort-value li[data-sorttype='plrty']");
            utility.clickEvent(popularityOption);

            Thread.sleep(3000);

            WebElement minPrice = utility.getWebElement1("name", "fromVal");
            WebElement maxPrice = utility.getWebElement1("name", "toVal");

            utility.clearEvent(minPrice);
            utility.setValues(minPrice, "700");
            utility.clearEvent(maxPrice);
            utility.setValues(maxPrice, "1400");
            WebElement goButton = utility.getWebElement1("className", "price-go-arrow");
            utility.clickEvent(goButton);

            Thread.sleep(3000);

            List<WebElement> productNames = utility.getWebElement2("cssSelector",".product-title");
            List<WebElement> productPrices =  utility.getWebElement2("cssSelector",".lfloat.product-price");

            System.out.println("Top 5 Bluetooth Headphones within price range 700-1400:");

           
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("products");

            
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Product Name");
            headerRow.createCell(1).setCellValue("Price");

            for (int i = 0; i < 5 && i < productNames.size(); i++) {
                String name = productNames.get(i).getText();
                String price = productPrices.get(i).getText();
                System.out.println((i + 1) + ". " + name + " - " + price);

                
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(name);
                row.createCell(1).setCellValue(price);
            }

           
            try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\2375316\\Downloads\\products.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Data has been written to products.xlsx");
            }
 catch (IOException e) {
                e.printStackTrace();
            }

           
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            
            DriverSetupFactory.quitDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

