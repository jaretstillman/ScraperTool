package js.scrapertool.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import js.scrapertool.exceptions.InvalidCredentialsException;
import js.scrapertool.gui.windows.swingworkers.RunDataSwingWorker;
import js.scrapertool.webpages.Crunchbase;
import js.scrapertool.webpages.LinkedIn;
import js.scrapertool.webpages.WebPage;

/*
 * Description: This class provides the background data crunching for the GUI
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class Main
{
	WebPage wp;
	ArrayList<String> items;
	ArrayList<ArrayList<String>> cmp;
	
	
	/*
	 * REQUIRES: email, password are valid emails and passwords for wp
	 * MODIFIES: wp
	 * EFFECTS: Initiales wp to correct site class, adds login cookies
	 */
	public void login(String site, String email, String password) throws InvalidCredentialsException
	{
		switch(site)
		{
			case "LinkedIn":
				wp = new LinkedIn();
				break;
			case "Crunchbase":
				wp = new Crunchbase();
				break;
			default:
				System.out.println("Non-existent site");
				System.exit(0);
		}
		wp.login(email, password);
	}

	/*
	 * MODIFIES: cmp
	 * EFFECST: initializes cmp
	 */
	public void createCategoriesList(String[] tags)
	{
		// This is an String ArrayList for each category (contained in tags[])
		// i.e. cmp[0] will be for all the dates founded, cmp[1] will be for all
		// the headquarters, cmp[2] will be for all the employeeNumbers, etc.
		
		cmp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < tags.length; i++) 
		{
			cmp.add(new ArrayList<String>());
		}
	}

	
	/*
	 * REQUIRES: site and type are each valid, tags are valid GUI tags
	 * MODIFIES: sw, wp, cmp
	 * EFFECTS: Gets data from site, adds it to cmp, publishes some of it to sw for processing
	 */
	public void getData(String site, String type, String[] tags, RunDataSwingWorker<Boolean, String> sw)
	{
		//Allow sw.process() to get through publish queue
		wp.wait(.5);
		//Get link for item, store data in wp, publish to sw 
		for (String item : items) 
		{
			sw.pub(item + "\n");
			String link = wp.getLink(item,type);
			if (link.equals("NO_LINK")) 
			{
				String t = type=="Companies" ? "COMPANY" : "PERSON";
				System.out.println(item + " WRONG " + t + " FROM LINK");
				for(int i=0; i<tags.length; i++)
				{
					cmp.get(i).add("-");
				}
			}
			else 
			{
				sw.pub(link+"\n");
				wp.getData(link, site, item, type, tags, cmp);
			}
			sw.pub("Added\n\n");
			
		}
	}

	/*
	 * EFFECTS: Sets this.items to items
	 */
	public void setItems(ArrayList<String> items)
	{
		this.items=items;
	}

	/*
	 * REQUIRES: fileName is a valid csvFile of correct format
	 * MODIFIES: items
	 * EFFECTS: Sets items to the first column of the csv file, skipping the first row
	 */
	public void importCSV(String fileName) throws FileNotFoundException, IOException
	{
		items = new ArrayList<String>();
		String line = "";
		String csvSplitBy = ",";
		int i = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
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
			items.add(row[0]);
		}
		
		br.close();
	}
	
	/*
	 * EFFECTS: constructs string with contents of cmp, formatted for easy copy into CSV/Excel/Google Sheets File
	 */
	public String print(String[] tags, String type)
	{
		String result = "";
		result+= type + " Names: \n\n";
		for(String item : items)
		{
			//Need to print name of company multiple time for funding rounds
			if(type.equals("Funding Rounds"))
			{
				Crunchbase c = (Crunchbase) wp;
				for(String s: c.getRoundsList())
				{
					result += s + "\n";
				}
			}
			else
			{
				result += item + "\n";
			}
		}
		result+="\n";
		for(int i=0; i<tags.length; i++)
		{
			result+= tags[i] + ":\n\n";
			for(String s : cmp.get(i))
			{
				result+= s+ "\n";
			}
			result+="\n";
		}
		return result;
	}

}