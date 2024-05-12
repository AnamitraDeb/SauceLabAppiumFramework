package com.saucelabs.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class ConfirmationPage extends HeaderPage
{
	AppiumDriver driver;

	public ConfirmationPage(AppiumDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-CHECKOUT: COMPLETE!']/android.view.ViewGroup/android.widget.TextView")
	private List<WebElement> confirmationMessage;
	
	@FindBy(xpath = "//*[@content-desc='test-BACK HOME']")
	private WebElement backToHomeButton;
	
	public List<String> getConfirmationMessage()
	{
		return confirmationMessage.stream().map(message -> message.getText()).collect(Collectors.toList());	
	}
	
	public ProductPage goToProductsPage()
	{
		backToHomeButton.click();
		ProductPage productPage = new ProductPage(driver);
		return productPage;
	}
	
	

}
