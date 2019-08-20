package ApodGUI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ietest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.ie.driver", "Drivers/IEDriverServer.exe");
		WebDriver d=new InternetExplorerDriver();
		d.get("http://localhost:8081/navigator/#/login");

	}

}
