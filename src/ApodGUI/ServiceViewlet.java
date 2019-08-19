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
public class ServiceViewlet {
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
	@TestRail(testCaseId=174)
    @Parameters({"Servicename", "WGSName", "DestinationManager"})
	public static void AddServiceViewlet(String Servicename, String WGSName, String DestinationManager, ITestContext context) throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Service
		driver.findElement(By.cssSelector(".object-type:nth-child(9)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Servicename);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(WGSName);
		
		/*try 
		{
			//driver.findElement(By.id("destinationQMName")).click();
			List<WebElement> Manager=driver.findElements(By.className("ng-select"));
			System.out.println(Manager.size());	
			if(Manager.size()>1)
			{
				//System.out.println("Radio button text:" + Manager.get(i).getText());
				Manager.get(1).click();
				
				List<WebElement> Manager1=driver.findElement(By.className("ng-dropdown-panel-items")).findElements(By.className("ng-option"));
				System.out.println(Manager1.size());	
				for (int i=0; i<Manager1.size();i++)
				{
	
					//System.out.println("Radio button text:" + Manager.get(i).getText());
					//System.out.println("Radio button id:" + Manager1.get(i).getAttribute("id"));
					String s=Manager1.get(i).getText();
					System.out.println(s);
					Thread.sleep(1000);
					if(s.equals(DestinationManager))
					{
						String id=Manager1.get(i).getAttribute("id");
						driver.findElement(By.id(id)).click();
						break;
					}
					
				}
				String s=Manager.get(1).getText();
				System.out.println(s);
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
		Thread.sleep(2000);*/
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		if(driver.getPageSource().contains(Servicename))
		{
			System.out.println("Service Viewlet is created");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Service viewlet is created successfully");
		}
		else
		{
			System.out.println("Service viewlet is not created");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to create service viewlet");
			driver.findElement(By.xpath("Not created")).click();
		}
		
		/*//Edit viewlet
		driver.findElement(By.xpath("(//div[@id='dropdownMenuButton'])[3]")).click();
		driver.findElement(By.linkText("Edit viewlet")).click();
		
		try 
		{
			//driver.findElement(By.id("destinationQMName")).click();
			List<WebElement> Manager=driver.findElements(By.className("ng-select"));
			System.out.println(Manager.size());	
			if(Manager.size()>1)
			{
				//System.out.println("Radio button text:" + Manager.get(i).getText());
				Manager.get(1).findElement(By.className("ng-clear-wrapper")).click();
				Thread.sleep(2000);
				
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(Servicename))
		{
			System.out.println("Service Viewlet is created");
		}
		else
		{
			System.out.println("Service viewlet is not created");
			driver.findElement(By.xpath("Not created")).click();
		}*/
			
    }
	
	@Parameters({"schemaName"})
	@TestRail(testCaseId=175)
	@Test(priority=7)
	public static void ShowObjectAttributesForService(String schemaName, ITestContext context) throws InterruptedException
	{
		/*//Search with the name
		driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
    	driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("ABC.SYSTEM.DEFAULT.SERVICE");
    	Thread.sleep(2000);*/
		try {
		ObjectsVerificationForAllViewlets obj=new ObjectsVerificationForAllViewlets();
		obj.ObjectAttributesVerification(driver, schemaName);
		context.setAttribute("Status",1);
		context.setAttribute("Comment", "Show object attributes for service viewlet is working fine");
		}catch(Exception e)
		{
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Got an exception while Show object attributes for service viewlet, check details:  "+ e.getMessage());
		}
	}
	
	
    @Test(priority=3)
    @TestRail(testCaseId=176)
    public static void ServiceEvents(ITestContext context) throws InterruptedException 
    {
    	//Select Events option
    	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
    	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]")).click();
    	Thread.sleep(1000);
    	
    	//Events Popup page
		String Eventdetails=driver.findElement(By.cssSelector("th:nth-child(1)")).getText();
		//System.out.println(Eventdetails);
						
		//Verification condition
		if(Eventdetails.equalsIgnoreCase("Event #"))
		{
			System.out.println("Events page is opened");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Service events are working fine");
		}
		else
		{
			System.out.println("Events page is not opened");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Faile to load Service events page");
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
    @TestRail(testCaseId=177)
	@Test(priority=4)
	public static void AddToFavoriteViewlet(String FavoriteViewletName, int Favwgs, ITestContext context) throws InterruptedException
	{
    	//Store the service name into string
		String ServiceName=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
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
		
		//Select Add to favorite option
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
		
		//Verification of service added to favorite viewlet
		if(Favdata.contains(ServiceName))
		{
			System.out.println("Service name is added to the Favorite viewlet");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Service names are  added successfully to favorite viewlet");
		}
		else
		{
			System.out.println("Service name is not added to the Favorite viewlet");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to add Service name to favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);

	}
	
	
	@Test(priority=5)
	 @TestRail(testCaseId=178)
	public static void CompareServiceNames(ITestContext context) throws InterruptedException
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
			System.out.println("Process Compare page is opened");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Comparing Servive names option is working fine");
		}
		else
		{
			System.out.println("Process Compare page is not opened");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to compare service names option");
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
			context.setAttribute("Comment", "Got an exception while comparing service names option, check details: "+ e.getMessage());
        }
		driver.findElement(By.cssSelector(".close-button")).click();
		Thread.sleep(1000);	
	}	
	
	@Parameters({"FavoriteViewletName"})
	@TestRail(testCaseId=179)
	@Test(priority=6)
	public static void AddToFavoriteForMultipleServices(String FavoriteViewletName, ITestContext context) throws InterruptedException
	{
		//Store the Service names into string
		String ServiceName2=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[2]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		String ServiceName3=driver.findElement(By.xpath("//div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[3]/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		
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
		
		//Verification of serviceses added to favorite viewlet
		if(Favdata.contains(ServiceName2) && Favdata.contains(ServiceName3))
		{
			System.out.println("Multiple Service names are added to the Favorite viewlet");
			context.setAttribute("Status",1);
			context.setAttribute("Comment", "Adding multiple service names to the favorite viewlet is working fine");
		}
		else
		{
			System.out.println("Multiple Service names are not added to the Favorite viewlet");
			context.setAttribute("Status",5);
			context.setAttribute("Comment", "Failed to add Multiple service names to the Favorite viewlet");
			driver.findElement(By.xpath("Favorite condition failed")).click();
		}
		Thread.sleep(1000);
	}

	@Test(priority=11)
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
