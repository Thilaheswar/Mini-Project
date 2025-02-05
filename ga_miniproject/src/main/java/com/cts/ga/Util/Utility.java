package com.cts.ga.Util;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utility {
	WebDriver driver;
	String baseurl;
	
	public Utility(WebDriver driver,String baseUrl){
		this.driver=driver;
		this.baseurl=baseUrl;
		driver.get(baseUrl);
	}
	
	public List<WebElement> getWebElement2(String type , String value){
		return driver.findElements( getWebElement(type , value)) ; 
		
	}
	public WebElement getWebElement1(String type , String value){
		return driver.findElement( getWebElement(type , value)) ; 
		
	}
	public By  getWebElement(String type,String value) {
		By webElement = null;
		switch(type) {
		case "id":
			webElement=By.id(value);
			break;
		case "name":
			webElement=By.name(value);
			break;
		case "className":
			webElement=By.className(value);
			break;
		case "cssSelector":
			webElement=By.cssSelector(value);
			break;
		case "xpath":
			webElement=By.xpath(value);
			break;
		case "linkText":
			webElement=By.xpath(value);
			break;
		default:
			throw new IllegalArgumentException("Invalid locator type: "+type);
 
		
			}
		return webElement;

	}

	public void setValues(WebElement webElement,String value) {
		webElement.sendKeys(value);
	}
	public void clickEvent(WebElement webElement) {
		webElement.click();
	}
	public void clearEvent(WebElement webElement) {
		webElement.clear();
	}
 
}