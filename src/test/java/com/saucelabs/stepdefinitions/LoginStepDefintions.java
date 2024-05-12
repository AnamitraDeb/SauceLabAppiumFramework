package com.saucelabs.stepdefinitions;

import java.net.MalformedURLException;

import org.testng.Assert;

import com.saucelabs.pages.LoginPage;
import com.saucelabs.pages.ProductPage;
import com.saucelabs.pages.SettingsPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginStepDefintions {
	
	public LoginPage loginPage;
	public ProductPage productPage;
	public SettingsPage settingsPage;

	public LoginStepDefintions()
	{
		loginPage = Hooks.pageObjectManager.getLoginPage();
	}

	@Given("user logs in with username as {string} and password as {string}")
	public void user_logs_in_with_username_as_and_password_as(String username, String password)
			throws MalformedURLException {
		productPage = loginPage.loginWithCreds(username, password);
	}

	@Then("verify error message as {string}")
	public void verify_error_message_as(String errormessage) {
		String errorPopupText = loginPage.getErrorMessage();
		Assert.assertEquals(errorPopupText, errormessage, "Login error message not retrieved or content not matching");
	}

	@Then("verify if login is successful")
	public void verify_if_login_is_successful() {
		Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
	}

	@Then("user logs out")
	public void user_logs_out() {
		settingsPage = productPage.goToSettingsPage();
		loginPage = settingsPage.logOut();
	}

	@Then("verify if logout is successful")
	public void verify_if_logout_is_successful() {
		Assert.assertTrue(loginPage.isUserTextFieldDisplayed(), "Logout Failed");
	}
}
