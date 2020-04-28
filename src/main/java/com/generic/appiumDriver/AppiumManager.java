package com.generic.appiumDriver;

import java.io.IOException;
import java.net.ServerSocket;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Appium Manager - this class contains method to start and stop appium server  
 * @author Harshvardhan Yadav(Expleo)
 */
public class AppiumManager 
{
	private AppiumDriverLocalService service;
	private AppiumServiceBuilder builder;
	private DesiredCapabilities cap;
	private String appiumCurrentIPAddress;
	private String appiumCurrentPort;
	/**
	 * start appium server with  : appium ip, appium port 
	 */
	public void startAppium() 
	{
		try {
			int defaultPort = 4723;
			String currentIp = "127.0.0.1";
			if(this.checkIfServerIsRunnning(defaultPort)){
				defaultPort = this.getFreePort();
			}
			this.setCurrentAppiumPort(String.valueOf(defaultPort));

			//Set Capabilities
			cap = new DesiredCapabilities();
			cap.setCapability("noReset", "false");

			//Build the Appium service
			builder = new AppiumServiceBuilder();
			builder.withIPAddress(currentIp);
			this.setCurrentAppiumIpAddress(currentIp);
			builder.usingPort(defaultPort);
			builder.withCapabilities(cap);
			builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
			builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");

			//Start the server with the builder
			service = AppiumDriverLocalService.buildService(builder);
			service.start();
			
			System.out.println("Appium service started..............");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * stop appium server 
	 */
	public void stopServer() 
	{ 
		try {
			service.stop();
			System.out.println("Appium service stopped..............");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}	

	public int getFreePort()  
	{
		try 
		{
			int port;
			ServerSocket socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			port = socket.getLocalPort(); 
			socket.close();
			return port;
		} catch ( Exception exception) {
			exception.printStackTrace();
			return 0;
		} 
	}
	
	public void setCurrentAppiumIpAddress(String appiumIPAddress){
		this.appiumCurrentIPAddress = appiumIPAddress;
	}

	public String getCurrentAppiumIpAddress(){
		return appiumCurrentIPAddress;
	}
	
	public void setCurrentAppiumPort(String appiumPort){
		this.appiumCurrentPort = appiumPort;
	}

	public String getCurrentAppiumPort(){
		return appiumCurrentPort;
	}
}
