package com.sgic.automation.base;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Base {
	public String baseUrl="http://localhost:4200";
	public String driverPath="webdrivers//chromedriver.exe";
	WebDriver driver;
	
	ExtentReports extentReports;
	ExtentTest extentTest;
	ExtentHtmlReporter extentHtmlReporter;
	
	@BeforeTest
	public void setUp() {
		extentHtmlReporter = new ExtentHtmlReporter(
				new File(System.getProperty("user.dir") + "/AutomationReports1.html"));
		extentHtmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/ReportsConfig.xml"));
		extentReports = new ExtentReports();
		extentReports.setSystemInfo("Environment", "QA");
		extentReports.attachReporter(extentHtmlReporter);
	}
	
	@BeforeMethod
	public void lunchBrowser(Method method) {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver();
		driver.get(baseUrl);
		
		String descriptiveTestName = method.getAnnotation(Test.class).testName();
		extentTest = extentReports.createTest(descriptiveTestName);
	}
	
	@AfterMethod
	public void closeBrowser(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS, "The Test Method Name As : " + result.getName() + " is Passed ");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(Status.FAIL, "The Test Method Name As : " + result.getName() + " is Fail ");
			extentTest.log(Status.FAIL, "The Test Failure : " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(Status.SKIP, "The Test Method Name As : " + result.getName() + " is Skiped ");
		}
		extentReports.flush();
		driver.quit();
	}
	
	@AfterTest
	public void endReport() {
		extentReports.flush();

	}

}
