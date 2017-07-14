package js.scrapertool.webpages;

import java.util.ArrayList;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import js.scrapertool.exceptions.InvalidCredentialsException;


/*
 * Description: This class defines the global vars and abstract methods for each WebPage module (LinkedIn, Crunchbase, Facebook, etc.)
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public abstract class WebPage
{
	PhantomJSDriver driver;
	public abstract void login(String e, String p) throws InvalidCredentialsException;
	public abstract String getLink(String search, String type);
	public abstract void getData(String link, String company, String type, String[] tags, ArrayList<ArrayList<String>> cmp);
	
	public WebPage()
	{
		
		// Prevent Log Messages from showing up in console, login
		System.setProperty("phantomjs.binary.path",
				"C:/Users/jrsti/Documents/Java/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		DesiredCapabilities dcap = new DesiredCapabilities();
		String[] phantomArgs = new String[] { "--webdriver-loglevel=NONE" };
		dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
		
		/*System.setProperty("webdriver.chrome.driver","C:/Users/jrsti/Documents/Java/chromedriver.exe");
		ChromeOptions cO = new ChromeOptions();
		cO.addArguments("--headless");
		cO.setBinary("C:/Users/jrsti/AppData/Local/Google/Chrome SxS/Application/chrome.exe");
		driver = new ChromeDriver(cO); */
		
		//Initialize driver, try 3 times before crashing
		int i=0;
		while(i<3)
		{
			try
			{
				driver=new PhantomJSDriver(dcap);
				break;
			}
			catch(WebDriverException e)
			{
				if(i>3) {e.printStackTrace(); System.exit(0);}
				i++;
			}
		}
		
	}
	
	// Helper function for waiting for page to load
	// REQUIRES: numSecs>=0
	// EFFECTS: Sleeps thread for numSecs
	protected void wait(double numSecs)
	{
		try
		{
			Thread.sleep((int) numSecs * 1000);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	} 

}
