package com.generic.interfaces;

import org.openqa.selenium.By;

/**
 * Web browser actions
 * @author Dheerendra Singh
 */
public interface _WebActions{
	abstract boolean click(By locator);
	abstract boolean setText(By locator, String content);
	abstract boolean setTextWithClear(By locator, String content);
	abstract boolean bootStrapSetText(By locator, String content);
	abstract String getText(By locator);
	abstract String getAttribute(By locator, String strategy);
	abstract boolean selectFromDropDown(By locator, String... options);
	abstract boolean selectFromCustomDropDown(By locator, String option);
	abstract boolean checkElementDisplayed(By locator);
	abstract boolean checkElementNotDisplayed(By locator); 
	abstract boolean selectCheckbox(By locator, Boolean status); 
	abstract boolean selectRadioButton(By locator, Boolean status); 
	abstract boolean isCheckBoxSelected(By locator); 
	abstract boolean isRadioButtonSelected(By locator); 
}
