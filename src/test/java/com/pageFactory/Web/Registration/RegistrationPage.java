package com.pageFactory.Web.Registration;

import org.openqa.selenium.By;

import com.generic.Pojo;

public class RegistrationPage {

	// Local variables
	private Pojo objPojo;

	// Input
	By inpFirstName = By.id("regFirstName");
	By inpLastName = By.id("regLastName");
	By inpEmail = By.id("regEmail");
	By inpAddress1 = By.id("regAddressLine1");
	By inpAddress2 = By.id("regAddressLine2");
	By inpTelephone = By.id("regTelephone");
	By inpCountry = By.id("regTown");
	By inpPostcode = By.id("regPostcode");
	By inpUserName = By.id("regUsername");
	By inpPassword = By.id("regPassword");
	By inpConfirmPassword = By.id("regConfirmPassword");
	By inpTown = By.id("regTown");

	// Button 
	By btnRegister = By.xpath("//button[contains(text(),'Register')]");

	// Link
	By drpTitle = By.xpath("//select[@id='regTitle']");
	By drpDOBDay = By.xpath("//select[@id='regDobDay']");
	By drpDOBMonth = By.xpath("//select[@id='regDobMonth']");
	By drpDOMYear = By.xpath("//select[@id='regDobYear']");
	By drpGender = By.xpath("//select[@id='regGender']");
	
	// Checkbox
	By chk = By.id("regTermsAndConditionsAgreed");

	public RegistrationPage(Pojo pojo){
		this.objPojo = pojo;
	}

	public void selectTitle(String title) {
		objPojo.getLogReporter().webLog("Select title", title, 
				objPojo.getWebActions().selectFromDropDown(drpTitle, title));
	}

	public void setFirstName(String firstName){
		objPojo.getLogReporter().webLog("Set First Name", firstName, 
				objPojo.getWebActions().setText(inpFirstName, firstName));
	}

	public void setLastName(String lastName){
		objPojo.getLogReporter().webLog("Set Last Name", lastName, 
				objPojo.getWebActions().setText(inpLastName, lastName));
	}

	public void selectDateOfBirth(String DOB) {
		String[] arrDOB = DOB.split("-");

		objPojo.getLogReporter().webLog("Select Date Of Birth - Day ", arrDOB[0], 
				objPojo.getWebActions().selectFromDropDown(drpDOBDay, arrDOB[0]));
		objPojo.getLogReporter().webLog("Select Date Of Birth - Month ", arrDOB[1], 
				objPojo.getWebActions().selectFromDropDown(drpDOBMonth, arrDOB[1]));
		objPojo.getLogReporter().webLog("Select Date Of Birth - Year ", arrDOB[2], 
				objPojo.getWebActions().selectFromDropDown(drpDOMYear, arrDOB[2]));
	}

	public void selectGender(String gender) {
		objPojo.getLogReporter().webLog("Select gender", gender, 
				objPojo.getWebActions().selectFromDropDown(drpGender, gender));
	}

	public void setEmail(String email){
		objPojo.getLogReporter().webLog("Set email", email, 
				objPojo.getWebActions().setText(inpEmail, email));
	}

	public void setAddress1(String address1){
		objPojo.getLogReporter().webLog("Set Address 1", address1, 
				objPojo.getWebActions().setText(inpAddress1, address1));
	}

	public void setAddress2(String address2){
		objPojo.getLogReporter().webLog("Set Address 2", address2, 
				objPojo.getWebActions().setText(inpAddress2, address2));
	}

	public void setTown(String town){
		objPojo.getLogReporter().webLog("Set town", town, 
				objPojo.getWebActions().setText(inpTown, town));
	}

	public void setPostcode(String postcode){
		objPojo.getLogReporter().webLog("Set postcode", postcode, 
				objPojo.getWebActions().setText(inpPostcode, postcode));
	}

	public void setCountry(String country){
		objPojo.getLogReporter().webLog("Set country", country, 
				objPojo.getWebActions().setText(inpCountry, country));
	}

	public void setTelephone(String telephone){
		objPojo.getLogReporter().webLog("Set telephone", telephone, 
				objPojo.getWebActions().setText(inpTelephone, telephone));
	}

	public void setUserName(String userName){
		objPojo.getLogReporter().webLog("Set userName", userName, 
				objPojo.getWebActions().setText(inpUserName, userName));
	}

	public void setPassword(String password){
		objPojo.getLogReporter().webLog("Set password", password, 
				objPojo.getWebActions().setText(inpPassword, password));
	}

	public void setConfirmPassword(String confirmPassword){
		objPojo.getLogReporter().webLog("Set confirm password", confirmPassword, 
				objPojo.getWebActions().setText(inpConfirmPassword, confirmPassword));
	}

	public void selectAcceptTermsCheckBox(){
		objPojo.getLogReporter().webLog("Select 'I am at least 18 years old and I accept the Terms and Conditions and privacy policy' checkbox", 
				objPojo.getWebActions().click(btnRegister));
	}

	public void clickRegister(){
		objPojo.getLogReporter().webLog("Click 'Register' button", 
				objPojo.getWebActions().click(btnRegister));
	}

	public void verifyLogoutLinkDisplayed(){
		objPojo.getLogReporter().webLog("Verify user logged in to application.", 
				objPojo.getWebActions().checkElementDisplayed(chk));
	}
}