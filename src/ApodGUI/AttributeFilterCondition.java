package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;



public class AttributeFilterCondition 
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
			//driver=new FirefoxDriver();
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("marionette", false);
			driver = new FirefoxDriver(options);
		}
		
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(2000);
	}
	
	@Parameters({"Dashboardname", "Node", "wgs", "Queuemanager"})
	@Test(priority=1)
	public static void AddDashboard(String Dashboardname, String Node, int wgs, String Queuemanager) throws Exception
	{
		
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		driver.findElement(By.id("createInitialViewlets")).click();
		
		
		//Work group server selection
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(2000);
		dd.selectByIndex(wgs);
		
		/*//Selection of Node
		driver.findElement(By.cssSelector(".field-queuem-input")).click();
		driver.findElement(By.cssSelector(".field-queuem-input")).sendKeys(Node);
		
		//Selectiom of Queue manager
		driver.findElement(By.cssSelector(".field-node-input")).click();
		driver.findElement(By.cssSelector(".field-node-input")).sendKeys(Queuemanager);*/
			
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
	}
	
	@Parameters({"ConditionName", "ConditionData", "RowValue"})
	@Test(priority=2)
	public static void AddAttributeFilterCondition(String ConditionName, String ConditionData, String RowValue) throws Exception
	{
		//Edit Viewlet page
		driver.findElement(By.id("dropdownMenuButton")).click();
		driver.findElement(By.linkText("Edit viewlet")).click();
		
		//Add attribute filter
		driver.findElement(By.cssSelector("button.btn-white-round")).click();
		driver.findElement(By.xpath("//app-mod-manage-attribute-filter/div/button")).click();
		
		//Filter name
		driver.findElement(By.cssSelector("div.mafa-filter-name > input")).sendKeys(ConditionName);
		driver.findElement(By.xpath("//div[4]/button")).click();
		
		//Select the attribute
		driver.findElement(By.xpath("//td[contains(.,'Maximum Depth')]")).click();
		driver.findElement(By.xpath("//app-mod-manage-attribute-filter-add-available-attr/div/div/div[3]/button")).click();
		
		
		/*//Filter the data
		driver.findElement(By.xpath("//app-mod-manage-attribute-filter-add-available-attr/div/div/div/div/input")).sendKeys(RowValue);
		driver.findElement(By.xpath("//td/div")).click();
		
		driver.findElement(By.xpath("(//button[@type='button'])[11]")).click();
		Thread.sleep(2000);*/
		
		//Enter the Condition value 
		driver.findElement(By.cssSelector("td.mafa-table-row-cell > input")).click();
		driver.findElement(By.cssSelector("td.mafa-table-row-cell > input")).sendKeys(ConditionData);
		
		//Click on Ok
		driver.findElement(By.xpath("(//button[@type='button'])[10]")).click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath("//td/input")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
		Thread.sleep(2000);
		
		//Click on Apply
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(4000);
		
		/*//Verification of the Filter condition
		 String totalqueues= driver.findElement(By.xpath("//datatable-footer/div/span")).getText();
		 System.out.println("Queues data:" +totalqueues);
		 
		 String total[]=totalqueues.split(",")[0].split(":");
		 
		 
		 int queuescount = total.length;
		 System.out.println("Queues Count information:" +queuescount);*/
		 
		 //First loop will find the 'Maximum Depth' in the first row
		 for (int i=3;i<=10;i++)
		 {
		 String sValue = null;
		 sValue = driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-header/div/div[2]/datatable-header-cell["+ i +"]/div")).getText();
		 //System.out.println(sValue);
		 if(sValue.equalsIgnoreCase(RowValue))
		 {
		 // If the sValue match with the description, it will initiate one more inner loop for all the columns of 'i' row 
		 for (int j=1;j<=1;j++)
		 {
			 
		 String sColumnValue= driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper["+ j +"]/datatable-body-row/div[2]/datatable-body-cell[6]/div/span")).getText();
		 System.out.println(sColumnValue);
		 
		 //Changing the string data into integer
		 int result = Integer.parseInt(ConditionData);
		 int result1 = Integer.parseInt(sColumnValue);
		
		 //Verification Condition
		 if(result == result1)
		 {
			 System.out.println("Attribute filter Condition is Working fine");
		 }
		 else
		 {
			 System.out.println("Attribute Filter Condition is not working fine");
			 driver.findElement(By.xpath("Failed the condition")).click();
		 }
		 }
		 break;
		 }
		 }

		
	}
	
	//Logout
	@Test(priority=3)
	public void Logout() 
	{
	driver.findElement(By.cssSelector(".fa-power-off")).click();
	driver.close();
	}

}
