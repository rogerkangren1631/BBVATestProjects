package exceldataprovider;

import org.testng.annotations.Test;

import utilities.Constants;
import utilities.ExcelUtility;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class TestUsingExcelLogin {
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Constants.LoginURL);
	
        driver.switchTo().frame(0); 
	
		ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name_Test, "LoginTests");
	}
	
	@DataProvider(name = "loginData")
	public Object[][] dataProvider() {
		Object[][] testData = ExcelUtility.getTestData("Invalid_Login");
		return testData;
	}
	
	@Test(dataProvider = "loginData")
	public void testUsingExcel(String username, String password) throws Exception {
		
		WebElement userNameTextBox = driver.findElement(By.id("alternateSignon_userId"));
		WebElement passwordTextBox = driver.findElement(By.id("alternateSignon_password"));
		userNameTextBox.clear();
		System.out.println( "UserName is " +username);

		System.out.println( "Password is " + password);
		userNameTextBox.sendKeys(username);
		passwordTextBox.sendKeys(password);
		passwordTextBox.sendKeys(Keys.ENTER);
		
		Thread.sleep(2000);

	}
  @BeforeClass
  public void beforeClass() {
  }

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

}
