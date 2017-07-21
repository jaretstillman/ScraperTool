package js.scrapertool.webpages;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import js.scrapertool.exceptions.InvalidCredentialsException;
import js.scrapertool.main.TagMap;

/*
 * Description: This class defines custom methods for the LinkedIn scraper module
 * Note: LinkedIn is significantly slower than Crunchbase because JSoup is a lot faster than Selenium, but LinkedIn wouldn't work with JSoup
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class LinkedIn extends WebPage
{
	private PhantomJSDriver driver;
	
	/*
	 * Logs in to LinkedIn 
	 * REQUIRES: e and p are a valid email and password for LinkedIn, respectively 
	 * MODIFIES: driver 
	 * EFFECTS: Adds login cookies to driver
	 */
	public void login(String e, String p) throws InvalidCredentialsException
	{
		initDriver();
		String url = "https://www.linkedin.com";
		
		// Go to login page, enter email and password
		
		try
		{
		driver.get(url);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(0);
		}
		
		WebElement email = driver.findElement(By.cssSelector("input[id='login-email']"));
		WebElement password = driver.findElement(By.cssSelector("input[id='login-password']"));
		email.sendKeys(e);
		password.sendKeys(p);
		
		// Click submit button
		WebElement submit = driver.findElement(By.cssSelector("input[id='login-submit']"));
		submit.click();
		wait(1.5);

		// Check to see if login is successful
		if (!driver.getCurrentUrl().equals("https://www.linkedin.com/feed/"))
		{
			System.out.println("Invalid Credentials: Login for " + e + " Not Successful");
			throw new InvalidCredentialsException("Invalid Credentials: Login for " + e + " Unsuccessful");
		}
		
		System.out.println("\n\nLogin for " + e + " successful\n\n");
	}

	/*
	 * Searches LinkedIn for the link to the LinkedIn Company Page 
	 * MODIFIES: driver 
	 * EFFECTS: Returns a link to the first search result under the "Companies" tab
	 */
	public String getLink(String search, String type)
	{
		try
		{
			// Edit names that don't work in search
			search.replace(" ", "%20").replace(",", "%2C"); // can add more, but these are most common, easier to fill in the rest
			
			try
			{
				driver.get("https://www.linkedin.com/search/results/" + type.toLowerCase() + "/?keywords=" + search
						+ "&origin=SWITCH_SEARCH_VERTICAL");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			
			//Check if we are getting the right page from a search
			WebElement firstResult = driver.findElement(By.cssSelector("a[data-control-name*='search_srp_result']"));
			WebElement title = type.equals("Companies") ? driver.findElement(By.cssSelector("h3[class*='search-result__title']")) :
									driver.findElement(By.cssSelector("span[class*='name actor-name']"));
			
			//If type = "People", check that the last name matches the search
			//In future, consider adding "Nicknames" module to check first name as well
			if(type.equals("People"))
			{
				String[] firstLast = new String[2];
				firstLast = search.split("\\s",2); //split into first and last name
				if(!trimWord(title.getAttribute("innerHTML")).contains(trimWord(firstLast[1])) && 
						!trimWord(firstLast[1]).contains(title.getAttribute("innerHTML")))
				{
					System.out.println(title.getAttribute("innerHTML") + " != " + search);
					return "NO_LINK";
				}
			}
			
			//If type = "Companies", check that the company name matches the search
			else 
			{
				String searchTrimmed = trimWord(search);
				String titleTrimmed = trimWord(title.getAttribute("innerHTML"));
				if (!searchTrimmed.contains(titleTrimmed) && !titleTrimmed.contains(searchTrimmed)) 
				{
					System.out.println(searchTrimmed + " != " + titleTrimmed);
					return "NO_LINK";
				}
			}
			String href = firstResult.getAttribute("href");
			return href;
		}

		catch (NoSuchElementException e) //there are no links on the page
		{
			return "NO_LINK";
		}
	}
	
	//Helper function for trimming words for easy comparison
	private String trimWord(String s)
	{
		return s.trim().toLowerCase().replaceAll("\u00A0", ""); // "\u00A0" is a non-trimmable character, so I manually replace it here just in case
	}

	/*
	 * REQUIRES: link is valid, tags are valid HTML tags, site and type are valid, cmp is initialized
	 * MODIFIES: cmp
	 * EFFECTS:  This method gets the data requested by the tags array and adds it to cmp
	 */
	public void getData(String link, String site, String item, String type, String[] tags, ArrayList<ArrayList<String>> cmp)
	{
		//Convert tags to HTML with a TagMap
		TagMap tm = new TagMap(site,type);
		String[] convertedTags = new String[tags.length];
		for(int i=0; i<tags.length; i++)
		{
			convertedTags[i] = tm.convert(tags[i]);
		}	
		
		
		
		// Connect to LinkedIn Page
		try
		{
			driver.get(link);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		// Check if the page is loading
		try
		{
			if(type.equals("Companies"))
			{
				driver.findElement(By.cssSelector("h1[class*='org-top-card-module__name']"));
			}
			else
			{
				driver.findElement(By.cssSelector("h1[class*='pv-top-card-section__name']"));
			}
		} 
		catch (NoSuchElementException e)
		{
			System.out.println(item + " PAGE NOT LOADING CORRECTLY");
			for (int i = 0; i < tags.length; i++)
			{
				cmp.get(i).add("-");
			}
			return;
		}
		
		WebElement e1;
		for (int i = 0; i < tags.length; i++)
		{
			try
			{
				e1 = driver.findElement(By.cssSelector(convertedTags[i]));
				cmp.get(i).add(e1.getAttribute("innerHTML").trim());
			}

			// LinkedIn Page doesn't contain that tag
			catch (NoSuchElementException e)
			{
				System.out.println(item +" "+tags[i] + " not found");
				cmp.get(i).add("-");
			}
		}
	}

	//Helper function to initialize the PhantomJSDriver
	private void initDriver()
	{	
		//Extract PhantomJs.exe
		try
		{
			File f = new File ("./phantomjs.exe");
			if (!f.exists()) 
			{
			  wait(2.0);
			  InputStream in = getClass().getClassLoader().getResourceAsStream("js/scrapertool/resources/phantomjs.exe");
			  OutputStream out = new FileOutputStream("./phantomjs.exe");
			  IOUtils.copy(in, out);
			}
			System.setProperty("phantomjs.binary.path","./phantomjs.exe");
		}
		catch(Exception e)
		{
			try 
			{
				e.printStackTrace(new PrintStream("output.text"));
			}
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
			System.exit(0);
		}
		
		// Prevent Log Messages from showing up in console
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
			catch(Exception ex)
			{
				if(i>2)
				{
					try 
					{
						ex.printStackTrace(new PrintStream("output.txt"));
					}
					catch (FileNotFoundException e) 
					{
						e.printStackTrace();
					} 
					System.exit(0);
				}
			}
		}
	}
}

