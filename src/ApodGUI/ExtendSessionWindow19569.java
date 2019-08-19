package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class ExtendSessionWindow19569 
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
	}
	
	@Parameters({"uname"})
	@TestRail(testCaseId = 10)
	@Test(priority=1)
	public void ExtensionPopup(String uname, ITestContext context) throws InterruptedException
	{
		Thread.sleep(306000);
		try {
		//Check the extension window present or not
		boolean extension=driver.findElement(By.xpath("//app-modal/div/div/div")).isDisplayed();
		if(extension)
		{
			Extension(context);
		}
		
		}catch(Exception ex)
		{
			System.out.println("In catch loop");
			Thread.sleep(60000);
			System.out.println("In catch loop after waiting");
			if(driver.findElement(By.xpath("//app-modal/div/div/div")).isDisplayed())
			{
			Extension(context);
			}
			else
			{
				System.out.println("Extension popup is not present");
				context.setAttribute("Status", 4);
				context.setAttribute("Comment", "Extension popup is not present");
				
			}
		}
		
	}
	
	public void Extension(ITestContext context) throws InterruptedException
	{
		driver.findElement(By.xpath("//button[2]")).click();
		Thread.sleep(1000);
		
		//Click on Workspace dash board
		driver.findElement(By.xpath("//li/div/div")).click();
		
		//Save the Work group server status into string
		String Status=driver.findElement(By.xpath("//datatable-body-cell[4]/div/span")).getText();
		
		//verification
		if(Status.equalsIgnoreCase("Connected"))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Extension popup is closed");
			System.out.println("Extension popup is closed");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Extension popup not closed");
			System.out.println("Extension popup not closed");
		}
		Thread.sleep(2000);
	}
	
	@Test(priority=2)
	public static void Logout() throws InterruptedException
	{		
		//Logout
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}

}
