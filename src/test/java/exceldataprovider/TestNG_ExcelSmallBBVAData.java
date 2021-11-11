// æ³¨æ„�ï¼š é€™å€‹ç¨‹åº�æ˜¯ç”¨ä¾†ç·´ç¿’ dataprovider çš„ï¼Œ åˆ©ç”¨excelçš„æ•¸æ“š
//é�‹è¡Œæ–¹å¼�ï¼š å�³æ“Š é�¸TestNG

//æ•™è¨“ï¼š æ²’æœ‰æ–·è¨€Assert å’Œ AfterMethod  æ–¹æ³•
//æ•™è¨“ï¼š æ�žéŒ¯è·¯å¾‘ C: æˆ�äº†D:


package exceldataprovider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Constants;
import utilities.ExcelUtility;
import utilities.Screenshots;


public class TestNG_ExcelSmallBBVAData {
	private WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	
	int start = 1;
/*	@DataProvider(name="inputs")
	public Object[][] getData(){
		Object[][] testData = ExcelUtility.getTestData("Link_Table");
		return testData;
	}
*/
	
  //data used for test
	@DataProvider(name="inputs")
	public Object[][] getData(){
		return new Object[][]{
		    { "https://www.bbvausa.com/" , "https://www.bbvausa.com/" },  
	    	{ "https://www.bbvausa.com/moneyfit/credit-management/when-to-refinance-your-car-loan.html" ,   "https://www.pnc.com/insights/personal-finance/borrow/what-you-need-to-know-before-you-finance-your-next-car.html" },          
	    	{ "https://www.bbvausa.com/moneyfit/credit-management/managing-your-credit-cards-online.html"  , "https://www.pnc.com/insights/personal-finance/borrow.html" },          
	//		{ "https://www.bbvausa.com/moneyfit/savings-and-budgeting/what-is-a-recession-and-what-might-it-mean-for-me.html" , "https://www.pnc.com/insights/personal-finance/save.html" },          
	//		{ "https://www.bbvausa.com/moneyfit/savings-and-budgeting/what-does-it-mean-to-live-paycheck-to-paycheck.html" , "https://www.pnc.com/insights/personal-finance/save.html" },          
	//		{ "https://www.bbvausa.com/moneyfit/savings-and-budgeting/the-features-that-make-mobile-banking-great.html" , "https://www.pnc.com/insights/personal-finance/spend/mobile-banking-advantages.html" },          
  
			{ "https://www.bbvausa.com/business.html" , "https://www.pnc.com/en/small-business.html" },  

		};
	}

	@BeforeClass
	public void SetUp() throws Exception {
		try {
		report = new ExtentReports("C:\\Reports\\redirectedlink.html");
		test = report.startTest("Verify if all redirected linka are successfully");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		  
		    test.log(LogStatus.INFO, "Broswer started .....");
		
		ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name_BBVA, "data_sheet");
		test.log(LogStatus.INFO, "Get data from Excel data file .....");
		}
		catch(Exception e) {
			
		}
	}
	
	   
	
	@Test(dataProvider="inputs")
	public void testRedirectedLinks(String input1, String input2) {
		start = start +1;
		System.out.println(start + " item : in the testing... " );
		
		//Open expected url in case which is redirected by web server
		String baseUrl = input2;
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Page at the second column : '"+baseUrl + "' opened.....");	
		
		String expectedURL =  driver.getCurrentUrl();
		baseUrl = input1;
		
		driver.get(baseUrl);
		test.log(LogStatus.INFO, "Page at the first column : '"+baseUrl + "' opened.....");		
		String returnString = driver.getCurrentUrl();
		
		
		test.log(LogStatus.INFO, "   Our expected page final link is '"+ expectedURL + "'  ");
		test.log(LogStatus.INFO, "The real opened final  link is '"+ returnString + "' ");
		
	    Assert.assertEquals(returnString, expectedURL);
	
	}
	
	@AfterMethod
	public void afterMethod(ITestResult testResult) throws IOException {

		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println("Item " + start + " Failed->" + testResult.getMethod().getMethodName());
			//Catch a failure screen
			String path = Screenshots.takeScreenshot(driver, testResult.getName()+start);
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL,"Item " + start + " Failed-> it's screenshot is here! "  );
			test.log(LogStatus.WARNING,"Item " + start + " Failed-> " + testResult.getMethod().getMethodName(), imagePath);
		}
		if (testResult.getStatus() == ITestResult.SUCCESS) {
			System.out.println("Item " + start +" Successful-> " + testResult.getName());
			test.log(LogStatus.PASS,"Item " + start + " Sucessful->" + testResult.getMethod().getMethodName());
	    }
	//	driver.close();    è¿™é‡Œä¸�èƒ½ç”¨ï¼Œ ä¼šäº§ç”Ÿå¾ˆå¤š skip
	}
	
	
	@AfterTest
	public void tearDown() throws Exception {
	      driver.quit();
	
	}
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
		report.endTest(test);
		report.flush();
	}
}