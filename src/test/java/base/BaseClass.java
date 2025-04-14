package base;
import utils.ConfigReader;
import utils.MyListener;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {
	protected WebDriver driver;

	@BeforeClass
	public void init() {
		driver = new ChromeDriver();
		// Set WebDriver instance for MyExtentReport (for screenshot generation)
		  MyListener.setDriver(driver); 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("http://45.77.221.159:89/login");
		String appUrl = ConfigReader.get("app.url");
        driver.get(appUrl);
	}
	
	
	@AfterClass
	public void tearDown()
	{
		if(driver !=null)
		{
			driver.quit();
		}
	}
}
