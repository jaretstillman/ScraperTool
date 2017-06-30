package js.scrapertool.main;

import js.scrapertool.webpages.WebPage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import js.scrapertool.webpages.LinkedIn;
import js.scrapetool.gui.GUI;

/*
 * Description: This class is the engine for the ScraperTool, it generates the GUI and WebPage Modules and controls them
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 * Version: 1.1
 * Date: 6/30/17
 */
public class Main
{
	public static void main(String[] args)
	{	
		Main m = new Main();
		GUI gui = new GUI();
		WebPage wp = null;
		//This will all come from gui
		String[] tags = { "p[class*='org-about-company-module__founded']",
				"p[class*='org-about-company-module__headquarters']",
				"p[class*='org-about-company-module__company-staff-count-range']" };
		
		String email = "jrsstill@umich.edu";
		String password = "My Password";
		String csvFile = "database_companies_export.csv";
		String webpage="LinkedIn";
		String type="Companies";
		
		switch(webpage)
		{
			case "LinkedIn":
				wp=new LinkedIn();
				break;
			case "Crunchbase":
				//wp=new Crunchbase();
				break;
			default:
				System.out.println("Incorrect Type");
				System.exit(0);
		}
		
		wp.login(email, password);
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
			String link = wp.getLink(company);
			if (link == "NO_LINK")
			{
				System.out.println("No Link Found");
			} 
			else
			{
				System.out.println(link);
				wp.getData(link, company, tags, cmp);
			}
			System.out.println("Added\n\n");
		}
		
		m.print(cmp);
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
	
}
