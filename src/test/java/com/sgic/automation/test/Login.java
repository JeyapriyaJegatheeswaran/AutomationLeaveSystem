//test case : verify login
//steps
//1)verify login page
//2)enter username
//3)enter password
//4)click login button
//5)verify home page 


package com.sgic.automation.test;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class Login extends Base{
	
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
