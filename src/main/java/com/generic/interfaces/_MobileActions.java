package com.generic.interfaces;

import org.openqa.selenium.By;

/**
 * Web browser actions
 * @author Harshvardhan Yadav(Expleo)
 */
public interface _MobileActions{
	abstract boolean click(By locator);
	abstract boolean setText(By locator, String content); 
	abstract String getText(By locator);
	abstract String getAttribute(By locator, String strategy); 
	abstract boolean checkElementDisplayed(By locator);
	abstract boolean checkElementNotDisplayed(By locator); 
}
