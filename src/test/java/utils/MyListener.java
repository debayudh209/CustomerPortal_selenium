package utils;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class MyListener implements ITestListener {
	private ExtentSparkReporter spark;
	private ExtentReports extent;
	private ExtentTest test;

	static String Projectpath = System.getProperty("user.dir");
	static String reportPath = System.getProperty("user.dir") + "/test-output/MyExtentReport.html"; // set the report
																									// path
	static WebDriver Mydriver; // we need this for screenshot capture. Will be initialized from the BaseClass

//Setter for WebDriver instance
	public static void setDriver(WebDriver webDriver) {
		Mydriver = webDriver;
	}

	@Override
	public void onStart(ITestContext context) {
		spark = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(spark);
		spark.config().setDocumentTitle("Automation Report"); // title of report
		spark.config().setReportName("Functional Test Report");
		spark.config().setTheme(Theme.DARK);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("[START] Test: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.PASS, "****Test case PASSED IS " + result.getName()); // send test status for PASS

	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName()); // create a new entry in the report
		test.log(Status.FAIL, "****Test case FAILED IS " + result.getName()); // send test status for PASS
		test.log(Status.FAIL, "****FAILED REASON IS " + result.getThrowable()); // returns the exception

		// call capture screenshot mthod that will return the screenshot path
		String screenshotPath = takeScreenshot(result.getName());
		try
		{
		test.addScreenCaptureFromPath(screenshotPath);
		} catch(Exception e)
		{
			System.out.println("Could not attach screenshot" +e.getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.SKIP, "****Test case SKIPPED IS " + result.getName()); // send test status for PASS
		test.log(Status.SKIP, "****SKIPPED REASON IS " + result.getThrowable()); // returns the exception
	}
	
	@Override
	public void onFinish(ITestContext context) {
	    extent.flush(); // This saves the report file
	}

	public static String takeScreenshot(String testName)
     {
    	 String screenshotPath = Projectpath + "/screenshots/" + testName + ".png";
    	 try
    	 {
    		 TakesScreenshot ts = (TakesScreenshot)Mydriver;
    		 File src = ts.getScreenshotAs(OutputType.FILE);
    		 File target = new File(screenshotPath);
    		 src.renameTo(target);
    	 }catch(Exception e)
    	 {
    		 System.out.println("Cannot take screenshot" + e.getMessage());
    	 }
    	// returns the screenshot path to the onTestFailure() method
 		System.out.println("Screenshot is saved in location --->" + screenshotPath);
 		return (screenshotPath);
     }

}
