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
public class EMSAdminRoles_19996 
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
	
	@Parameters({"EMSRoleName"})
	@TestRail(testCaseId = 22)
	@Test(priority=1)
	public void EMSRole(String EMSRoleName,ITestContext context) throws InterruptedException
	{
		try
		{
		//Click on Audit Management
		driver.findElement(By.id("mngmntNavForm:navRoles")).click();
		Thread.sleep(2000);
		//Store the 
		String EMSRole=driver.findElement(By.xpath("//th[4]/a/span")).getText();
		System.out.println(EMSRole);
		
		if(EMSRole.equalsIgnoreCase(EMSRoleName))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "EMS admin role Option is present");
			System.out.println("EMS admin role Option is present");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "EMS admin role Option is not present");
			System.out.println("EMS admin role Option is not present");
			driver.findElement(By.xpath("EMS Admin Role failed")).click();
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "EMS admin role Option is not working: "+e.getMessage());
			driver.findElement(By.xpath("EMS Admin Role failed")).click();
		}
		Thread.sleep(2000);
	}
	
	@Test(priority=2)
	public void Logout()
	{
		driver.findElement(By.id("topAppFrm:logoutLnk")).click();
		driver.close();
	}

}
