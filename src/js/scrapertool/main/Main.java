package js.scrapertool.main;

import java.io.IOException;
import java.util.ArrayList;

import js.scrapertool.exceptions.InvalidCredentialsException;
import js.scrapertool.gui.windows.swingworkers.RunDataSwingWorker;
import js.scrapertool.webpages.LinkedIn;
import js.scrapertool.webpages.WebPage;

public class Main
{
	WebPage wp;
	ArrayList<String> companies;
	ArrayList<ArrayList<String>> cmp;
	public Main()
	{
	}
	
	
	public void login(String site, String email, String password) throws InvalidCredentialsException
	{
		switch(site)
		{
			case "LinkedIn":
				wp = new LinkedIn();
				break;
			case "Crunchbase":
				//wp = new Crunchbase();
				break;
			default:
				System.out.println("Non-existent site");
				System.exit(0);
		}
	}

	public void createCategoriesList(String[] tags)
	{}

	public void getData(String site, String type, String[] tags, RunDataSwingWorker<Boolean, String> runDataSwingWorker)
	{}

	public void setCompanies(ArrayList<String> info)
	{}

	public void importCSV(String string) throws IOException
	{}
}