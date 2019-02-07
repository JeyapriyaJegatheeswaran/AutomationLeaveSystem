package com.sgic.automation.test;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class AddUser extends Base {
	@Test(priority=1,dataProvider="HRMSAddUser",testName="test login ")
	public void addUser(String userName,String email,String password,String firstName,String lastName,String role,String department,String date) {
		System.out.println(email);
		System.out.println(password);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(role);
		System.out.println(department);
		System.out.println(date);
		String expected ="Login";
		  String actual = driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/div/div/div[1]/h4")).getText();
		  assertEquals(actual, expected);
			
		  //driver.findElement(By.id("userName")).clear();
		  driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("admin");
		  //driver.findElement(By.id("password")).clear();
		  driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("admin");
		  driver.findElement(By.id("btnLogin")).click();
		  
		  String expected1 ="Dashboard";
		  String actual1 = driver.findElement(By.xpath("/html/body/app-root/div/app-side-nav/div/div[2]/ul/li[1]/a/p")).getText();
		  assertEquals(actual1, expected1);
		  
		  
		  driver.findElement(By.xpath("/html/body/app-root/div/app-side-nav/div/div[2]/ul/li[5]/a/p")).click();
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[1]/div[1]/input")).sendKeys(userName);
		  
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[1]/div[2]/input")).
		  									sendKeys(email);
		  
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[1]/div[3]/input")).
		  									sendKeys(password);
		  
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[2]/div[1]/input")).sendKeys(firstName);
		  
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[2]/div[2]/input")).sendKeys(lastName);
		  
		  Select role1=new Select(driver.findElement(By.
				  xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[3]/div[1]/select")));
			role1.selectByVisibleText(role);	
		  
			  Select department1=new Select(driver.findElement(By.
					  xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[3]/div[2]/select")));
				department1.selectByVisibleText(department);
			
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[3]/div[3]/input")).
		  					sendKeys(date);
		  driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-add-user/div/div[2]/form/div[4]/div/button")).click();
		  
		  extentTest.log(Status.INFO, "Assert Pass as condition is True");

		  
		  try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	@DataProvider(name = "HRMSAddUser")
	public Object[][] HRMSAddUser() {

		ExcelDataConfig exConfig = new ExcelDataConfig("excelData\\Excel.xlsx");
		int rows = exConfig.getRowCount(1);

		Object[][] data = new Object[rows][8];
		for (int i = 1; i < rows; i++) {
			data[i][0] = exConfig.getData("Sheet2", i, 0);
			data[i][1] = exConfig.getData("Sheet2", i, 1);
			data[i][2] = exConfig.getData("Sheet2", i, 2);
			data[i][3] = exConfig.getData("Sheet2", i, 3);
			data[i][4] = exConfig.getData("Sheet2", i, 4);
			data[i][5] = exConfig.getData("Sheet2", i, 5);
			data[i][6] = exConfig.getData("Sheet2", i, 6);
			data[i][7] = exConfig.getData("Sheet2", i, 7).toString();
			
		}
		return data;
	}
}
