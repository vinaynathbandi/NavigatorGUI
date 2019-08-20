package ApodGUI;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import testrail.TestRailAPI;

@Listeners(TestClass.class)
public class EMSTopic 
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
	static String DWGS;
	static String Dnode;
	static String DestinationManager;
	static String DestinationQueue;

	
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
		DWGS =Settings.getDWGS();
		Dnode =Settings.getDnode();
		DestinationManager =Settings.getDestinationManager();
		DestinationQueue =Settings.getDestinationQueue();
	}
	
	@Parameters({"sDriver", "sDriverpath", "Dashboardname", "TopicName"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String Dashboardname, String TopicName) throws Exception
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
		dd.selectByIndex(wgs);
		
		//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		//--------- Create EMS Topic viewlet-----------
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Route viewlet
		driver.findElement(By.cssSelector(".object-type:nth-child(6)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(TopicName);
		
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(1000);
		dd.selectByVisibleText(EMS_WGSNAME);
		
		//Click on EMS checkbox
		driver.findElement(By.id("ems")).click();
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
	}
	
	
	@Parameters({"schemaName"})
	@Test(priority=17)
	public static void ShowObjectAttributesForTopic(String SchemaName, ITestContext context) throws InterruptedException
	{
		try {
		EMSObjects obj=new EMSObjects();
		obj.ObjectAttributesVerification(driver, SchemaName);
		context.setAttribute("Status", 1);
		context.setAttribute("Comment", "Show object attributes working fine");
		
		}catch(Exception e)
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Got exception while showing object attributes, check details: "+  e.getMessage());
		}
	}
	
	
	@Parameters({"TopicNameFromIcon"})
	@TestRail(testCaseId=309)
	@Test(priority=2)
	public void CreateTopicFromPlusIcon(String TopicNameFromIcon, ITestContext context) throws InterruptedException
	{
		//Click on + icon present in the viewlet
		driver.findElement(By.xpath("//img[@title='Add EMS Topic']")).click();
		
		//Select WGS
		Select WGS=new Select(driver.findElement(By.xpath("//app-mod-select-object-path-for-create/div/div/select")));
		WGS.selectByVisibleText(EMS_WGSNAME);
		
		/*//Select Node 
		driver.findElement(By.xpath("//div[2]/input")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		
		//Select Manager
         driver.findElement(By.xpath("//div[2]/ng-select/div")).click();
	     //driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
         driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();*/
		
		//Click on Select path button
		driver.findElement(By.xpath("//div[3]/div/div/div/button")).click();
		Thread.sleep(1000);
		
		//Give the name of the topic
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys(TopicNameFromIcon);
		
		//Click on submit button
		driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		Thread.sleep(4000);
		
		//Store the Topic viewlet data into string
		String Viewletdata=driver.findElement(By.xpath("//datatable-body")).getText();
		//System.out.println(Viewletdata);
		
		//Verification condition
		if(Viewletdata.contains(TopicNameFromIcon))
		{
			System.out.println("Topic is created successfully");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Topic is created successfully");
		}
		else
		{
			System.out.println("Topic is not created");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to create topic");
			driver.findElement(By.xpath("Topic vielwr Failed")).click();
		}
		Thread.sleep(1000);
		
	}
	
	@Parameters({"CopyObjectName", "TopicNameFromIcon" })
	@TestRail(testCaseId=310)
	@Test(priority=4)
	public static void CopyAsFromCommands(String CopyObjectName, String TopicNameFromIcon, ITestContext context) throws InterruptedException
	{
		//Store the EMS topic into string 
		//String TopicNameFromOptions=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		
		//Search with the added Topic name
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(TopicNameFromIcon);
    	Thread.sleep(1000);
		
		//Select Copy as From commands
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	Actions Mousehovercopy=new Actions(driver);
    	Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li")).click();
    	
    	//Give the object name
    	driver.findElement(By.xpath("//div[2]/div/input")).sendKeys(CopyObjectName);
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(2000);
    	
    	//Edit the search field data
    	for(int j=0; j<=TopicNameFromIcon.length(); j++)
    	{
    	
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.BACK_SPACE);
    	}
    	Thread.sleep(4000);
    	
    	//Refresh the viewlet
    	for(int i=0; i<=2; i++)
    	{
    	driver.findElement(By.xpath("//img[@title='Refresh viewlet']")).click();
    	Thread.sleep(4000);
    	}
    	
    	//Combining the strings 
    	String CopyasTopicName=TopicNameFromIcon+CopyObjectName;
    	System.out.println(CopyasTopicName);
    	    	
    	//Store the viewlet data into string
    	String Subviewlet=driver.findElement(By.xpath("//datatable-body")).getText();
    	//System.out.println(Subviewlet);
    	
    	//Verification condition
    	if(Subviewlet.contains(CopyasTopicName))
    	{
    		System.out.println("Topic is copied");
    		context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Topic is copied successfully");
    	}
    	else
    	{
    		System.out.println("Topic is not copied");
    		context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to copy Topic using CopyAs command");
    		driver.findElement(By.xpath("Topic failed to copy")).click();
    	}
    	Thread.sleep(1000);	
    	
    	//Search with that name
    	driver.findElement(By.xpath("//input[@type='text']")).clear();
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(TopicNameFromIcon);
    	Thread.sleep(1000);
    	
	}
	
	@Parameters({"TopicNameFromIcon", "CopyObjectName"})
	@TestRail(testCaseId=311)
	@Test(priority=5)
	public static void DeleteFromCommands(String TopicNameFromIcon, String CopyObjectName, ITestContext context) throws InterruptedException
	{
		    	
		//Select Delete From commands
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	Actions Mousehovercopy=new Actions(driver);
    	Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li[2]")).click();
		
    	//Click on Yes
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(4000);
    	
    	String CopyasTopicName=TopicNameFromIcon+CopyObjectName;
    	
    	//Edit the search field data
    	for(int j=0; j<=TopicNameFromIcon.length(); j++)
    	{
    	
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(Keys.BACK_SPACE);
    	}
    	Thread.sleep(4000);
    	
    	//Store the viewlet data into string
    	String Subviewlet=driver.findElement(By.xpath("//datatable-body")).getText();
    	//System.out.println(Subviewlet);
    	
    	//Verification of Subscription delete
    	if(Subviewlet.contains(TopicNameFromIcon) && Subviewlet.contains(CopyasTopicName))
    	{
    		System.out.println("Topic is not deleted");
    		context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to delete Topic using delete command");
    		driver.findElement(By.xpath("Topic delete failed")).click();
    	}
    	else
    	{
    		System.out.println("Topic is deleted");
    		context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Topic is deleted successfully using delete command");
    	}
    	Thread.sleep(1000);
	}
	
	@Parameters({"MessageData", "PropertyName", "PropertyValue"})
	@TestRail(testCaseId=312)
	@Test(priority=6)
	public void EMSTopicPublish(String MessageData, String PropertyName, String PropertyValue, ITestContext context) throws InterruptedException
	{
		//Select publish From commands
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	Actions Mousehovercopy=new Actions(driver);
    	Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li[4]")).click();
		
    	//Send the New name into field
    	driver.findElement(By.id("messageData")).sendKeys(MessageData);
    	
    	driver.findElement(By.id("propertyName")).sendKeys(PropertyName);
    	driver.findElement(By.id("propertyValue")).sendKeys(PropertyValue);
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(2000);
    	
    	try
    	{
    		if(driver.findElement(By.xpath("//app-mod-errors-display/div/button")).isDisplayed())
    		{
    			context.setAttribute("Status", 1);
    			context.setAttribute("Comment", "EMS topic is published successfuly");
    			driver.findElement(By.xpath("//app-mod-errors-display/div/button")).click();
    			driver.findElement(By.cssSelector(".btn-danger")).click();
    			Thread.sleep(1000);
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error not present while publishing topic");
    		context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Failed to publish EMS  topic, check details: "+ e.getMessage());
    	}
		
	}
	
	@Parameters({"MessageData", "PropertyName", "PropertyValue", "AddSubscriptionName", "DestinationTopicName"})
	
	@Test(priority=66)
	public void PublishFromCommands(String MessageData, String PropertyName, String PropertyValue, String AddSubscriptionName, String DestinationTopicName) throws InterruptedException
	{
		this.Addsubscription(AddSubscriptionName, DestinationTopicName);
		
		//Show Empty queues
    	driver.findElement(By.xpath("//i[3]")).click();
    	driver.findElement(By.xpath("//div[2]/input")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//div[3]/button")).click();
    	Thread.sleep(1000);
    	
    	//Search the queue
    	driver.findElement(By.xpath("//input[@type='text']")).clear();
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(DestinationQueue);
    	
    	//get the Current depth of the queue
    	String Queuedepth=driver.findElement(By.xpath("//datatable-body-cell[6]/div/span")).getText();
    	int result = Integer.parseInt(Queuedepth);
		System.out.println(result);
    	
		//Select publish From commands
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	Actions Mousehovercopy=new Actions(driver);
    	Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li[4]")).click();
		
    	//Send the New name into field
    	driver.findElement(By.id("messageData")).sendKeys(MessageData);
    	
    	driver.findElement(By.id("propertyName")).sendKeys(PropertyName);
    	driver.findElement(By.id("propertyValue")).sendKeys(PropertyValue);
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(2000);
    	
    	try
    	{
    		if(driver.findElement(By.xpath("//app-mod-errors-display/div/button")).isDisplayed())
    		{
    			driver.findElement(By.xpath("//app-mod-errors-display/div/button")).click();
    			driver.findElement(By.cssSelector(".btn-danger")).click();
    			Thread.sleep(1000);
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error not present while publishing topic");
    	}
    	
    	Thread.sleep(4000);
    	//get the Current depth of the queue
    	String Queuedepth1=driver.findElement(By.xpath("//datatable-body-cell[6]/div/span")).getText();
    	int result1 = Integer.parseInt(Queuedepth1);
		System.out.println(result1);
    	
		//Show Empty queues
    	driver.findElement(By.xpath("//i[3]")).click();
    	driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//div[3]/button")).click();
    	Thread.sleep(1000);
    	
    	if(result!=result1)
    	{
    		System.out.println("Publish the messgae into queue is done");
    	}
    	else
    	{
    		System.out.println("Publish the messgae into queue is failed");
    		driver.findElement(By.xpath("Publish failed")).click();
    	}
	}
	
	@Test(priority=7)
	@TestRail(testCaseId=313)
	public void TopicProperties(ITestContext context) throws InterruptedException
	{
		//Select Properties option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//storing the name field status into boolean
		boolean NameField=driver.findElement(By.id("name")).isEnabled();
		System.out.println(NameField);
		
		//Verification Condition
		if(NameField == false)
		{
			System.out.println("The Topic name field is Disabled");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "The Topic name field is Disabled");
			driver.findElement(By.cssSelector(".btn-primary")).click();
		}
		else
		{
			System.out.println("The Topic name field is Enabled");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "The Topic name field is Enabled");
			driver.findElement(By.cssSelector(".btn-primary")).click();
			driver.findElement(By.xpath("Name field is disabled")).click();
			
		}
		Thread.sleep(1000);
		
	}
	//@Parameters({"Eventsdata"})
	@Test(priority=8)
	@TestRail(testCaseId=314)
	public static void TopicEvents(ITestContext context) throws InterruptedException
	{
		//Events option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[5]")).click();
		Thread.sleep(1000);
		
		//Events Popup page
		String Eventdetails=driver.findElement(By.cssSelector("th:nth-child(1)")).getText();
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
	
	@Parameters({"FavoriteViewletName"})
	@Test(priority=9)
	public static void AddToFavoriteViewlet(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Store Topic name into string
		String TopicName=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		
		//Create the favorite viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();
		
		//Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);
		
		Select wgsdropdown=new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByVisibleText(EMS_WGSNAME);
		
		//Submit
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Select Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(1000);
		
		//Storing the Favorite Viewlet data
		String Favdata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		                                            
		//Verification of topic added to favorite viewlet 
		if(Favdata.contains(TopicName))
		{
			System.out.println("Topic name is added to the Favorite viewlet");
		}
		else
		{
			System.out.println("Topic name is not added to the Favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
		
	}
	
	
	@Parameters({"CopyObjectNameForMultiple"})
	@Test(priority=11)
	public void CopyAsFromCommandsForMultipleTopics(String CopyObjectNameForMultiple) throws InterruptedException
	{
		//Select the multiple Topics and choose Copy as for multiple topics
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions Mousehovercopy=new Actions(driver);
		Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li")).click();
		Thread.sleep(1000);
		
		//Give the object name
    	driver.findElement(By.xpath("//div[2]/div/input")).sendKeys(CopyObjectNameForMultiple);
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(2000);
    	
    	//Refresh the viewlet
    	driver.findElement(By.xpath("//img[@title='Refresh viewlet']")).click();
    	Thread.sleep(4000);
    	
    	/*//Search with that name
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(CopyObjectNameForMultiple);
    	Thread.sleep(2000);*/
    	
    	//Store the viewlet data into string
    	String Subviewlet=driver.findElement(By.xpath("//datatable-body")).getText();
    	//System.out.println(Subviewlet);
    	
    	//Verification condition
    	if(Subviewlet.contains(CopyObjectNameForMultiple) && Subviewlet.contains(CopyObjectNameForMultiple))
    	{
    		System.out.println("Multiple Topics are copied");
    	}
    	else
    	{
    		System.out.println("Multiple Topics are not copied");
    		driver.findElement(By.xpath("Multiple Topics failed to copy")).click();
    	}
    	Thread.sleep(1000);		
	}
	
	@Parameters({"CopyObjectNameForMultiple"})
	@Test(priority=12)
	public void DeleteFromCommandsForMultipleTopics(String CopyObjectNameForMultiple) throws InterruptedException
	{
		/*//Send the New name into field
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(CopyObjectNameForMultiple);
    	Thread.sleep(2000);*/
    	
    	//Select the multiple topics and choose Delete option for multiple topics
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions Mousehovercopy=new Actions(driver);
		Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li[2]")).click();
		Thread.sleep(1000);
		
    	//Click on Yes
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(3000);
    	
    	//Refresh the view let
    	driver.findElement(By.xpath("//img[@title='Refresh viewlet'])")).click();
    	Thread.sleep(4000);
    	
    	//Store the viewlet data into string
    	String Subviewlet=driver.findElement(By.xpath("//datatable-body")).getText();
    	//System.out.println(Subviewlet);
    	
    	//Verification of Subscription delete
    	if(Subviewlet.contains(CopyObjectNameForMultiple))
    	{
    		System.out.println("Topics are not deleted");
    		driver.findElement(By.xpath("Topics delete failed")).click();
    	}
    	else
    	{
    		System.out.println("Topics are deleted");
    	}
    	Thread.sleep(1000);
    	
    	/*//Clear the search data
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();*/
	}
	
	@Parameters({"AddSubscriptionNameforMultiple", "MessageDataForMultiple", "PropertyNameForMultiple", "PropertyValueForMultiple", "DestinationTopicName"})
	@Test(priority=13)
	public void PublishFromCommandsForMultipleTopics(String AddSubscriptionNameforMultiple, String MessageDataForMultiple, String PropertyNameForMultiple, String PropertyValueForMultiple, String DestinationTopicName) throws InterruptedException
	{
		this.AddsubscriptionForMultiple(AddSubscriptionNameforMultiple, DestinationTopicName);
		
		//Show Empty queues
    	driver.findElement(By.xpath("//i[3]")).click();
    	driver.findElement(By.xpath("//div[2]/input")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//div[3]/button")).click();
    	Thread.sleep(1000);
    	
    	//Search the queue
    	driver.findElement(By.xpath("//input[@type='text']")).clear();
    	driver.findElement(By.xpath("//input[@type='text']")).sendKeys(DestinationQueue);
    	Thread.sleep(2000);
    	
    	//get the Current depth of the queue
    	String Queuedepth=driver.findElement(By.xpath("//datatable-body-cell[6]/div/span")).getText();
    	int result = Integer.parseInt(Queuedepth);
		System.out.println(result);
    	
		//Select the multiple topics and choose publish option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions Mousehovercopy=new Actions(driver);
		Mousehovercopy.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[4]")).click();
		Thread.sleep(1000);
				
		//Send the New name into field
		 driver.findElement(By.id("messageData")).sendKeys(MessageDataForMultiple);
		    	
		 driver.findElement(By.id("propertyName")).sendKeys(PropertyNameForMultiple);
		 driver.findElement(By.id("propertyValue")).sendKeys(PropertyValueForMultiple);
		 driver.findElement(By.cssSelector(".btn-primary")).click();
		 Thread.sleep(4000);
    	
    	try
    	{
    		if(driver.findElement(By.xpath("//app-mod-errors-display/div/button")).isDisplayed())
    		{
    			driver.findElement(By.xpath("//app-mod-errors-display/div/button")).click();
    			driver.findElement(By.cssSelector(".btn-danger")).click();
    			Thread.sleep(1000);
    		}
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error not present while publishing topic");
    	}
    	
    	Thread.sleep(4000);
    	//get the Current depth of the queue
    	String Queuedepth1=driver.findElement(By.xpath("//datatable-body-cell[6]/div/span")).getText();
    	int result1 = Integer.parseInt(Queuedepth1);
		System.out.println(result1);
    	
		//Show Empty queues
    	driver.findElement(By.xpath("//i[3]")).click();
    	driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
    	Thread.sleep(1000);
    	driver.findElement(By.xpath("//div[3]/button")).click();
    	Thread.sleep(1000);
    	
    	if(result!=result1)
    	{
    		System.out.println("Publish the messgae into queue is done for multipublish");
    	}
    	else
    	{
    		System.out.println("Publish the messgae into queue is failed for multuipublish");
    		driver.findElement(By.xpath("Multi Publish failed")).click();
    	}
	}
	
	
	@Test(priority=14)
	@TestRail(testCaseId=315)
	public void MultipleProperties(ITestContext context) throws InterruptedException
	{
		//Select the multiple topics and choose properties option for multiple topics
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")).click();
		Thread.sleep(1000); 
		
		WebElement Top=driver.findElement(By.id("name"));
		Actions a=new Actions(driver);
		a.moveToElement(Top).perform();
		
		//Get tooltip data
		String Tooltipdata=driver.findElement(By.tagName("ngb-tooltip-window")).getText();
		//System.out.println(Tooltipdata);
				
		//click on OK
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(5000);
		
		//Open the properties for First topic
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//Get the description and communication info for First topic
		String FirstTopicName=driver.findElement(By.id("name")).getAttribute("value");
		//String FirstCommunicationinfo=driver.findElement(By.id("communicationInfo")).getAttribute("value");
		
		//close the properties page
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		//Open the properties for second topic
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
				
		//Get the description and communication info for second topic
		String SecondTopicName=driver.findElement(By.id("name")).getAttribute("value");
		//String SecondCommunicationinfo=driver.findElement(By.id("communicationInfo")).getAttribute("value");
		
		//close the properties page
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		//Verification 
		if(Tooltipdata.contains(FirstTopicName) && Tooltipdata.contains(SecondTopicName))
		{
			System.out.println("Properites are Updated for multiple topics");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Properites are Updated for multiple topics");
		}
		else
		{
			System.out.println("Properites are not Updated for multiple topics");
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Failed to update Properites for multiple topics");
			driver.findElement(By.xpath("Properties updation failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Parameters({"FavoriteViewletName"})
	@Test(priority=15)
	public static void AddToFavoriteForMultipleTopics(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Store the Topic Names into string
		String TopicName2=driver.findElement(By.xpath("//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String TopicName3=driver.findElement(By.xpath("//datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Add to favorite option
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
		
		//Storing the Favorite Viewlet data
		String Favdata=driver.findElement(By.xpath("//div[2]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verification of topics added to favorite viewlet
		if(Favdata.contains(TopicName2) && Favdata.contains(TopicName3))
		{
			System.out.println("Topic names are added to the Favorite viewlet");
		}
		else
		{
			System.out.println("Topic names are not added to the Favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
				
	}
	

	@Test(priority=20)
	public static void Logout() throws InterruptedException
	{
		try
		{
		//Close the opened Dashboard
		driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
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
	
	
	
	//Create Subscription Viewlet and Add Subscription
	
	@Parameters({"AddSubscriptionName", "DestinationTopicName"})
	public void Addsubscription(String AddSubscriptionName, String DestinationTopicName) throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create subscription
		driver.findElement(By.cssSelector(".object-type:nth-child(12)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys("Test Subscription");
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(EMS_WGSNAME);
		
	    //Click on Save changes button
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(2000);
		
		//click on check box and choose create subscription
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[4]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		
		//Give the Subscription name
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys(AddSubscriptionName);
		
		//Select the Topic name from the list
		/*driver.findElement(By.cssSelector(".ng-input > input")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();*/
		
		try 
		{
			driver.findElement(By.id("topicName")).click();
			List<WebElement> Topic=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Topic.size());	
			for (int i=0; i<Topic.size();i++)
			{
				//System.out.println("Radio button text:" + Topic.get(i).getText());
				System.out.println("Radio button id:" + Topic.get(i).getAttribute("id"));
				String s=Topic.get(i).getText();
				if(s.equals(DestinationTopicName))
				{
					String id=Topic.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		
		//Give the Topic String
		//driver.findElement(By.id("topicString")).sendKeys(TopicStringData);
		
		//Click on Destination tab
		driver.findElement(By.linkText("Destination")).click();
		
		//Select WGS name
		Select DestinationWGS=new Select(driver.findElement(By.id("destinationGMName")));
		DestinationWGS.selectByVisibleText(DWGS);
		
		//Select WGS name
		/*driver.findElement(By.xpath("//ng-select[@id='destinationNodeName']/div")).click();
		driver.findElement(By.xpath("//ng-select[@id='destinationNodeName']/div")).sendKeys("DESKTOP-E1JT2VR");
		//span[@class, 'ng-option-label ng-star-inserted']*/	
	
		try 
		{
			driver.findElement(By.id("destinationNodeName")).click();
			List<WebElement> Node=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Node.size());	
			for (int i=0; i<Node.size();i++)
			{
				System.out.println("Radio button text:" + Node.get(i).getText());
				System.out.println("Radio button id:" + Node.get(i).getAttribute("id"));
				String s=Node.get(i).getText();
				if(s.equals(Dnode))
				{
					String id=Node.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		
		//Select Manager value
		try 
		{
			driver.findElement(By.id("destinationQMName")).click();
			List<WebElement> Manager=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Manager.size());	
			for (int i=0; i<Manager.size();i++)
			{
				//System.out.println("Radio button text:" + Manager.get(i).getText());
				System.out.println("Radio button id:" + Manager.get(i).getAttribute("id"));
				String s=Manager.get(i).getText();
				if(s.equals(DestinationManager))
				{
					String id=Manager.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Select Queue name
		try 
		{
			driver.findElement(By.id("destinationQName")).click();
			List<WebElement> QueueName=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(QueueName.size());	
			for (int i=0; i<QueueName.size();i++)
			{
				//System.out.println("Radio button text:" + QueueName.get(i).getText());
				System.out.println("Radio button id:" + QueueName.get(i).getAttribute("id"));
				String s=QueueName.get(i).getText();
				if(s.equals(DestinationQueue))
				{
					String id=QueueName.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(2000);
		
		//Click on OK button
		driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(3000);
		
	}
	
	
	@Parameters({"AddSubscriptionNameforMultiple", "DestinationTopicName"})
	public void AddsubscriptionForMultiple(String AddSubscriptionNameforMultiple, String DestinationTopicName) throws InterruptedException
	{		
		//click on check box and choose create subscription
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[4]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//Give the Subscription name
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys(AddSubscriptionNameforMultiple);
		
		/*//Select the Topic name from the list
		driver.findElement(By.cssSelector(".ng-input > input")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();*/
		
		try 
		{
			driver.findElement(By.id("topicName")).click();
			List<WebElement> Topic=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Topic.size());	
			for (int i=0; i<Topic.size();i++)
			{
				//System.out.println("Radio button text:" + Topic.get(i).getText());
				System.out.println("Radio button id:" + Topic.get(i).getAttribute("id"));
				String s=Topic.get(i).getText();
				if(s.equals(DestinationTopicName))
				{
					String id=Topic.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		//Give the Topic String
		//driver.findElement(By.id("topicString")).sendKeys(TopicStringData);
		
		//Click on Destination tab
		driver.findElement(By.linkText("Destination")).click();
		
		//Select WGS name
		Select DestinationWGS=new Select(driver.findElement(By.id("destinationGMName")));
		DestinationWGS.selectByVisibleText(DWGS);
		
		//Select WGS name
		/*driver.findElement(By.xpath("//ng-select[@id='destinationNodeName']/div")).click();
		driver.findElement(By.xpath("//ng-select[@id='destinationNodeName']/div")).sendKeys("DESKTOP-E1JT2VR");
		//span[@class, 'ng-option-label ng-star-inserted']*/	
	
		try 
		{
			driver.findElement(By.id("destinationNodeName")).click();
			List<WebElement> Node=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Node.size());	
			for (int i=0; i<Node.size();i++)
			{
				//System.out.println("Radio button text:" + Node.get(i).getText());
				System.out.println("Radio button id:" + Node.get(i).getAttribute("id"));
				String s=Node.get(i).getText();
				if(s.equals(Dnode))
				{
					String id=Node.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		
		//Select Manager value
		try 
		{
			driver.findElement(By.id("destinationQMName")).click();
			List<WebElement> Manager=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(Manager.size());	
			for (int i=0; i<Manager.size();i++)
			{
				//System.out.println("Radio button text:" + Manager.get(i).getText());
				System.out.println("Radio button id:" + Manager.get(i).getAttribute("id"));
				String s=Manager.get(i).getText();
				if(s.equals(DestinationManager))
				{
					String id=Manager.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Select Queue name
		try 
		{
			driver.findElement(By.id("destinationQName")).click();
			List<WebElement> QueueName=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
			System.out.println(QueueName.size());	
			for (int i=0; i<QueueName.size();i++)
			{
				//System.out.println("Radio button text:" + QueueName.get(i).getText());
				System.out.println("Radio button id:" + QueueName.get(i).getAttribute("id"));
				String s=QueueName.get(i).getText();
				if(s.equals(DestinationQueue))
				{
					String id=QueueName.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(2000);
		
		//Click on OK button
		driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(3000);
		
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
