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

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;
import testrail.TestRailAPI;

@Listeners(TestClass.class)
public class EMSChannels 
{
	static WebDriver driver;
	static String WGS_INDEX;
	static String Screenshotpath;
	static String DownloadPath;
	static String WGSName;
	static String UploadFilepath;
	static String EMS_WGS_INDEX;
	static String EMS_WGSNAME;
	static String SelectTopicName;
	static String DeleteDurableName;

	
	@BeforeTest
	public void beforeTest() throws Exception {
		System.out.println("BeforeTest");
		Settings.read();
		
		WGS_INDEX =Settings.getWGS_INDEX();
		Screenshotpath =Settings.getScreenshotPath();
		DownloadPath =Settings.getDownloadPath();
		WGSName =Settings.getWGSNAME();
		UploadFilepath =Settings.getUploadFilepath();
		EMS_WGS_INDEX =Settings.getEMS_WGS_INDEX();
		EMS_WGSNAME =Settings.getEMS_WGSNAME();
		SelectTopicName = Settings.getSelectTopicName(); 
		DeleteDurableName =Settings.getDeleteDurableName();
	}

	
	@Parameters({"sDriver", "sDriverpath", "Dashboardname", "ChannelName"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String Dashboardname, String ChannelName) throws Exception
	{
		Settings.read();
		String URL = Settings.getSettingURL();
		String uname=Settings.getNav_Username();
		String password=Settings.getNav_Password();
		
		if(sDriver.equalsIgnoreCase("webdriver.chrome.driver"))
		{
		System.setProperty(sDriver, sDriverpath);
		driver=new ChromeDriver();
		}
		else if(sDriver.equalsIgnoreCase("webdriver.ie.driver"))
		{
			System.setProperty(sDriver, sDriverpath);
			driver=new InternetExplorerDriver();
		}
		else if(sDriver.equalsIgnoreCase("webdriver.edge.driver"))
		{
			System.setProperty(sDriver, sDriverpath);
			driver= new EdgeDriver();
		}
		else
		{
			System.setProperty(sDriver, sDriverpath);
			driver= new FirefoxDriver();
		}
		
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Login page
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(2000);
		
		//Create New Dashboard
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		/*driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByIndex(wgs);*/
		
		/*//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		
		// ---- Creating Channel Viewlet ----
		//Click on Viewlet button
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.xpath("//app-mod-select-viewlet-type/div/div[2]/button[2]")).click(); 
			
		//Create Channel
		driver.findElement(By.cssSelector(".object-type:nth-child(4)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(ChannelName);
		
		//Select WGS type
		Select WGSSelection=new Select(driver.findElement(By.name("wgsKey")));
		WGSSelection.selectByVisibleText(EMS_WGSNAME);
		
		//Check ems checkbox
		driver.findElement(By.id("ems")).click();
		
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
	
	}
	
	@Parameters({"schemaName"})
	@TestRail(testCaseId=276)
	@Test(priority=18)
	public static void ShowObjectAttributes(String schemaName, ITestContext context) throws InterruptedException
	{	
		try {
		EMSObjects obj=new EMSObjects();
		obj.ChannelObjectAttributesVerification(driver, schemaName);
		context.setAttribute("Status", 1);
		context.setAttribute("Comment", "Show Object Attributes option is working fine");
		}
		catch (Exception e) {
			// TODO: handle exception
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Show Object Attributes option is not working prperly, check details: "+ e.getMessage());
		}
	}
		
	@Test(priority=9)
	@TestRail(testCaseId=277)
	public static void Events(ITestContext context) throws InterruptedException
	{
		//Select channel Events option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]")).click();
		Thread.sleep(1000);
		
		//Events Popup page
		String Eventdetails=driver.findElement(By.xpath("//th")).getText();
		//System.out.println(Eventdetails);
				
		//Verification condition
		if(Eventdetails.equalsIgnoreCase("Event #"))
		{
			System.out.println("Events page is opened");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Events page is opened and working fine");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open events page");
			System.out.println("Events page is not opened");
			driver.findElement(By.xpath("Events failed")).click();
		}
				
		//Clicking on Events Count
		try 
		{
			if(driver.findElement(By.xpath("//td")).isDisplayed())
			{
				String Eventcount=driver.findElement(By.xpath("//td")).getText();
				System.out.println(Eventcount);
				driver.findElement(By.xpath("//td")).click();
				
				//Click on daignostic tab
				driver.findElement(By.xpath("//app-mod-event-details/div/div/div/button[2]")).click();
				
				//get the vents count and store the into string
				String DignosticCount=driver.findElement(By.xpath("//div/input")).getAttribute("value");
				System.out.println("Daignostic events count:" +DignosticCount);
				
				if(Eventcount.equalsIgnoreCase(DignosticCount))
				{
					System.out.println("Events count is matched");
					context.setAttribute("Status", 1);
					context.setAttribute("Comment", "Event Count is Matched and working fine");
					//Close the Event details page
					driver.findElement(By.xpath("//app-mod-event-details/div/div[2]/button")).click();
				}
				else
				{
					System.out.println("Events count is not matched");
					context.setAttribute("Status", 5);
					context.setAttribute("Comment", "Got exception while opening events page, check details: ");
					driver.findElement(By.xpath("//app-mod-event-details/div/div[2]/button")).click();
					driver.findElement(By.xpath("//app-console-tabs/div/div/ul/li/div/div[2]/i")).click();
					driver.findElement(By.id("Events count failed")).click();
				}
				
			}
		}
		catch (Exception e)
		{
			System.out.println("Events are not present");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Events not found");
		}
				
		//Close the events popup page
		driver.findElement(By.xpath("//app-console-tabs/div/div/ul/li/div/div[2]/i")).click();
		
	}
	
	@Parameters({"FavoriteViewletName"})
	@TestRail(testCaseId=278)
	@Test(priority=10)
	public static void AddToFavorites(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//CLick on Viewlet and choose favorite viewlet crate check box
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();
		
		//Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);
		
		//WGS selection
		Select wgsdropdown=new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByVisibleText(EMS_WGSNAME);
		
		//Submit
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Store String value
		String channelName=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		
		
		//Select Add to Favorites option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(2000);
		
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Get the data from favorite viewlet
		String Favoritedata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verify the channel added to Favorite viewlet
		if(Favoritedata.contains(channelName))
		{
			System.out.println("Channel is added to the favorite viewlet");
			 context.setAttribute("Status", 1);
			 context.setAttribute("Comment", "Channel is added to the favorite viewlet");
		}
		else
		{
			System.out.println("Channel is added to the favorite viewlet");
			 context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Failed to add channels to favorite viewlet");
			driver.findElement(By.xpath("Channel condition failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=11)
	@TestRail(testCaseId=279)
	public static void ComapareChannels(ITestContext context) throws InterruptedException
	{
		//Select Two channels
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
		Thread.sleep(1000);
		
		//Store the popup page value into string
		String CompareChannels=driver.findElement(By.cssSelector("span.name")).getText();
		System.out.println(CompareChannels);
		
		//Verification condition
		if(CompareChannels.equalsIgnoreCase("Compare"))
		{
			System.out.println("Channels Compare page is opened");
			context.setAttribute("Status", 1);
			 context.setAttribute("Comment", "Channels Compare page is opened and working fine");
		}
		else
		{
			System.out.println("Channels Compare page is not opened");
			context.setAttribute("Status", 5);
			 context.setAttribute("Comment", "Failed to open channels Compare page");
			driver.findElement(By.xpath("Comparision failed")).click();
		}
		Thread.sleep(1000);
				
		//Differences only
		driver.findElement(By.cssSelector("div.differences > label.switch > span.slider.round")).click();
				
		try
		{
		String difference1=driver.findElement(By.xpath("//tr[2]/td[2]")).getText();
		System.out.println("First value" +difference1);
		String difference2=driver.findElement(By.xpath("//tr[2]/td[3]")).getText();
		System.out.println("Second value" +difference2);
		
		if(!(difference1.isEmpty() && difference2.isEmpty()))
		{
		
		if(difference1.equalsIgnoreCase(difference2))
		{
			System.out.println("Popup showing the same values Differences");
			context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Bridge comparision is working fine");
    		driver.findElement(By.xpath("Differences")).click();
		}
		else
		{
			System.out.println("Popup showing the Different values");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Showing the different values");
			
		}
		}
		else
		{
			System.out.println("Empty records");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Showing the different values");
		}
		}
			
		catch (Exception e) {
	     // TODO Auto-generated catch block
	        System.out.println("No differences between Processes");
	        context.setAttribute("Status", 5);
			context.setAttribute("Comment", "No differences between Processes");
	       } 
		 
		//Closing the compare popup page
		driver.findElement(By.cssSelector(".close-button")).click();
	    Thread.sleep(1000);		
	}
	
	@Parameters({"FavoriteViewletName"})
	@TestRail(testCaseId=280)
	@Test(priority=16)
	public static void AddToFavoriteForMultipleChannels(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Storage of channel names
		String channelname1=driver.findElement(By.xpath("//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String channelname2=driver.findElement(By.xpath("//datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Addto favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(1000);
		
		//Get the data from favorite viewlet
		String Favoritedata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verify the channel is added to Favorite viewlet
		if(Favoritedata.contains(channelname1) && Favoritedata.contains(channelname2))
		{
			System.out.println("Multiple channels are added into the Favorite Viewlet");
			context.setAttribute("Status", 1);
			 context.setAttribute("Comment", "Multiple channels are added into the Favorite Viewlet");
		}
		else
		{
			System.out.println("Multiple channels are not added into the Favorite Viewlet");
			context.setAttribute("Status", 5);
			 context.setAttribute("Comment", "Failed to add multiple channels to Favorite Viewlet");
			driver.findElement(By.xpath("favorite viewlet condition failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=27)
	public static void Logout() throws InterruptedException
	{
		//Delete the dashboard 
		try
		{
			driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
			//driver.findElement(By.cssSelector(".fa-times")).click();
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(1000);
		}
		catch (Exception e)
		{
			System.out.println("Dashboards are not present");
		}
		Thread.sleep(1000);
		
		//Logout
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {

		final String dir = System.getProperty("user.dir");
		String screenshotPath;
		
		System.out.println("result getStatus: " + result.getStatus());
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
