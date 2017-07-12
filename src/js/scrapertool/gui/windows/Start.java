package js.scrapertool.gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Start extends Windows
{

	/**
	 * Initialize the contents of the panel.
	 */
	public Start()
	{
		JLabel lbl1 = new JLabel("ScraperTool");
		lbl1.setFont(new Font("Arial", Font.BOLD, 95));
		lbl1.setBounds(322, 100, 556, 150);
		panel.add(lbl1);
		
		JLabel lbl2 = new JLabel("Developed by Jaret Stillman");
		lbl2.setFont(new Font("Arial", Font.ITALIC, 40));
		lbl2.setBounds(350, 229, 500, 50);
		panel.add(lbl2);
		
		JLabel lbl3 = new JLabel("Version 2.0");
		lbl3.setFont(new Font("Arial", Font.PLAIN, 21));
		lbl3.setBounds(1070, 535, 119, 26);
		panel.add(lbl3);
		
		JLabel lbl4 = new JLabel("All Data from LinkedIn");
		lbl4.setFont(new Font("Arial", Font.PLAIN, 21));
		lbl4.setBounds(974, 522, 208, 16);
		panel.add(lbl4);
		
		JButton btn1 = new JButton("PRESS TO START");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				next="PickSite";
			}
		});
		btn1.setFont(new Font("Arial", Font.BOLD, 30));
		btn1.setBounds(450,350,300,100);
		panel.add(btn1);
	}

}
