package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class DataLimitOffSet_19344 {
	static WebDriver driver;

	@Parameters({ "sDriver", "sDriverpath", "URL", "uname", "password" })
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password)
			throws Exception {
		
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
		if (sDriver.equalsIgnoreCase("webdriver.chrome.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new ChromeDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.ie.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new InternetExplorerDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.edge.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new EdgeDriver();
		} else {
			System.setProperty(sDriver, sDriverpath);
			driver = new FirefoxDriver();
		}

		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Login page
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(10000);
	}

	@Parameters({ "DashBoard", "ViewletName" })
	@Test(priority = 1)
	public static void creation(String DashBoard, String ViewletName) throws Throwable {
		if (!driver.findElement(By.cssSelector(".nav")).getText().contains(DashBoard)) {

			// CREATE DASHBOARD
			driver.findElement(By.cssSelector(".fa-plus:nth-child(1)")).click();
			driver.findElement(By.cssSelector(".input-text-field")).sendKeys(DashBoard);
			//driver.findElement(By.cssSelector(".g-button-blue:nth-child(2) > span")).click();
			driver.findElement(By.xpath("//button[contains(.,'Create')]")).click();
			Thread.sleep(2000);

			// CREATE VIEWLET
			driver.findElement(By.xpath("(//*[contains(.,'Viewlet')])[14]")).click();
			//driver.findElement(By.cssSelector(".g-button-blue:nth-child(2)")).click();
			driver.findElement(By.xpath("//button[contains(.,'OK')]")).click();
			Thread.sleep(2000);

			// CREATE QUEUE VIEWLET
			driver.findElement(By.name("viewletName")).sendKeys(ViewletName);
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(2000);

		}
		String DashBoardName = "//li[contains(.,'" + DashBoard + "')]";
		driver.findElement(By.xpath(DashBoardName)).click();
		Thread.sleep(2000);
		
		if (!driver.findElement(By.id("main-page")).getText().contains(ViewletName)) {
			
			driver.findElement(By.xpath("(//*[contains(.,'Viewlet')])[14]")).click();
			//driver.findElement(By.cssSelector(".g-button-blue:nth-child(2)")).click();
			driver.findElement(By.xpath("//button[contains(.,'OK')]")).click();

			// CREATE QUEUE VIEWLET
			driver.findElement(By.name("viewletName")).sendKeys(ViewletName);
			driver.findElement(By.cssSelector(".btn-primary")).click();
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[3]")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(.,'Save Changes')]")).click();
		Thread.sleep(3000);
		
		
		// USER SETTINGS
		// driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//i[3]")).click();
		if (!driver.findElement(By.cssSelector(".checkbox:nth-child(2) > input")).isSelected()) {
			driver.findElement(By.cssSelector(".checkbox:nth-child(2) > input")).click();
			Thread.sleep(2000);
			
		}
		if (!driver.findElement(By.cssSelector(".checkbox:nth-child(6) > input")).isSelected()) {
			driver.findElement(By.cssSelector(".checkbox:nth-child(6) > input")).click();
			Thread.sleep(2000);
		}
		driver.findElement(By.xpath("//button[contains(.,'Save Changes')]")).click();
		Thread.sleep(3000);

	}
	
	@Parameters({ "LimitOffsetValue", "URL", "uname", "password", "DashBoard"})
	@TestRail(testCaseId = 5)
	@Test(priority = 2)
	public static void setDataLimitoffset(String LimitOffsetValue, String URL, String uname, String password, String DashBoard, ITestContext context) throws Throwable {
		try
		{
	//SETTING DATA LIMIT OFFSET VALUE	
		driver.findElement(By.id("dropdownMenuButton")).click();
		driver.findElement(By.linkText("Edit viewlet")).click();
		driver.findElement(By.cssSelector(".g-text-and-input > .ng-valid")).click();
		driver.findElement(By.cssSelector(".g-text-and-input > .ng-valid")).clear();
		driver.findElement(By.cssSelector(".g-text-and-input > .ng-valid")).sendKeys(LimitOffsetValue);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(5000);
	//VERIFYING DATALIMIT OFFSET VALUE AFTER LOGOUT AND LOGIN
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(3000);
		String DashBoardName = "//li[contains(.,'" + DashBoard + "')]";
		driver.findElement(By.xpath(DashBoardName)).click();
		driver.findElement(By.id("dropdownMenuButton")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Edit viewlet")).click();
		Thread.sleep(5000);
		String Value = driver.findElement(By.cssSelector(".g-text-and-input > .ng-valid")).getAttribute("value");
		System.out.println(Value);
		
		if(LimitOffsetValue.equals(Value)) {
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Test passed");
			System.out.println("Test Passed");
			driver.findElement(By.cssSelector(".btn-danger")).click();
			driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
			Thread.sleep(5000);
			driver.findElement(By.cssSelector(".btn-primary")).click();
			driver.findElement(By.cssSelector(".fa-power-off")).click();
			driver.close();
		}
		else {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Test failed");
			System.out.println("Test Failed");
		}
	
	}
	catch (Exception e) {
		// TODO: handle exception
		//e.printStackTrace();
		context.setAttribute("Status",5);
		context.setAttribute("Comment", "Setting data limit offset value Failed: " + e.getMessage());
		driver.findElement(By.cssSelector(".btn-danger")).click();
		driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}
	}
	
	

}
