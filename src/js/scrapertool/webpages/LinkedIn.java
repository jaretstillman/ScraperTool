package js.scrapertool.webpages;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import js.scrapertool.exceptions.InvalidCredentialsException;

/*
 * Description: This class defines custom methods for the LinkedIn scraper module
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 * Version: 1.1
 * Date: 6/30/17
 */
public class LinkedIn extends WebPage
{

	/*
	 * Logs in to LinkedIn 
	 * REQUIRES: e and p are a valid email and password for LinkedIn, respectively 
	 * MODIFIES: driver 
	 * EFFECTS: Adds login cookies to driver
	 */
	public void login(String e, String p) throws InvalidCredentialsException
	{
		String url = "https://www.linkedin.com";
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
			throw new InvalidCredentialsException("Invalid Credentials: Login for " + e + " Unsuccessful");
		}
		
		System.out.println("\n\nLogin for " + e + " successful");
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
				System.out.println(tags[i] + " found");
				cmp.get(i).add(e1.getAttribute("innerHTML").trim());
			}

			// LinkedIn Page doesn't contain that tag
			catch (NoSuchElementException e)
			{
				System.out.println(tags[i] + " not found");
				cmp.get(i).add(" ");
			}
		}
	}

}
