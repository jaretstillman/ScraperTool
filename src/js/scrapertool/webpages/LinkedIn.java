package js.scrapertool.webpages;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/*
 * This class is the engine for the TTL Scraper Tool
 * It is capable of going through ~150 companies in an hour
 * At the end, it compiles a list which can be copied into a spreadsheet
 * Accuracy TBD
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 * Version: 1.0
 * Date: 6/29/17
 */
public class LinkedIn implements WebPage
{
	PhantomJSDriver driver;

	public static void main(String[] args)
	{

		String[] tags = { "p[class*='org-about-company-module__founded']",
				"p[class*='org-about-company-module__headquarters']",
				"p[class*='org-about-company-module__company-staff-count-range']" };
		
		String email = "jrsstill@umich.edu";
		String password = "My Password";
		String csvFile = "database_companies_export.csv";
		
		run(tags, email, password,csvFile);
	}
	
	public static void run(String[] tags, String email, String password, String csvFile)
	{

		// Setup driver, login to LinkedIn
		Main m = new Main();

		// Prevent Log Messages from showing up in console, login
		System.setProperty("phantomjs.binary.path",
				"C:/Users/jrsti/Documents/Java/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		DesiredCapabilities dcap = new DesiredCapabilities();
		String[] phantomArgs = new String[] { "--webdriver-loglevel=NONE" };
		dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
		m.login(email, password, dcap);

		// Import CSV File
		ArrayList<String> companies = m.readCSV(csvFile);

		// This is an String ArrayList for each category (contained in tags[])
		// i.e. cmp[0] will be for all the dates founded, cmp[1] will be for all
		// the headquarters, cmp[2] will be for all the employeeNumbers, etc.
		ArrayList<ArrayList<String>> cmp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < tags.length; i++)
		{
			cmp.add(new ArrayList<String>());
		}


		// Iterate through companies, compile data
		for (String company : companies)
		{
			System.out.println(company + " ");
			String link = m.getLink(company);
			if (link == "NO_LINK")
			{
				System.out.println("No Link Found");
			} 
			else
			{
				System.out.println(link);
				m.getData(link, company, tags, cmp);
			}
			System.out.println("Added\n\n");
		}

		// Print data
		m.print(cmp);
	}

	/*
	 * Logs in to LinkedIn 
	 * REQUIRES: e and p are a valid email and password for LinkedIn, respectively 
	 * MODIFIES: driver 
	 * EFFECTS: Adds login cookies to driver
	 */
	public void login(String e, String p, DesiredCapabilities dcap)
	{
		String url = "https://www.linkedin.com";

		// Initialize Driver
		driver = new PhantomJSDriver(dcap);

		// Go to login page, enter email and password
		driver.get(url);
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
			System.exit(0);
		}
		
		System.out.println("\n\nLogin for " + e + " successful");
	}

	/*
	 * Read in CSV File from "filename.csv" 
	 * Format: Company, xxx, xxx... (headers) 
	 * Company_name1, info1a, info1b... 
	 * Company_name2, info2a, info2b...
	 * 
	 * REQUIRES: filename leads to a valid CSV file of correct format (see above) 
	 * EFFECTS: Returns an ArrayList<String> of company names from CSV column 1
	 */
	public ArrayList<String> readCSV(String fileName)
	{
		String line = "";
		String csvSplitBy = ",";
		ArrayList<String> companies = new ArrayList<String>();
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
		{
			while ((line = br.readLine()) != null)
			{
				// skip the first line
				if (i == 0)
				{
					++i;
					continue;
				}
				// use comma as separator
				String[] row = line.split(csvSplitBy);
				companies.add(row[0]);
			}
		} 
		catch (IOException e)
		{
			System.out.println("Import of " + fileName + " failed");
			System.exit(0);
		}
		
		System.out.println(fileName + " succesfully imported\n\n");
		return companies;
	}

	/*
	 * Searches LinkedIn for the link to the LinkedIn Company Page 
	 * MODIFIES: driver 
	 * EFFECTS: Returns a link to the first search result under the "Companies" tab
	 */
	public String getLink(String search)
	{
		try
		{
			// Edit names that don't work in search
			search.replaceAll(" ", "%20").replaceAll(",", "%2C"); // can add more, but these are most common, easier to fill in the rest
			
			driver.get("https://www.linkedin.com/search/results/companies/?keywords=" + search
					+ "&origin=SWITCH_SEARCH_VERTICAL");

			WebElement company = driver.findElement(By.cssSelector("a[data-control-name='search_srp_result']"));
			String href = company.getAttribute("href");
			return href;
		}

		catch (NoSuchElementException e)
		{
			return "NO_LINK";
		}
	}

	// OPEN LINK, SCRAPE HTML
	public void getData(String link, String company, String[] tags, ArrayList<ArrayList<String>> cmp)
	{
		// Connect to LinkedIn Company Page
		driver.get(link);

		// Check if this is the right company page
		try
		{
			if (!driver.findElement(By.cssSelector("h1[class*='org-top-card-module__name']")).getAttribute("innerHTML")
					.toLowerCase().trim().contains(company.toLowerCase().trim()))
			{
				System.out.println("Wrong company from link");
				for (int i = 0; i < tags.length; i++)
				{
					cmp.get(i).add(" ");
				}
				return;
			}
		} 
		catch (NoSuchElementException e)
		{
			System.out.println("Page Not Loading Correctly");
			for (int i = 0; i < tags.length; i++)
			{
				cmp.get(i).add(" ");
			}
			return;
		}

		// Get Data, Sort into ArrayList
		WebElement e1;
		for (int i = 0; i < tags.length; i++)
		{
			try
			{
				e1 = driver.findElement(By.cssSelector(tags[i]));
				cmp.get(i).add(e1.getAttribute("innerHTML").trim());
			}

			// LinkedIn Page doesn't contain that tag
			catch (NoSuchElementException e)
			{
				cmp.get(i).add(" ");
			}
		}
	}

	// PRINT PACKAGE
	public void print(ArrayList<ArrayList<String>> cmp)
	{
		for (ArrayList<String> tag : cmp)
		{
			for (String s : tag)
			{
				System.out.println(s);
			}
			System.out.println("\n\n");
		}
	}

	// Helper function for waiting for page to load
	// REQUIRES: numSecs>=0
	// EFFECTS: Sleeps thread for numSecs
	private void wait(double numSecs)
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
