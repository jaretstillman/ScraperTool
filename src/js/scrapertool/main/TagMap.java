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
		map = new HashMap<String,String>();
		
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
						break;
					case "People":
						//inside div[class*='pv-entity__summary-info']
						map.put("Current Title", "title");
						map.put("Company", "companyName");
						map.put("Years Range at Job", "");
						map.put("Number of Years at Job", "");
						map.put("Job Location", "locationName");
						map.put("Profile Picture Link", "");
						break;
					default:
						System.out.println("No such type found");
						System.exit(0);
				}
				break;
				
			case "Crunchbase":
				switch(type)
				{
					case "Companies":
						map.put("Company Logo Link", "img.entity-info-card-primary-image");
						map.put("Number of Investors","span:contains(from) + a");
						map.put("Total Equity Funding","span.funding_amount");
						map.put("Founder(s)","dt:contains(Founders) + dd > a[class*='follow_card'][data-type*='person']");
						map.put("Headquarters","dt:contains(Headquarters) + dd > a");
						map.put("Short Description","dt:contains(Description) + dd");
						map.put("Long Description","div.description-ellipsis > p");
						map.put("Website","dt:contains(Website) + dd > a");
						map.put("Twitter","a[data-icons*='twitter']");
						map.put("LinkedIn","a[data-icons*='linkedin']");
						map.put("Facebook","a[data-icons*='facebook']");
						map.put("Date Founded","dt:contains(Founded) + dd");
						map.put("Employee Number","dt:contains(Employees) + dd");
						map.put("Contact Email","span.email > a");
						map.put("Contact Phone Number", "span.phone_number");
						map.put("Investor Names (First 15 Alphabetically)", "table[class*='table section-list investors'] > tbody > tr > td > a.follow_card");
						break;
						
					case "People":
						map.put("Primary Title","dt:contains(Primary Role) + dd");
						map.put("Company", "dt:contains(Primary Role) + dd > a");
						map.put("Number of Investments","dt:contains(Investments) + dd > a");
						map.put("Number of Companies Invested In","dt:contains(Investments) + dd");
						map.put("Companies Invested In (Most Recent 40)", "table[class*='section-list table investors'] > tbody > tr > td > a.follow_card");
						map.put("Personal Website","dt:contains(Website) + dd > a");
						map.put("Birthdate", "dt:contains(Born) + dd");
						map.put("Location", "dt:contains(Location) + dd");
						map.put("Twitter","a[data-icons*='twitter']");
						map.put("LinkedIn","a[data-icons*='linkedin']");
						map.put("Facebook","a[data-icons*='facebook']");
						map.put("Details","div.description-ellipsis > p");
						break; 
					case "Funding Rounds":
						map.put("Funding Round Name","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(2)>a");
						map.put("Funding Round Link","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(2)>a");
						map.put("Funding Round Amount","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(2)");
						map.put("Funding Round Date","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(1)");
						map.put("Funding Round Lead","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(4)");
						map.put("Funding Round Valuation","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(3)");
						map.put("Funding Round Investor Number","div[class*='base info-tab funding_rounds']>div>table>tbody>tr>td:nth-child(5)>a");
						break;
				}
				break;
				
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

		if(ct.equals(null) || ct.equals(""))
		{
			System.out.println("No such tag found");
			System.exit(0);
		}

		return ct;
	}

}
