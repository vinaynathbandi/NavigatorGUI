package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class StartandStopListener18772 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname", "wgs"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname, String wgs) throws Exception
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
		Thread.sleep(10000);
		
		//Create New Dashboard
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);
		
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
	
	@Parameters({"Listenername", "wgs1"})
	@TestRail(testCaseId = 18)
	@Test(priority=1)
	public static void AddListenerViewlet(String Listenername, String wgs1, ITestContext context) throws InterruptedException
	{
		try {
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
				
		//Create Listener
		driver.findElement(By.cssSelector(".object-type:nth-child(7)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Listenername);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs1);
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(Listenername))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Listener Viewlet is created");
			System.out.println("Listener Viewlet is created");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Listener Viewlet is not created");
			System.out.println("Listner viewlet is not created");
			driver.findElement(By.xpath("Not created")).click();
		}
		Thread.sleep(1000);
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 4);
			context.setAttribute("Comment", "Error occured");
		
			driver.close();
		}
	}
	
	@Parameters({"Command1", "Command2"})
	@Test(priority=2)
	public void CommandOptions(String Command1, String Command2) throws InterruptedException
	{
		//Click on Check box
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[3]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions MouseCommands=new Actions(driver);
		MouseCommands.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]"))).perform();
		
		//Store the start Values into string
		String Start=driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]/ul/li")).getText();
		
		//Store the stop value into string
		String Stop=driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[4]/ul/li[2]")).getText();
		
		if(Start.equalsIgnoreCase(Command1) && Stop.equalsIgnoreCase(Command2))
		{
			System.out.println("Start and stop commands are present for the listener");
		}
		else
		{
			System.out.println("Start and stop commands are not present for the listener");
			driver.findElement(By.xpath("Commands failed")).click();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=3)
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

}
