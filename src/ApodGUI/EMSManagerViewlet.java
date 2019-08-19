package ApodGUI;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.Set;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class EMSManagerViewlet {
	static WebDriver driver;

	static String IPAddress;
	static String HostName;
	static String PortNo;
	static String WGSPassword;
	static String uname;
	static String password;
	static String Favwgs;
	static String URL;
	static String WGSName;
	String Screenshotpath;
	String DWGS;
	String Dnode;
	String NodeName;
	String Node;
	static String DownloadPath;
	String Queuemanager;
	String wgs;
	static String QueueName;
	static String LocalQueue;
	static String ManagerName;

	@BeforeTest
	public void beforeTest() throws Exception {
		System.out.println("BeforeTest");
		testrail.Settings.read();
		IPAddress = Settings.getIPAddress();
		HostName = Settings.getWGS_HostName();
		PortNo = Settings.getWGS_PortNo();
		WGSPassword = Settings.getWGS_Password();

		URL = Settings.getSettingURL();
		uname = Settings.getNav_Username();
		password = Settings.getNav_Password();
		WGSName = Settings.getS_WGSName();

		Screenshotpath = Settings.getScreenshotPath();
		DWGS = Settings.getWGS_HostName();
		Dnode = Settings.getDnode();
		NodeName = Settings.getEMS_NodeName();
		DownloadPath = Settings.getDownloadPath();
		Node = Settings.getEMS_NodeName();
		Queuemanager = Settings.getQueuemanager();
		wgs = Settings.getWGS_INDEX();
		QueueName = Settings.getQueueName();
		LocalQueue = Settings.getLocalQueue();
		ManagerName = Settings.getManagerName();
	}

	@Parameters({ "sDriver", "sDriverpath", "Dashboardname" })
	@Test
	public void Login(String sDriver, String sDriverpath, String Dashboardname) throws Exception {

		// Selecting the browser
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

		// Enter the URL into the browser and Maximize the window
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Login page
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(2000);

		// Create New Dashboard
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);

		// Create viewlet button
		driver.findElement(By.xpath("//form/div/button[2]")).click();
		Thread.sleep(2000);

		// ---- Creating Manager Viewlet ----
		// Click on Viewlet button
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.xpath("//app-mod-select-viewlet-type/div/div[2]/button[2]")).click();

		// Create Manager
		driver.findElement(By.cssSelector(".object-type:nth-child(2)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(ManagerName);

		// Select WGS type
		Select WGSSelection = new Select(driver.findElement(By.name("wgsKey")));
		WGSSelection.selectByVisibleText(WGSName);

		// Click on EMS Check box
		driver.findElement(By.id("ems")).click();

		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);

	}

	@Parameters({ "SchemaName" })
	@TestRail(testCaseId = 281)
	@Test(priority = 9)
	public static void ShowObjectAttributes(String SchemaName, ITestContext context) throws InterruptedException {
		try {
			// Objects Verification
			EMSObjects obj = new EMSObjects();
			obj.ObjectAttributesVerification(driver, SchemaName);
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show Object Attributes option is working fine");
		} catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Show Object Attributes option is not working prperly, check details: " + e.getMessage());
		}
	}

	@Parameters({ "FavoriteViewletName" })
	@TestRail(testCaseId = 282)
	@Test(priority = 1)
	public static void AddToFavorites(String FavoriteViewletName, ITestContext context) throws InterruptedException {
		// Create favorite Viewlet
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.xpath("//app-mod-select-viewlet-type/div/div[2]/button[2]")).click();

		// Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);

		Select wgsdropdown1 = new Select(driver.findElement(By.name("wgs")));
		wgsdropdown1.selectByVisibleText(WGSName);

		// Submit
		driver.findElement(By.xpath("//app-modal-add-viewlet-favorite/div/div[2]/button[2]")).click();
		Thread.sleep(2000);

		// Manager names data storage
		String NodeName = driver.findElement(By.xpath("//datatable-body-cell[7]/div/span")).getText();
		System.out.println(NodeName);

		// ----------- Add Manager to favorite viewlet -----------------
		// Select Add tofavorite option
		driver.findElement(By.xpath(
				"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
				.click();
		driver.findElement(By.xpath("//div/ul[2]/li")).click();
		Thread.sleep(2000);

		// Select the favorite viewlet name
		Select fav = new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByIndex(0);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".g-block-bottom-buttons > .g-button-blue")).click();
		Thread.sleep(2000);

		// Favorite viewlet data storing
		String Fav1 = driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body"))
				.getText();
		System.out.println(Fav1);

		// Verification condition
		if (Fav1.contains(NodeName)) {
			System.out.println("EMS Manager is added to Favorite viewlet");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "EMS Manager is added to Favorite viewlet");
		} else {
			System.out.println("EMS Manager is not added to Favorite viewlet");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to add EMS Manager to Favorite viewlet");
			driver.findElement(By.id("Add EMS Manager to favorite failed")).click();
		}
		Thread.sleep(1000);
	}

	@Test(priority = 10)
	public void Logout() throws Exception {
		// Delete the dashboard
		try {
			driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
			// driver.findElement(By.cssSelector(".fa-times")).click();
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Dashboards are not present");
		}
		Thread.sleep(1000);

		// Logout option
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {

		final String dir = System.getProperty("user.dir");
		String screenshotPath;
		//System.out.println("dir: " + dir);
		if (!result.getMethod().getMethodName().contains("Logout")) {
			if (ITestResult.FAILURE == result.getStatus()) {
				this.capturescreen(driver, result.getMethod().getMethodName(), "FAILURE");
				Reporter.setCurrentTestResult(result);

				Reporter.log("<br/>Failed to execute method: " + result.getMethod().getMethodName() + "<br/>");
				// Attach screenshot to report log
				screenshotPath = dir + "/" + Screenshotpath + "/ScreenshotsFailure/"
						+ result.getMethod().getMethodName() + ".png";

			} else {
				this.capturescreen(driver, result.getMethod().getMethodName(), "SUCCESS");
				Reporter.setCurrentTestResult(result);

				// Attach screenshot to report log
				screenshotPath = dir + "/" + Screenshotpath + "/ScreenshotsSuccess/"
						+ result.getMethod().getMethodName() + ".png";

			}

			String path = "<img src=\" " + screenshotPath + "\" alt=\"\"\"/\" />";
			// To add it in the report
			Reporter.log("<br/>");
			Reporter.log(path);
		}

	}

	public void capturescreen(WebDriver driver, String screenShotName, String status) {
		try {
			

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			if (status.equals("FAILURE")) {
				FileUtils.copyFile(scrFile,
						new File(Screenshotpath + "/ScreenshotsFailure/" + screenShotName + ".png"));
				Reporter.log(Screenshotpath + "/ScreenshotsFailure/" + screenShotName + ".png");
			} else if (status.equals("SUCCESS")) {
				FileUtils.copyFile(scrFile,
						new File(Screenshotpath + "./ScreenshotsSuccess/" + screenShotName + ".png"));

			}

			System.out.println("Printing screen shot taken for className " + screenShotName);

		} catch (Exception e) {
			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

	}

}
