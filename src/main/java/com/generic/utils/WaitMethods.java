package com.generic.utils;

import java.util.concurrent.TimeUnit;
/**
 * @author Dheerendra Singh
 */
public class WaitMethods 
{	
	// thread.sleep 
	public void sleep(int timeUnitSeconds) 
	{
		try {
			Thread.sleep(TimeUnit.MILLISECONDS.convert(timeUnitSeconds, TimeUnit.SECONDS));
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
}
