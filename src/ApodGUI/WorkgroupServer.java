package ApodGUI;

import java.io.File;
import java.io.IOException;
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
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;
import testrail.TestRailAPI;

@Listeners(TestClass.class)
public class WorkgroupServer {
	static WebDriver driver;
	static String IPAddress;
	static String HostName;
	static String PortNo;
	static String WGSPassword;
	String VerificationData;
	String Screenshotpath;
	static String NodeName;
	static String Node_PortNumber;
	static String Node_NewConnectionName;
	static String WGSSearchInputData;

	@BeforeTest
	public void beforeTest() throws Exception {
		System.out.println("BeforeTest");
		Settings.read();
		IPAddress = Settings.getIPAddress();
		HostName = Settings.getWGS_HostName();
		PortNo = Settings.getWGS_PortNo();
		WGSPassword = Settings.getWGS_Password();
		VerificationData = Settings.getVerificationData();
		Screenshotpath = Settings.getScreenshotPath();
		NodeName = Settings.getNodeName();
		Node_PortNumber = Settings.getNode_PortNumber();
		Node_NewConnectionName = Settings.getNode_NewConnectionName();
		WGSSearchInputData = Settings.getWGSSearchInputData();
	}

	@Test
	@Parameters({ "sDriver", "sDriverpath" })
	public void Login(String sDriver, String sDriverpath, ITestContext context) throws Exception {

		Settings.read();
		String URL = Settings.getSettingURL();

		// Select the required browser for running the script
		if (sDriver.equalsIgnoreCase("webdriver.chrome.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new ChromeDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.edge.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new EdgeDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.ie.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new InternetExplorerDriver();
		} else {
			System.setProperty(sDriver, sDriverpath);
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("marionette", false);
			driver = new FirefoxDriver(options);
		}

		// Enter the URL into browser and Maximize the browser
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Login
		driver.findElement(By.id("username")).sendKeys(Settings.getNav_Username());
		driver.findElement(By.id("password")).sendKeys(Settings.getNav_Password());
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(2000);

		// Delete the WGS10 if present
		// ITestContext context = null;
		this.DeleteWorkgroup(context);
		Thread.sleep(1000);
	}

	@Test(priority = 1)
	@TestRail(testCaseId = 25)
	public void AddWorkgroupFromPlusIcon(ITestContext context) throws Exception {

		try {
			// Click on + icon
			driver.findElement(By.cssSelector("img[title=\"Add Workgroup Server\"]")).click();

			// Add WGS Details
			driver.findElement(By.name("ip")).sendKeys(IPAddress);
			driver.findElement(By.name("hostName")).sendKeys(HostName);
			driver.findElement(By.name("port")).clear();
			driver.findElement(By.name("port")).sendKeys(PortNo);
			driver.findElement(By.name("password")).sendKeys(WGSPassword);
			Thread.sleep(1000);

			// Click on Verify
			driver.findElement(By.xpath("//form/div[2]/button[2]")).click();
			Thread.sleep(2000);

			// Click on submit button
			driver.findElement(By.cssSelector(".button-blue")).click();
			Thread.sleep(2000);

			// Store the Viewlet data into string
			String ViewletData = driver.findElement(By.cssSelector("datatable-body.datatable-body")).getText();
			// System.out.println(ViewletData);

			// Verification Condition
			if (ViewletData.contains(VerificationData)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Workgroup server is added successfylly");
				System.out.println("Workgroup server is added successfylly");
				Reporter.log("Workgroup server is added successfylly");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed adding workgrou server");
				System.out.println("Workgroup Server is not added");
				driver.findElement(By.xpath("Condition failed")).click();
			}
		} catch (Exception e) {
			driver.findElement(By.cssSelector(".g-button-red")).click();
			/*
			 * File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			 * FileUtils.copyFile(scrFile,new File(Screenshotpath+ "/"+
			 * "AddWorkgroupFromPlusIcon.png"));
			 */
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Exception Occured when adding the work group server : " + e.getMessage());
			System.out.println("Exception Occured when adding the work group server");
			driver.findElement(By.cssSelector(".g-button-red")).click();
		}
		Thread.sleep(1000);
	}

	@Parameters({ "ChangedHostName" })
	@TestRail(testCaseId = 26)
	@Test(priority = 2)
	public static void EditWorkgroup(String ChangedHostName, ITestContext context) throws Exception {
		try {
			// Select the Edit WGS option
			WebElement EditWGS = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"));
			EditWGS.click();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]")).click();
			Thread.sleep(2000);

			// Change Local host Name
			driver.findElement(By.name("hostName")).clear();
			driver.findElement(By.name("hostName")).sendKeys(ChangedHostName);
			Thread.sleep(2000);
			driver.findElement(By.name("password")).sendKeys(WGSPassword);

			// Click on Verify
			driver.findElement(By.xpath("//form/div[2]/button[2]")).click();
			Thread.sleep(2000);

			// Click on Submit button
			driver.findElement(By.cssSelector(".button-blue")).click();
			Thread.sleep(3000);

			// Store the Host name into string
			String HostName = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[6]/div/span"))
					.getText();
			// System.out.println(HostName);

			// Verification condition
			if (HostName.equalsIgnoreCase(ChangedHostName)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Updating Workgroup server is working fine");
				System.out.println("Updating Workgroup server is working fine");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to edit workgroup server");
				System.out.println("Updating Workgroup server is not working fine");
				driver.findElement(By.xpath("condition failed")).click();
			}
		} catch (Exception e) {

			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Exception Occured when Editing the work group server :" + e.getMessage());
			System.out.println("Exception Occured when Editing the work group server");
			driver.findElement(By.cssSelector(".g-button-red")).click();
		}
		Thread.sleep(1000);

	}

