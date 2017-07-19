package js.scrapertool.gui.windows;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import js.scrapertool.main.Main;

/*
 * Description: This class sets the UI for the "Display" window
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class Display extends Windows
{
	public Display(Main main, String[] tags, String type)
	{
		TextArea textArea = new TextArea();
		textArea.setText(main.print(tags,type));
		textArea.setBounds(47, 58, 1047, 360);
		textArea.setFont(new Font("Arial",Font.PLAIN,18));
		panel.add(textArea);
		
		JButton btn1 = new JButton("RESET");
		btn1.setFont(new Font("Arial", Font.PLAIN, 30));
		btn1.setBounds(500, 458, 200, 60);
		btn1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				next="PickData";
			}
		});
		panel.add(btn1);
	}

}
