package ApodGUI;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
public class NodeDropdown18508 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "wgs"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password,String wgs) throws Exception
	{
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
		
		if(sDriver.equalsIgnoreCase("webdriver.chrome.driver"))
		{
		System.setProperty(sDriver, sDriverpath);
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.prompt_for_download", "false");
		//chromePrefs.put("download.default_directory", DownloadPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		driver=new ChromeDriver(options);
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
		
		//Enter Url
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Login page
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(10000);
		
		//Create New Dashboard
		/*driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);*/
		
		/*//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		//driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		
	}
	
	@Parameters({"wgs"})
	@TestRail(testCaseId = 13)
	@Test(priority=2)
	public static void NodeDrodownlist(String wgs,ITestContext context) throws InterruptedException
	{
		try {
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
		
		//Create Manager
		//driver.findElement(By.cssSelector(".object-type:nth-child(2)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys("NodeView");
		
		//Select workgroup server
		Select server = new Select(driver.findElement(By.name("wgsKey")));
		server.selectByVisibleText(wgs);
	
		//Select node options
		driver.findElement(By.xpath("(//input[@type='text'])[5]")).click();
		
		//Capture node value on selection
		String node1=driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).getText();
		
		//Selecting node
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div")).click();
		Thread.sleep(1000);
		
		
		//Select node options
		driver.findElement(By.xpath("(//input[@type='text'])[5]")).click();
		
		//Capture node value on selection
		String node2=driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).getText();
		
		//Selecting node
		driver.findElement(By.xpath("//ng-dropdown-panel/div/div[2]/div[2]")).click();
		Thread.sleep(1000);
		
		//System.out.println("node2:" +node2);
		
		if(!node1.equalsIgnoreCase(node2))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Node dropdown list working fine");
			
			System.out.println("Node dropdown list working fine");
			driver.findElement(By.xpath("//div[3]/button")).click();
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Node dropdown not working");
			
			System.out.println("Node dropdown not working");
			driver.findElement(By.xpath("//div[3]/button")).click();
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
	
	@Test(priority=3)
	public void Logout() throws Exception
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
