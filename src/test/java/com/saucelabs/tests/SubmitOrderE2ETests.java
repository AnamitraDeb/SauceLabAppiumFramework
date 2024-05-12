package com.saucelabs.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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

public class SubmitOrderE2ETests extends BaseTest {
	
	ProductPage productPage;
	SettingsPage settingsPage;
	ProductDetailsPage productDetailsPage;
	CartPage cartPage;
	UserInformationPage userInformationPage;
	ReviewPage reviewPage;
	ConfirmationPage confirmationPage;
	
	
	@Test(description = "User tries to login and submit order for a product", dataProvider = "getData" ,
			  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
		public void submitOrderForSingleProduct(HashMap<String, String> input) throws InterruptedException
		{		
			String productName = input.get("productName");
			
			logger.info("Logging in with username and password");
			
			productPage = loginPage.loginWithCreds(input.get("validUsername"), input.get("validPassword"));
			Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
			
			logger.info("Logging in successful");
			logger.info("Retrieving details from Catalogue Page");

			String detailsRetrievedFromProductCatalogue = productPage.getProductDetailsFromProductCataloguePage(productName);
			
			productDetailsPage = productPage.goToProductDetailsPage(productName);
			
			logger.info("Retrieving details from Details Page");
			
		    String detailsRetrievedFromProductDetailsPage = productDetailsPage.getProductTitle()+ " " + productDetailsPage.getProductPrice();
		    
		    Assert.assertEquals(detailsRetrievedFromProductDetailsPage, detailsRetrievedFromProductCatalogue, "Product Details not matching");
		    
		    logger.info("Adding product to Cart");
		    
		    productDetailsPage.addToCart();
		    cartPage = productDetailsPage.goToCartsPage();
		    
		    logger.info("Checking products in cart");
		    
		    String detailsRetrievedFromCartPage = cartPage.getCartItemDetails(productName);
		    Assert.assertEquals(detailsRetrievedFromCartPage, detailsRetrievedFromProductDetailsPage, "Product Details not matching");
		    
		    logger.info("Checking out products in cart");
		    logger.info("Entering user details");
		    
		    userInformationPage = cartPage.checkOutCartItems();
		    userInformationPage.enterDetails(input.get("firstName"), input.get("lastName"), input.get("zipcode"));
		    reviewPage = userInformationPage.goToReviewPage();
		    
		    logger.info("Checking products in Review page");
		    
		    String detailsRetrievedFromReviewPage = reviewPage.getProductDetailsFromReviewPage(productName);
		    Assert.assertEquals(detailsRetrievedFromReviewPage,detailsRetrievedFromCartPage , "Product Details not matching");
		    
		    logger.info("Submitting order");
		    
		    confirmationPage = reviewPage.finishOrder();
		    List<String> confirmationMessage = confirmationPage.getConfirmationMessage();
		    
		    logger.info("Verifying confirmation message");
		    
		    Assert.assertTrue(confirmationMessage.contains("THANK YOU FOR YOU ORDER"), "Order not submitted");
		    productPage = confirmationPage.goToProductsPage();
		    
		    
		    Assert.assertTrue(productPage.productDashboardDisplay(), "Not able to return to Products Page");
		    settingsPage = productPage.goToSettingsPage();
		    
		    logger.info("Logging out");
		    
		    settingsPage.logOut();
		    Assert.assertTrue(loginPage.isUserTextFieldDisplayed(), "Logout not successfull");		    		    
		}
	

	@Test(description = "User tries to login and submit order for multiple products", dataProvider = "getData" ,
			  retryAnalyzer = com.saucelabs.listeners.RetryListener.class)
		public void submitOrderForMultipleProducts(HashMap<String, String> input) throws InterruptedException
		{
		    logger.info("Logging in with username and password");
		
		    productPage = loginPage.loginWithCreds(input.get("validUsername"), input.get("validPassword"));
		    Assert.assertTrue(productPage.productDashboardDisplay(), "Login Failed");
		
		    List<String> productNames = Arrays.asList(input.get("multiProducts").split(","));
		    
		    String detailsRetrievedFromProductCatalogue, detailsRetrievedFromProductDetailsPage, detailsRetrievedFromCartPage; 
		    String detailsRetrievedFromReviewPage;
		    
		    List<String> detailsRetrievedFromProductDetailsPageList = new ArrayList<String>();
		    List<String> detailsRetrievedFromCartPageList = new ArrayList<String>();
		    List<String> detailsRetrievedFromReviewPageList = new ArrayList<String>();
		    
		    logger.info("Adding products to cart");
		    
			for(String productName : productNames)
			{
				detailsRetrievedFromProductCatalogue = productPage.getProductDetailsFromProductCataloguePage(productName);
				productDetailsPage = productPage.goToProductDetailsPage(productName);
				
				detailsRetrievedFromProductDetailsPage = productDetailsPage.getProductTitle()+ " " + productDetailsPage.getProductPrice();
				detailsRetrievedFromProductDetailsPageList.add(detailsRetrievedFromProductDetailsPage);
				
				Assert.assertEquals(detailsRetrievedFromProductDetailsPage, detailsRetrievedFromProductCatalogue, "Product Details not matching");
				
				productDetailsPage.addToCart();
				
				productPage = productDetailsPage.goBackToProductsPage();
				Assert.assertTrue(productPage.productDashboardDisplay(), "Unable to return to product catalogue page");
			}
			
		    cartPage = productDetailsPage.goToCartsPage();
		    
		    logger.info("Checking products added to cart and checking out");
		    
		    int numberOFItemsInCart = cartPage.getNumberOfItemsAddedInCart();
		    
		    System.out.println("No of items in cart==>" + numberOFItemsInCart);
		    
		    Assert.assertEquals(numberOFItemsInCart, productNames.size(), "Unable to add all products in Cart");
		    for(String productName : productNames)
		    {
		    	detailsRetrievedFromCartPage = cartPage.getCartItemDetails(productName);
		    	detailsRetrievedFromCartPageList.add(detailsRetrievedFromCartPage);
		    }
		    
		    Assert.assertEquals(detailsRetrievedFromCartPageList, detailsRetrievedFromProductDetailsPageList, 
		    		           "All added products are not in cart");
		    
		    logger.info("Entering user information");
		    
		    userInformationPage = cartPage.checkOutCartItems();
		    userInformationPage.enterDetails(input.get("firstName"), input.get("lastName"), input.get("zipcode"));
		    reviewPage = userInformationPage.goToReviewPage();
		    
		    logger.info("Checking products in review page");

		    System.out.println("------------ReviewPage------------");
		    for(String productName : productNames)
		    {
		    	detailsRetrievedFromReviewPage = reviewPage.getProductDetailsFromReviewPage(productName);
                detailsRetrievedFromReviewPageList.add(detailsRetrievedFromReviewPage);
		    }

		    Assert.assertEquals(detailsRetrievedFromReviewPageList, detailsRetrievedFromCartPageList, "Product Details not matching");
		    
		    logger.info("Submit order and verify confirmation meesage");
		    
		    confirmationPage = reviewPage.finishOrder();
		    List<String> confirmationMessage = confirmationPage.getConfirmationMessage();
		    
	        Assert.assertTrue(confirmationMessage.contains("THANK YOU FOR YOU ORDER"), "Order not submitted");
		    productPage = confirmationPage.goToProductsPage();
		    
	    
		    Assert.assertTrue(productPage.productDashboardDisplay(), "Not able to return to Products Page");
		    settingsPage = productPage.goToSettingsPage();
		    
		    logger.info("Logging out");
		    
		    settingsPage.logOut();
		    Assert.assertTrue(loginPage.isUserTextFieldDisplayed(), "Logout not successfull");		    		    
		
		}
	
	
	
	@DataProvider(name = "getData")
	public Object[][] getData()
	{
		List<HashMap<String, String>> dataList = DataLoader.readJsonFiles("submitOrder.json");
        return DataLoader.getMappedData(dataList);
	}
}
