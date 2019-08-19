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
public class AutoLogin19862 
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
	@TestRail(testCaseId = 3)
	@Test(priority=1)
	public void LoginUser(String uname, ITestContext context) throws InterruptedException
	{
		try {
		//Store the User name into string
		String LoginName=driver.findElement(By.xpath("//div[2]/div")).getText();
		
		if(LoginName.equalsIgnoreCase(uname))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "User able to login the navigator");
			System.out.println("User able to login the navigator");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "User unable to login the navigator");
			System.out.println("User unable to login the navigator");
			driver.findElement(By.xpath("Login failed")).click();
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "User login failed, check below details :" + e.getMessage());
			driver.findElement(By.xpath("Login failed")).click();
			driver.close();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=2)
	public static void Logout() throws InterruptedException
	{		
		//Logout
		driver.findElement(By.cssSelector(".fa-power-off")).click();
		driver.close();
	}

}
