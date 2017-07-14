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
import js.scrapertool.gui.windows.Display;
import js.scrapertool.gui.windows.Import;

/*
* ScraperTool .main() Class
* Description: This class initiates the GUI and manages the entire app
* External Libraries/Resources used: Selenium/PhantomJS, WindowBuilder
* Data: public from LinkedIn
* 
* Author: Jaret Stillman (jrsstill@umich.edu)
* Version: 2.0
* Date: 7/14/17
*/
public class GUI extends Frame
{
	private static final long serialVersionUID = 1L;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	private String site;
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
        addWindowListener( new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent we) 
            {
                System.exit(0);
            }
        } );
        setVisible(true);
		switchScreen("Start");
	}
	
	/*
	 * REQUIRES: screen is a valid "next window"
	 * MODIFIES: this
	 * EFFECTS: Handles switches between windows, waits until windows are ready to be updated
	 */
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
				currentWindow = new Import(main,type);
				break;
			case "PickData":
				currentWindow = new PickData(site,type);
				break;
			case "Run":
				currentWindow = new RunData(main,site,type,tags);
				break;
			case "Display":
				currentWindow = new Display(main,tags,type);
				break; 
				
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
	
	/*
	 * REQUIRES: s is a valid window
	 * MODIFIES: site, type, tags
	 * EFFECTS: sets global vars based on returns from window programs finishing
	 */
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
			case "PickData":
				tags = info.toArray(new String[info.size()]);
				break;
		}
	}

}
