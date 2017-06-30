package js.scrapertool.webpages;

import java.util.ArrayList;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


/*
 * Description: This class defines the global vars and abstract methods for each WebPage module (LinkedIn, Crunchbase, Facebook, etc.)
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 * Version: 1.1
 * Date: 6/30/17
 */
public abstract class WebPage
{
	PhantomJSDriver driver;
	public abstract void login(String e, String p);
	public abstract String getLink(String search);
	public abstract void getData(String link, String company, String[] tags, ArrayList<ArrayList<String>> cmp);
	
	public WebPage()
	{
		// Prevent Log Messages from showing up in console, login
		System.setProperty("phantomjs.binary.path",
				"C:/Users/jrsti/Documents/Java/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		DesiredCapabilities dcap = new DesiredCapabilities();
		String[] phantomArgs = new String[] { "--webdriver-loglevel=NONE" };
		dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
		
		//Initialize driver
		driver=new PhantomJSDriver(dcap);
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