	@TestRail(testCaseId = 27)
	@Test(priority = 4)
	public void DeleteWorkgroup(ITestContext context) throws Exception {
		try {
			WebElement DeleteWGS = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"));
			if (DeleteWGS.isDisplayed()) {
				// Select the Delete WGS option
				DeleteWGS.click();
				driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
				driver.findElement(By.cssSelector(".btn-primary")).click();
				Thread.sleep(3000);

				// Store the Viewlet data into string
				String WGSServerData = driver.findElement(By.xpath("//datatable-body")).getText();
				// System.out.println(WGSServerData);

				// Verification of delete work group server
				if (WGSServerData.contains(PortNo)) {
					System.out.println("WGS is not deleted");
					context.setAttribute("Status", 5);
					context.setAttribute("Comment", "Failed Deleting Workgroup server");
					driver.findElement(By.xpath("Deleting failed")).click();
				} else {
					context.setAttribute("Status", 1);
					context.setAttribute("Comment", "Deleting Workgroup server is success");
					System.out.println("WGS is deleted successfully");
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Got exception while deleting workgroup server check details : " + e.getMessage());
			// TODO Auto-generated catch block
			System.out.println("Second WGS is not present");
		}
	}

	@Test(priority = 3)

	public static void ConnectIcon(ITestContext context) throws InterruptedException {
		try {
			// Select the Edit WGS option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();

			// driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/div/div[2]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//li[2]/a")).click();

			// Change password
			driver.findElement(By.name("port")).clear();
			driver.findElement(By.name("port")).sendKeys("4010");
			driver.findElement(By.cssSelector(".button-blue")).click();
			Thread.sleep(3000);

			// Store the Connection status into string
			String ConnectionFail = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span"))
					.getText();
			System.out.println(ConnectionFail);

			// Verification condition
			if (ConnectionFail.equalsIgnoreCase("Not Connected")) {
				System.out.println("Work Group Server connection failed is successfully done");
			} else {
				System.out.println("Work Group Server connection failed");
			}

			// Click on Connection Icon
			driver.findElement(By.cssSelector("img.settings-image")).click();
			Thread.sleep(3000);

			// Connection Verification
			String ConnectionPass = driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span"))
					.getText();
			if (ConnectionPass.equalsIgnoreCase("Connected")) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Workgrup server connection success");
				System.out.println("Work Group Server connected");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Workgrup server connection failed");
				System.out.println("Work Group Server not connected");
			}
			Thread.sleep(1000);

			// Click on Edit
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			// driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/div/div[2]")).click();
			driver.findElement(By.xpath("//li[2]/a")).click();

			// Change password
			driver.findElement(By.name("port")).clear();
			driver.findElement(By.name("port")).sendKeys("4010");
			Thread.sleep(3000);
			driver.findElement(By.cssSelector(".button-blue")).click();
			Thread.sleep(3000);
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occures while connecting to workgroup server, check details: " + e.getMessage());
		}
	}

	@TestRail(testCaseId = 28)
	@Test(priority = 4)
	public static void AddNode(ITestContext context) throws InterruptedException, IOException {
		try {
			// Click on checbox and Select the create node option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			Actions MousehourNode = new Actions(driver);
			MousehourNode
					.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")))
					.perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li")).click();

			// Create Node page
			// driver.findElement(By.xpath("(//input[@type='text'])[4]")).click();
			driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(NodeName);
			driver.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(HostName);
			driver.findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(IPAddress);

			// port number
			driver.findElement(By.xpath("(//input[@type='text'])[6]")).clear();
			driver.findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys(Node_PortNumber);

			// Submit
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(6000);

			// Refresh the Node viewlet
			driver.findElement(By.xpath("//img[@title='Refresh viewlet']")).click();
			Thread.sleep(4000);

			// Store the viewlet data into string
			String NodeData = driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body"))
					.getText();

			// Verification of node is added or not
			if (NodeData.contains(NodeName)) {
				System.out.println("Node is created successfully");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Node creation success");
			} else {
				System.out.println("Node is not created");
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Node creation failed");
				driver.findElement(By.xpath("Node creation failed")).click();
			}
		} catch (Exception e) {
			/*
			 * File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			 * FileUtils.copyFile(scrFile,new
			 * File("F:\\Nagaraju\\Workgroup server\\AddNode.png"));
			 */
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured when adding Node from Workspace page, check details: " + e.getMessage());
			System.out.println("Exception occured when adding Node from Workspace page");
			driver.findElement(By.cssSelector(".btn-danger")).click();
		}
		Thread.sleep(1000);
	}

	@Parameters({ "ConnectionInstanceName", "RemoteQueueManagerName", "ConnectionName", "CommandQueueName",
			"ChannelName" })
	@TestRail(testCaseId = 29)
	@Test(priority = 5)
	public void AddRemoteQueueManager(String ConnectionInstanceName, String RemoteQueueManagerName,
			String ConnectionName, String CommandQueueName, String ChannelName, ITestContext context)
			throws InterruptedException {
		try {
			// Select Remote queue manager option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			Actions Mousehour = new Actions(driver);
			Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")))
					.perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[2]/a")).click();
			Thread.sleep(1000);

			// Click on Add button
			driver.findElement(By.xpath("//div/div/div/div[2]/div/div/button")).click();

			// Queue manager connection instance
			driver.findElement(By.name("name")).clear();
			driver.findElement(By.name("name")).sendKeys(ConnectionInstanceName);

			// Queue manager name
			driver.findElement(By.name("qmgrName")).clear();
			driver.findElement(By.name("qmgrName")).sendKeys(RemoteQueueManagerName);

			// Goto communication Tab
			driver.findElement(By.linkText("Communication")).click();

			// Connection name
			driver.findElement(By.name("connName")).clear();
			driver.findElement(By.name("connName")).sendKeys(ConnectionName);

			// command Queue Name
			Select queue = new Select(driver.findElement(By.name("references")));
			queue.selectByVisibleText(CommandQueueName);

			// Channel Name
			driver.findElement(By.name("channelName")).clear();
			driver.findElement(By.name("channelName")).sendKeys(ChannelName);

			// click on OK button
			driver.findElement(By.xpath("//app-mod-remote-queue-manager-options/div/div[2]/div/div/div/button"))
					.click();
			Thread.sleep(2000);

			// Store the Queue managers into string
			String Queuemanagers = driver
					.findElement(By.xpath("//app-mod-remote-queue-manager-connections/div/div/div/div")).getText();

			// Verification
			if (Queuemanagers.contains(RemoteQueueManagerName)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote Queue Manager is added successfully");
				System.out.println("Remote Queue Manager is added successfully");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to add Remote Queue Manager");
				System.out.println("Remote Queue Manager is not added");
				driver.findElement(By.xpath("Queue manager add option is failed")).click();
			}
			Thread.sleep(1000);

			// Close the remote queue managers connection popup page
			driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured when Adding remote Queue manager, Check details: " + e.getMessage());
			System.out.println("Exception occured when Adding remote Queue manager");
			driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		}
		Thread.sleep(1000);

	}

	@TestRail(testCaseId = 30)
	@Test(priority = 6)
	public void ModifyRemoteQueueManager(ITestContext context) throws InterruptedException {
		try {
			// Select Remote queue manager option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			Actions Mousehour = new Actions(driver);
			Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")))
					.perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[2]/a")).click();
			Thread.sleep(1000);

			// Click on Modify button
			driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();

			// Goto communication Tab
			driver.findElement(By.linkText("Communication")).click();

			// Connection name
			driver.findElement(By.name("connName")).clear();
			driver.findElement(By.name("connName")).sendKeys(Node_NewConnectionName);

			// click on OK button
			driver.findElement(By.xpath("//app-mod-remote-queue-manager-options/div/div[2]/div/div/div/button"))
					.click();
			Thread.sleep(2000);

			// Store the connection ip into string after modifying the name
			String ChangedConnectionip = driver.findElement(By.xpath("//div[2]/div/table/tbody/tr/td[2]")).getText();

			if (ChangedConnectionip.equalsIgnoreCase(Node_NewConnectionName)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote Queue modification are done successfully");
				System.out.println("Remote Queue modification is done successfully");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to edit Remote Queue Manager");
				System.out.println("Remote Queue modification is failed");
				driver.findElement(By.xpath("Modification failed")).click();
			}

			// Close the remote queue managers connection popup page
			driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while modifying remote Queue manager, Check details: " + e.getMessage());
			System.out.println("Exception occured when modifying remote Queue manager");
			driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		}
		Thread.sleep(1000);
	}

	@Parameters({ "DeleteManagerName" })
	@TestRail(testCaseId = 31)
	@Test(priority = 7)
	public void DeleteRemoteQueueManager(String DeleteManagerName, ITestContext context) throws InterruptedException {
		try {
			// Select Remote queue manager option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			Actions Mousehour = new Actions(driver);
			Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")))
					.perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[2]/a")).click();
			Thread.sleep(1000);

			// Select the required Queue manager
			driver.findElement(By.xpath("//td[contains(.,'" + DeleteManagerName + "')]")).click();

			// Click on Delete
			driver.findElement(By.xpath("//div[3]/button")).click();
			driver.findElement(By.xpath("//app-mod-confirmation/div/div[2]/div/div/div/button")).click();

			// Store the Queue managers into string
			String Queuemanagers = driver
					.findElement(By.xpath("//app-mod-remote-queue-manager-connections/div/div/div/div")).getText();
			System.out.println(Queuemanagers);

			// Verification condition
			if (Queuemanagers.contains(DeleteManagerName)) {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to delete Remote Queue Manager");
				System.out.println("Remote Queue Manager is not deleted");
				driver.findElement(By.xpath("Remote Queue Manager delete Failed")).click();
			} else {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote Queue Manager deleted successflly");
				System.out.println("Remote Queue Manager is deleted");
			}
			Thread.sleep(2000);
			// Close the remote queue managers connection popup page
			driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while Deleting remote Queue manager, Check details: " + e.getMessage());
			System.out.println("Exception occured when Deleting remote Queue manager");
			driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		}
		Thread.sleep(1000);
	}

	@Parameters({ "AgentInstanceName", "ServerName", "ServerURL" })
	@TestRail(testCaseId = 32)
	@Test(priority = 8)
	public void AddRemoteEMSManager(String AgentInstanceName, String ServerName, String ServerURL, ITestContext context)
			throws InterruptedException {
		try {
			// Select Remote EMS manager option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			Thread.sleep(2000);

			Actions Mousehour = new Actions(driver);
			Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")))
					.perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[3]")).click();
			Thread.sleep(1000);

			// Click on Add button //app-dropdown[@id='dropdown-block']/div/ul/li[3]/a
			driver.findElement(By.xpath("//div[2]/div/div/button")).click();

			// EMS Agent Instance Name
			driver.findElement(By.id("agentInstanceName")).clear();
			driver.findElement(By.id("agentInstanceName")).sendKeys(AgentInstanceName);

			// EMS Server name
			driver.findElement(By.id("serverName")).clear();
			driver.findElement(By.id("serverName")).sendKeys(ServerName);

			// Server URL
			driver.findElement(By.id("serverURL")).clear();
			driver.findElement(By.id("serverURL")).sendKeys(ServerURL);

			// click on OK button
			driver.findElement(By.xpath("//app-mod-remote-ems-manager-options/div/div[2]/div/div/div/button")).click();
			Thread.sleep(2000);

			// Store the EMS servers data into string
			String RemoteEMSserver = driver
					.findElement(By.xpath("//app-mod-remote-ems-manager-connections/div/div/div/div")).getText();

			// verification of Remote ems server
			if (RemoteEMSserver.contains(AgentInstanceName) && RemoteEMSserver.contains(ServerName)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote EMS server is added successflly");
				System.out.println("Remote EMS server is added");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to add Remote EMS server");
				System.out.println("Remote EMS server is not added");
				driver.findElement(By.id("Add EMS failed")).click();
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while adding Remote EMS server, Check details: " + e.getMessage());
		}
	}

	@Parameters({ "UpdatedServerURL" })
	@TestRail(testCaseId = 33)
	@Test(priority = 9)
	public void ModifyRemoteEMSServer(String UpdatedServerURL, ITestContext context) throws InterruptedException {
		try {
			// Click on Modify button
			driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();

			// Server URL
			driver.findElement(By.id("serverURL")).clear();
			driver.findElement(By.id("serverURL")).sendKeys(UpdatedServerURL);

			// click on OK button
			driver.findElement(By.xpath("//app-mod-remote-ems-manager-options/div/div[2]/div/div/div/button")).click();
			Thread.sleep(2000);

			// Store the Server URL value into string
			String URL = driver.findElement(By.xpath("//div[2]/div/table/tbody/tr/td[2]")).getText();

			if (URL.equalsIgnoreCase(UpdatedServerURL)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote EMS server is edited successflly");
				System.out.println("Remote EMS server is modified");
			} else {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Failed to edit Remote EMS server");
				System.out.println("Remote EMS server is not modified");
				driver.findElement(By.id("Modify failed")).click();
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while editing Remote EMS server, Check details: " + e.getMessage());
		}
	}

	@Parameters({ "ServerName" })
	@TestRail(testCaseId = 34)
	@Test(priority = 10)
	public void DeleteRemoteEMSServer(String ServerName, ITestContext context) throws InterruptedException {
		try {
			// Click on Server name
			driver.findElement(By.xpath("//td[contains(.,'" + ServerName + "')]")).click();

			Thread.sleep(2000);

			// Click on Delete button
			driver.findElement(By.xpath("//div[3]/button")).click();

			// Click on Confirmation yes button
			driver.findElement(By.id("accept-true")).click();
			Thread.sleep(2000);

			// Store the EMS servers data into string
			String RemoteEMSserver = driver
					.findElement(By.xpath("//app-mod-remote-ems-manager-connections/div/div/div/div")).getText();

			// verification of Remote ems server
			if (RemoteEMSserver.contains(ServerName)) {
				System.out.println("Delete Remote EMS server is failed");
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to delete Remote EMS server");
				driver.findElement(By.id("Delete failed")).click();
			} else {
				System.out.println("Remote EMS server is deleed successflly");
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Remote EMS server is edited successflly");
			}
			Thread.sleep(1000);

			// Close the window
			driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while deleting Remote EMS server, Check details: " + e.getMessage());
		}

	}

	@Parameters({ "Dashboardname", "FavoriteViewletName", "Favwgs" })

	@Test(priority = 11)
	public void AddToFavoriteviewlet(String Dashboardname, String FavoriteViewletName, int Favwgs, ITestContext context)
			throws InterruptedException {
		try {
			// Store the WGS name into string
			String WGSName = driver.findElement(By.xpath("//datatable-body-cell[3]/div/span")).getText();

			// Add Dashboard
			driver.findElement(By.cssSelector("div.block-with-border")).click();
			driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);

			// Create viewlet button
			driver.findElement(By.xpath("//button[@type='submit']")).click();
			Thread.sleep(2000);

			// Create the favorite viewlet
			driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
			driver.findElement(By.id("fav")).click();
			driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();

			// Viewlet Name
			driver.findElement(By.name("viewlet-name")).click();
			driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);

			Select wgsdropdown = new Select(driver.findElement(By.name("wgs")));
			wgsdropdown.selectByIndex(Favwgs);

			// Submit
			driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue"))
					.click();
			Thread.sleep(2000);

			// Back to Workspace page
			driver.findElement(By.xpath("//li/div/div")).click();
			Thread.sleep(1000);

			// Select Add to Favorite option
			driver.findElement(By.xpath(
					"/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input"))
					.click();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul[2]/li")).click();
			Thread.sleep(1000);

			// Select the favorite viewlet name
			Select fav = new Select(driver.findElement(By.cssSelector(".fav-select")));
			fav.selectByVisibleText(FavoriteViewletName);
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue"))
					.click();
			Thread.sleep(1000);

			// Go to Favorite viewlet dashboard page
			driver.findElement(By.xpath("//li[2]")).click();
			Thread.sleep(1000);

			// Store the favorite viewlet data into string
			String FavViewletData = driver.findElement(By.xpath("//datatable-body")).getText();

			if (FavViewletData.contains(WGSName)) {
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "WGS is added to favorite viewlet");
				System.out.println("WGS is added to favorite viewlet");
			} else {
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to add WGS to favorite viewlet");
				System.out.println("WGS is not added to favorite viewlet");
				driver.findElement(By.id("Add to favorite failed")).click();
			}
			Thread.sleep(2000);

		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while adding WGS to favorite viewlet, Check details: " + e.getMessage());
		}
	}

