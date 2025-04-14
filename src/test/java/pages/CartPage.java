package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

	WebDriver driver;
	WebDriverWait wait;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	private By CheckOutBtn = By.xpath("//a[normalize-space()='Checkout']");
	private By OrderBtn = By.xpath("//button[normalize-space()='Place order']");
	private By checkAddress = By.xpath("//input[@type='radio' and @formcontrolname ='addr']");

	private By successHeader = By.xpath("//h1[normalize-space()='Success!']");
	private By successText = By.xpath("//div[contains(@class,'alert-card-success')]//p");
	private By okBtn = By.xpath("//a[normalize-space()='OK']");

	public void jsClick(By locator) {
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void clickCheckOut() {
		jsClick(CheckOutBtn);
	}

	public void clickOrderButton() {

		try {
			WebElement radio = wait.until(ExpectedConditions.visibilityOfElementLocated(checkAddress));
			if (!radio.isSelected()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
			}
		} catch (Exception e) {
			System.out.println("Radio button for address not found or already selected.");
		}

		jsClick(OrderBtn);
	}

	// Wait for and return success message
	public String getSuccessMessage() {
		WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader));
		WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(successText));

		if (!header.isDisplayed()) {
			throw new AssertionError("Success popup did not appear.");
		}

		return text.getText();
	}

	// click the OK button on the header message
	// Click OK button on success popup
	public void dismissSuccessPopup() {
		jsClick(okBtn);
	}

}
