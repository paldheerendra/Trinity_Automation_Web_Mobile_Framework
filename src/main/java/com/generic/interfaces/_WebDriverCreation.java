package com.generic.interfaces;

import org.openqa.selenium.WebDriver;

import com.generic.utils.Configuration;
import com.generic.utils.NetworkMonitor;

/**
 * WebDriver Creation
 * @author Dheerendra Singh
 */
public interface _WebDriverCreation{
	abstract WebDriver setWebDriver(Configuration objConfiguration, NetworkMonitor networkMonitor) throws Exception;
}
