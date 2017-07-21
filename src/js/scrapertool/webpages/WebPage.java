package js.scrapertool.webpages;

import java.util.ArrayList;

import js.scrapertool.exceptions.InvalidCredentialsException;


/*
 * Description: This class defines the global vars and abstract methods for each WebPage module (LinkedIn, Crunchbase, Facebook, etc.)
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public abstract class WebPage
{
	public abstract void login(String e, String p) throws InvalidCredentialsException;
	public abstract String getLink(String search, String type);
	public abstract void getData(String link, String site, String item, String type, String[] tags, ArrayList<ArrayList<String>> cmp);
	
	// Helper function for waiting for page to load
	// REQUIRES: numSecs>=0
	// EFFECTS: Sleeps thread for numSecs
	public void wait(double numSecs)
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
