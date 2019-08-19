package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class AuthorizationInfoViewlet {
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname", "wgs"})
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
	
	@Test(priority=1)
	@TestRail(testCaseId=180)
    @Parameters({"Authoinfoname", "WGSName"})
	public static void AddAuthInfoViewlet(String Authoinfoname, String WGSName, ITestContext context) throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Auth info viewlet
		driver.findElement(By.cssSelector(".object-type:nth-child(10)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Authoinfoname);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(WGSName);
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(Authoinfoname))
		{
			System.out.println("Service Viewlet is created");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Authinfo viewlet is created successfully");
		}
		else
		{
			System.out.println("Service viewlet is not created");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to create Authinfo viewlet");
			driver.findElement(By.xpath("Not created")).click();
		}
			
    }
	
	@Parameters({"AuthinfoAttributes", "schemaName"})
	@TestRail(testCaseId=181)
	@Test(priority=8)
	public static void ShowObjectAttributesForAuthInfo(String AuthinfoAttributes, String schemaName, ITestContext context) throws InterruptedException
	{
		/*//Show Object Attribute option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
		Thread.sleep(1000);String  AttributeName=driver.findElement(By.cssSelector("th:nth-child(1)")).getText();
		System.out.println(AttributeName);
		Thread.sleep(1000);
		
		//Verification of Object Attribute page
		if(AttributeName.equals(AuthinfoAttributes))
		{
		System.out.println("Show Object Attribute page is opened");
		}
		else
		{
		System.out.println("Show Object Attribute page is not opened");
		driver.findElement(By.xpath("Attribute page verification failed")).click();
		}
			
		//Close the object Attribute page
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);*/
		try {
		ObjectsVerificationForAllViewlets obj=new ObjectsVerificationForAllViewlets();
		obj.AuthinfoObjectAttributesVerification(driver, schemaName);
		context.setAttribute("Status",1);
		context.setAttribute("Comment", "Show object attributes for AuthInfo viewlet is working fine");
		}
		catch(Exception e)
		{
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to Show object attributes for AuthInfo viewlet, check details: "+ e.getMessage());
		}
	}
	
	
    @Test(priority=3)
    @TestRail(testCaseId=182)
    public static void AuthInfoEvents(ITestContext context) throws InterruptedException 
    {
    	//Select Events option
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")).click();
    	Thread.sleep(1000);//Events Popup page
		String Eventdetails=driver.findElement(By.xpath("//th")).getText();
		//System.out.println(Eventdetails);
						
		//Verification condition
		if(Eventdetails.equalsIgnoreCase("Event #"))
		{
			System.out.println("Events page is opened");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "AuthInfo viewlet events page is working fine");
		}
		else
		{
			System.out.println("Events page is not opened");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Faile to oprn AuthInfo viewlet events page");
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
				}
				else
				{
					System.out.println("Events count is not matched");
					context.setAttribute("Status", 5);
					context.setAttribute("Comment", "Got exception while opening events page, check details: ");
					driver.findElement(By.xpath("//app-mod-event-details/div/div[2]/button")).click();
					driver.findElement(By.xpath("//li/div/div[2]/i")).click();
					driver.findElement(By.id("Events count failed")).click();
				}
				
			}
		}
		catch (Exception e)
		{
			System.out.println("Events are not present");
		}
		//Close the Event details page
		driver.findElement(By.xpath("//app-mod-event-details/div/div[2]/button")).click();
		
		//Close the events popup page
		driver.findElement(By.xpath("//li/div/div[2]/i")).click();
	}
	
    @Parameters({"FavoriteViewletName", "Favwgs" })
    @TestRail(testCaseId=183)
	@Test(priority=4)
	public static void AddToFavoriteViewlet(String FavoriteViewletName, int Favwgs, ITestContext context) throws InterruptedException
	{
    	//Store the Auth info name into string
		String AuthinfoName=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
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
		
		//select Add to favorite option
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
		
		//Verification of Auth info added to favorite viewlet
		if(Favdata.contains(AuthinfoName))
		{
			System.out.println("Auth info is added to the Favorite viewlet");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "AuthInfo is added successfully to favorite viewlet");
		}
		else
		{
			System.out.println("Auth info is not added to the Favorite viewlet");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Faile to add AuthInfo to favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);

	}
	
	
	@Test(priority=5)
	@TestRail(testCaseId=184)
	public static void CompareAuthInfo(ITestContext context) throws InterruptedException
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
			System.out.println("AuthoInfo Compare page is opened");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Comparing AuthoInfo option is working fine");
		}
		else
		{
			System.out.println("AuthoInfo Compare page is not opened");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to compare AuthoInfo option");
			driver.findElement(By.xpath("Comparision failed")).click();
		}
		Thread.sleep(1000);
				
		//Differences only
		driver.findElement(By.cssSelector("div.differences > label.switch > span.slider.round")).click();
		
		try
		{
		String difference1=driver.findElement(By.xpath("//td[2]")).getText();
		String difference2=driver.findElement(By.xpath("//td[3]")).getText();
				
		if(difference1.equalsIgnoreCase(difference2))
		{
			System.out.println("Popup showing the Differences");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Comparing AuthoInfo option is working fine");
		}
		else
		{
			System.out.println("Popup not showing the Differences");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to compare AuthoInfo option");
			driver.findElement(By.xpath("Differences")).click();
		}
		}
		catch (Exception e) {
         // TODO Auto-generated catch block
         System.out.println("No differences between Names lists");
         context.setAttribute("Status",5);
		 context.setAttribute("Comment", "Got an exception while compare AuthoInfo option, check details: "+ e.getMessage());
        }
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);	
	}	
	
	@Parameters({"FavoriteViewletName"})
	@TestRail(testCaseId=185)
	@Test(priority=6)
	public static void AddToFavoriteForMultipleAuthInfo(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Store the Auth info names into strings
		String AuthInfo2=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String AuthInfo3=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
		//Select Add to favorite option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
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
		
		//Verification of Auth infos added to favorite viewlet
		if(Favdata.contains(AuthInfo2) && Favdata.contains(AuthInfo3))
		{
			System.out.println("Multiple Auth infos are added to the Favorite viewlet");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Adding multiple AuthInfo to the favorite viewlet is working fine");
		}
		else
		{
			System.out.println("Multiple Auth infos are not added to the Favorite viewlet");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to add Multiple AuthInfo to the Favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
	}

	@Test(priority=12)
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
 }
