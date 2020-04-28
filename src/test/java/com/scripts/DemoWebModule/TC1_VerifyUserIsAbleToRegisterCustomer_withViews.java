package com.scripts.DemoWebModule;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.generic.BaseTest;
import com.generic.customAnnotation.ActionToPerformOn;
import com.generic.customAnnotation.TestDataSource;
import com.generic.utils.GenericUtils;
import com.view.DemoWebModule.RegistrationView;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

/**	
 *  Verify that user is able to register customer
 * 	@author : Harshvardhan Yadav (Expleo)
 */
public class TC1_VerifyUserIsAbleToRegisterCustomer_withViews extends BaseTest 
{
	@Title("TC_1")
	@Description("Verify that user is able to register customer")
	@ActionToPerformOn(webAction="web")
	@TestDataSource(testCaseID = "TC1", dataSource = "/DemoTest/Regression")
	@Test(dataProvider = "TestDataProvider", groups = {"Web", "Regression"})
	public void tc1_VerifyUserIsAbleToAddAccountInSetup(Hashtable<String, String> dataSet)
	{
		initializeWebEnvironment(dataSet);
		// Register new customer 
		PageObjectManager(RegistrationView.class).newUserRegistration(
				dataPool("title"), dataPool("firstName"), dataPool("lastName"), 
				GenericUtils.getRequiredDateWithCustomYear(dataPool("dateOfBirth"), "DD-MMM-YYYY", ""), dataPool("gender"), 
				dataPool("emailAddress"), dataPool("address1"), dataPool("address2"), dataPool("town"), dataPool("postcode"), 
				dataPool("county"), dataPool("telephone"), dataPool("username"), dataPool("password"), dataPool("confirmPassword"));
	}

	@AfterMethod(groups = {"Web"})
	private void tearDown(){
		tearDownWebEnvironment(); 
	}
}