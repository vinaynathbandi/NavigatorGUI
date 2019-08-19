package ApodGUI;

import java.io.File;
import java.util.List;
import java.util.StringJoiner;
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
public class BridgeViewlet {
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
static String wgs;
static String QueueName;
static String LocalQueue;
static String ManagerName;
static String DeleteBridgeName;

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
	DeleteBridgeName=Settings.getDeleteBridgeName();
}
	
	@Parameters({"sDriver", "sDriverpath", "Dashboardname"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname, int wgs) throws Exception
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
		driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByIndex(wgs);
		
		/*//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
	}
	
	@Parameters({"Bridgename"})
	@TestRail(testCaseId=219)
	@Test(priority=1)
	public static void AddBridgeViewlet(String Bridgename, ITestContext context) throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Bridge viewlet
		driver.findElement(By.cssSelector(".object-type:nth-child(15)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Bridgename);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(WGSName);
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(Bridgename))
		{
			System.out.println("Bridge Viewlet is created");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Bridge viewlet is created successfully");
		}
		else
		{
			System.out.println("Bridge viewlet is not created");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to create bridge viewlet");
			driver.findElement(By.xpath("Not created")).click();
		}
		Thread.sleep(2000);
			
    }
	 
	@Parameters({"SourceTypevalue", "SourceName", "TargetType", "TargetName"})
	@TestRail(testCaseId=220)
	@Test(priority=2)
	public void AddBridgeFromPlusIcon(String SourceTypevalue, String SourceName, String TargetType, String TargetName, ITestContext context) throws InterruptedException
	{
		//Click on + icon
		driver.findElement(By.xpath("//img[@title='Add EMS Bridge']")).click();
		
		//Select WGS
		Select WGS=new Select(driver.findElement(By.xpath("//app-mod-select-object-path-for-create/div/div/select")));
		WGS.selectByVisibleText(WGSName);
		
		//Select Node 
		driver.findElement(By.xpath("//div[2]/input")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(1000);
		
		//Select Manager
		driver.findElement(By.xpath("//div[2]/ng-select/div")).click();
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(1000);
		
		//Click on Select path
		driver.findElement(By.xpath("//div[3]/div/div/div/button")).click();
		Thread.sleep(1000);
		
		
		try
		{
		//Select Source type
		Select Source=new Select(driver.findElement(By.id("sourceType")));
		Source.selectByVisibleText(SourceTypevalue);
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Source Name selection
		try 
		{
			driver.findElement(By.id("sourceName")).click();
			List<WebElement> SourcesList=driver.findElements(By.className("ng-option"));
			System.out.println(SourcesList.size());	
			for (int i=0; i<SourcesList.size();i++)
			{
				//System.out.println("Radio button text:" + Topic.get(i).getText());
				System.out.println("Radio button id:" + SourcesList.get(i).getAttribute("id"));
				String s=SourcesList.get(i).getText();
				if(s.equals(SourceName))
				{
					String id=SourcesList.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		
		//Select Target type
		Select target=new Select(driver.findElement(By.id("targetType")));
		target.selectByVisibleText(TargetType);
				
		//Select Target Name
		try 
		{
			driver.findElement(By.id("targetName")).click();
			List<WebElement> TargetList=driver.findElements(By.className("ng-option"));
			System.out.println(TargetList.size());	
			for (int i=0; i<TargetList.size();i++)
			{
				//System.out.println("Radio button text:" + Topic.get(i).getText());
				System.out.println("Radio button id:" + TargetList.get(i).getAttribute("id"));
				String s=TargetList.get(i).getText();
				if(s.equals(TargetName))
				{
					String id=TargetList.get(i).getAttribute("id");
					driver.findElement(By.id(id)).click();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		Thread.sleep(1000);
		
		//Click on Ok button
		driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		Thread.sleep(2000);
		
		//Store the Topic viewlet data into string
		String Viewletdata=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		//System.out.println(Viewletdata);
		
		//Store the Bridge name into string
		String FinalSource=SourceTypevalue.toLowerCase();
		String FinalType=TargetType.toLowerCase();
		
		StringJoiner joiner = new StringJoiner(":"); 
		joiner.add(FinalSource);
		joiner.add(SourceName);
		joiner.add(FinalType);
		joiner.add(TargetName);
		
		String BridgeName = joiner.toString();
		System.out.println(BridgeName);
	
		if(Viewletdata.contains(BridgeName))
		{
			System.out.println("Bridge is added");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Bridge viewlet is created using add Icon");
		}
		else
		{
			System.out.println("Bridge is not added");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to add bridge viewlet using add Icon");
			driver.findElement(By.id("Bridge add Failed")).click();
		}
		Thread.sleep(2000);	
	}
	
	@Parameters({"schemaName"})
	@TestRail(testCaseId=221)
	@Test(priority=11)
	public static void ShowObjectAttributesForBridge(String schemaName, ITestContext context) throws InterruptedException
	{	
		try {
		ObjectsVerificationForAllViewlets obj=new ObjectsVerificationForAllViewlets();
		obj.ObjectAttributesVerification(driver, schemaName);
		context.setAttribute("Status",1);
		context.setAttribute("Comment", "Show object attributes for Bridge viewlet is working fine");
		}
		catch(Exception e)
		{
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to show object attributes for Bridge viewlet");
		}
	}
	
	
	@TestRail(testCaseId=222)
	@Test(priority=12)
	public void DeleteCommand(ITestContext context) throws InterruptedException
	{
		//Search the bridge name
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(DeleteBridgeName);
		Thread.sleep(1000);
		
		//Select Delete from commands option
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	Actions Mousehour=new Actions(driver);
    	Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
    	driver.findElement(By.xpath("//li[2]/ul/li")).click();
    	Thread.sleep(1000);
    	
    	//Click on yes button
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(1000);
    	
    	//Clear the search data
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
    	Thread.sleep(1000);
    	
    	//Refresh the viewlet
    	driver.findElement(By.xpath("(//img[@title='Refresh viewlet'])[3]")).click();
    	Thread.sleep(3000);
    	
    	//Store the viewlet data into string
    	String viewletdata=driver.findElement(By.xpath("//datatable-body")).getText();
    	System.out.println(viewletdata);
    	
    	//verification
    	if(viewletdata.contains(DeleteBridgeName))
    	{
    		System.out.println("Bridge is not deleted");
    		context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Bridge is deleted successfuuly");
    		driver.findElement(By.xpath("Bridge delete failed")).click();
    	}
    	else
    	{
    		System.out.println("Bridge is deleted");
    		context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to delete bridge");
    	}
    	Thread.sleep(1000);
	}
	
	@Test(priority=4)
	@TestRail(testCaseId=223)
	public void Properties(ITestContext context) throws InterruptedException
	{
		//Select properties option
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
    	Thread.sleep(2000);
    	
    	//storing the name field status into boolean
		boolean NameField=driver.findElement(By.xpath("//ng-select/div")).isEnabled();
		System.out.println(NameField);
		
		//Verification Condition
		if(NameField == true)
		{
			System.out.println("The Bridge name field is Disabled");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Bridge properties are working fine");
			driver.findElement(By.cssSelector(".btn-primary")).click();
		}
		else
		{
			System.out.println("The Brideg name field is Enabled");
			context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Bridge properties are not working properly");
			driver.findElement(By.cssSelector(".btn-primary")).click();
			driver.findElement(By.xpath("Name field is disabled")).click();
			
		}
		Thread.sleep(1000); 
	}
	
	@Test(priority=5)
	@TestRail(testCaseId=224)
    public static void BridgeEvents(ITestContext context) throws InterruptedException 
    {
    	//Select Events option
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[5]")).click();
    	Thread.sleep(1000);
    	
    	//Events Popup page
		String Eventdetails=driver.findElement(By.cssSelector("th:nth-child(1)")).getText();
		//System.out.println(Eventdetails);
						
		//Verification condition
		if(Eventdetails.equalsIgnoreCase("Event #"))
		{
			System.out.println("Events page is opened");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Bridge events are working fine");
		}
		else
		{
			System.out.println("Events page is not opened");
			context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to show bridge events");
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
	@TestRail(testCaseId=225)
	@Test(priority=6)
	public static void AddToFavoriteViewlet(String FavoriteViewletName, int Favwgs, ITestContext context) throws InterruptedException
	{
    	//Store the Bridge Name into string
		String BridgeName=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Create favorite viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();
		
		//Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);
		
		Select wgsdropdown=new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByIndex(Favwgs);
		
		//Submit
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		//Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(1000);

		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(1000);
		
		//Storing the Favorite Viewlet data
		String Favdata=driver.findElement(By.xpath("//div[4]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verification of Bridge added to favorite viewlet
		if(Favdata.contains(BridgeName))
		{
			System.out.println("Bridge name is added to the Favorite viewlet");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Bridge name added successfully to favorite viewlet");
		}
		else
		{
			System.out.println("Bridge name is not added to the Favorite viewlet");
			context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to add Bridge name added to favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=7)
	@TestRail(testCaseId=226)
	public static void CompareBridges(ITestContext context) throws InterruptedException
	{
		//Select compare option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
		Thread.sleep(1000);
		
		//Verification of popup
		String CompareProcess=driver.findElement(By.cssSelector("span.name")).getText();
		System.out.println(CompareProcess);
				
		if(CompareProcess.equalsIgnoreCase("Compare"))
		{
			System.out.println("Bridge Compare page is opened");
		}
		else
		{
			System.out.println("Bridge Compare page is not opened");
			driver.findElement(By.xpath("Comparision failed")).click();
		}
		Thread.sleep(1000);
				
		//Differences only
		driver.findElement(By.cssSelector("div.differences > label.switch > span.slider.round")).click();
		Thread.sleep(1000);
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
         System.out.println("No differences between Names lists");
         context.setAttribute("Status",5);
 		context.setAttribute("Comment", "Exception occured while checking comparision of bridges");
        }
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);	
	}	
	
	@Test(priority=13)
	@TestRail(testCaseId=227)
	public void DeleteMultiples(ITestContext context) throws InterruptedException
	{
		//Store the Bridge names into string          
		String FirstName=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String SecondName=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Delete from Commands option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions Mousehour=new Actions(driver);
		Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[2]/ul/li")).click();
		Thread.sleep(1000);
		
		//Click on yes button
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(1000);
    	
    	//Store the viewlet data into string
    	String viewletdata=driver.findElement(By.xpath("//datatable-body")).getText();
    	System.out.println(viewletdata);
    	
    	//verification
    	if(viewletdata.contains(FirstName) || viewletdata.contains(SecondName))
    	{
    		System.out.println("Multiple Bridges are not deleted");
    		context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to delete Multiple Bridges");
    		driver.findElement(By.xpath("Multiple Bridges delete failed")).click();
    	}
    	else
    	{
    		System.out.println("Multiple Bridges are deleted");
    		context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Multiple Bridges are deleted");
    	}
    	Thread.sleep(1000);
		
	}
	
	@Parameters({"SelectorInput"})
	@TestRail(testCaseId=228)
	@Test(priority=9)
	public void MultipleBridgeProperties(String SelectorInput, ITestContext context) throws InterruptedException
	{
		//Select Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		/*WebElement ele=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span"));
		Actions a=new Actions(driver);
		a.moveToElement(ele).perform();
		System.out.println(ele.getAttribute("value"));
		Thread.sleep(1000);*/
		
		//Enter the data into selector field
		driver.findElement(By.id("targetSelector")).clear();
		driver.findElement(By.id("targetSelector")).sendKeys(SelectorInput);
		
		//Click on yes button
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(10000);
    	
    	for(int i=0; i<=10; i++)
    	{
    	//Refresh the viewlet
    	driver.findElement(By.xpath("(//img[@title='Refresh viewlet'])[3]")).click();
    	//Thread.sleep(1000);
    	}
		
    	//Open the properties of first Bridge
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//Get the Source type data and store into string
		String FirstBridge=driver.findElement(By.id("targetSelector")).getAttribute("value");
		System.out.println(FirstBridge);
		
		//Click on yes button
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(1000);
    	
    	//Open the properties of second Bridge
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]")).click();
		Thread.sleep(1000);
		
		//Get the Source type data and store into string
		String SecondBridge=driver.findElement(By.id("targetSelector")).getAttribute("value");
		System.out.println(SecondBridge);
		
		//Click on yes button
    	driver.findElement(By.cssSelector(".btn-primary")).click();
    	Thread.sleep(1000);
    	
    	//Verification condition
    	if(SelectorInput.equals(FirstBridge) && SelectorInput.equals(SecondBridge))
    	{
    		System.out.println("Multiple properties are verified");
    		context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Multiple Bridges are deleted");
    	}
    	else
    	{
    		System.out.println("Multiple properties are not verified");
    		context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to delete multiple bridges");
    		driver.findElement(By.id("Multiple properties failed")).click();
    	}
    	Thread.sleep(1000);
		
	}
	
	@Parameters({"FavoriteViewletName"})
	@TestRail(testCaseId=229)
	@Test(priority=10)
	public static void AddToFavoriteForMultipleBridges(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Store the Bridge names into string
		String BridgeName2=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String BridgeName3=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//ul[2]/li")).click();
		Thread.sleep(2000);
		
		//Select the favorite viewlet name
		Select fav=new Select(driver.findElement(By.cssSelector(".fav-select")));
		fav.selectByVisibleText(FavoriteViewletName);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(1000);
		
		//Storing the Favorite Viewlet data
		String Favdata=driver.findElement(By.xpath("//div[4]/app-viewlet/div/ngx-datatable/div/datatable-body")).getText();
		
		//Verification of Bridges added into favorite viewlet
		if(Favdata.contains(BridgeName2) && Favdata.contains(BridgeName3))
		{
			System.out.println("Multiple Bridges are added to the Favorite viewlet");
			context.setAttribute("Status",1);
    		context.setAttribute("Comment", "Multiple Bridges are added successfully to favorite viewlet");
		}
		else
		{
			System.out.println("Multiple Bridges are not added to the Favorite viewlet");
			context.setAttribute("Status",5);
    		context.setAttribute("Comment", "Failed to add Multiple Bridges to favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=16)
	public void Logout() throws InterruptedException 
	{
		//Logout
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
		
		//Logout option
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