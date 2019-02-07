//test case : verify login
//steps
//1)verify login page
//2)enter username
//3)enter password
//4)click login button
//5)verify home page 


package com.sgic.automation.test;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Login {
	public String baseUrl="http://localhost:4200/";
	public String driverPath="webdrivers//chromedriver.exe";
	public WebDriver driver;
	
	ExtentReports extentReports;
	ExtentTest extentTest;
	ExtentHtmlReporter extentHtmlReporter;
	
	@BeforeTest
	public void setUp() {

		extentHtmlReporter = new ExtentHtmlReporter(
				new File(System.getProperty("user.dir") + "/AutomationReports.html"));
		extentHtmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "/ReportsConfig.xml"));
		extentReports = new ExtentReports();
		extentReports.setSystemInfo("Environment", "QA");
		extentReports.attachReporter(extentHtmlReporter);
	}
	
	@BeforeMethod
	public void invokeBrowser(Method method) {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver= new ChromeDriver();
		driver.get(baseUrl);
		
		String descriptiveTestName = method.getAnnotation(Test.class).testName();
		extentTest = extentReports.createTest(descriptiveTestName);
	}
//verify login	
  @Test(priority=1,dataProvider="HRMSLogin",testName="test login ")
  public void login(String userName , String password)  {
	  String expected ="Login";
	  String actual = driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/div/div/div[1]/h4")).getText();
	  assertEquals(actual, expected);
		
	  driver.findElement(By.id("userName")).clear();
	  driver.findElement(By.id("userName")).sendKeys(userName);
	  driver.findElement(By.id("password")).clear();
	  driver.findElement(By.id("password")).sendKeys(password);
	  driver.findElement(By.id("btnLogin")).click();
	  
	  String expected1 ="Dashboard";
	  String actual1 = driver.findElement(By.xpath("/html/body/app-root/div/app-side-nav/div/div[2]/ul/li[1]/a/p")).getText();
	  assertEquals(actual1, expected1);
	  
		extentTest.log(Status.INFO, "Assert Pass as condition is True");

	  
	  try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
 
  @AfterMethod
	public void tearDown(ITestResult result) {
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
  
  @DataProvider(name = "HRMSLogin")
	public Object[][] HRMLogin() {

		ExcelDataConfig exConfig = new ExcelDataConfig("excelData\\Excel.xlsx");
		int rows = exConfig.getRowCount(0);

		Object[][] data = new Object[rows][2];
		for (int i = 1; i < rows; i++) {
			data[i][0] = exConfig.getData("Sheet1", i, 0);
			data[i][1] = exConfig.getData("Sheet1", i, 1);
		}
		return data;
	}
}
