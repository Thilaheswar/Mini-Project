package com.cts.pla.factory;
 
import java.time.Duration;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
 
public class DriverSetupFactory {
 
	
 
	private static WebDriver driver;
	
	private DriverSetupFactory() {}
 
	public static WebDriver getDriver(String browserType)
	{
		try 
		{
			if (driver == null) 
			{
				switch (browserType.toLowerCase()) {
				
				case "chrome":
					driver = new ChromeDriver();
					break;
				case "firefox":
					driver = new FirefoxDriver();
					break;
				
				default:
					throw new IllegalArgumentException("Browser type not supported: " + browserType);
				}
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Failed to initialize the browser driver: " + e.getMessage());
		}
		return driver;
	}
 
	public static void quitDriver()
	{
		if (driver != null) 
		{
			driver.quit();
			driver = null;
		}
	
}
}