package ApodGUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testrail.TestClass;
import testrail.TestRail;

@Listeners(TestClass.class)	
public class TestCaseScripts 
{
	static WebDriver driver;
	
	
		
	@Test(priority=1)
	@TestRail(testCaseId = 5)
	@Parameters({"result"})
	public void AddWorkgroupFromPlusIcon(Integer result,ITestContext context) throws Exception
	{
		System.out.println("result: " + result);
		
	try
	{
		//Verification Condition
		if(result==1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Workgroup server is added successfylly");
			System.out.println("Workgroup server is added successfylly");
			
			//TestClass t=new TestClass();
			
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Workgroup Server is not added");
			System.out.println("Workgroup Server is not added");
			
		}
	}catch(Exception ex)
	{
		//ex.printStackTrace();
	}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 6)
	@Test(priority=2)
	public static void EditWorkgroup(Integer result,ITestContext context) throws Exception
	{
		
		
		//Verification condition
		if(result==1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Updating Workgroup server is working fine");
			System.out.println("Updating Workgroup server is working fine");
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Updating Workgroup server is not working fine");
			System.out.println("Updating Workgroup server is not working fine");
			//driver.findElement(By.xpath("condition failed")).click();
		}
		//Thread.sleep(1000);
		
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 7)
	@Test(priority=4)
	public void DeleteWorkgroup(Integer result,ITestContext context) throws Exception
	{
		try {
		
			
			//Verification of delete work group server
			if(result != 1)
			{
				context.setAttribute("Status", result);
				context.setAttribute("Comment", "WGS is not deleted");
				System.out.println("WGS is not deleted");
				
			}
			else
			{
				context.setAttribute("Status", result);
				context.setAttribute("Comment", "WGS is deleted successfully");
				System.out.println("WGS is deleted successfully");
			}
			
		
		}
		catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Second WGS is not present");
        }
	}
	
	@Test(priority=3)
	public static void ConnectIcon() throws InterruptedException
	{
		//Select the Edit WGS option
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/div/div[2]")).click();
		
		//Change password
		driver.findElement(By.name("port")).clear();
		driver.findElement(By.name("port")).sendKeys("4011");
		driver.findElement(By.cssSelector(".button-blue")).click();
		Thread.sleep(3000);
		
		//Store the Connection status into string
		String ConnectionFail=driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		System.out.println(ConnectionFail);
		
		//Verification condition
		if(ConnectionFail.equalsIgnoreCase("Not Connected"))
		{
			
			System.out.println("Work Group Server connection failed is successfully done");
		}
		else
		{
			
			System.out.println("Work Group Server connection failed");
		}
		
		//Click on Connection Icon
		driver.findElement(By.cssSelector("img.settings-image")).click();
		Thread.sleep(3000);
		
		//Connection Verification
		String ConnectionPass=driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[4]/div/span")).getText();
		if(ConnectionPass.equalsIgnoreCase("Connected"))
		{
			System.out.println("Work Group Server connected");
		}
		else
		{
			System.out.println("Work Group Server not connected");
		}
		Thread.sleep(1000);
		
		//Click on Edit 
		driver.findElement(By.xpath("/html/body/app-root/div/app-main-page/div/app-tab/div/div/div[1]/app-viewlet/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/input")).click();
		driver.findElement(By.xpath("//app-dropdown[@id='dropdown-block']/div/div/div[2]")).click();
		
		//Change password
		driver.findElement(By.name("port")).clear();
		driver.findElement(By.name("port")).sendKeys("4010");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector(".button-blue")).click();
		Thread.sleep(3000);
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 8)
	@Test(priority=5)
	public static void AddNode(Integer result,ITestContext context) throws InterruptedException
	{
				
		//Verification of node is added or not
		if(result==1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Node is created successfully");
			System.out.println("Node is created successfully");
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Node creation failed");
			System.out.println("Node is not created");
			
		}
		
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 9)
	@Test(priority=6)
	public void AddRemoteQueueManager(Integer result,ITestContext context) throws InterruptedException
	{
				
		//Verification
		if(result==1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is added successfully");
			System.out.println("Remote Queue Manager is added successfully");
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is not added");
			System.out.println("Remote Queue Manager is not added");
			
		}
							
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 10)
	@Test(priority=7)
	public void ModifyRemoteQueueManager(Integer result,ITestContext context) throws InterruptedException
	{
		
		if(result==1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue modification is done successfully");
			System.out.println("Remote Queue modification is done successfully");
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue modification is failed");
			System.out.println("Remote Queue modification is failed");
			//driver.findElement(By.xpath("Modification failed")).click();
		}
		
	
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 11)
	@Test(priority=8)
	public void DeleteRemoteQueueManager(Integer result,ITestContext context) throws InterruptedException
	{
				
		//Verification condition
		if(result !=1)
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is not deleted");
			System.out.println("Remote Queue Manager is not deleted");
			
		}
		else
		{
			context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is deleted");
			System.out.println("Remote Queue Manager is deleted");
		}
			           
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 12)
	@Test(priority=9)
	public  void SearchFilter(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Search is working fine");
	       System.out.println("Search is working fine");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Search is not working fine");
	       System.out.println("Search is not working fine");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 13)
	@Test(priority=10)
	public  void AddRemoteQueueManager19564(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is added successfully");
	       System.out.println("Remote Queue Manager is added successfully");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Remote Queue Manager is not added");
	       System.out.println("Remote Queue Manager is not added");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 14)
	@Test(priority=11)
	public  void Attributefiletr(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Duplicate is not allowed, So Issue has been resolved");
	       System.out.println("Duplicate is not allowed, So Issue has been resolved");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Issue Exits, Filter condition failed");
	       System.out.println("Issue Exits, Filter condition failed");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 15)
	@Test(priority=12)
	public  void LoginUser(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "User able to login the navigator");
	       System.out.println("User able to login the navigator");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "User unable to login the navigator");
	       System.out.println("User unable to login the navigator");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 16)
	@Test(priority=13)
	public  void ChannelProperties19729(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Channel properties are present");
	       System.out.println("Channel properties are present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Channel properties are not present");
	       System.out.println("Channel properties are not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 17)
	@Test(priority=14)
	public  void DataLimitOffSet19344(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Limit Offset Value, Passed");
	       System.out.println("Limit Offset Value, Passed");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Limit Offset Value, Fail");
	       System.out.println("Limit Offset Value, Fail");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 18)
	@Test(priority=15)
	public  void DatePicker19570(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Date Picker is present");
	       System.out.println("Date Picker is present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Date Picker is not present");
	       System.out.println("Date Picker is not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 19)
	@Test(priority=16)
	public  void EMSChannels18790(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "EMS channels are present");
	       System.out.println("EMS channels are present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "EMS channels are not present");
	       System.out.println("EMS channels are not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 20)
	@Test(priority=17)
	public  void EMSStartandStop19262(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", 1);
			context.setAttribute("Comment", "EMS objects does not have the Start and Stop options");
	       System.out.println("EMS objects does not have the Start and Stop options");
	    }
	    else
	    {
	    	context.setAttribute("Status", 5);
			context.setAttribute("Comment", "EMS objects have the Start and Stop options");
	       System.out.println("EMS objects have the Start and Stop options");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 21)
	@Test(priority=18)
	public  void ExportMessagesText19715(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Command settings page data is displaying properly");
	       System.out.println("Command settings page data is displaying properly");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Command settings page data is not displaying properly");
	       System.out.println("Command settings page data is not displaying properly");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 22)
	@Test(priority=19)
	public  void ExtendSessionWindow19569(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test passed");
	       System.out.println("Test passed");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test failed");
	       System.out.println("Test failed");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 23)
	@Test(priority=20)
	public  void ManagerOptions19787(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test passed");
	       System.out.println("Test passed");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test failed");
	       System.out.println("Test failed");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 24)
	@Test(priority=21)
	public  void MessageLengthField19678(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test passed");
	       System.out.println("Test passed");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Test failed");
	       System.out.println("Test failed");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 25)
	@Test(priority=22)
	public  void NodeDropdown18508(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Node dropdown list working fine");
	       System.out.println("Node dropdown list working fine");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Node dropdown not working properly");
	       System.out.println("Node dropdown not working properly");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 26)
	@Test(priority=23)
	public  void NodeInsteadOfManager20002(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Node viewlet is created in the work space");
	       System.out.println("Node viewlet is created in the work space");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Other than node, viewlet is created in the work space");
	       System.out.println("Other than node, viewlet is created in the work space");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 27)
	@Test(priority=24)
	public  void Putmessage19733(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Message Data Stored in queue");
	       System.out.println("Message Data Stored in queue");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Message Data not Stored in queue");
	       System.out.println("Message Data not Stored in queue");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 28)
	@Test(priority=25)
	public  void RenewToken19411(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "");
	       System.out.println("");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Not tested");
	       System.out.println("Not tested");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 29)
	@Test(priority=26)
	public  void ScheduleView19595(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Schedular list popup success");
	       System.out.println("Schedular list popup success");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Schedular not working");
	       System.out.println("Schedular not working");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 30)
	@Test(priority=27)
	public  void StartandStopListener18772(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Listener Viewlet is created");
	       System.out.println("Listener Viewlet is created");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Listner viewlet is not created");
	       System.out.println("Listner viewlet is not created");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 31)
	@Test(priority=28)
	public  void ListenerAtrributes_Edit_18419(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Editing listener attributes working fine");
	       System.out.println("Editing listener attributes working fine");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Editing listener attributes not working properly");
	       System.out.println("Editing listener attributes not working properly");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 32)
	@Test(priority=29)
	public  void ListeningPort_19426(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Working fine");
	       System.out.println("Working fine");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Not working properly");
	       System.out.println("Not working properly");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 33)
	@Test(priority=30)
	public  void DefaultAuditProfile_20008(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Data Displaying as Expected");
	       System.out.println("Data Displaying as Expected");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Data is Not Displaying as Expected");
	       System.out.println("Data is Not Displaying as Expected");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 34)
	@Test(priority=31)
	public  void EMSAdminRoles_19996(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "EMS admin role Option is present");
			System.out.println("EMS admin role Option is present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "EMS admin role Option is not present");
			System.out.println("EMS admin role Option is not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 35)
	@Test(priority=32)
	public  void ManagerNameUnderServerGroup_20012(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Manager Name option is present");
			System.out.println("Manager Name option is present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Manager Name option is not present");
			System.out.println("Manager Name option is not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Parameters({"result"})
	@TestRail(testCaseId = 36)
	@Test(priority=33)
	public  void NavigatorFeatures_20031(Integer result,ITestContext context) throws Exception
	{
				
		//Add New WGS10
		try
		{
		
	    if(result==1)
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Navigator Features option is present");
			System.out.println("Navigator Features option is present");
	    }
	    else
	    {
	    	context.setAttribute("Status", result);
			context.setAttribute("Comment", "Navigator Features option is not present");
			System.out.println("Navigator Features option is not present");
	      
	    }
	        
	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}


}
