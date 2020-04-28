package com.generic.utils;

import com.generic.Pojo;
/**
 * Navigation to URL for AUT
 * @author Harshvardhan Yadav(Expleo)
 */
public class WebDomains 
{
	private Pojo objPojo;
	
	// Constructor
	public WebDomains(Pojo pojo){
		objPojo = pojo;
	}

	public void getURLForWeb(){
	 	String strURL = objPojo.getConfiguration().getConfig("web.Url");
		try
		{ 
			objPojo.getWebDriverProvider().getDriver().get(strURL);
			objPojo.getLogReporter().webLog("Get URL '" + strURL + "'", true);
		}catch(Exception exception){
			objPojo.getLogReporter().webLog("Get URL '" + strURL + "'", false);
		}
	}
	
	public void getURLForMobile(){
	 	String strURL = objPojo.getConfiguration().getConfig("web.Url");
		try
		{ 
			objPojo.getAppiumDriverProvider().getAppiumDriver().get(strURL);
			objPojo.getLogReporter().mobileLog("Get URL '" + strURL + "'", true);
		}catch(Exception exception){
			objPojo.getLogReporter().mobileLog("Get URL '" + strURL + "'", false);
		}
	}
}