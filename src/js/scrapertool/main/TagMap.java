package js.scrapertool.main;

import java.util.HashMap;

public class TagMap
{
	private HashMap<String,String> linkedInCompanies;
	private HashMap<String,String> linkedInPeople;
	private HashMap<String,String> crunchBaseCompanies;
	private HashMap<String,String> crunchBasePeople;
	
	public TagMap()
	{
		linkedInCompanies = new HashMap<String,String>();
		linkedInCompanies.put("Website", "a[class*='org-about-us-company-module__website']");
		linkedInCompanies.put("Headquarters", "p[class*='org-about-company-module__headquarters']");
		linkedInCompanies.put("Year Founded", "p[class*='org-about-company-module__founded']");
		linkedInCompanies.put("Company Type", "p[class*='org-about-company-module__company-type']");
		linkedInCompanies.put("Employee Range", "p[class*='org-about-company-module__company-staff-count-range']");
		linkedInCompanies.put("Specialties", "p[class*='org-about-company-module__specialties']");
		linkedInCompanies.put("Short Overview", "p[class*='org-about-organization-description__text']");
		
		linkedInPeople = new HashMap<String,String>();
		//inside div[class*='pv-entity__summary-info']
		linkedInPeople.put("Current Title", "div[class*='pv-entity__summary-info'] > h3:first-of-type");
		linkedInPeople.put("Company", "span[class*='pv-entity__secondary-title']");
		linkedInPeople.put("Years Range at Job", "h4[class*='pv-entity__date-range'] > span:last-of-type");
		linkedInPeople.put("Number of Years at Job", "div[class*='pv-entity__summary-info'] > span[class*='pv-entity__bullet-item']");
		linkedInPeople.put("Job Location", "h3[class*='pv-entity__location'] > span:last-of-type");
		linkedInPeople.put("Profile Picture Link", "");
	}
	
	public String convert(String tag, String site, String type)
	{
		switch(site)
		{
			case "LinkedIn":
				switch(type)
				{
					case "Companies":
						return linkedInCompanies.get(tag);
					case "People":
						return linkedInPeople.get(tag);
				}
			case "Crunchbase":
				switch(type)
				{
					case "Companies":
						return crunchBaseCompanies.get(tag);
					case "People":
						return crunchBasePeople.get(tag);
				}
		}
		System.out.println("No such tag found");
		System.exit(0);
		return "";
	}

}
