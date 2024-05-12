package com.saucelabs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class LoginPage{
	
    AppiumDriver driver;
	public LoginPage(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-Username']")
	 private WebElement userNameTextField;
	
	@FindBy(xpath = "//*[@content-desc='test-Password']")
	 private WebElement passwordTextField;
	
	@FindBy(xpath = "//*[@content-desc='test-LOGIN']")
	 private WebElement loginButton;
	
	@FindBy(xpath = "//*[@content-desc='test-Error message']/android.widget.TextView")
	 private WebElement errorPopup;
	
	public ProductPage loginWithCreds(String username, String password)
	{
		userNameTextField.sendKeys(username);
		passwordTextField.sendKeys(password);
		loginButton.click();
		ProductPage productPage = new ProductPage(driver);
		return productPage;
	}
	
	public String getErrorMessage()
	{
		return errorPopup.getText();
		
	}
	
	public boolean isUserTextFieldDisplayed()
	{
		return userNameTextField.isDisplayed();
		
	}
	
	
	
}
