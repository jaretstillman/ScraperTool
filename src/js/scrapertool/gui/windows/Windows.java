package js.scrapertool.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Windows
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

