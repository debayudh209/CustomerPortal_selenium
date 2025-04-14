package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// declare the locators:

	private By passwordtabLoc = By.xpath("//a[@id='password-tab']");
	private By userNameloc = By.xpath("//input[@placeholder='Username']");
	private By PasswordLoc = By.xpath("//input[@placeholder='Password']");
	private By loginBtnLoc = By.xpath("//button[@id='sign-in']");
	
	

	
	//add the methods:
	
 public void clickPassWordTab()
 {
	 wait.until(ExpectedConditions.elementToBeClickable(passwordtabLoc)).click();
 }
 
 public void enterUsername(String username) {
     driver.findElement(userNameloc).sendKeys(username);
 }
 
 public void enterPassword(String password) {
     driver.findElement(PasswordLoc).sendKeys(password);
     
 }
 
 public DashBoardPage clickLoginButton()
 {
	 wait.until(ExpectedConditions.elementToBeClickable(loginBtnLoc)).click();
	 return new DashBoardPage(driver);
 }
 


}
