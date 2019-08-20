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
public class EMSChannels18790 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname) throws Exception
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
		//driver.findElement(By.id("createInitialViewlets")).click();
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(10000);
	}
	
	@Parameters({"Channelname", "wgs"})
	@TestRail(testCaseId = 7)
	@Test(priority=1)
	public void EMSchannelscreation(String Channelname, String wgs, ITestContext context) throws InterruptedException
	{	
		try
		{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create EMS channel
		driver.findElement(By.cssSelector(".object-type:nth-child(4)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Channelname);
		
		//Select WGS10
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);
		
		//Click on EMS checkbox
		driver.findElement(By.id("ems")).click();
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		//Store the EMS manager name into string
		String Managername=driver.findElement(By.xpath("//datatable-body-cell[5]/div/span")).getText();
		
		//Store the viewlet data into string
		String EMSChannelViewletdata=driver.findElement(By.xpath("//datatable-body")).getText();
		
		//Verification of EMS channel viewlet
		if(EMSChannelViewletdata.contains(Managername))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "EMS channels are present");
			System.out.println("EMS channels are present");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "EMS channels are not present");
			System.out.println("EMS channels are not present");
			driver.findElement(By.xpath("EMS channel failed")).click();
		}
		Thread.sleep(1000);
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "EMS channels failed: "+ e.getMessage());
			driver.findElement(By.xpath("EMS channel failed")).click();
			driver.close();
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=2)
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
