package com.saucelabs.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.saucelabs.gestures.AndroidGestures;

import io.appium.java_client.AppiumDriver;

public class ReviewPage extends HeaderPage
{
	AppiumDriver driver;

	public ReviewPage(AppiumDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//*[@content-desc='test-Description']/android.widget.TextView[1]")
	private List<WebElement> productTitles;
	
	@FindBy(xpath = "//*[@content-desc='test-Description']/android.widget.TextView[1]")
	private List<WebElement> productDescriptions;
	
	@FindBy(xpath = "//*[@content-desc='test-Price']/android.widget.TextView")
	private List<WebElement> productprices;
	
	@FindBy(xpath = "//*[@content-desc='test-CHECKOUT: OVERVIEW']/android.view.ViewGroup/android.widget.TextView")
	private List<WebElement> productsOverviews;
	
	@FindBy(xpath = "//*[@content-desc='test-CANCEL']")
	private WebElement cancelButton;
	
	@FindBy(xpath = "//*[@content-desc='test-FINISH']")
	private WebElement finishButton;
	
	public List<String> getItemsinReviewPage()
	{
		return productTitles.stream().map(reviewProductName -> reviewProductName.getText()).collect(Collectors.toList());
	}
	
	public String getProductDetailsFromReviewPage(String productName) throws InterruptedException
	{
		
		String itemName ="", itemPrice ="";
		List<String> reviewItemList = getItemsinReviewPage();
		while(true)
		{
			reviewItemList = getItemsinReviewPage();
			if(reviewItemList.contains(productName))
			{	
			break;
			}
			else
			{
			AndroidGestures.scroll(driver);
			Thread.sleep(3000);
			}
		}
		WebElement product = productTitles.stream().filter(productTitle -> productTitle.getText().equalsIgnoreCase(productName)).
	             findFirst().orElse(null);
		itemName = product.getText();
		try
		{
			itemPrice = productprices.get(reviewItemList.indexOf(itemName)).getText();	
		}
		catch(Exception e)
		{
			 AndroidGestures.scroll(driver);
			 Thread.sleep(3000);
			 reviewItemList = getItemsinReviewPage();
			 itemPrice = productprices.get(reviewItemList.indexOf(itemName)).getText();
		}
		System.out.println(itemName + " " + itemPrice);
		return itemName + " " + itemPrice;	
	}

	public ConfirmationPage finishOrder()
	{
		AndroidGestures.scrollToText(driver, "FINISH");
		finishButton.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}

}
