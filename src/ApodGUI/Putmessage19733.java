package ApodGUI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
public class Putmessage19733 
{
static WebDriver driver;
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "Dashboardname", "wgs", "MessageData"})
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String Dashboardname, String wgs, String MessageData) throws Exception
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
		driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByVisibleText(wgs);
		
		/*//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		//Check Show Empty Queues check box
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(2000);
		
		//put the messages into empty queues for testing
		for(int m=1; m<=1; m++)
		{
			//Select the put new message option
			driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper["+ m +"]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
			Actions PutMessagesMousehour=new Actions(driver);
			PutMessagesMousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[6]"))).perform();
			driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[6]/ul/li")).click();
			Thread.sleep(1000);
			
			//Select the number of messages
			driver.findElement(By.name("generalNumberOfMsgs")).click();
			driver.findElement(By.name("generalNumberOfMsgs")).clear();
			driver.findElement(By.name("generalNumberOfMsgs")).sendKeys("1");
			
			//Put a message data
			driver.findElement(By.xpath("//*[@id=\"9\"]")).click();
			driver.findElement(By.xpath("//*[@id=\"9\"]")).sendKeys(MessageData);
			driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
			Thread.sleep(2000);
		}
		
		//Restoring the Default Settings
		driver.findElement(By.cssSelector(".fa-cog")).click();
		driver.findElement(By.xpath("//div[2]/div/div/div[2]/button")).click();
		driver.findElement(By.xpath("//div[3]/button")).click();
		Thread.sleep(2000);
		
		
	}
	
	@Parameters({"MessageData", "EncodingType"})
	@TestRail(testCaseId = 15)
	@Test(priority=1)
	public static void PutNewMessageIntoQueue(String MessageData, String EncodingType, ITestContext context) throws InterruptedException
	{		
		try {
		//Select put new message Option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		Actions MessagesMousehour=new Actions(driver);
		MessagesMousehour.moveToElement(driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[6]"))).perform();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[6]/ul/li")).click();
		Thread.sleep(1000);
		
		//Change the Option from the Encoding Dropdown
		Select Encoding=new Select(driver.findElement(By.id("encoding")));
		Encoding.selectByVisibleText(EncodingType);
				
		//Message data
		driver.findElement(By.xpath("//*[@id=\"9\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"9\"]")).sendKeys(MessageData);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		Thread.sleep(2000);
		
		//Browse the message from the queue
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li")).click();
		Thread.sleep(1000);
		
		List<WebElement> Attributescolumn = driver.findElements(By.xpath("//tbody/tr/td[2]/div"));
		//System.out.println(Attributescolumn.size());
		
		int i=Attributescolumn.size()+1;
		System.out.println(i);
		
		if(i<=10)
		{
			driver.findElement(By.xpath("//tr["+ i +"]/td[2]/div")).click();
		
		}
		else if(i<=20)
		{
			driver.findElement(By.cssSelector(".next")).click();
			driver.findElement(By.xpath("//tr["+ i +"]/td[2]/div")).click();
		}
		
		//Store the Encoding value
		String Encodingdata=driver.findElement(By.xpath("//hex-editor-lib/div/div")).getText();
		//System.out.println(Encodingdata);
		Thread.sleep(1000);
		Select selectedValue=new Select(driver.findElement(By.id("encoding")));
		 String Encoding1 = selectedValue.getFirstSelectedOption().getText();
		 System.out.println(Encoding1);
		
		if(Encoding1.contains(EncodingType))
		{
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Message Data Stored in the"+ Encodingdata +"Formate");
			System.out.println("Message Data Stored in the"+ Encodingdata +"Formate");
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Message Data not Stored in the"+ Encodingdata +"Formate");
			System.out.println("Message Data not Stored in the"+ Encodingdata +"Formate");
			driver.findElement(By.xpath("Failed test case")).click();
		}
		
		//Close the Window
		driver.findElement(By.cssSelector(".btn-danger")).click();
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
