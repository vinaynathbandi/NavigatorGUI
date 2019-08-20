package ApodGUI;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
public class AddRemoteQueueManager19564 
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
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(10000);
	}
	
		
		
	@Parameters({"ConnectionInstanceName", "RemoteQueueManagerName", "ConnectionName", "CommandQueueName", "ChannelName"})
	@TestRail(testCaseId = 1,defectId = 19564)
	@Test(priority=1)
	public void AddRemoteQueueManager(String ConnectionInstanceName, String RemoteQueueManagerName, String ConnectionName, String CommandQueueName, String ChannelName,ITestContext context) throws InterruptedException
	{
		try {
		//Select Remote queue manager option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions Mousehour=new Actions(driver);
		Mousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]/ul/li[2]/a")).click();
		Thread.sleep(1000);
		
		if(driver.findElement(By.xpath("//td[contains(.,'REMOTE_QMGRS')]")).isDisplayed())
		{
			driver.findElement(By.xpath("//td[contains(.,'REMOTE_QMGRS')]")).click();
			driver.findElement(By.xpath("//div[3]/button")).click();
			
			//Click on Yes button
			driver.findElement(By.xpath("//app-mod-confirmation/div/div[2]/div/div/div/button")).click();
			Thread.sleep(2000);
		}
		
		//Click on Add button
		driver.findElement(By.xpath("//div/div/div/div[2]/div/div/button")).click();
		
		//Queue manager connection instance
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys(ConnectionInstanceName);
		
		//Queue manager name
		driver.findElement(By.name("qmgrName")).clear();
		driver.findElement(By.name("qmgrName")).sendKeys(RemoteQueueManagerName);
		
		//Goto communication Tab
		driver.findElement(By.linkText("Communication")).click();
		
		//Connection name
		driver.findElement(By.name("connName")).clear();
		driver.findElement(By.name("connName")).sendKeys(ConnectionName);
		
		//command Queue Name
		Select queue=new Select(driver.findElement(By.name("references")));
		queue.selectByVisibleText(CommandQueueName);
		
		//Channel Name
		driver.findElement(By.name("channelName")).clear();
		driver.findElement(By.name("channelName")).sendKeys(ChannelName);
		
		//click on OK button
		driver.findElement(By.xpath("//app-mod-remote-queue-manager-options/div/div[2]/div/div/div/button1")).click();
		Thread.sleep(2000);
		
		//Store the Queue managers into string
		String Queuemanagers=driver.findElement(By.xpath("//app-mod-remote-queue-manager-connections/div/div/div/div")).getText();
		
		//Verification
		if(Queuemanagers.contains(RemoteQueueManagerName))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Remote Queue Manager is added successfully");
			System.out.println("Remote Queue Manager is added successfully");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Queue manager add option is failed");
			System.out.println("Remote Queue Manager is not added");
			driver.findElement(By.xpath("Queue manager add option is failed")).click();
		}
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			System.out.println("in excepion");
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Adding Queue manager failed, please check below stacktrace message : " + e.getMessage());
		}
		Thread.sleep(1000);
			
		//Close the remote queue managers connection popup page
		driver.findElement(By.xpath("//div[2]/div/div/div/button")).click();
		Thread.sleep(1000);
		
					
	}
	
	@Test(priority=2)
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
