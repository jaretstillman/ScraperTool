package js.scrapertool.gui.windows;

import java.awt.Color;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import js.scrapertool.main.Main;
import js.scrapertool.gui.windows.swingworkers.RunDataSwingWorker;

public class RunData extends Windows
{
	public RunData(Main main, String site, String type, String[] tags)
	{
		JLabel lbl1 = new JLabel("Running...");
		lbl1.setForeground(Color.BLUE);
		lbl1.setFont(new Font("Arial", Font.PLAIN, 40));
		lbl1.setBounds(510, 35, 180, 50);
		panel.add(lbl1);
		
		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(SystemColor.menu);
		textArea.setBounds(130, 100, 940, 350);
		panel.add(textArea);
		
		JButton btn1 = new JButton("DISPLAY RESULTS");
		btn1.setForeground(Color.RED);
		btn1.setFont(new Font("Arial", Font.PLAIN, 20));
		btn1.setBounds(450, 493, 300, 45);
		panel.add(btn1);
		
		RunDataSwingWorker<Boolean, String> worker = new RunDataSwingWorker<Boolean,String>()
		{
			@Override
			protected Boolean doInBackground()
			{
				main.createCategoriesList(tags);
				main.getData(site, type, tags, this);
				return true;
			}
			@Override
			protected void process(List<String> stuff)
			{
				textArea.append(stuff.get(stuff.size()-1));
			}
			
			@Override
			protected void done()
			{
				btn1.setForeground(Color.GREEN);
				btn1.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						next = "Display";
					}
				});
			}
		};
		
		worker.execute();
	}
}
