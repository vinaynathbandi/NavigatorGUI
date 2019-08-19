package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;



public class SearchCriteria 
{
	static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password) throws Exception
	{
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
		if(sDriver.equalsIgnoreCase("webdriver.chrome.driver"))
		{
			System.setProperty(sDriver, sDriverpath);
			driver=new ChromeDriver();
		}
		else if(sDriver.equalsIgnoreCase("webdriver.edge.driver"))
		{
			System.setProperty(sDriver, sDriverpath);
			driver=new EdgeDriver();
		}
		else if(sDriver.equalsIgnoreCase("webdriver.ie.driver"))
		{
			System.setProperty(sDriver, sDriverpath);
			driver=new InternetExplorerDriver();
		}
		else
		{
			System.setProperty(sDriver, sDriverpath);
			//driver=new FirefoxDriver();
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("marionette", false);
			driver = new FirefoxDriver(options);
		}
		
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(2000);
	}
	
	@Parameters({"Dashboardname", "Node", "wgs", "Queuemanager"})
	@Test(priority=1)
	public static void AddDashboard(String Dashboardname, String Node, int wgs, String Queuemanager) throws Exception
	{
		
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
	
	
	@Parameters({"SearchCriteriaName", "SearchCriteriaData"})
	@Test(priority=2)
	public static void AddSearchCriteriaCondition(String SearchCriteriaName, String SearchCriteriaData) throws Exception
	{
		
		//Edit Viewlet page
		driver.findElement(By.id("dropdownMenuButton")).click();
		driver.findElement(By.linkText("Edit viewlet")).click();
		
		//Search Criteria
		driver.findElement(By.id("sc-find-messages-checkbox")).click();
		driver.findElement(By.cssSelector("div.right > div.g-text-and-input.line > button.btn-white-round")).click();
		Thread.sleep(1000);
		
		//Search criteria name
		driver.findElement(By.cssSelector(".input-group > .form-control")).sendKeys(SearchCriteriaName);
		driver.findElement(By.cssSelector("button.btn.btn-outline-secondary")).click();
		Thread.sleep(1000);
		
		//Add Message data
		driver.findElement(By.xpath("//td/input")).click();
		driver.findElement(By.xpath("//img[@alt='Data']")).click();
		Thread.sleep(1000);
		
		//Enter data
		driver.findElement(By.xpath("//app-mod-message-data-criteria/div/div[2]/input")).sendKeys(SearchCriteriaData);
		
		//Click on OK buttons
		driver.findElement(By.xpath("//app-mod-message-data-criteria/div/div[3]/button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[5]/button")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(4000);
	}
	
	//Logout
	@Test(priority=3)
	public void Logout() 
	{
	driver.findElement(By.cssSelector(".fa-power-off")).click();
	driver.close();
	}

}
