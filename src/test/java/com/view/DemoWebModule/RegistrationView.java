package com.view.DemoWebModule;

import com.generic.BaseView;
import com.generic.Pojo;
import com.pageFactory.Web.Registration.RegistrationPage;

import ru.yandex.qatools.allure.annotations.Step;

public class RegistrationView extends BaseView
{ 
 	public RegistrationView(Pojo pojo){
		super(pojo); 
	 }
  
	@Step("Registration of new user")
	public void newUserRegistration(String title, String firstName, String lastName, String dateOfBirth, 
			String gender, String emailAddress, String address1, String address2, 
			String town, String postcode, String county, String telephone,
			String username, String password, String confirmPassword) 
	{
		 PageObjectManager(RegistrationPage.class).selectTitle(title);
		 PageObjectManager(RegistrationPage.class).setFirstName(firstName);
		 PageObjectManager(RegistrationPage.class).setLastName(lastName);
		 PageObjectManager(RegistrationPage.class).selectDateOfBirth(dateOfBirth);
		 PageObjectManager(RegistrationPage.class).selectGender(gender);
		 PageObjectManager(RegistrationPage.class).setEmail(emailAddress);
		 PageObjectManager(RegistrationPage.class).setAddress1(address1);
		 PageObjectManager(RegistrationPage.class).setAddress2(address2);
		 PageObjectManager(RegistrationPage.class).setTown(town);
		 PageObjectManager(RegistrationPage.class).setPostcode(postcode);
		 PageObjectManager(RegistrationPage.class).setCountry(county);
		 PageObjectManager(RegistrationPage.class).setTelephone(telephone);
		 PageObjectManager(RegistrationPage.class).setUserName(username);
		 PageObjectManager(RegistrationPage.class).setPassword(password);
		 PageObjectManager(RegistrationPage.class).setConfirmPassword(confirmPassword);
		 PageObjectManager(RegistrationPage.class).clickRegister();
	} 
}
