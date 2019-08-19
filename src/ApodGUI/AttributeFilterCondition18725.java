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
public class AttributeFilterCondition18725 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname", "Queuename", "wgs"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname, String Queuename, String wgs) throws Exception
	{
		//Reading url
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
			
		//click on Create button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create Queue
		driver.findElement(By.name("viewletName")).clear();
		driver.findElement(By.name("viewletName")).sendKeys(Queuename);
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);
	
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
	}
	
	@Parameters({"FilterName", "ErrorMessage"})
	@TestRail(testCaseId = 2)
	@Test(priority=1)
	public void Attributefiletr(String FilterName, String ErrorMessage,ITestContext context) throws InterruptedException
	{
		try
		{
		//click on  edit viewlet btn
		driver.findElement(By.xpath("//*[@id=\"dropdownMenuButton\"]")).click();
		driver.findElement(By.linkText("Edit viewlet")).click();
		Thread.sleep(1000);
		
		//click on add filters btn
		driver.findElement(By.xpath("//div[4]/div/div/button")).click();
		Thread.sleep(2000);
		
		for(int i=0; i<=1; i++)
		{			
			//click on Add btn
			driver.findElement(By.xpath("//app-mod-manage-attribute-filter/div/button")).click();
			
			//Ada value into filter name text box
			driver.findElement(By.xpath("//app-mod-manage-attribute-filter-add/div/div/div/div/input")).sendKeys(FilterName);
			
			//click on ok btn
			driver.findElement(By.xpath("//div[4]/button[2]")).click();
			Thread.sleep(2000);
			
		}
		
		//Store the Alert message into string
		String AlertMessage=driver.findElement(By.xpath("//app-mod-manage-attribute-filter-add/div/div/div/div/div[2]")).getText();
		System.out.println(AlertMessage);
		
		//Click on Cancel button
		driver.findElement(By.xpath("//div[4]/button[3]")).click();
		
		/*List<WebElement> Attributescolumn = driver.findElements(By.xpath("//tbody/tr/td/input"));
		System.out.println(Attributescolumn.size());*/
		
		//Select the Added filter
		driver.findElement(By.xpath("//td/input")).click();
		
		//click on Delete
		driver.findElement(By.xpath("//button[3]")).click();
		
		//Click on OK
		driver.findElement(By.xpath("//app-mod-manage-attribute-filter/div/div[2]/button")).click();
		
		//click on Apply changers
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(2000);
		
		//Verification
		if(AlertMessage.equals(ErrorMessage))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Duplicate is not allowed, So Issue has been resolved");
			System.out.println("Duplicate is not allowed, So Issue has been resolved");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Attribute Filter condition failed");
			System.out.println("Issue Exits");
			driver.findElement(By.xpath("Filter condition failed")).click();
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Attribute Filter condition failed, please check stacktrace :" + e.getMessage());
			driver.findElement(By.xpath("Filter condition failed")).click();
			
		}
		Thread.sleep(2000);
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
