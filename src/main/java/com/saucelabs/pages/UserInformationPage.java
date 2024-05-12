package com.saucelabs.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class UserInformationPage extends HeaderPage
{

	AppiumDriver driver;
	
	public UserInformationPage(AppiumDriver driver) 
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-First Name']")
	private WebElement firstNameTextField;
	
	@FindBy(xpath = "//*[@content-desc='test-Last Name']")
	private WebElement lastNameTextField;
	
	@FindBy(xpath = "//*[@content-desc='test-Zip/Postal Code']")
	private WebElement zipCodeTextField;
	
	@FindBy(xpath = "//*[@content-desc='test-CANCEL']")
	private WebElement cancelButton;
	
	@FindBy(xpath = "//*[@content-desc='test-CONTINUE']")
	private WebElement continueButton;
	
	public void enterDetails(String firstName, String lastName, String zipCode)
	{
		firstNameTextField.sendKeys(firstName);
		lastNameTextField.sendKeys(lastName);
		zipCodeTextField.sendKeys(zipCode);
	}
	
	 public ReviewPage goToReviewPage()
	 {
		 continueButton.click();
		 ReviewPage reviewPage = new ReviewPage(driver);
		 return reviewPage;
	 }
	

}
