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
public class MessageLengthField19678 
{
	static WebDriver driver;

@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "PortNo", "Dashboardname", "Managername"})
@Test
public void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String PortNo, String Dashboardname, String Managername) throws Exception
{
	Settings.read();
	String urlstr=Settings.getSettingURL();
	URL= urlstr+URL;
	
	//Selecting the browser
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
	
	//Enter the URL into the browser and Maximize the window
	driver.get(URL);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	//Login page
	driver.findElement(By.id("username")).sendKeys(uname);
	driver.findElement(By.id("password")).sendKeys(password);
	driver.findElement(By.cssSelector("button.btn-submit")).click();
	Thread.sleep(10000);
	
	//Create a dashboard
	driver.findElement(By.cssSelector("div.block-with-border")).click();
	driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
	
	//Click on Create button
	driver.findElement(By.xpath("//button[2]/span")).click();
	Thread.sleep(1000);
	
	// ---- Creating Manager Viewlet ----
	//Click on Viewlet button
	driver.findElement(By.xpath("//button")).click();
	driver.findElement(By.xpath("//button[2]")).click(); 
		
	//Create Manager
	driver.findElement(By.cssSelector(".object-type:nth-child(2)")).click();
	driver.findElement(By.name("viewletName")).clear();
	driver.findElement(By.name("viewletName")).sendKeys(Managername);
	
	driver.findElement(By.cssSelector(".btn-primary")).click();
	Thread.sleep(1000);
	
}

@TestRail(testCaseId = 12)
@Test(priority=1)
public void MessageProperties(ITestContext context) throws InterruptedException
{
	try {
	//Select properties
	driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
	driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[5]")).click();
	
	//Click on General tab
	driver.findElement(By.linkText("General")).click();
	
	//Edit the message length field 
	driver.findElement(By.id("maximumMessageLength")).clear();
	Thread.sleep(1000);
	
	//Store the editable function in to a string
	boolean Okbutton=driver.findElement(By.id("name")).isEnabled();
	System.out.println(Okbutton);
	
	//Verification
	if(Okbutton == false)
	{
		context.setAttribute("Status", 1);
		context.setAttribute("Comment", "Ok button is disable");
		System.out.println("Ok button is disable");
		 driver.findElement(By.xpath("//div[3]/button")).click();
	}
	else
	{
		context.setAttribute("Status", 5);
		context.setAttribute("Comment", "Ok button is enable");
		System.out.println("Ok button is enable");
		driver.findElement(By.xpath("//div[3]/button")).click();
		driver.findElement(By.xpath("Ok button Failed")).click();
		
	}
	Thread.sleep(1000);
	
	//Close the Manager viewlet
	driver.findElement(By.xpath("//li[2]/div/div[2]/i")).click();
	driver.findElement(By.cssSelector(".btn-primary")).click();
	Thread.sleep(1000);
	}catch (Exception e) {
		// TODO: handle exception
		//e.printStackTrace();
		context.setAttribute("Status", 4);
		context.setAttribute("Comment", "Error occured");
		
		driver.close();
	}
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
	
	//Logout option
	driver.findElement(By.cssSelector(".fa-power-off")).click();
	driver.close();
}
	

}
