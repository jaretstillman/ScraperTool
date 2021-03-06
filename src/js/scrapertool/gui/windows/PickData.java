package js.scrapertool.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/*
 * Description: This class sets the UI for the "PickData" window
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class PickData extends Windows
{

	public PickData(String site, String type)
	{
		DefaultListModel<String> tagOptions = new DefaultListModel<String>();
		getTagOptions(site, type,tagOptions);
		
		JLabel lbl1 = new JLabel("Pick the Categories You Want in the Final Report");
		lbl1.setFont(new Font("Arial", Font.PLAIN,30));
		lbl1.setBounds(250, 75, 700, 30);
		panel.add(lbl1);
		
		JLabel lbl2 = new JLabel ("Hold CTRL to Select More than 1 Category");
		lbl2.setFont(new Font("Arial", Font.PLAIN,18));
		lbl2.setForeground(Color.BLUE);
		lbl2.setBounds(425, 105, 550, 30);
		panel.add(lbl2);
		
		JList<String> list = new JList<String>(tagOptions);
		list.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		list.setFont(new Font("Arial", Font.PLAIN, 20));
		list.setBounds(300, 150,600, 300);
		JScrollPane p = new JScrollPane(list);
		p.setBounds(300,150,600,300);
		panel.add(p);	
		
		JButton btn1 = new JButton("RUN");
		btn1.setFont(new Font("Arial", Font.PLAIN, 25));
		btn1.setBounds(500,480,200,70);
		btn1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				List<String> l = list.getSelectedValuesList();
				if(l.isEmpty())
				{
					JOptionPane.showMessageDialog(panel, "Please select at least one category");
				}
				
				else
				{
					info = new ArrayList<String>(l);
					next = "Run";
				}
			}		
		});
		panel.add(btn1);
		
		addBackButton("Import");
	}

	//Helper function for putting options into the second comboBox
	private void getTagOptions(String site, String type, DefaultListModel<String> tagOptions)
	{
		switch(site)
		{
			case "LinkedIn":
				switch(type)
				{
					case "Companies":
						tagOptions.addElement("Website");
						tagOptions.addElement("Headquarters");
						tagOptions.addElement("Year Founded");
						tagOptions.addElement("Company Type");
						tagOptions.addElement("Employee Range");
						tagOptions.addElement("Specialties");
						tagOptions.addElement("Short Overview");
						break;
					case "People":
						tagOptions.addElement("Current Title");
						tagOptions.addElement("Company");
						tagOptions.addElement("Number of Years at Job");
						tagOptions.addElement("Years Range at Job");
						tagOptions.addElement("Job Location");
						tagOptions.addElement("Profile Picture Link");
						break;
				}
				break;
			case "Crunchbase":
				switch(type)
				{
					case "Companies":
						tagOptions.addElement("Company Logo Link");
						tagOptions.addElement("Number of Investors");
						tagOptions.addElement("Investor Names (First 15 Alphabetically)");
						tagOptions.addElement("Total Equity Funding");
						tagOptions.addElement("Founder(s)");
						tagOptions.addElement("Headquarters");
						tagOptions.addElement("Short Description");
						tagOptions.addElement("Long Description");
						tagOptions.addElement("Website");
						tagOptions.addElement("Twitter");
						tagOptions.addElement("LinkedIn");
						tagOptions.addElement("Facebook");
						tagOptions.addElement("Date Founded");
						tagOptions.addElement("Employee Number");
						tagOptions.addElement("Contact Email");
						tagOptions.addElement("Contact Phone Number");
						break;
					case "People":
						tagOptions.addElement("Primary Title");
						tagOptions.addElement("Company");
						tagOptions.addElement("Number of Investments");
						tagOptions.addElement("Number of Companies Invested In");
						tagOptions.addElement("Companies Invested In (Most Recent 40)");
						tagOptions.addElement("Personal Website");
						tagOptions.addElement("Birthdate");
						tagOptions.addElement("Location");
						tagOptions.addElement("Twitter");
						tagOptions.addElement("LinkedIn");
						tagOptions.addElement("Facebook");
						tagOptions.addElement("Details");
						break;
					
					case "Funding Rounds":
						tagOptions.addElement("Funding Round Name");
						tagOptions.addElement("Funding Round Link");
						tagOptions.addElement("Funding Round Amount");
						tagOptions.addElement("Funding Round Name");
						tagOptions.addElement("Funding Round Date");
						tagOptions.addElement("Funding Round Lead");
						tagOptions.addElement("Funding Round Valuation");
						tagOptions.addElement("Funding Round Investor Number");
						break;
				}
				break;
		}
					
	}
}
