package com.generic.appiumDriver;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.generic.interfaces._AppiumDriverCreation;
import com.generic.utils.CommandPrompt;
import com.generic.utils.Configuration;
import com.generic.utils.DeviceConfigurationAndroid;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * used to initiate appium driver for automation  
 * @author Dheerendra Singh
 */
public class AppiumDriverFactory implements _AppiumDriverCreation
{
	//* initiate android driver instance using npm and node js on windows*/
	public AndroidDriver<?> setNPMAndroidDriver(Configuration objConfiguration, String appiumServerIP, String appiumServerPort) throws Exception
	{
		// Load APK/IPA properties file
		Properties objAppConfig = new Properties();

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app"))
			objAppConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/mobileResources/apk_ipa/android/" + objConfiguration.getConfig("test.app.name") + "_config.properties"));

		// Set the capabilities for AndroidDriver
		DesiredCapabilities capabilities = new DesiredCapabilities();
		DeviceConfigurationAndroid objDeviceConfigurationAndroid = new DeviceConfigurationAndroid();

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app"))
			capabilities.setCapability("app", System.getProperty("user.dir") + "/src/main/resources/mobileResources/apk_ipa/android/" + objConfiguration.getConfig("test.app.name")+".apk");

		capabilities.setCapability("deviceName", objDeviceConfigurationAndroid.getDeviceName());
		capabilities.setCapability("platformName", objDeviceConfigurationAndroid.getDevicePlatForm());
		capabilities.setCapability("platformVersion", objDeviceConfigurationAndroid.getDevicePlatFormVersion());
		capabilities.setCapability("enablePerformanceLogging", true);
		capabilities.setCapability("automationName", "UiAutomator2");

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("web")) {
			capabilities.setCapability("browserName", "Chrome");
		}
		else if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app")) {
			capabilities.setCapability("appPackage", objAppConfig.getProperty("app.Package"));
			capabilities.setCapability("appActivity", objAppConfig.getProperty("app.Activity"));
			capabilities.setCapability("appWaitActivity", objAppConfig.getProperty("app.WaitActivity"));
		}
		capabilities.setCapability("orientation", objConfiguration.getConfig("mobile.orientation"));
		capabilities.setCapability("newCommandTimeout", objConfiguration.getConfig("appium.NewCommandTimeout"));
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("printPageSourceOnFindFailure", true);
		capabilities.setCapability("appWaitDuration", objConfiguration.getConfigIntegerValue("mobile.appWaitDuration"));
		capabilities.setCapability("unicodeKeyboard", true);     // to hide keyboard permanantly throught the app
		capabilities.setCapability("resetKeyboard", true);     // to hide keyboard permanantly throught the app
		//capabilities.setCapability("autoGrantPermissions", true);

		capabilities.setCapability("uiautomator2ServerInstallTimeout", 40000);
		capabilities.setCapability("uiautomator2ServerLaunchTimeout", 40000);
		capabilities.setCapability("skipServerInstallation", false);
		capabilities.setCapability("appWaitDuration", 80000);
		capabilities.setCapability("deviceReadyTimeout", 5);

		if(Boolean.parseBoolean(objConfiguration.getConfig("mobile.android.clearAppChache")))
			this.androidClearAppCache(objConfiguration, objAppConfig);

		@SuppressWarnings("rawtypes")
		AndroidDriver<?> objAndroidDriver = new AndroidDriver(new URL("http://" + appiumServerIP + ":" + appiumServerPort + "/wd/hub"), capabilities);
		System.out.println("Appium server connected..............");
		System.out.println("appiumServerIP---------->" + appiumServerIP);
		System.out.println("appiumServerPort---------->" + appiumServerPort);

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("web")) {
		 	objAndroidDriver.manage().timeouts().implicitlyWait(Integer.parseInt(objConfiguration.getConfig("driver.implicitlyWait")), TimeUnit.SECONDS);
			objAndroidDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(objConfiguration.getConfig("driver.pageLoadTimeout")), TimeUnit.SECONDS);
			objAndroidDriver.manage().timeouts().setScriptTimeout(Integer.parseInt(objConfiguration.getConfig("driver.scriptTimeoutWait")), TimeUnit.SECONDS);
		}
	 
		return objAndroidDriver;
	}

	//* initiate android driver instance using desktop appium application on windows*/
	public AndroidDriver<?> setDesktopAppiumAndroidDriver(Configuration objConfiguration) throws Exception
	{
		// Load APK/IPA properties file
		Properties objAppConfig = new Properties();
		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app"))
			objAppConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/application/android/" + objConfiguration.getConfig("test.app.name") + "_config.properties"));
		
		DeviceConfigurationAndroid objDeviceConfigurationAndroid = new DeviceConfigurationAndroid();

		// Set the capabilities for AndroidDriver
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app"))
			capabilities.setCapability("app", System.getProperty("user.dir") + "/src/main/resources/application/" + objConfiguration.getConfig("test.app.apk")+".apk");
		
		capabilities.setCapability("deviceName", objDeviceConfigurationAndroid.getDeviceName());
		capabilities.setCapability("platformName", objDeviceConfigurationAndroid.getDevicePlatForm());
		capabilities.setCapability("platformVersion", objDeviceConfigurationAndroid.getDevicePlatFormVersion());
		capabilities.setCapability("enablePerformanceLogging", true);
		capabilities.setCapability("automationName", "UiAutomator2");

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("web")) {
			capabilities.setCapability("browserName", "Chrome");
		}
		else if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("app")) {
			capabilities.setCapability("appPackage", objAppConfig.getProperty("app.Package"));
			capabilities.setCapability("appActivity", objAppConfig.getProperty("app.Activity"));
			capabilities.setCapability("appWaitActivity", objAppConfig.getProperty("app.WaitActivity"));
		}
		capabilities.setCapability("orientation", objConfiguration.getConfig("mobile.orientation"));
		capabilities.setCapability("newCommandTimeout", objConfiguration.getConfig("appium.NewCommandTimeout"));
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("printPageSourceOnFindFailure", true);
		capabilities.setCapability("appWaitDuration", objConfiguration.getConfigIntegerValue("mobile.appWaitDuration"));
		capabilities.setCapability("unicodeKeyboard", true);     // to hide keyboard permanantly throught the app
		capabilities.setCapability("resetKeyboard", true);     // to hide keyboard permanantly throught the app
		//capabilities.setCapability("autoGrantPermissions", true);

		if(Boolean.parseBoolean(objConfiguration.getConfig("mobile.android.clearAppChache")))
			this.androidClearAppCache(objConfiguration, objAppConfig);

		@SuppressWarnings("rawtypes")
		AndroidDriver<?> objAndroidDriver = new AndroidDriver(new URL("http://" + objConfiguration.getConfig("desktop.appium.ip") + ":" + objConfiguration.getConfig("desktop.appium.port") + "/wd/hub"), capabilities);
		System.out.println("Appium connected..............");
		System.out.println("appiumServerIP---------->" +objConfiguration.getConfig("desktop.appium.ip"));
		System.out.println("appiumServerPort---------->" + objConfiguration.getConfig("desktop.appium.port"));

		if(objConfiguration.getConfig("mobile.test").equalsIgnoreCase("web")) {
		 	objAndroidDriver.manage().timeouts().implicitlyWait(Integer.parseInt(objConfiguration.getConfig("driver.implicitlyWait")), TimeUnit.SECONDS);
			objAndroidDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(objConfiguration.getConfig("driver.pageLoadTimeout")), TimeUnit.SECONDS);
			objAndroidDriver.manage().timeouts().setScriptTimeout(Integer.parseInt(objConfiguration.getConfig("driver.scriptTimeoutWait")), TimeUnit.SECONDS);
		}

		return objAndroidDriver;
	}


	//************* Need to update DesiredCapabilities of iOS *********//
	public IOSDriver<?> setDesktopAppiumIOSDriver(Configuration objConfiguration) throws Exception
	{
		System.out.println("####Into setDesktopAppiumIOSDriver");
		// Load APK/IPA properties file
		Properties objAppConfig = new Properties();
		objAppConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/application/iOS/" + objConfiguration.getConfig("test.app.name") + "_config.properties"));

		// Set the capabilities for AndroidDriver
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "SQS iPhone 6+ 05");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11");
		capabilities.setCapability("udid","FF339E04FAA2D7B2E860DB1669AE3DA0034DFB62");
		capabilities.setCapability("bundleid", objAppConfig.getProperty("app.bundleid"));
		capabilities.setCapability("enablePerformanceLogging", true);
		capabilities.setCapability("newCommandTimeout", objConfiguration.getConfig("test.appium.newCommandTimeout"));
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("unicodeKeyboard", true);     // to hide keyboard permanantly throught the app

		@SuppressWarnings("rawtypes")
		IOSDriver<?> iOSDriver = new IOSDriver(new URL("http://" + objConfiguration.getConfig("desktop.appium.ip") + ":" + objConfiguration.getConfig("desktop.appium.port") + "/wd/hub"), capabilities);

		return iOSDriver;
	}

	public void androidClearAppCache(Configuration objConfiguration, Properties objAppConfig) {
		CommandPrompt.runCommand("adb shell pm clear " + objAppConfig.getProperty("app.Package"));
	} 
}