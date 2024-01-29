package Utilities1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Driversetup {
	public static WebDriver driver;
	public static WebDriver getWebDriver() throws IOException {	
		
		Properties obj = new Properties();
		File file= new File("config.properties");
		FileInputStream f = new FileInputStream(file);
		obj.load(f);		
		String browserName=obj.getProperty("browser");		
		WebDriver driver=null;		
		if(browserName.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
				 driver=new EdgeDriver();
		}
		else {
			System.out.println("Enter a Valid Browser");
		}						
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		driver.navigate().to("https://www.yatra.com/");
		driver.manage().window().maximize();		
		return driver;
	}
}
