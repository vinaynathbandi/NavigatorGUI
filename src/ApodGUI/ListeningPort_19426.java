package ApodGUI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.Settings;
import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)
public class ListeningPort_19426 {
	static WebDriver driver;

	@Parameters({ "sDriver", "sDriverpath", "URL", "uname", "password" })
	@TestRail(testCaseId = 20)
	@Test
	public static void Login(String sDriver, String sDriverpath, String URL, String uname, String password,ITestContext context)
			throws Exception {
		
		try {
		Settings.read();
		String urlstr=Settings.getSettingURL();
		URL= urlstr+URL;
		
		if (sDriver.equalsIgnoreCase("webdriver.chrome.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new ChromeDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.ie.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new InternetExplorerDriver();
		} else if (sDriver.equalsIgnoreCase("webdriver.edge.driver")) {
			System.setProperty(sDriver, sDriverpath);
			driver = new EdgeDriver();
		} else {
			System.setProperty(sDriver, sDriverpath);
			driver = new FirefoxDriver();
		}

		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Login page
		driver.findElement(By.id("username")).sendKeys(uname);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.cssSelector("button.btn-submit")).click();
		Thread.sleep(10000);
		Actions act1 = new Actions(driver);
		driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
		WebElement CREATE = driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/ul/li[3]"));
		act1.moveToElement(CREATE);

		WebElement CreateNODE = driver.findElement(By.cssSelector(".sub-menu > li:nth-child(1)"));
		act1.moveToElement(CreateNODE).click().build().perform();
		driver.findElement(By.xpath("//div[7]/div/input")).click();
		String PORT = driver.findElement(By.cssSelector(".row:nth-child(7) .form-control")).getAttribute("value");
		
		System.out.println(PORT);
		
		if(PORT.equals("5010")) {
			context.setAttribute("Status", 1);
			context.setAttribute("Comment", "Test Passed");
			System.out.println("Test Passed");
			driver.findElement(By.cssSelector(".btn-danger")).click();
			driver.findElement(By.cssSelector(".fa-power-off")).click();
			driver.close();
		}
		else
		{
			context.setAttribute("Status", 5);
			context.setAttribute("Comment", "Test Failed");
			System.out.println("Test Failed");
		}
		}catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			context.setAttribute("Status", 4);
			context.setAttribute("Comment", e.getMessage());
			//driver.findElement(By.cssSelector(".fa-power-off")).click();
			driver.close();
		}
	}
		
	
}
