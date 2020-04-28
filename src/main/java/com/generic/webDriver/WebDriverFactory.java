package com.generic.webDriver;

import java.io.IOException;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.generic.interfaces._WebDriverCreation;
import com.generic.utils.Configuration;
import com.generic.utils.NetworkMonitor;

import net.lightbody.bmp.client.ClientUtil;
/**
 * used to initiate selenium webdriver for automation  
 * @author Harshvardhan Yadav(Expleo)
 */
public class WebDriverFactory implements _WebDriverCreation
{
	public WebDriver setWebDriver(Configuration objConfiguration, NetworkMonitor networkMonitor) throws Exception
	{
		WebDriver webDriver;
		String browser = objConfiguration.getConfig("web.browser").trim().toLowerCase();

		switch (browser)
		{ 
		case "ie":
			webDriver = new InternetExplorerDriver(this.internetExplorerOptions(objConfiguration));
			break;

		case "chrome":   
			webDriver = new ChromeDriver(this.chromeOptions(objConfiguration, networkMonitor));
			break;

		default:
			webDriver = new ChromeDriver(this.chromeOptions(objConfiguration, networkMonitor));
		}
		return webDriver;
	} 

	private InternetExplorerOptions internetExplorerOptions(Configuration objConfiguration) throws IOException
	{
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + objConfiguration.getConfig("webdriver.ie.driver").trim());
		InternetExplorerOptions options = new InternetExplorerOptions();
		//options.addCommandSwitches("initialBrowserUrl", "about:blank");
		//options.addCommandSwitches("ie.ensureCleanSession", "true");
		return options;
	}

	private ChromeOptions chromeOptions(Configuration objConfiguration, NetworkMonitor networkMonitor) throws IOException
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver_win32/chromedriver.exe");		
		ChromeOptions options = new ChromeOptions(); 

		if (objConfiguration.getConfig("browsermob.proxy").equalsIgnoreCase("true")) {
			Proxy seleniumProxy = ClientUtil.createSeleniumProxy(networkMonitor.getNetworkMonitorProxy());
			options.setProxy(seleniumProxy);	
			//options.setAcceptInsecureCerts(true); 
		}
 
		//options.setBinary(System.getProperty("user.dir") + "/src/main/resources/browsers/Chrome/Application/chrome.exe");

		return options;
	}
}