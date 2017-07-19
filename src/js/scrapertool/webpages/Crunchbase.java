package js.scrapertool.webpages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import js.scrapertool.exceptions.InvalidCredentialsException;
import js.scrapertool.main.TagMap;

/*
 * Description: This class defines custom methods for the Crunchbase scraper module
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */


public class Crunchbase extends WebPage
{
	private int numRounds;
	
	//keeps track of how many times each company name should be entered for funding rounds
	private ArrayList<String> rounds;
	
	@Override
	public void login(String e, String p) throws InvalidCredentialsException {}

	/*
	 * REQUIRES: search and type are valid searches and type
	 * EFFECTS: returns a link that may or may not be valid based on the name of the company/person and type. Invalid link handling is done in this.getData().
	 */
	@Override
	public String getLink(String search, String type)
	{
		//Crunchbase won't let me connect directly, so I'm using the cached version of the site
		String stub = "http://webcache.googleusercontent.com/search?q=cache:";
		String s = type.equals("People") ? "person" : "organization";
		String searchMod=search.replace('.','-').replace(' ','-').replace(',', '-');
		return stub + "crunchbase.com/"+ s + "/" + searchMod;
	}

	
	/*
	 * REQUIRES: link is valid, tags are valid HTML tags, site and type are valid, cmp is initialized
	 * MODIFIES: cmp
	 * EFFECTS:  This method gets the data requested by the tags array and adds it to cmp. Handles invalid links instead of main.getData()
	 */
	@Override
	public void getData(String link, String site, String item, String type, String[] tags, ArrayList<ArrayList<String>> cmp)
	{
		rounds = new ArrayList<String>();
		
		//Convert tags to HTML with a TagMap
		TagMap tm = new TagMap(site,type);
		String[] convertedTags = new String[tags.length];
		for(int i=0; i<tags.length; i++)
		{
			convertedTags[i] = tm.convert(tags[i]);
		}
		
		Document doc;
		try 
		{
			  URL url = new URL(link);
			  HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			  uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
			  uc.connect();

			    String line = null;
			    StringBuffer tmp = new StringBuffer();
			    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			    while ((line = in.readLine()) != null) {
			      tmp.append(line);
			    }

			    doc = Jsoup.parse(String.valueOf(tmp));
		}
		catch (Exception e) //invalid link
		{
			System.out.println(item + " NO PAGE FOUND");
			for (int i = 0; i < tags.length; i++)
			{
				cmp.get(i).add("-");
			}
			return;
		}
		
		Elements elts;
		for(int i = 0; i<tags.length; i++)
		{
			elts = doc.select(convertedTags[i]);
			if(elts.isEmpty())
			{
				System.out.println(doc.html());
				cmp.get(i).add("-");
				System.out.println(item + " " + tags[i] + " NOT FOUND");
			}
			else
			{
				numRounds=0;
				add(i,type,tags,cmp,elts);
			}
		}
		if(type.equals("Funding Rounds"))
		{
			for(int i=0; i<numRounds; i++)
			{
				//repeat name for number of rounds, so that this can be copied into a spreadsheet and the columns will line up
				rounds.add(item);
			}
		}
	}

	/*
	 * REQUIRES: elts is not null, tag is a vlid tag, type is a valid type, 0<=i<tags.length
	 * MODIFIES: cmp
	 * EFFECTS: This is a function for managing custom adds for Crunchbase (i.e. lists of items, non-text attributes, formatting, etc.)
	 */
	private void add(int i, String type, String[] tags, ArrayList<ArrayList<String>> cmp, Elements elts)
	{
		if(tags[i].equals("Twitter")  || tags[i].equals("Facebook") || tags[i].equals("LinkedIn"))
		{
			cmp.get(i).add(elts.get(0).attr("href"));
		}
		else if (tags[i].equals("Founder(s)") || tags[i].equals("Companies Invested In (Most Recent 40)") || tags[i].equals("Investor Names (First 15 Alphabetically)"))
		{
			String tmp = "";
			for(Element e : elts)
			{
				tmp += e.text() + ", ";
			}
			cmp.get(i).add(tmp);
		}
		else if (tags[i].equals("Company Logo Link"))
		{
			cmp.get(i).add(elts.get(0).attr("src"));
		}
		else if (tags[i].equals("Primary Title"))
		{
			cmp.get(i).add(elts.get(0).text().substring(0,elts.get(0).text().indexOf('@')));
		}
		else if(tags[i].equals("Number of Companies Invested In"))
		{
			cmp.get(i).add(elts.get(0).text().substring(elts.get(0).text().indexOf("investments")+20,elts.get(0).text().length()));		
		}
		else if (type.equals("Funding Rounds")) 
		{
			for (Element e : elts)
			{
				numRounds++;
				if (tags[i].equals("Funding Round Amount"))
				{
					cmp.get(i).add(e.text().substring(0, e.text().indexOf('/')));
				}
				else if (tags[i].equals("Funding Round Link")) 
				{
					cmp.get(i).add(e.attr("href"));
				}
				else 
				{
					if(e.text().equals("—")) //weird null character that shows up for some reason
					{
						cmp.get(i).add("-");
					}
					else
					{
						cmp.get(i).add(e.text());
					}
				}
			}
		}
		else
		{
			cmp.get(i).add(elts.get(0).text());
		}
	}
	
	public ArrayList<String> getRoundsList()
	{
		return rounds;
	}
	
}
