package com.generic.webDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.generic.utils.Configuration;
import com.generic.utils.NetworkMonitor;
/**
 * used to initiate selenium webdriver for automation  
 * @author Dheerendra Singh
 *
 */
public class WebDriverProvider extends WebDriverFactory 
{
	private WebDriver webDriver;
	
	/**
	 *  initialize selenium webdriver for automation
	 */
	public WebDriver initialize(Configuration objConfiguration, NetworkMonitor networkMonitor){
		try
		{
		   	webDriver = this.setWebDriver(objConfiguration, networkMonitor);
			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(objConfiguration.getConfig("driver.implicitlyWait")), TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(objConfiguration.getConfig("driver.pageLoadTimeout")), TimeUnit.SECONDS);
			webDriver.manage().timeouts().setScriptTimeout(Integer.parseInt(objConfiguration.getConfig("driver.scriptTimeoutWait")), TimeUnit.SECONDS);
			
			return webDriver;
		}
		catch(Exception exception){
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 *  initialize selenium webdriver for automation
	 */
	public void tearDown(){
		try{
	 		webDriver.quit();
		}
		catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
	public WebDriver getDriver(){
		return webDriver;
	}
}