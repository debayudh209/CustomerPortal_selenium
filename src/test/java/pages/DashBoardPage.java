package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashBoardPage {
	WebDriver driver;
	WebDriverWait wait;

	// constructor
	public DashBoardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// locators:
	// private By loaderOverlay = By.cssSelector(".loading-container");
	private By selectDistributor = By.xpath("//input[@type = 'radio' and @id= '3']");
	private By confirmButton = By.xpath("//button[normalize-space()='Confirm']");
	private By searchBox = By.xpath("//input[@id='search-box']");
	private By qtyInput = By.xpath("//input[@name='quantity']");
	private By AddtoCart = By.xpath("//button[normalize-space()='Add to Cart']");
	private By cartIcon = By.cssSelector("a[title='Cart']");
	private By popupButton = By.xpath("//a[normalize-space()='Yes']");
	
	private By MyAccountMenu = By.xpath("//span[normalize-space()='My Account']");

	 private By LogoutMenu= By.xpath("//div[@class='dropdown-menu']//a[@class='dropdown-item'][normalize-space()='Logout']");

	// methods 1: common method to click elments that need a javascript to be
	// clicked:
	public void jsClick(By locator) {
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	// method2: select the distributor from the pop-up
	public void selectDistributor() {
		WebElement radioBtn = wait.until(ExpectedConditions.elementToBeClickable(selectDistributor));
		radioBtn.click();
	}

	// method 3: Confirm the distributor
	public void confirmDistributor() {
		WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
		confirmBtn.click();
	}

	// method 4: Type the item code in the searchbox
	public void enterItemCode(String itemCode) {

		WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", search);
		search.clear();
		search.sendKeys(itemCode);

	}

	// method 5: Select the item that matches with the itemCode
	public void selectItemfromDropdown(String itemCode) throws InterruptedException {
		By itemOption = By.xpath(
				"//span[contains(@class,'mdc-list-item__primary-text') and contains(text(),'" + itemCode + "')]");

		jsClick(itemOption); // call the jsclick function to click the item.
		Thread.sleep(1000);
	}

	// method 6: enter the required qty for the item:
	public void enterQuantity(String quantity) {
		WebElement Enteredqty = wait.until(ExpectedConditions.elementToBeClickable(qtyInput));
		
		// Scroll into view and use JS to set the value
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Enteredqty);
		//Clear the existing value (using JS for reliability)
	    ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", Enteredqty);

	 // Set the new quantity value safely
	    ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];",Enteredqty, quantity);
	}

	// method 7: click AddtoCart button for the qty:
	public void clickAddToCart() {

		jsClick(AddtoCart);
	}

	// method 8: if the replace cart pop-up comes, select No:
	public void dismissPopupIfVisible() {
		try {

			jsClick(popupButton);
		} catch (Exception ignore) {

		}
	}

	// method 9: CLick the cart icon once all items have been added:
	public CartPage clickCartIcon() {
		// WebElement cart =
		// wait.until(ExpectedConditions.presenceOfElementLocated(cartIcon));
		jsClick(cartIcon);
		return new CartPage(driver);
	}
	
	public void logOut()
	{
		WebElement accountMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(MyAccountMenu));
        
        Actions actions = new Actions(driver);
        actions.moveToElement(accountMenu).pause(500).perform();
       // WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(LogoutMenu));
       // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutBtn);
        jsClick(LogoutMenu);

    }
	

}

