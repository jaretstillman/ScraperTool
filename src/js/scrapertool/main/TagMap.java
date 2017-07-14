package js.scrapertool.main;

import java.util.HashMap;

/*
 * Description: This class acts as a map between GUI tag options and actual HTML tags
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class TagMap
{	
	HashMap<String,String> map;
	String site;
	String type;
	
	public TagMap(String site, String type)
	{
	
		
		this.site = site;
		this.type = type; 
		
		switch(site)
		{
			case "LinkedIn":
				switch(type)
				{
					case "Companies":
						map.put("Website", "a[class*='org-about-us-company-module__website']");
						map.put("Headquarters", "p[class*='org-about-company-module__headquarters']");
						map.put("Year Founded", "p[class*='org-about-company-module__founded']");
						map.put("Company Type", "p[class*='org-about-company-module__company-type']");
						map.put("Employee Range", "p[class*='org-about-company-module__company-staff-count-range']");
						map.put("Specialties", "p[class*='org-about-company-module__specialities']");
						map.put("Short Overview", "p[class*='org-about-us-organization-description__text']");
					case "People":
						//inside div[class*='pv-entity__summary-info']
						map.put("Current Title", "div.pv-entity__summary-info > h3");
						map.put("Company", "span[class*='pv-entity__secondary-title']");
						map.put("Years Range at Job", "h4[class*='pv-entity__date-range'] > span:last-of-type");
						map.put("Number of Years at Job", "div[class*='pv-entity__summary-info'] > span[class*='pv-entity__bullet-item']");
						map.put("Job Location", "h3[class*='pv-entity__location'] > span:last-of-type");
						map.put("Profile Picture Link", "img.pv-top-card-section__image");
					default:
						System.out.println("No such type found");
						System.exit(0);
				}
			case "Crunchbase":
				switch(type)
				{
					case "Companies":
						break;
					case "People":
						break;
					default:
						System.out.println("No such type found");
						System.exit(0);
				}
			default:
				System.out.println("No such site found");
				System.exit(0);
		}

	}
	
	/*
	 * REQUIRES: Tag is a tag associated with the site and type
	 * EFFECTS: Returns the HTML tag associated with the GUI tag
	 */
	
	public String convert(String tag)
	{
		String ct = map.get(tag);
		if(tag==null)
		{
			System.out.println("No such tag found");
			System.exit(0);
		}

		return ct;
	}

}
