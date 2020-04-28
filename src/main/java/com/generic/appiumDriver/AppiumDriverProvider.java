package com.generic.appiumDriver;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;

import com.generic.utils.Configuration;

import io.appium.java_client.AppiumDriver;
/**
 * used to initiate selenium webdriver for automation  
 * @author Harshvardhan Yadav(Expleo)
 *
 */
public class AppiumDriverProvider extends AppiumDriverFactory 
{
	// Local variables
	private AppiumDriver<?> appiumDriver;
	private LogEntries appiumLogEntries;

	/**
	 *  initialize mobile appium driver for automation
	 */
	public void initializeConsoleBaseAppium(String mobileEnvironment, Configuration objConfiguration, String appiumServerIP, String appiumServerPort) throws Exception
	{
		switch (mobileEnvironment.toLowerCase())
		{
		case "android":
			appiumDriver = (AppiumDriver<?>) this.setNPMAndroidDriver(objConfiguration, appiumServerIP, appiumServerPort);
			break;
		}

		appiumLogEntries = appiumDriver.manage().logs().get(LogType.DRIVER); 
	}

	/**
	 *  initialize mobile appium driver for automation
	 */
	public void initializeDesktopAppium(String mobileEnvironment, Configuration objConfiguration) throws Exception
	{
		switch (mobileEnvironment.toLowerCase())
		{
		case "android":
			appiumDriver = (AppiumDriver<?>) this.setDesktopAppiumAndroidDriver(objConfiguration);
			break;
		case "ios":
			appiumDriver = (AppiumDriver<?>) this.setDesktopAppiumIOSDriver(objConfiguration);
			break;
		}

		appiumLogEntries = appiumDriver.manage().logs().get(LogType.DRIVER);
	}

	/**
	 *  initialize appiumDriver for automation
	 */
	public void tearDown() throws Exception{
		appiumDriver.quit();
	}

	/**
	 * @return - appiumDriver instance
	 */
	public AppiumDriver<?> getAppiumDriver(){
		return appiumDriver;
	}

	/**
	 * @return - appiumDriver instance
	 */
	public LogEntries getAppumLogEntries(){
		return appiumLogEntries;
	}
}
