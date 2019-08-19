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

@Listeners(TestClass.class)
public class EMSQueues 
{
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
String  DWGS;
String Dnode;
String NodeName;
String Node;
static String DownloadPath;
String Queuemanager;
String wgs;
static String QueueName;
static String LocalQueue;

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
	WGSName=Settings.getS_WGSName();

	Screenshotpath = Settings.getScreenshotPath();
	DWGS=Settings.getWGS_HostName();
	Dnode=Settings.getDnode();
	NodeName=Settings.getEMS_NodeName();
	DownloadPath=Settings.getDownloadPath();
	Node=Settings.getEMS_NodeName();
	Queuemanager=Settings.getQueuemanager();
	wgs=Settings.getWGS_INDEX();
	QueueName=Settings.getQueueName();
	LocalQueue=Settings.getLocalQueue();
}
	
	@Parameters({"sDriver", "sDriverpath",  "Dashboardname"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String Dashboardname) throws Exception
	{
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
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
		
		//--------- Create Queue viewlet-----------
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Route viewlet
		driver.findElement(By.cssSelector(".object-type:nth-child(3)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(LocalQueue);
		
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(1000);
		dd.selectByVisibleText(WGSName);
		
		//Click on EMS checkbox
		driver.findElement(By.id("ems")).click();
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		
	}
	
	@Test(priority=1)
	@TestRail(testCaseId=290)
	public static void BrowseMessages(ITestContext context) throws InterruptedException
	{
		//Select Browse Messages Option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
		Thread.sleep(1000);               
		
		//Store the browse message page value into string
		String BrowseMessagespage=driver.findElement(By.cssSelector(".g-row-head:nth-child(2)")).getText();
		System.out.println(BrowseMessagespage);
		
		//verification
		if(BrowseMessagespage.equalsIgnoreCase("Message Cursor"))
		{
			System.out.println("Message browse page is opened");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Message browse page is opened");
		}
		else
		{
			System.out.println("Message browse page is not opened");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open message browse page");
			driver.findElement(By.xpath("Message browse page is failed")).click();
		}
		//Close the popup page
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);
	}
	
	@Parameters({"SchemaName"})
	@Test(priority=20)
	public static void ShowObjectAttributesForQueues(String SchemaName, ITestContext context) throws InterruptedException
	{	try {
		EMSObjects obj=new EMSObjects();
		obj.EMSQueueObjectAttributesVerification(driver, SchemaName);
		context.setAttribute("Status", 1);
		context.setAttribute("Comment", "Show object attributes working fine");
		
		}catch(Exception e)
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Got exception while showing object attributes, check details: "+  e.getMessage());
		}
		
	}
	
	@Test(priority=3)
	@TestRail(testCaseId=291)
	public static void ShowQueueStatus(ITestContext context) throws InterruptedException
	{
		//Select show object Attributes Option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")).click();
		Thread.sleep(1000);
		
		//Store the column name "Name" into string
		String Queuestatuspage=driver.findElement(By.cssSelector(".wrapper-console-tabs")).getText();
		System.out.println(Queuestatuspage);
		
		//Verification condition
		if(Queuestatuspage.contains("Name"))
		{
			System.out.println("Show Queue Status page is opened");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show Queue Status page is opened");
		}
		else
		{
			System.out.println("Show Queue Status page is opened");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open Queue Status page");
			driver.findElement(By.xpath("Queue Status page opening is failed")).click();
		}
		
		//Close the Queue status page
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);
			
	}
	
	@Parameters({"QueueNameFromOptions"})
	@TestRail(testCaseId=292)
	@Test(priority=4)
	public void CreateQueueFromOptions(String QueueNameFromOptions, ITestContext context) throws InterruptedException
	{
	
		//Select show object Attributes Option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//Create Queue Window
		driver.findElement(By.name("name")).sendKeys(QueueNameFromOptions);
		Thread.sleep(10000);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(3000);
		
		try
		{
			driver.findElement(By.id("yes")).click();
		}
		catch (Exception e)
		{
			System.out.println("No Exception occured");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Exception occured while creating queue, check details: "+ e.getMessage());
		}
		Thread.sleep(2000);
		
		//Change the Settings We need to check show empty queues for verification
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.cssSelector(".checkbox:nth-child(2) > input")).click();
		driver.findElement(By.cssSelector(".btn-group:nth-child(3) > .btn")).click();
		Thread.sleep(2000);
		
		//Store the first queue name into string
		String Firstqueue=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		System.out.println(Firstqueue); 
		
		//Applying the loop from second queue onwords
		for(int q=2; q<=100; q++)
		{
		String AddedQueuename=driver.findElement(By.xpath("//datatable-row-wrapper["+ q +"]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		System.out.println(AddedQueuename);               
		
		//Verification 
		if(AddedQueuename.equalsIgnoreCase(QueueNameFromOptions) || Firstqueue.equalsIgnoreCase(QueueNameFromOptions))
		{
			System.out.println("Queue is created From options");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Queue is created From options");
			break;
		}
		/*else
		{
			System.out.println("Queue is not Created from options");
			driver.findElement(By.xpath("Queue creation failed")).click();
		}*/
		}		
		Thread.sleep(2000);
			
	}
	
	@Parameters({"ObjectName"})
	@TestRail(testCaseId=293)
	@Test(priority=5)
	public static void QueueCommands(String ObjectName, ITestContext context) throws InterruptedException
	{
		//Select copy as option from Commands
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions CopyMousehover=new Actions(driver);
		CopyMousehover.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[7]"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[7]/ul/li")).click();
		Thread.sleep(1000);
		
		//Object Details
		driver.findElement(By.xpath("//div[2]/div/input")).sendKeys(ObjectName);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(3000);
		
		context.setAttribute("Status", 1);
		context.setAttribute("Comment", "Queue command option is working fine");
		
		//---------  Delete the Queue -----------
		/*//change settings
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(1000);*/
		
		/*//Store the First Queue into string
		String Firstqueue=driver.findElement(By.xpath("//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		System.out.println(Firstqueue);
		
		String FinalQueueName=Firstqueue+ObjectName;
		
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(FinalQueueName);
		Thread.sleep(1000);
				
		//Take the Queue name whcih one you want to delete
		String Queuenamebefore=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		System.out.println(Queuenamebefore);
		
		//Select Commands option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions DeleteMousehover=new Actions(driver);
		DeleteMousehover.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[7]"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[7]/ul/li[2]")).click();
		Thread.sleep(1000);
		
		//Delete option
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(4000);
		
		//clear search data
		for(int j=0; j<=FinalQueueName.length(); j++)
    	{
    	
			driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.BACK_SPACE);
    	}
    	Thread.sleep(2000);
    	
    	for(int j=1; j<=2; j++)
    	{
    	//Refresh the viewlet
    	driver.findElement(By.xpath("//img[@title='Refresh viewlet']")).click();
    	Thread.sleep(1000);
    	}
		
		try
		{
		if(driver.findElement(By.xpath("//app-mod-errors-display/div/button")).isDisplayed())
		{
			driver.findElement(By.xpath("//app-mod-errors-display/div/button")).click();
			
			//Click on Cancel button
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		
		else
		{
			//Store the Queue name after deleting the Queue
			String Queuenameafterdelete=driver.findElement(By.xpath("//datatable-body-cell[3]/div/span")).getText();
			System.out.println(Queuenameafterdelete);
			
			if(Queuenamebefore.equalsIgnoreCase(Queuenameafterdelete))
			{
				System.out.println("Queue is not deleted");
				driver.findElement(By.xpath("Queue Delete failed")).click();
			}
			else
			{
				System.out.println("Queue is deleted Successfully");
			}
			Thread.sleep(1000);
		}
		}
		catch (Exception e) 
		{
            System.out.println("Exception occured while deleting the queue");
        }
		
		// Changing the Settings 
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(1000);	*/
	}
	
	@Test(priority=6)
	@TestRail(testCaseId=294)
	public static void QueueProperties(ITestContext context) throws InterruptedException
	{
		//Select Queue properties option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[9]")).click();
		Thread.sleep(1000);
		
		boolean FieldNamevalue=driver.findElement(By.id("name")).isEnabled();
		System.out.println(FieldNamevalue);
		
		//Verification
		if(FieldNamevalue == false)
		{
			System.out.println("Queue Name field is UnEditable");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Queue Name field is UnEditable");
			 driver.findElement(By.cssSelector(".btn-primary")).click();
		}
		else
		{
			System.out.println("Queue Name field is Editable");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Queue Name field is Editable");
			driver.findElement(By.cssSelector(".btn-primary")).click();
			driver.findElement(By.xpath("Queue name edit function Failed")).click();
			
		}
	}
	
	@Test(priority=7)
	@TestRail(testCaseId=295)
	public static void QueueEvents(ITestContext context) throws InterruptedException
	{
		//Select Queue Events option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[10]")).click();
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
			System.out.println("Events page is not opened");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open events page");
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
	
	@Parameters({"FavoriteViewletName", "Favwgs" })
	@Test(priority=8)
	public void AddToFavoriteViewlet(String FavoriteViewletName, int Favwgs) throws InterruptedException
	{
		//Select the viewlet option and select the favorite checkbox
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();
		
		//Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);
		
		//Select WGS
		Select wgsdropdown=new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByIndex(Favwgs);
		
		//Submit
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Store Queue name
		String QueueFav=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		
		//Select Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Store favorite viewlet data into string
		String Favdata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//verification
		if(Favdata.contains(QueueFav))
		{
			System.out.println("Queue is added to favorite viewlet");
		}
		else
		{
			System.out.println("Queue is not added to favorite viewlet");
			driver.findElement(By.xpath("Queue not add to favorite")).click();
		}
		Thread.sleep(1000);
		
	}
	
	@Test(priority=9)
	@TestRail(testCaseId=296)
	public static void CompareQueues(ITestContext context) throws InterruptedException
	{
		//Check multiple checkboxes and select compare option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.cssSelector(".vertical-nav > .ng-star-inserted:nth-child(1)")).click();
		Thread.sleep(1000);
		
		//Store the compare popup page value into string
		String CompareQueues=driver.findElement(By.xpath("//div[2]/div/div/span")).getText();
		System.out.println(CompareQueues);
		
		//Verification
		if(CompareQueues.equalsIgnoreCase("Compare"))
		{
			System.out.println("Managers Compare page is opened");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Comaparision is working fine");
		}
		else
		{
			System.out.println("Managers Compare page is opened");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open managers compare page");
			driver.findElement(By.xpath("Comparision failed")).click();
		}
		Thread.sleep(1000);
		
		//Click on Differences only
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
	
	@Test(priority=10)
	@TestRail(testCaseId=297)
	public static void ShowQueueStatusForMultipleQueues(ITestContext context) throws InterruptedException
	{
		//Select show queue status option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.cssSelector(".vertical-nav > .ng-star-inserted:nth-child(2)")).click();
		Thread.sleep(1000);
		
		//Store the column name "Name" into string
		String Queuestatuspage=driver.findElement(By.cssSelector(".wrapper-console-tabs")).getText();
		//System.out.println(Queuestatuspage);
		
		//Verification condition
		if(Queuestatuspage.contains("Name"))
		{
			System.out.println("Show Multiple Queue Status page is opened");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show Multiple Queue Status page is opened");
		}
		else
		{
			System.out.println("Show Multiple Queue Status page is opened");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open Multiple Queue Status page");
			driver.findElement(By.xpath("Queue Status page opening is failed")).click();
		}
		
		//Close the Queue status page
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);
	}
	
	@Parameters({"TestDescription"})
	@TestRail(testCaseId=298)
	@Test(priority=11)
	public void MultipleQueueProperties(String TestDescription, ITestContext context) throws InterruptedException
	{
		//Select viewlet option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[6]")).click();
		Thread.sleep(1000);
		
		WebElement Top=driver.findElement(By.id("name"));
		Actions a=new Actions(driver);
		a.moveToElement(Top).perform();
		
		//Get tooltip data
		String Tooltipdata=driver.findElement(By.tagName("ngb-tooltip-window")).getText();
		//System.out.println(Tooltipdata);
		
		//close the properties page
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(4000);
		
		//Open the properties of first queue
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[9]")).click();
		Thread.sleep(1000);
		
		//Save the Connection URL value into string
		String FirstQueuedata=driver.findElement(By.id("name")).getAttribute("value");
		
		//close the properties page
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(2000);
		
		//Open the properties of Second queue
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[9]")).click();
		Thread.sleep(1000);
		
		//Save the Connection URL value into string
		String SecondQueuedata=driver.findElement(By.id("name")).getAttribute("value");
		
		//close the properties page
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(2000);
		
		//Verification
		if(Tooltipdata.contains(FirstQueuedata) && Tooltipdata.contains(SecondQueuedata))
		{
			System.out.println("multiple properties working fine");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Show multiple properties are working fine");
		}
		else
		{
			System.out.println("multiple properties are not wotrking");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to open multiple properties");
			driver.findElement(By.id("multiple properties failed")).click();
		}
		Thread.sleep(1000);
		
	}
	
	@Parameters({"FavoriteViewletName"})
	@Test(priority=12)
	public static void AddToFavoriteForMultipleQueues(String FavoriteViewletName) throws InterruptedException
	{
		//Store Queue names
		String Queue2=driver.findElement(By.xpath("//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String Queue3=driver.findElement(By.xpath("//datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Queue Events option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
	
		//Store favorite viewlet data into string
		String Favdata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verification
		if(Favdata.contains(Queue2)  && Favdata.contains(Queue3))
		{
			System.out.println("Multiple Queues are added to Favorite viewlet");
		}
		else
		{
			System.out.println("Multiple Queues are not added to Favorite viewlet");
			driver.findElement(By.xpath("Multiple queues to fav failed")).click();
		}
		Thread.sleep(1000);
	}
	
	
	@Parameters({"QueueDescription"})
	@TestRail(testCaseId=299)
	@Test(priority=13)
	public void AddQueueFromPlusIcon(String QueueDescription, ITestContext context) throws InterruptedException
	{
		// Changing the Settings 
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(1000);
		
		try
		{
		//Click on + icon
		driver.findElement(By.xpath("//img[@title='Add EMS Queue']")).click();
		
		/*//Select Node 
		driver.findElement(By.xpath("//div[2]/input")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		
		//Select Manager
		driver.findElement(By.xpath("//div[2]/ng-select/div")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();*/
		
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		//Create Queue Window
		driver.findElement(By.name("name")).sendKeys(QueueName);
		driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		Thread.sleep(6000);
				
		//Change the Settings We need to check show empty queues for verification
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.cssSelector(".checkbox:nth-child(2) > input")).click();
		driver.findElement(By.cssSelector(".btn-group:nth-child(3) > .btn")).click();
		Thread.sleep(2000);
		
		//Store the first queue name into string
		String Firstqueue=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		System.out.println("First queue name is:" +Firstqueue); 
		
		//Applying the loop from second queue onwords
		for(int q=2; q<=100; q++)
		{
		String AddedQueuename=driver.findElement(By.xpath("//datatable-row-wrapper["+ q +"]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
	    System.out.println(AddedQueuename);   
		
		//Verification 
		if(AddedQueuename.equalsIgnoreCase(QueueName) || Firstqueue.equalsIgnoreCase(QueueName))
		{
			System.out.println("Queue is created From Icon");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Queue is created From create Icon");
			break;
		}
		}
		}
		
		catch (Exception e)
		{
			System.out.println("Exception occured while creating queue from the Icon");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Exception occured while creating queue from the Icon, check details: "+ e.getMessage());
			if(driver.findElement(By.cssSelector("//div[2]/div/div/div[3]/button")).isDisplayed())
			{
				driver.findElement(By.cssSelector("//div[2]/div/div/div[3]/button")).click();
				
			}
			
		}
		Thread.sleep(2000);		
	}
	
	
	@Test(priority=25)
	public static void Logout() throws Exception
	{
		// Changing the Settings 
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(1000);
		
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
