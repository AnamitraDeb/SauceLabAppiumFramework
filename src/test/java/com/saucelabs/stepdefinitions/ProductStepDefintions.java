package com.saucelabs.stepdefinitions;

import org.testng.Assert;

import com.saucelabs.pages.CartPage;
import com.saucelabs.pages.ProductDetailsPage;
import com.saucelabs.pages.ProductPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class ProductStepDefintions {
	
	public ProductPage productPage;
	public ProductDetailsPage productDetailsPage;
	public CartPage cartPage;

	public static String detailsRetrievedFromProductCatalogue;
	public static String detailsRetrievedFromProductDetailsPage;
	public static String detailsRetrievedFromCartPage;
	
	public ProductStepDefintions()
	{
		productPage = Hooks.pageObjectManager.getProductPage();
	}

	@When("user checks product {string} from product catalogue")
	public void user_checks_product_from_product_catalogue(String productname) {
		detailsRetrievedFromProductCatalogue = productPage.getProductDetailsFromProductCataloguePage(productname);

	}

	@And("user check for the product {string} details form product details page")
	public void user_check_for_the_product_details_form_product_details_page(String productname) {
		productDetailsPage = productPage.goToProductDetailsPage(productname);

		detailsRetrievedFromProductDetailsPage = productDetailsPage.getProductTitle() + " "
				+ productDetailsPage.getProductPrice();

		Assert.assertEquals(detailsRetrievedFromProductDetailsPage, detailsRetrievedFromProductCatalogue,
				"Product Details not matching");

	}
}
