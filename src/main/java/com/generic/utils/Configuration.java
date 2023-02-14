package com.generic.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 * read write configuration properties 
 * @author Dheerendra Singh
 */
public class Configuration 
{
	private Properties config;

	/** 
	 * Load project config properties file
	 */
	public void loadConfigProperties(){
		try {
			config = new Properties();
			config.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}

	/** return string value of config paramater */
	public String getConfig(String configKey){
		try {
			if(config.getProperty(configKey) != null)
				return config.getProperty(configKey).trim();
			else {
				System.out.println("Excepted configuration properites header - '" + configKey + "' is not available in config.properties" );
				return "";
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/** return int value of config paramater */
	public int getConfigIntegerValue(String configKey){ 
		try {
			if(config.getProperty(configKey) != null)
				return Integer.parseInt(config.getProperty(configKey).trim());
			else {
				System.out.println("Excepted configuration properites header - '" + configKey + "' is not available in config.properties" );
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return 0;
		}
	}

	/** return boolean value of config paramater */
	public boolean getConfigBooleanValue(String configKey){
		try {
			if(config.getProperty(configKey) != null)
				return Boolean.parseBoolean(config.getProperty(configKey).trim());
			else{
				System.out.println("Excepted configuration properites header - '" + configKey + "' is not available in config.properties" );
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/** return config Properties object */
	public Properties getConfigProperties(){
		try {	
			return config;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	} 
}