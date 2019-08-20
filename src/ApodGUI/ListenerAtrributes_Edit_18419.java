package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class ListenerAtrributes_Edit_18419 {
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
	@Test(priority = 19)
	public static void creation(String DashBoard, String ViewletName) throws Throwable {

		if (!driver.findElement(By.cssSelector(".nav")).getText().contains(DashBoard)) {

			// CREATE DASHBOARD
			driver.findElement(By.cssSelector(".fa-plus:nth-child(1)")).click();
			driver.findElement(By.cssSelector(".input-text-field")).sendKeys(DashBoard);
			driver.findElement(By.xpath("//button[contains(.,'Create')]")).click();
			Thread.sleep(7000);
			
			// CREATE VIEWLET
			driver.findElement(By.xpath("(//*[contains(.,'Viewlet')])[14]")).click();
			driver.findElement(By.xpath("//button[contains(.,'OK')]")).click();

			// CREATE LISTENER VIEWLET
			driver.findElement(By.xpath("//div/div[7]")).click();
			driver.findElement(By.name("viewletName")).sendKeys(ViewletName);
			// SELECTING NODE
			driver.findElement(By.xpath("//ng-select/div/div/div[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
			// SELECTING QUEUE MANAGER
			driver.findElement(By.xpath("//div[2]/ng-select/div/div/div[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();

			driver.findElement(By.xpath("//button[contains(.,'Save changes')]")).click();	
			
			
			
			driver.findElement(By.xpath("//input[@type='text']")).sendKeys("TESTLISTENER");
			try {
			if (driver.findElement(By.xpath("//datatable-body[contains(.,'No data to display')]")).isDisplayed()) {
				// CREATE LISTENER
				driver.findElement(By.xpath("//img[@title='Add Listener']")).click();
				driver.findElement(By.xpath("//button[contains(.,'Select path')]")).click();
				driver.findElement(By.xpath("//input[@id='name']")).clear();
				driver.findElement(By.xpath("//input[@id='name']")).sendKeys("TESTLISTENER");
				driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
				Thread.sleep(3000);
			}}
			catch (Exception e) {
				System.out.println("Listener existed");
			}
			}
			
			
			
		

		String DashBoardName = "//li[contains(.,'" + DashBoard + "')]";
		driver.findElement(By.xpath(DashBoardName)).click();
		Thread.sleep(5000);
		if (!driver.findElement(By.id("main-page")).getText().contains(ViewletName)) {
			
			// CREATE VIEWLET
			driver.findElement(By.xpath("(//*[contains(.,'Viewlet')])[14]")).click();
			driver.findElement(By.xpath("//button[contains(.,'OK')]")).click();

			// CREATE LISTENER VIEWLET
			driver.findElement(By.xpath("//div/div[7]")).click();
			driver.findElement(By.name("viewletName")).sendKeys(ViewletName);
			// SELECTING NODE
			driver.findElement(By.xpath("//ng-select/div/div/div[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
			// SELECTING QUEUE MANAGER
			driver.findElement(By.xpath("//div[2]/ng-select/div/div/div[2]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();

			driver.findElement(By.xpath("//button[contains(.,'Save changes')]")).click();
			
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@type='text']")).sendKeys("TESTLISTENER");
			try {
			if (driver.findElement(By.xpath("//datatable-body[contains(.,'No data to display')]")).isDisplayed()) {
				// CREATE LISTENER
				driver.findElement(By.xpath("//img[@title='Add Listener']")).click();
				driver.findElement(By.xpath("//button[contains(.,'Select path')]")).click();
				driver.findElement(By.xpath("//input[@id='name']")).clear();
				driver.findElement(By.xpath("//input[@id='name']")).sendKeys("TESTLISTENER");
				driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
				Thread.sleep(3000);
			}}
			catch (Exception e) {
				System.out.println("Listener existed");
			}
			
			
			
		}
		else
		{
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("TESTLISTENER");
		try {
		if (driver.findElement(By.xpath("//datatable-body[contains(.,'No data to display')]")).isDisplayed()) {
			// CREATE LISTENER
			driver.findElement(By.xpath("//img[@title='Add Listener']")).click();
			driver.findElement(By.xpath("//button[contains(.,'Select path')]")).click();
			driver.findElement(By.xpath("//input[@id='name']")).clear();
			driver.findElement(By.xpath("//input[@id='name']")).sendKeys("TESTLISTENER");
			driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
			Thread.sleep(3000);
		}}
		catch (Exception e) {
			System.out.println("Listener existed");
		}
		}
	}
	

	

	@Parameters({ "Description", "IPADD", "PORT" })
	@TestRail(testCaseId = 83)
	@Test(priority = 2)
	public static void EditProperties(String Description, String IPADD, String PORT,ITestContext context) throws Throwable {

		try {
		// CHANGE DESCRIPTION OF A LISTENER
		//driver.findElement(By.xpath("//input[@type='text']")).sendKeys("TESTLISTENER");
		properties();
		// Storing previous values
		String InitDescription = driver.findElement(By.xpath("//input[@id='description']")).getAttribute("value");
		System.out.println(InitDescription);

		driver.findElement(By.xpath("//input[@id='description']")).clear();
		driver.findElement(By.xpath("//input[@id='description']")).sendKeys(Description);
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(8000);

		// VERIFYING DESCRIPTION AFTER CHANGE

		properties();
		String ChangedDescription = driver.findElement(By.xpath("//input[@id='description']")).getAttribute("value");
		System.out.println(ChangedDescription);

		if (!Description.equals(ChangedDescription)) {
			System.out.println("Edit Description is not working");
			driver.findElement(By.xpath("//li[contains(.,'Properties...')]")).click();

		} else
			System.out.println("Able to edit Description");
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(2000);

		// EDIT IPADDRESS AND VERIFY
		properties();
		driver.findElement(By.linkText("Extended")).click();
		driver.findElement(By.id("ipAddress")).clear();
		driver.findElement(By.id("ipAddress")).sendKeys(IPADD);
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(5000);
		properties();
		driver.findElement(By.linkText("Extended")).click();
		String UpdatedIP = driver.findElement(By.id("ipAddress")).getAttribute("value");
		System.out.println(UpdatedIP);
		if (!IPADD.equals(UpdatedIP)) {
			System.out.println("Edit IPADDRESS is not working");
			driver.findElement(By.xpath("//li[contains(.,'Properties...')]")).click();
		} else
			System.out.println("Able To Edit IPADDRESS");
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(2000);

		// EDIT PORT AND VERIFY
		properties();
		driver.findElement(By.linkText("Extended")).click();
		driver.findElement(By.id("ipPort")).clear();
		driver.findElement(By.id("ipPort")).sendKeys(PORT);
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(5000);
		properties();
		driver.findElement(By.linkText("Extended")).click();
		String UpdatedPORT = driver.findElement(By.id("ipAddress")).getAttribute("value");
		System.out.println(UpdatedPORT);
		if (!IPADD.equals(UpdatedPORT)) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Edit PORT is not working");
			System.out.println("Edit PORT is not working");
			driver.findElement(By.xpath("//li[contains(.,'Properties...')]")).click();
		} else
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Able To Edit PORT");
			System.out.println("Able To Edit PORT");
		}
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		Thread.sleep(2000);
		//Delete DashBoard
		driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 4);
			context.setAttribute("Comment", "Error occured");
			//driver.close();
		}
		driver.close();
	}
	

	public static void properties() throws Throwable {
		// CHANGE DESCRIPTION OF A LISTENER

		driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(.,'Properties...')]")));
		driver.findElement(By.xpath("//li[contains(.,'Properties...')]")).click();
		Thread.sleep(2000);
	}

}
