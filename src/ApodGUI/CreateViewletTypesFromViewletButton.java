package ApodGUI;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

public class CreateViewletTypesFromViewletButton 
{
static WebDriver driver;
	
	
	@Parameters({"sDriver", "sDriverpath", "URL", "uname", "password", "ScreenshotPath", "Dashboardname"})
	@Test
	public void Login(String sDriver, String sDriverpath, String URL, String uname, String password, String ScreenshotPath, String Dashboardname) throws IOException
	{
		try
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
		Thread.sleep(2000);
		
		//Create New Dashboard
		driver.findElement(By.cssSelector("div.block-with-border")).click();
		driver.findElement(By.name("dashboardName")).sendKeys(Dashboardname);
		
		//Create viewlet button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(2000);
		
		}
		catch (Exception e)
		{
			File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,new File(ScreenshotPath+"Loginpage.png"));
		}	
	}
	
	@Parameters({"Nodename", "WGSName"})
	@Test(priority=1)
	public void CreateViewletUsingObjectCheckbox(String Nodename, String WGSName) throws InterruptedException
	{	
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
			
		//Create a Node
		driver.findElement(By.cssSelector("div.object-type")).click();
		driver.findElement(By.name("viewletName")).clear();
		//Thread.sleep(2000);
		driver.findElement(By.name("viewletName")).sendKeys(Nodename);
		
		//Select WGS
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(1000);
		dd.selectByVisibleText(WGSName);
		
		//driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(Nodename))
		{
			System.out.println("Node Viewlet is created");
		}
		else
		{
			System.out.println("Node viewlet is not created");
			driver.findElement(By.xpath("Not created")).click();
		}
	}
	
	@Parameters({"WGSName"})
	@Test(priority=2)
	public void CreateATemporaryViewletUsingSearchCheckbox(String WGSName) throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		
		//Click on Temporary viewlet check box
		driver.findElement(By.id("temp")).click();
		
		//Click on Create Button
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click(); 
		Thread.sleep(1000);
		
		String ViewletName=driver.findElement(By.name("viewletName")).getText();
		System.out.println(ViewletName);
		
		//Select WGS
		Select dd=new Select(driver.findElement(By.cssSelector("select[name=\"wgsKey\"]")));
		Thread.sleep(1000);
		dd.selectByVisibleText(WGSName);
		
		//driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		if(driver.getPageSource().contains(ViewletName))
		{
			System.out.println("Temporary Viewlet is created");
		}
		else
		{
			System.out.println("Temporary viewlet is not created");
			driver.findElement(By.xpath("Temporary viewlet Not created")).click();
		}
	}
	
	
	@Parameters({"FavoriteViewletName", "Favwgs"})
	@Test(priority=3)
	public void CreateFavoriteViewletUsingCheckbox(String FavoriteViewletName, String Favwgs) throws InterruptedException
	{
		//Create favorite viewlet 
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		driver.findElement(By.id("fav")).click();
		driver.findElement(By.cssSelector("div.mod-select-viewlet-buttons > button.g-button-blue")).click();
		
		//Viewlet Name
		driver.findElement(By.name("viewlet-name")).click();
		driver.findElement(By.name("viewlet-name")).sendKeys(FavoriteViewletName);
		
		Select wgsdropdown=new Select(driver.findElement(By.name("wgs")));
		wgsdropdown.selectByVisibleText(Favwgs);
		
		//Submit
		driver.findElement(By.cssSelector("div.g-block-bottom-buttons.buttons-block > button.g-button-blue")).click();
		Thread.sleep(2000);
		
		if(driver.getPageSource().contains(FavoriteViewletName))
		{
			System.out.println("Favorite Viewlet is created");
		}
		else
		{
			System.out.println("Favorite viewlet is not created");
			driver.findElement(By.xpath("Not created Favorite")).click();
		}
	}
	
	@Test(priority=4)
	public void OpenAnExistingCheckbox() throws InterruptedException
	{
		//Click on Viewlet
		driver.findElement(By.cssSelector("button.g-button-blue.button-add")).click();
		
		Boolean Checkbox=driver.findElement(By.id("existing")).isEnabled();
		System.out.println(Checkbox);
		
		if(Checkbox == false)
		{
			System.out.println("Open existing viewlet checkbox is disabled state");
		}
		else
		{
			System.out.println("Open existing viewlet checkbox is Enabled state");
			driver.findElement(By.id("Open existing check box condition failed")).click();
		}
		Thread.sleep(1000);
		
		//Click on cancel button
		driver.findElement(By.cssSelector(".g-button-red")).click();
	}
	
	
	@Parameters({"NewViewletName"})
	@Test(priority=5)
	public void EditViewletOption(String NewViewletName) throws InterruptedException
	{		
		//Click on Dropdown menu
		driver.findElement(By.id("dropdownMenuButton")).click();
		
		//select Edit viewlet option
		driver.findElement(By.linkText("Edit viewlet")).click();
		Thread.sleep(1000);
		
		//Change the viewlet name
		driver.findElement(By.id("viewletName")).clear();
		driver.findElement(By.id("viewletName")).sendKeys(NewViewletName);
		
		//click on Apply changes
		driver.findElement(By.cssSelector(".btn-primary")).click();
		Thread.sleep(1000);
		
		//Store the viewlet name into string
		String ViewletName=driver.findElement(By.cssSelector(".title-table")).getText();
		System.out.println("Viewlet Name is:" +ViewletName);
		
		if(ViewletName.equalsIgnoreCase(NewViewletName))
		{
			System.out.println("Viewlet Edit is working fine");
		}
		else
		{
			System.out.println("Viewlet Edit is not working");
			driver.findElement(By.id("Edit option failed")).click();
		}
		Thread.sleep(1000);
	}
	
	
	@Test(priority=6)
	public void DeleteViewletOption() throws InterruptedException
	{
		//Store the viewlet name into string
		String BeforeDeleteViewletName=driver.findElement(By.cssSelector(".title-table")).getText();
		System.out.println("Viewlet Name is:" +BeforeDeleteViewletName);
		
		//Click on Dropdown menu
		driver.findElement(By.id("dropdownMenuButton")).click();
		
		//select Edit viewlet option
		driver.findElement(By.linkText("Delete viewlet")).click();
		Thread.sleep(1000);
		
		//Confirmation ok button
		driver.findElement(By.id("accept-true")).click();
		Thread.sleep(4000);
		
		//Store the viewlet name into string
		String AfterDeleteViewletName=driver.findElement(By.cssSelector(".block-title-row")).getText();
		System.out.println("Viewlet page data:" +AfterDeleteViewletName);
		
		if(AfterDeleteViewletName.equalsIgnoreCase(BeforeDeleteViewletName))
		{
			System.out.println("Delete option is not working");
			driver.findElement(By.id("Delete viewlet option failed")).click();
		}
		else
		{
			System.out.println("Delete option is working fine");
		}
		Thread.sleep(1000);
	}
	
	@Test(priority=20)
	public static void Logout() throws InterruptedException
	{
		//Logout
		try
		{
			driver.findElement(By.cssSelector(".active .g-tab-btn-close-block")).click();
			//driver.findElement(By.cssSelector(".fa-times")).click();
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