	@TestRail(testCaseId = 35)
	@Test(priority = 12)
	public void SearchFilter(ITestContext context) throws Exception {
		try {
			// Back to Workspace page
			driver.findElement(By.xpath("//li/div/div")).click();
			Thread.sleep(1000);

			// Add New WGS10
			this.AddWorkgroupFromPlusIcon(context);
			Thread.sleep(2000);

			// Enter the search data into search field
			driver.findElement(By.xpath("//input[@type='text']")).sendKeys(WGSSearchInputData);
			Thread.sleep(2000);

			// driver.findElement(By.id("faile test case")).click();

			// Store the Viewlet data into String
			String Viewletdata = driver.findElement(By.xpath("//datatable-body")).getText();
			// System.out.println(Viewletdata);

			// Split the Number of rows present in the viewlet
			String[] multiwords = Viewletdata.split("4010", 1);
			// System.out.println("The Rows are:" +Arrays.toString(multiwords));
			Thread.sleep(2000);

			// Check the each row contains the searched data or not
			for (String a : multiwords) {
				if (a.toUpperCase().contains(WGSSearchInputData.toUpperCase())) {
					context.setAttribute("Status", 1);
					context.setAttribute("Comment", "Search is working fine");
					System.out.println("Search is working fine");
				} else {
					context.setAttribute("Status", 5);
					context.setAttribute("Comment", "Search is not working");
					System.out.println("Search is not working fine");
					driver.findElement(By.xpath("Search is failed")).click();
				}

			}
			Thread.sleep(2000);
			// Clear the Search data
			driver.findElement(By.xpath("//input[@type='text']")).clear();
			Thread.sleep(2000);
		} catch (Exception e) {
			context.setAttribute("Status", 5);
			context.setAttribute("Comment",
					"Exception occured while doing search on workgroup server, Check details: " + e.getMessage());

			driver.findElement(By.id("Failing the condition")).click();
		}
	}

	@Test(priority = 13)
	public void Logout() throws Exception {
		try {
			// Close the opened Dashboard
			driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Dashboards are not present");
		}
		Thread.sleep(1000);

		// Logout
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
			
			try {
				//Update attachment to testrail server
				int testCaseID=0;
				//int status=(int) result.getTestContext().getAttribute("Status");
				//String comment=(String) result.getTestContext().getAttribute("Comment");
				  if (result.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(TestRail.class))
					{
					TestRail testCase = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestRail.class);
					// Get the TestCase ID for TestRail
					testCaseID = testCase.testCaseId();
					
					
					
					TestRailAPI api=new TestRailAPI();
					api.Getresults(testCaseID, result.getMethod().getMethodName());
					
					}
				}catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
				}
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
