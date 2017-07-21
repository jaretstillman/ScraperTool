package js.scrapertool.gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
 * Description: This class sets the UI for the "PickSite" window
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class PickSite extends Windows
{
	JComboBox<String> comboBox2;
	public PickSite()
	{
		
		String[] options = {"Crunchbase", "LinkedIn"};
		JComboBox<String> comboBox1 = new JComboBox<String>(options);
		comboBox1.setFont(new Font("Arial", Font.PLAIN, 50));
		((JLabel)comboBox1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		comboBox1.setBounds(300, 174, 600, 65);
		comboBox1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				panel.remove(comboBox2);
				comboBox2 = new JComboBox<String>(getOptions(comboBox1.getItemAt(comboBox1.getSelectedIndex())));
				((JLabel)comboBox2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
				comboBox2.setFont(new Font("Arial", Font.PLAIN, 50));
				comboBox2.setBounds(300, 352, 600, 57);
				panel.add(comboBox2);
			}
		});
		panel.add(comboBox1);
		
		JLabel lbl1 = new JLabel("Pick Site");
		lbl1.setFont(new Font("Arial", Font.PLAIN, 50));
		lbl1.setBounds(510, 92, 204, 80);
		panel.add(lbl1);
		
		//initialize combobox
		comboBox2 = new JComboBox<String>(getOptions(comboBox1.getItemAt(comboBox1.getSelectedIndex())));
		((JLabel)comboBox2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		comboBox2.setFont(new Font("Arial", Font.PLAIN, 50));
		comboBox2.setBounds(300, 352, 600, 65);
		panel.add(comboBox2);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Arial", Font.PLAIN, 50));
		lblType.setBounds(550, 289, 300, 55);
		panel.add(lblType);
		
		JButton btn1 = new JButton("Next >>>");
		
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				info.add(comboBox1.getItemAt(comboBox1.getSelectedIndex()));
				info.add(comboBox2.getItemAt(comboBox2.getSelectedIndex()));
				next = "Login";
			}
		});
		
		btn1.setFont(new Font("Arial", Font.PLAIN, 25));
		btn1.setBounds(965, 460, 180, 45);
		panel.add(btn1);
		
		addBackButton("Start");
	}
	
	private String[] getOptions(String site)
	{
		String[] options = new String[2];
		switch(site)
		{
			case "LinkedIn":
				options = new String[1];
				options[0]="Companies";
				break;
			case "Crunchbase":
				options = new String[3];
				options[0]="Companies";
				options[1]="People";
				options[2]="Funding Rounds";
				break;
		}
		return options;
	}
}
