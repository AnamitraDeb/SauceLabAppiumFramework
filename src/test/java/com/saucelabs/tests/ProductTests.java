package com.saucelabs.tests;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucelabs.pages.CartPage;
import com.saucelabs.pages.ConfirmationPage;
import com.saucelabs.pages.ProductDetailsPage;
import com.saucelabs.pages.ProductPage;
import com.saucelabs.pages.ReviewPage;
import com.saucelabs.pages.SettingsPage;
import com.saucelabs.pages.UserInformationPage;
import com.saucelabs.testutils.BaseTest;
import com.saucelabs.utilities.DataLoader;

public class ProductTests extends BaseTest {
	
	ProductPage productPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
	CartPage cartPage;
	UserInformationPage userInformationPage;
	ReviewPage reviewPage;
	ConfirmationPage confirmationPage;
	
	@Test(description = "User tries to login and check Product deatils of a given Product", dataProvider = "getData" ,
		  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
	public void selectProductFromCatalogueAndCheckDetails(HashMap<String, String> input) 
	{		
		String productName = input.get("productName");
		
		logger.info("Logging in with invalid username and password");
		
		productPage = loginPage.loginWithCreds(input.get("validUsername"), input.get("validPassword"));
		Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
		
		logger.info("Retrieving details from Product catalogue page");

		String detailsRetrievedFromProductCatalogue = productPage.getProductDetailsFromProductCataloguePage(productName);
		
		logger.info("Retrieving details from Product details page");
		
		productDetailsPage = productPage.goToProductDetailsPage(productName);
		
	    String detailsRetrievedFromProductDetailsPage = productDetailsPage.getProductTitle()+ " " + productDetailsPage.getProductPrice();
	    
	    Assert.assertEquals(detailsRetrievedFromProductDetailsPage, detailsRetrievedFromProductCatalogue, "Product Details not matching");

	}
		
	@DataProvider(name = "getData")
	public Object[][] getData()
	{
		List<HashMap<String, String>> dataList = DataLoader.readJsonFiles("productsCheck.json");
		return DataLoader.getMappedData(dataList);
		
	}
}
