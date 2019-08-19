package ApodGUI;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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


import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class Settings {
	static WebDriver driver;

	static String IPAddress;
	static String HostName;
	static String PortNo;
	static String WGSPassword;
	static String uname;
	static String password;
	static String Favwgs;
	static String URL;
	static String wgs;
	String Screenshotpath;

	@BeforeTest
	public void beforeTest() throws Exception {
		System.out.println("BeforeTest");
		testrail.Settings.read();
		IPAddress = testrail.Settings.getIPAddress();
		HostName = testrail.Settings.getWGS_HostName();
		PortNo = testrail.Settings.getWGS_PortNo();
		WGSPassword = testrail.Settings.getWGS_Password();

		URL = testrail.Settings.getSettingURL();
		uname = testrail.Settings.getNav_Username();
		password = testrail.Settings.getNav_Password();
	
		Screenshotpath = testrail.Settings.getScreenshotPath();
	}

	@Parameters({ "sDriver", "sDriverpath", "wgs", "Favwgs","Dashboardname", "FavoriteViewletName" })
	@Test
	public static void Login(String sDriver, String sDriverpath, int wgs, int Favwgs,String Dashboardname, String FavoriteViewletName)
			throws Exception {
	
	
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
		Thread.sleep(2000);

		// ---------- Create New Dashboard ---
		// Create New Dashboard
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		driver.findElement(By.id("createInitialViewlets")).click();

		// Work group server selection
		Select dd = new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByIndex(wgs);

		/*
		 * //Selection of Node
		 * driver.findElement(By.cssSelector(".field-queuem-input")).click();
		 * driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		 * 
		 * //Selectiom of Queue manager
		 * driver.findElement(By.cssSelector(".field-node-input")).click();
		 * driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager
		 * );
		 */

		// Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(1000);

		// ------------- Create Favorite Viewlet ---------

		// Add Favorite Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();

		// Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);

		Select wgsdropdown = new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByIndex(Favwgs);

		// Submit
		driver.findElement(By.xpath("//app-modal-add-viewlet-favorite/div/div[2]/button[2]")).click();
		Thread.sleep(1000);

		// Restoring the Default Settings
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(5000);

	}

	@Test(priority = 1)
	@TestRail(testCaseId = 327)
	public static void ShowInActiveChannelsCheckbox(ITestContext context) throws InterruptedException {
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();

		// Show Inactive channels check box
		WebElement Checkbox = driver.findElement(By.xpath("//input[@name='user-settings']"));
		if (Checkbox.isSelected()) {
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		String ChanneleViewletData = driver.findElement(By.xpath("//div[@id='main-page']/div[2]")).getText();

		if (ChanneleViewletData.contains("Inactive")) {
			System.out.println("Inactive Check box is Working fine");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Inactive Check box is Working fine");
		} else {
			System.out.println("Inactive Check box is not Working fine");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Inactive Check box is not Working properly");
			driver.findElement(By.xpath("Checkbox not working")).click();
		}

		Thread.sleep(1000);
		}
		catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Inactive Check box is not Working properly");
			driver.findElement(By.xpath("Checkbox not working")).click();
		}
	}

	@Test(priority = 2)
	@TestRail(testCaseId = 328)
	public static void ShowEmptyQueuesCheckbox(ITestContext context) throws InterruptedException {
		try
		{
		driver.findElement(By.cssSelector(".fa-cog")).click();

		// Show Empty Queues check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[2]"));
		if (Checkbox.isSelected()) {
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		for (int i = 5; i <= 20; i++) {
			String Depth = driver.findElement(By.xpath("//datatable-body-cell[" + i + "]/div/span")).getText();
			int result = Integer.parseInt(Depth);
			if (result == 0) {
				System.out.println("Show Empty Queues Check box is working fine");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Show Empty Queues Check box is working fine");
				break;
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Show Empty Queues Check box is not working properly");
			}

		}
		Thread.sleep(1000);
		}catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Empty Queues Check box is not working properly");
			driver.findElement(By.xpath("Checkbox not working")).click();
		}
	}

	@Test(priority = 3)
	@TestRail(testCaseId = 329)
	public static void ShowTemporaryDynamicQueues(ITestContext context) throws InterruptedException {
		try
		{
		driver.findElement(By.cssSelector(".fa-cog")).click();

		// Show Empty Queues check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[3]"));
		if (Checkbox.isSelected()) {
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		for (int i = 3; i <= 11; i++) {
			String Queuename = driver.findElement(By.xpath(
					"//datatable-row-wrapper[" + i + "]/datatable-body-row/div[2]/datatable-body-cell[3]/div/span"))
					.getText();
			// System.out.println(Queuename);

			if (Queuename.contains("INQ") || Queuename.contains("HASH") || Queuename.contains("NSQ")) {
				System.out.println("Show Temporary Dynamic Queues Check box is working fine");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Show Temporary Dynamic Queues Check box is working fine");
				break;
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Show Temporary Dynamic Queues Check box is not working properly");
			}
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Temporary Dynamic Queues Check box is not working properly");
			driver.findElement(By.xpath("Checkbox not working")).click();
		}
	}

	@Parameters({ "FavoriteViewletName", "objpath1", "objpath2" })
	@TestRail(testCaseId = 330)
	@Test(priority = 4)
	public static void ShowFullNamesForFavoriteShortcuts(String FavoriteViewletName, String objpath1, String objpath2,
			ITestContext context) throws InterruptedException {
		
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();

		// Show Empty Queues check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[4]"));
		if (Checkbox.isSelected()) {
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		// Select Add to favorite option in queue
		driver.findElement(By.xpath(
				"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
				.click();
		driver.findElement(By.linkText("Add to favorites...")).click();
		Thread.sleep(1000);

		// Select the favorite viewlet name
		Select fav = new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		// fav.selectByIndex(0);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//app-mod-add-to-favorite-viewlet/div/div[2]/button[2]")).click();
		Thread.sleep(2000);

		String FullNameForFavorite = driver.findElement(By.xpath(
				"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[3]/div/span"))
				.getText();
		// System.out.println(FullNameForFavorite);

		if (FullNameForFavorite.contains(objpath1) || FullNameForFavorite.contains(objpath2)) {
			System.out.println("Show Full name for Favorite Short cuts is working fine");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show Full name for Favorite Short cuts is working fine");
		} else {
			System.out.println("Show Full name for Favorite Short cuts is not working fine");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Full name for Favorite Short cuts is not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}
		Thread.sleep(1000);
		}catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Full name for Favorite Short cuts is not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}
	}

	@Parameters({ "objpath1", "objpath2" })
	@TestRail(testCaseId = 331)
	@Test(priority = 5)
	public static void ShowFullNamesForSearchResultsObjects(String objpath1, String objpath2, ITestContext context)
			throws InterruptedException {
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();

		// Show Empty Queues check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[5]"));
		if (Checkbox.isSelected()) {
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		String Queuename = driver.findElement(By.xpath(
				"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[3]/div/span"))
				.getText();
		// System.out.println(Queuename);

		if (Queuename.contains(objpath1) || Queuename.contains(objpath2)) {
			System.out.println("Show Full name For Search results checkbox is working fine");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show Full name For Search results checkbox is working fine");
		} else {
			System.out.println("Show Full name For Search results checkbox is not working fine");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Full name For Search results checkbox is not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}
		Thread.sleep(1000);
		}catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Full name For Search results checkbox is not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}

	}

	@Test(priority = 6)
	@TestRail(testCaseId = 332)
	public static void ShowSystemObjectsOnly(ITestContext context) throws InterruptedException {
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();
		Thread.sleep(2000);

		// Show Show managers for default schema check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[6]"));
		if (Checkbox.isSelected()) {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		for (int i = 1; i <= 10; i++) {
			String ColumnValues = driver.findElement(By.xpath(
					"//datatable-row-wrapper[" + i + "]/datatable-body-row/div[2]/datatable-body-cell[6]/div/span"))
					.getText();
			System.out.println(ColumnValues);
			int result = Integer.parseInt(ColumnValues);
			if (result == 5000) {
				System.out.println("Show System Objects only checkbox is working fine");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Show System Objects only checkbox is working fine");
			} else {
				System.out.println("Show System Objects only checkbox is not working fine");
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Show System Objects only checkbox is not working properly");
				driver.findElement(By.xpath("Condition failed")).click();
			}
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show System Objects only checkbox is not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}
	}

	@Parameters({ "Manager1", "Manager2", "Manager3", "Manager4" })
	@TestRail(testCaseId = 333)
	@Test(priority = 7)
	public static void ShowObjectSearchresultsFromActiveManagersOnly(String Manager1, String Manager2, String Manager3,
			String Manager4, ITestContext context) throws InterruptedException {
		
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='user-settings']")).click();
		driver.findElement(By.xpath("(//input[@name='user-settings'])[2]")).click();
		driver.findElement(By.xpath("(//input[@name='user-settings'])[3]")).click();
		driver.findElement(By.xpath("(//input[@name='user-settings'])[4]")).click();
		driver.findElement(By.xpath("(//input[@name='user-settings'])[5]")).click();
		Thread.sleep(1000);

		// Show Show managers for default schema check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[7]"));
		if (Checkbox.isSelected()) {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(3000);
		try {
			for (int i = 1; i <= 3; i++) {
				String Managernames = driver.findElement(By.xpath(
						"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper["
								+ i + "]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span"))
						.getText();
				// System.out.println(Managernames);

				if (Managernames.equalsIgnoreCase(Manager1) || Managernames.equalsIgnoreCase(Manager2)
						|| Managernames.equalsIgnoreCase(Manager3) || Managernames.equalsIgnoreCase(Manager4)) {
					System.out.println("Show object search results for Active queue managers condition not working");
					context.setAttribute("Status", 5);
					context.setAttribute("Comment",
							"Show object search results for Active queue managers condition not working properly");
					driver.findElement(By.xpath("Condition failed")).click();
				} else {
					System.out
							.println("Show object search results for Active queue managers condition is working fine");
					context.setAttribute("Status", 1);
					context.setAttribute("Comment",
							"Show object search results for Active queue managers condition is working fine");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occured");
		}
		Thread.sleep(1000);
		}
		catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Show object search results for Active queue managers condition not working properly");
			driver.findElement(By.xpath("Condition failed")).click();
		}
	}

	@Test(priority = 8)
	@TestRail(testCaseId = 334)
	public static void ShowManagersForDefaultSchemas(ITestContext context) throws InterruptedException {
		try {
		driver.findElement(By.cssSelector(".fa-cog")).click();
		Thread.sleep(2000);

		// Show Show managers for default schema check box
		WebElement Checkbox = driver.findElement(By.xpath("(//input[@name='user-settings'])[8]"));
		if (Checkbox.isSelected()) {
			Checkbox.click();
			driver.findElement(By.xpath("//div[3]/button")).click();
		} else {
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		Thread.sleep(1000);

		for (int i = 3; i <= 10; i++) {
			String Columnheading = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-header/div/div[2]/datatable-header-cell["
							+ i + "]/div"))
					.getText();
			// System.out.println(Columnheading);

			if (Columnheading.equalsIgnoreCase("Manager Name")) {
				System.out.println("Show manager for Default schemas checkbox failed");
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Show manager for Default schemas checkbox failed");
				driver.findElement(By.xpath("Condition failed")).click();
				break;
			} else {
				System.out.println("Show manager for Default schems checkbox function is working fine");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Show manager for Default schems checkbox function is working fine");
			}

		}
		Thread.sleep(1000);

		// Restoring the Default Settings
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(5000);
		}catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show manager for Default schemas checkbox failed");
			driver.findElement(By.xpath("Condition failed")).click();
		}
	}

	@Test(priority = 9)
	public void Logout() throws InterruptedException {
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
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		final String dir = System.getProperty("user.dir");
		String screenshotPath;
		// System.out.println("dir: " + dir);
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
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

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
