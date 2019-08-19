package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class ManagerNameUnderServerGroup_20012 
{
static WebDriver driver;
	
	@Test
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password"})
	public void Login(String sDriver, String sDriverpath, String URL, String uname, String password) throws Exception
	{
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
		//Select the required browser for running the script
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
			FirefoxOptions options=new FirefoxOptions();
			options.setCapability("marionette", false);
			driver=new FirefoxDriver(options);
		} 
		
		//Enter the URL into browser and Maximize the browser 
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Login
		driver.findElement(By.id("j_username")).sendKeys(uname);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.id("submitBtn")).click();
		Thread.sleep(10000);
	}
	
	@Parameters({"ManagerName"})
	@TestRail(testCaseId = 23)
	@Test(priority=1)
	public void Manager(String ManagerName,ITestContext context) throws InterruptedException
	{
		try {
		//Click on Audit Management
		driver.findElement(By.id("mngmntNavForm:navSrvGrp")).click();
		Thread.sleep(2000);
		
		//Click on Add new server group icon
		driver.findElement(By.xpath("//img[@alt='Add new']")).click();
		Thread.sleep(1000);
		
		//Store the option into string
		String ManagerNameOption=driver.findElement(By.cssSelector(".textCenter:nth-child(4)")).getText();
		System.out.println(ManagerNameOption);
		
		if(ManagerNameOption.equalsIgnoreCase(ManagerName))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Manager Name option is present");
			System.out.println("Manager Name option is present");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Manager Name option is not present");
			System.out.println("Manager Name option is not present");
			driver.findElement(By.xpath("Manager Name option failed")).click();
		}
		Thread.sleep(2000);
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 4);
			context.setAttribute("Comment", "Error occured");
			
			driver.close();
		}
	}
	
	@Test(priority=2)
	public void Logout()
	{
		driver.findElement(By.id("topAppFrm:logoutLnk")).click();
		driver.close();
	}

}
