package test;
import utils.ConfigReader;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import pages.CartPage;
import pages.DashBoardPage;
import pages.LoginPage;

public class TestCustomerPortal extends BaseClass {
	@Test
	public void LoginTest() throws InterruptedException {
		// create object of LoginPage

		LoginPage login = new LoginPage(driver);
		login.clickPassWordTab();
		
		login.enterUsername(ConfigReader.get("username"));
		login.enterPassword(ConfigReader.get("password"));

		Thread.sleep(2000);

		DashBoardPage dashboard = login.clickLoginButton(); // when we click login button, it returns Dashboard page obj
		Thread.sleep(3000);

		dashboard.selectDistributor();
		dashboard.confirmDistributor();

		// assert the current Url
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("myaccount"), "Login failed, current URL: " + currentUrl);
	}

	@Test(priority = 2)
	public void AddItemtoCart() throws InterruptedException {
		// create object of Dashboard page
		DashBoardPage dashboard = new DashBoardPage(driver);
		//addItem(dashboard, "4050", "5");
		addItem(dashboard, ConfigReader.get("item1.code"), ConfigReader.get("item1.qty"));
		//addItem(dashboard, "4051", "7");
		addItem(dashboard, ConfigReader.get("item2.code"), ConfigReader.get("item2.qty"));
		
		//addItem(dashboard, "4055", "9");
		addItem(dashboard, ConfigReader.get("item3.code"), ConfigReader.get("item3.qty"));

		dashboard.clickCartIcon();
		Thread.sleep(3000);

		String cartUrl = driver.getCurrentUrl();
		Assert.assertTrue(cartUrl.contains("cp/cart"), "Navigation to cart failed, current URL: " + cartUrl);
	}

	@Test(priority = 3)
	public void cartFunctions() throws InterruptedException {
		// from the cart page - add another item. We will re-use methods from the
		// Dashboard page.
		DashBoardPage dashboard = new DashBoardPage(driver);
		addItem(dashboard, ConfigReader.get("item4.code"), ConfigReader.get("item4.qty"));

		// go to the cart again
		dashboard.clickCartIcon();

		Thread.sleep(2000); // optional visual wait
		CartPage cart = new CartPage(driver);
		cart.clickCheckOut();

		Thread.sleep(1000);
		cart.clickOrderButton();

		String successMsg = cart.getSuccessMessage();
		System.out.println(successMsg);

		// assert the order creation:
		Assert.assertTrue(successMsg.contains("Order created successfully with"),
				"Unexpected success message: " + successMsg);

		cart.dismissSuccessPopup();

		Thread.sleep(4000);
	}

	@Test(priority = 4)
	public void LogoutfromPortal() throws InterruptedException {
		DashBoardPage dashboard = new DashBoardPage(driver);
		dashboard.logOut();

		Thread.sleep(4000);
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("login"), "Logout is not successful");

	}

	private void addItem(DashBoardPage dashboard, String itemCode, String quantity) throws InterruptedException {
		dashboard.enterItemCode(itemCode);
		dashboard.selectItemfromDropdown(itemCode);
		dashboard.enterQuantity(quantity);
		dashboard.clickAddToCart();

		dashboard.dismissPopupIfVisible();
		Thread.sleep(2000);
	}

}// end of the test class
