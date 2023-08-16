package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

	private WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	private void setUp() {

//	     Create Driver
		driver = new ChromeDriver();
		System.out.println("Browser started");
		sleep(1);

//			open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
//			sleep(1);

		driver.manage().window().maximize();

	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {
		System.out.println("Test started");

//		sleep(1);

		System.out.println("Page is opened");

//		enter username
//      To find element using By class
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

//		enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

//		click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();
//		
//		verifications:
//		new url
		String expectedUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//		logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Log Out button is not visible");

//		successful login message
		WebElement successMessage = driver.findElement(By.xpath("//div[@id='flash']"));
		String expectedMessage = "You logged into a secure area!";
		String actualMessage = successMessage.getText();
//	    Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not the same as expected");
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not the contain expected message.\nActual Message: " + actualMessage
						+ "\nExpected Message:" + expectedMessage);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {

		System.out.println("negativeLoginTest Started" + username + "and" + password);

//		enter username
		WebElement usernameElement = driver.findElement(By.id("username"));
//		usernameElement.sendKeys("incorrect");
		usernameElement.sendKeys(username);

//		enter password
		WebElement passwordElement = driver.findElement(By.name("password"));
//		passwordElement.sendKeys("SuperSecretPassword!");
		passwordElement.sendKeys(password);

//		click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

//		Verifications
//		invalid login message
//		WebElement errorMessage = driver.findElement(By.xpath("//div[@id='flash']"));
		WebElement errorMessage = driver.findElement(By.id("flash"));
//		String expectedErrorMessage = "Your username is invalid!";
		String actualErrorMessage = errorMessage.getText();
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual Error Message doesn't contain the expected error Message \nActual Error Message:"
						+ actualErrorMessage + "\nExpected Error Message:" + expectedErrorMessage);

	}

	/**
	 * stop execution for the given amount of time
	 * 
	 * @param seconds
	 */
	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds = 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
//      close browser
		driver.quit();
		System.out.println("Test Finished");
	}

}
