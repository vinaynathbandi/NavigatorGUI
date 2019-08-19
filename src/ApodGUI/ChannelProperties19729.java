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
public class ChannelProperties19729 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname", "wgs", "Channelname"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname, String wgs, String Channelname) throws Exception
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
		
		//Create Dashboard button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create channel
		driver.findElement(By.cssSelector(".object-type:nth-child(4)")).click();
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Channelname);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
	
	    }
	
        @Parameters({"Description"})
        @TestRail(testCaseId = 4)
		@Test(priority=2)
       public static void CahnnelProperties(String Description,ITestContext context) throws InterruptedException 
		{
        	try {
			//Select channel properties option
			driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
			driver.findElement(By.cssSelector(".vertical-nav > li:nth-child(5)")).click();
			Thread.sleep(2000);
			
			//Add the description
			driver.findElement(By.id("description")).clear();
			driver.findElement(By.id("description")).sendKeys(Description);
			
			//Click on OK button
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(2000);
			
			//Select channel properties option
			driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
			driver.findElement(By.cssSelector(".vertical-nav > li:nth-child(5)")).click();
			Thread.sleep(2000);
			
			//Store the Transmission Queue data into string
			String Queuename=driver.findElement(By.xpath("//div[2]/div/app-text-input/div/div/input")).getAttribute("value");
			System.out.println(Queuename);
			
			if(Queuename.equalsIgnoreCase(Description))
			{
				context.setAttribute("Status", 1);
				context.setAttribute("Comment", "Channel properties exits");
				System.out.println("Channel properties are present");
		    }
			else
			{
				context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Channel properties are not present");
				System.out.println("Channel properties are not present");
			}
			
		
        	}catch (Exception e) {
				// TODO: handle exception
        		context.setAttribute("Status", 5);
				context.setAttribute("Comment", "Channel properties are not executed properly: "+ e.getMessage());
				System.out.println("Channel properties are not executed properly");
				driver.findElement(By.cssSelector(".btn-primary")).click();
			}
        	//click on ok btn
			driver.findElement(By.cssSelector(".btn-primary")).click();
			Thread.sleep(2000);
			
		}
	    
	    @Test(priority=3)
       public void Logout() throws InterruptedException 
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

