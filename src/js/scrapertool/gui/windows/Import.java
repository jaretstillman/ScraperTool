package js.scrapertool.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import js.scrapertool.main.Main;

/*
 * Description: This class sets the UI for the "Import" window
 * 
 * Author: Jaret Stillman (jrsstill@umich.edu)
 */

public class Import extends Windows
{
	private JTextField textField;
	private int numItems=1;
	public Import(Main main, String type)
	{
		ArrayList<JTextField> items = new ArrayList<JTextField>();
		JButton btn2 = new JButton("IMPORT");
		String t = type.equals("People") ? "Person" : "Company";
		
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(info.isEmpty())
				{
					//Opting to use items list
					if(!textField.getText().trim().equals(""))
					{
						for(int i=0; i<numItems; i++)
						{
							info.add(items.get(i).getText());
						}
						main.setItems(info);
						next = "PickData";
					}
					else
					{
						JOptionPane.showMessageDialog(panel, "Please import a list of " + type.toLowerCase() +", or add " + t.toLowerCase() + " manually");
					}
				}
				
				//if file is imported
				else
				{
					try 
					{
						main.importCSV(info.get(0));
						next = "PickData";
					}
					catch (IOException e1) 
					{
						JOptionPane.showMessageDialog(panel, "Error importing file:\n"+info.get(0)+"\nPlease use a valid CSV file");
					}
				}
			}
		});
		
		btn2.setForeground(Color.RED);
		btn2.setFont(new Font("Arial", Font.PLAIN, 20));
		btn2.setBounds(471, 493, 205, 45);
		panel.add(btn2);
		
		JLabel lbl1 = new JLabel("OR");
		lbl1.setBounds(517, 180, 110, 120);
		lbl1.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setFont(new Font("Arial", Font.PLAIN, 50));
		panel.add(lbl1);
		
		JLabel lblEnterAList = new JLabel("ENTER A LIST OF "+ t.toUpperCase() + " NAMES");
		lblEnterAList.setBounds(652, 60, 530, 39);
		lblEnterAList.setFont(new Font("Arial", Font.PLAIN, 30));
		panel.add(lblEnterAList);
		
		JPanel p = new JPanel();
		p.setBackground(Color.WHITE);
		p.setPreferredSize(new Dimension(480,1200));
		p.setMinimumSize(new Dimension(480,1200));
		p.setMaximumSize(new Dimension(480,1200));
		p.setLayout(null);
		
		int x0 = 12;
		int y0 = 14;	
		int x1 = 176;
		
		JScrollPane sp = new JScrollPane(p,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBackground(Color.WHITE);
		sp.setBounds(679,125,480,311);		
		panel.add(sp);
		
		JLabel promptText = new JLabel(t + " Name: ");
		promptText.setBounds(x0, y0, 152, 30);
		promptText.setFont(new Font("Arial", Font.PLAIN, 20));
		promptText.setHorizontalAlignment(SwingConstants.CENTER);
		promptText.setHorizontalTextPosition(SwingConstants.CENTER);
		p.add(promptText);

		textField = new JTextField();
		items.add(textField);
		
		textField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				btn2.setForeground(Color.GREEN);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
				if(Import.this.textField.getText().equals(""))
				{
					btn2.setForeground(Color.RED);
				}
			}
		});
		
		textField.setBounds(x1, y0, 218, 30);
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		p.add(textField);
		textField.setColumns(10);
		textField.setFocusTraversalKeysEnabled(false);
		
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_TAB)
				{
					if(items.indexOf(textField)!=items.size()-1)
					{
						items.get(items.indexOf(textField)+1).grabFocus();
					}
				}
			}
		});
		
		JButton plus = new JButton("+");
		plus.setBounds(12, 35+y0, 50, 40);
		
		plus.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createLabelPrompt(type,x0,y0,x1,p,items,plus);	
			}
		});
		
		plus.setVerticalTextPosition(SwingConstants.TOP);
		plus.setVerticalAlignment(SwingConstants.TOP);
		plus.setFont(new Font("Arial", Font.PLAIN, 25));
		p.add(plus);
		p.setBounds(379,125,480,311);
		
		JButton btn1 = new JButton("SELECT A FILE");
		btn1.setBounds(100, 150, 400, 200);
		btn1.setFont(new Font("Arial", Font.PLAIN, 30));
		
		JLabel lbl4 = new JLabel("File selected: None");
		lbl4.setFont(new Font("Arial",Font.PLAIN,20));
		lbl4.setBounds(100,355,600,45);
		panel.add(lbl4);
		
		btn1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(panel,
						"Select a CSV File\n\nFormat:\n\n" + t + ", InfoA, InfoB... (headers)\n" + t +"_name1, info1a, info1b...\n" + t + ", info2a, info2b...\n\nNote: for best results, get rid of \nUnnecessary characters (i.e. inc., ltd., etc.)");
				
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "CSV Files", "csv");
				fc.setFileFilter(filter);
				int retVal = fc.showOpenDialog(panel);
				if (retVal == JFileChooser.APPROVE_OPTION)
				{
					info.clear();
					info.add(fc.getSelectedFile().getPath().replace('\\', '/'));
					String extension = FilenameUtils.getExtension(info.get(0));
					if(!extension.toLowerCase().equals("csv"))
					{
						JOptionPane.showMessageDialog(panel, "Please select only CSV file types");
					}
					else
					{
						btn2.setForeground(Color.GREEN);
						lbl4.setText("File selected: " + fc.getSelectedFile().getName());
						lbl4.setBounds(50,355,600,45);
					}
				}
			}
		});
		panel.add(btn1);
		
		addBackButton("PickSite");
	}
	
	private void createLabelPrompt(String type,int x0, int y0, int x1, JPanel p,ArrayList<JTextField> items, JButton plus)
	{
		numItems++;
		
		String t = type.equals("People") ? "Person" : "Company";
		JLabel promptText = new JLabel(t+" Name: ");
		promptText.setBounds(x0, 35*(numItems-1)+y0, 152, 30);
		promptText.setFont(new Font("Arial", Font.PLAIN, 20));
		promptText.setHorizontalAlignment(SwingConstants.CENTER);
		promptText.setHorizontalTextPosition(SwingConstants.CENTER);
		p.add(promptText);
		
		JTextField textField = new JTextField();
		textField.setBounds(x1, 35*(numItems-1)+y0, 218, 30);
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		p.add(textField);
		textField.setColumns(10);
		items.add(textField);
		textField.setFocusTraversalKeysEnabled(false);
		textField.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_TAB)
				{
					if(items.indexOf(textField)!=items.size()-1)
					{
					items.get(items.indexOf(textField)+1).grabFocus();
					}
				}
			}
		});
		plus.setBounds(12,35*numItems+y0,50,40);
		
	}
	
}
