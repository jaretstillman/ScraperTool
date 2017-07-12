package js.scrapertool.gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import js.scrapertool.gui.windows.Login;
import js.scrapertool.gui.windows.PickData;
import js.scrapertool.gui.windows.PickSite;
import js.scrapertool.gui.windows.RunData;
import js.scrapertool.gui.windows.Start;
import js.scrapertool.gui.windows.Windows;
import js.scrapertool.main.Main;
import js.scrapertool.gui.windows.Import;

public class GUI extends Frame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	private String email;
	private String password;
	private String site;
	private String csvFile;
	private String type;
	private String[] tags;

	private Main main;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		GUI gui = new GUI();
	}
	
	public GUI()
	{
		main = new Main();
		
		setTitle("ScraperTool v2.0 (data provided by LinkedIn)");
		setResizable(false);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setBounds(100, 100, 1200, 600);
	    setLocation(this.dim.width / 2 - getSize().width / 2, this.dim.height / 2 - getSize().height / 2);
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        } );
        setVisible(true);
        site = "LinkedIn";
        type = "Companies";
		switchScreen("Login");
	}
	
	private void switchScreen(String screen)
	{	
		Windows currentWindow = null;
		removeAll();
		
		switch (screen)
		{
			case "Start":
				currentWindow = new Start();
				break;		
			case "PickSite":
				currentWindow = new PickSite();
				break;
			case "Login":
				currentWindow = new Login(site,main,this);
				break;
			case "Import":
				currentWindow = new Import(main);
				break;
			case "PickData":
				currentWindow = new PickData(site,type);
				break;
			case "Run":
				currentWindow = new RunData(main,site,type,tags);
				break;
				/*
			case "Display":
				currentWindow = new DisplayData();
				break;*/
				
			default:
				System.out.println("Not a valid screen");
				System.exit(0);
		}
		
		add(currentWindow.getPanel(),"Center");
		while(currentWindow.getNext()==null){
			currentWindow.getPanel().revalidate(); currentWindow.getPanel().repaint();}
		extractInfo(screen,currentWindow.getInfo());
		switchScreen(currentWindow.getNext());
	}
	
	private void extractInfo(String s, ArrayList<String> info)
	{
		switch (s)
		{
			case "Start":
				break;
			case "PickSite":
				site = info.get(0);
				type = info.get(1);
				break;
			case "Login":
				email = info.get(0);
				password = info.get(1);
				break;
				
			case "Import":
				/*if(info.get(0)!=null)
				{
					csvFile = info.get(0);
				}*/
				break;
				
			case "PickData":
				tags = info.toArray(new String[info.size()]);
				break;
			case "Run":
				break;
			case "Display":
				break;
		}
	}

}
