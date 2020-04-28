package com.generic.interfaces;

import com.generic.utils.Configuration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * Appium Creation
 * @author Harshvardhan Yadav(Expleo)
 */
public interface _AppiumDriverCreation{
	abstract AndroidDriver<?> setNPMAndroidDriver(Configuration objConfiguration, String appiumServerIP, String appiumServerPort) throws Exception;
	abstract AndroidDriver<?> setDesktopAppiumAndroidDriver(Configuration objConfiguration) throws Exception;
	abstract IOSDriver<?> setDesktopAppiumIOSDriver(Configuration objConfiguration) throws Exception;
}
