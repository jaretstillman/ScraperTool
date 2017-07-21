package js.scrapertool.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import js.scrapertool.exceptions.InvalidCredentialsException;
import js.scrapertool.main.Main;

/*
 * Description: This class sets the UI for the "Login" window
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class Login extends Windows
{
	private JTextField textField;
	private JPasswordField textField2;
	
	public Login(String site, Main main)
	{	
		if(site.equals("Crunchbase"))
		{
			try 
			{
				main.login(site, "", "");
			}
			catch (InvalidCredentialsException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			next = "Import";
		}
		else
		{
			init(site,main);
		}
	}
	
	private void init(String site, Main main)
	{
		JLabel lbl1 = new JLabel("Log In to " + site);
		lbl1.setFont(new Font("Arial", Font.PLAIN, 40));
		lbl1.setBounds(444, 90, 339, 55);
		panel.add(lbl1);
		
		JLabel lbl2 = new JLabel("Email: ");
		lbl2.setFont(new Font("Arial", Font.PLAIN, 30));
		lbl2.setBounds(270, 215, 100, 30);
		panel.add(lbl2);
		
		JLabel lbl3 = new JLabel("Password: ");
		lbl3.setFont(new Font("Arial", Font.PLAIN, 30));
		lbl3.setBounds(215, 300, 150, 30);
		panel.add(lbl3);
		
		JLabel lbl4 = new JLabel("");
		lbl4.setFont(new Font("Arial",Font.PLAIN,20));
		lbl4.setBounds(500,475,300,67);
		panel.add(lbl4);
		
		textField = new JTextField();
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setText("example@example.com");
		
		textField.addFocusListener(new FocusAdapter()
		{
			boolean modified=false;
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if(!modified)
				{
					textField.setText("");
					textField.setForeground(Color.BLACK);
					modified=true;
				}
			}
		});
		
		textField.setFocusTraversalKeysEnabled(false);
		
		textField.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_TAB)
				{
					textField2.grabFocus();
				}
			}
		});
		
		textField.setFont(new Font("Arial", Font.PLAIN, 30));
		textField.setColumns(10);
		textField.setBounds(375, 200, 450, 65);
		panel.add(textField);
		
		textField2 = new JPasswordField();
		textField2.setForeground(Color.LIGHT_GRAY);
		textField2.setText("password");
		
		textField2.addFocusListener(new FocusAdapter()
		{
			boolean modified=false;
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if(!modified)
				{
					textField2.setText("");
					textField2.setForeground(Color.BLACK);
					modified=true;
				}
			}
		});
		
		textField2.setFont(new Font("Arial", Font.PLAIN, 30));
		textField2.setColumns(10);
		textField2.setBounds(375, 285, 450, 65);
		panel.add(textField2);
		
		JButton btn1 = new JButton("Submit");
		btn1.setFont(new Font("Arial", Font.PLAIN, 20));
		btn1.setBounds(500, 380, 200, 67);
		
		
		SwingWorker<Boolean, String> worker = new SwingWorker<Boolean,String>()
		{
			@Override
			protected Boolean doInBackground() throws Exception
			{
				try 
				{
					publish("Logging in to " + site+ "...");
					main.login(site, info.get(0), info.get(1));
				}
				catch (InvalidCredentialsException e1) 
				{
					return false;
				}
				return true;
			}
			
			@Override
			protected void process(List<String> stuff)
			{
				lbl4.setText(stuff.get(0));
				lbl4.setForeground(Color.RED);
			}
			
			@Override
			protected void done()
			{
				try 
				{
					if(get())
					{
						lbl4.setText("Log-In for " + info.get(0) + "Successful!");
						lbl4.setForeground(Color.GREEN);
						//JOptionPane.showMessageDialog(panel, "Log in for user " + info.get(0) + " successful!");
						next = "Import";
					}
					else
					{
						JOptionPane.showMessageDialog(panel, "Invalid Credentials");
						next = "Login";
					}
				}
				catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
				}
			}
			
		};
		
		btn1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				
				info.add(textField.getText());
				info.add(String.valueOf(textField2.getPassword()));
				worker.execute();
			}
		});
		
		panel.add(btn1);
		
		textField2.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					btn1.doClick();
				}
			}
		});
		
		addBackButton("PickSite").addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				//still needs work
				worker.cancel(true);
			}
			
		});
	}

}
