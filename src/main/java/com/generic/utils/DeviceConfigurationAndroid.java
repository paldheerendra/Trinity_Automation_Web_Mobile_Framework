package com.generic.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * contains adb commands to get connected android device properties. 
 * @author Dheerendra Singh
 */
public class DeviceConfigurationAndroid 
{
	private String deviceName = "";
	private String devicePlatform = "";
	private String devicePlatformVersion = ""; 
	private String deviceModel = "";
	
	public DeviceConfigurationAndroid() 
	{
	 	this.startADB();	
	 	this.getDevices();
	 	this.stopADB();
	}

 	Map<String, String> devices = new HashMap<String, String>();

	/**
	 * This method start adb server
	 * @Author        : Dheerendra
	 */
	public void startADB()
	{
		String output = CommandPrompt.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			//System.exit(0);
		}
	}

	/**
	 * This method stop adb server
	 * @Author        : Dheerendra
	 */
	public void stopADB()  
	{
		CommandPrompt.runCommand("adb kill-server");
	}

	/**
	 * This method return connected devices
	 * @return hashmap of connected devices information
	 * @Author        : Dheerendra
	 */
	public Map<String, String> getDevices() 
	{
		startADB(); // start adb service
		String output = CommandPrompt.runCommand("adb devices");
		String[] lines = output.split("\n");

		if(lines.length<=1)
		{
			System.out.println("No Device Connected");
			stopADB();
			//System.exit(0);	// exit if no connected devices found
		}

		for(int i=1;i<lines.length;i++)
		{
			lines[i]=lines[i].replaceAll("\\s+", "");

			if(lines[i].contains("device"))
			{
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = CommandPrompt.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = CommandPrompt.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = CommandPrompt.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				
				/*String getprop = cmd.runCommand("adb shell getprop");
				System.out.println("osVersion----->" + getprop);*/
		 		
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, brand);
				devices.put("model"+i, model);
				devices.put("osVersion"+i, osVersion);
			  	 
				System.out.println("Following device is connected");
				System.out.println(deviceID + " " + brand + " " + model + " " + osVersion + "\n");
				
				this.setDeviceModel(model);
				this.setDeviceName(deviceID);
				this.setDevicePlatForm("Android");
				this.setDevicePlatFormVersion(osVersion);
	  		}
			else if(lines[i].contains("unauthorized"))
			{
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];

				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}
			else if(lines[i].contains("offline"))
			{
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];

				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return devices;
	}
	
	 
	public void setDeviceName(String connectedDeviceName){
		this.deviceName = connectedDeviceName;
	}
	
	public void setDevicePlatForm(String connectedDevicePlatForm){
		this.devicePlatform = connectedDevicePlatForm;
	}
	
	public void setDevicePlatFormVersion(String connectedDevicePlatFormVersion){
		this.devicePlatformVersion = connectedDevicePlatFormVersion;
	}
	
	public void setDeviceModel(String connectedDeviceModel){
		this.deviceModel = connectedDeviceModel;
	}
	
	public String getDeviceName(){
		return deviceName;
	}
	
	public String getDevicePlatForm(){
		return devicePlatform;
	}
	
	public String getDevicePlatFormVersion(){
		return devicePlatformVersion;
	}
	
	public String getDeviceModel(){
		return deviceModel;
	}
 }