package Miniprojectmodifications;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.excel.Writeexcel;

import Miniprojectmodifications.Yatra;

import Utilities1.Driversetup;

public class Yatra {
	public static WebDriver driver;
	public static String test;
	String file = System.getProperty("user.dir")+"\\excel\\Book.xlsx";
	// DRIVER SETUP
	public void setup() throws IOException {
		driver = Driversetup.getWebDriver();
	}

	public void ClickOffersAndValidate() throws IOException {
		driver.findElement(By.linkText("Offers")).click();
		String mytitle = driver.getTitle();
		int row=Writeexcel.getRowCount(file,"Sheet1");
		for(int i=1;i<=row;i++) {
			String expectedtitle=Writeexcel.getCellData(file, "Sheet1", i, 0);
			if(mytitle.equalsIgnoreCase(expectedtitle)) {
				Writeexcel.setCellData(file, "Sheet1", i, 2, "Pass");
				Writeexcel.fillGreenColor(file, "Sheet1", i, 2);
			}
			else {
				Writeexcel.setCellData(file, "Sheet1", i, 2, "Fail");
				Writeexcel.fillRedColor(file, "Sheet1", i, 2);
			}
		}	
	}

	public void ValidateBannerText() throws IOException {
		int row=Writeexcel.getRowCount(file,"Validating_banner");
		String bannertext = driver.findElement(By.cssSelector("h2[class='wfull bxs']")).getText();
		for(int i=1;i<=row;i++) {
			String expectedbannertext = Writeexcel.getCellData(file, "Validating_banner", i, 0);
			if (bannertext.equalsIgnoreCase(expectedbannertext)) {
				Writeexcel.setCellData(file, "Validating_banner", i, 2, "Pass");
				Writeexcel.fillGreenColor(file, "Validating_banner", i, 2);
			} 
			else {
				Writeexcel.setCellData(file, "Validating_banner", i, 2, "Fail");
				Writeexcel.fillRedColor(file, "Validating_banner", i, 2);
			}
		}
	}
	
	public void ScreenShot() throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File("C:\\Users\\2304126\\eclipse-workspace\\vinayTesting\\vinayTesting\\ScreenShot\\picture.png");
		FileUtils.copyFile(src, trg);
	}

	public void ToursimInfo() throws IOException {
		List<String> tourismnames = new ArrayList<String>();
		List<String> costs = new ArrayList<String>();	
		driver.findElement(By.linkText("Holidays")).click();
		List<WebElement> imgs = driver.findElements(By.cssSelector("*.respnsiv-img"));
		
		
		//int count = 1;		
		for (WebElement i : imgs) {
			//if (count <= 5) {
				String parenttab = driver.getWindowHandle();
				// The new windows
				i.click();
				Set<String> alltabs = driver.getWindowHandles();
				for (String tab : alltabs) {
					if (!tab.equals(parenttab)) {
						driver.switchTo().window(tab);
						break;
					}
				}
				
				String name = driver.findElement(By.tagName("h1")).getText();
				String cost = driver
						.findElement(By.xpath(
								"/html/body/div[1]/div/section/div[1]/div/aside/div/div[2]/div[1]/span[1]/span/span"))
						.getText();				
				tourismnames.add(name);
				costs.add(cost);
				System.out.println("Te name of the tourism is : " + name + "   the cost is: " + cost);				
				driver.close();
				driver.switchTo().window(parenttab);
			//}
			//count = count + 1;
		}
		//Write data to excel
		int r=1;
		for(int i=0;i<tourismnames.size();i++) {
			Writeexcel.setCellData(file, "Holiday_Packages", r, 0,tourismnames.get(i));
			r+=1;
		}		
		r=1;
		for(int j=0;j<costs.size();j++) {
			Writeexcel.setCellData(file, "Holiday_Packages", r, 1,costs.get(j));
			r+=1;
		}
	}

	public void CloseBrowser() {
		driver.quit();
	}

	public static void main(String[] args) throws IOException {
		Yatra trip = new Yatra();
		trip.setup();
		// Navigate to home page of the application and click “Offers” link.
		// Validate the page by checking the title "Domestic Flights Offers | Deals on
		// Domestic Flight Booking | Yatra.com".
		
		trip.ClickOffersAndValidate();
		// verifying the banner text
		
		trip.ValidateBannerText();
		// screen shot of the browser window.
		
		trip.ScreenShot();
		// Tourismname and priceinformation
		
		trip.ToursimInfo();
		// closing the browser
		
		trip.CloseBrowser();
	}
}
