package js.scrapertool.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Description: Acts as an abstract class for the different windows used by the GUI class
 * Provides a Jpanel for the GUI/Frame to add, as well as a next panel string (next) 
 * and a list of return strings (info) 
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public abstract class Windows
{
	protected JPanel panel;
	protected String next;
	protected ArrayList<String> info;
	
	public Windows()
	{
		//Initialize JPanel components when done setting stuff up
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setMinimumSize(new Dimension(1200, 600));
		panel.setPreferredSize(new Dimension(1200, 600));
		panel.setMaximumSize(new Dimension(1200, 600));
		info = new ArrayList<String>();
		next=null;
	}
	
	/*
	 * REQUIRES: n is a valid "next window" string
	 * EFFECTS: adds a "back" button to panel
	 */
	protected JButton addBackButton(String n)
	{
		JButton back = new JButton("<<< Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				info.add(null);
				info.add(null);
				next = n;
			}
		});
		back.setFont(new Font("Arial", Font.PLAIN, 25));
		back.setBounds(55, 460, 180, 45);
		panel.add(back);
		return back;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public String getNext()
	{
		return next;
	}
	
	public ArrayList<String> getInfo()
	{
		return info;
	}

}

