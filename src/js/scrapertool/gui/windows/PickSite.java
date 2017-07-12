package js.scrapertool.gui.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PickSite extends Windows
{
	/**
	 * Create the application.
	 */
	public PickSite()
	{
		
		String[] options = {"LinkedIn", "Crunchbase"};
		JComboBox<String> comboBox1 = new JComboBox<String>(options);
		comboBox1.setFont(new Font("Arial", Font.PLAIN, 50));
		((JLabel)comboBox1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		comboBox1.setBounds(300, 174, 600, 57);
		panel.add(comboBox1);
		
		JLabel lbl1 = new JLabel("Pick Site");
		lbl1.setFont(new Font("Arial", Font.PLAIN, 50));
		lbl1.setBounds(487, 92, 204, 80);
		panel.add(lbl1);
		
		String[] options2 = {"Companies","People"};
		JComboBox<String> comboBox2 = new JComboBox<String>(options2);
		((JLabel)comboBox2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		comboBox2.setFont(new Font("Arial", Font.PLAIN, 50));
		comboBox2.setBounds(300, 352, 600, 57);
		panel.add(comboBox2);
		
		JLabel lblPeopleOrCompanies = new JLabel("People or Companies?");
		lblPeopleOrCompanies.setFont(new Font("Arial", Font.PLAIN, 50));
		lblPeopleOrCompanies.setBounds(350, 289, 520, 45);
		panel.add(lblPeopleOrCompanies);
		
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
	}
}
