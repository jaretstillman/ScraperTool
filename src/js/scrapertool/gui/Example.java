package js.scrapertool.gui;
/*import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JScrollPane;

public class GUI
  extends Frame
  implements WindowListener, ActionListener
{
  private static final long serialVersionUID = 1L;
  Panel panel;
  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  private final int LENGTH = 1300;
  private final int WIDTH = 650;
  private int numPlayers = 1;
  private Engine engine;
  private ArrayList<GUI.StatLine> statline;
  
  class StatLine
  {
    String first;
    String last;
    String year1;
    String year2;
    
    StatLine(String f, String l, String y1, String y2)
    {
      this.first = f;
      this.last = l;
      this.year1 = y1;
      this.year2 = y2;
    }
  }
  
  public GUI()
  {
    addWindowListener(this);
    setResizable(false);
    setSize(new Dimension(1300, 650));
    setLocation(this.dim.width / 2 - getSize().width / 2, this.dim.height / 2 - getSize().height / 2);
    setTitle("StatGrabber (Statistics provided by Pro Football Reference)");
    setLayout(new BorderLayout());
    this.panel = new Panel();
    this.engine = new Engine();
    this.statline = new ArrayList();
    add(this.panel, "Center");
    init();
  }
  
  public void init()
  {
    this.panel.removeAll();
    this.panel.setLayout(new FlowLayout());
    for (int i = 0; i < this.numPlayers; i++)
    {
      Label namesLabel = new Label("Enter the name of a player: ");
      namesLabel.setFont(new Font("Arial", 1, 25));
      
      TextField firstName = new TextField(10);
      firstName = createTextPrompt(firstName, "First");
      
      TextField lastName = new TextField(10);
      lastName = createTextPrompt(lastName, "Last");
      
      Choice year1 = new Choice();
      year1 = createYearPrompt(year1);
      year1.setFont(new Font("Arial", 0, 25));
      
      Choice year2 = new Choice();
      year2 = createYearPrompt(year2);
      year2.setFont(new Font("Arial", 0, 25));
      
      Button b = new Button("X");
      setContainerSize(b, 30, 30);
      b.setFont(new Font("Arial", 1, 20));
      b.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          GUI.this.numPlayers -= 1;
          GUI.this.init();
        }
      });
      Label dash = new Label("-");
      dash.setFont(new Font("Arial", 0, 30));
      
      this.panel.add(namesLabel);
      this.panel.add(firstName);
      this.panel.add(lastName);
      this.panel.add(Box.createRigidArea(new Dimension(80, 40)));
      this.panel.add(year1);
      this.panel.add(dash);
      this.panel.add(year2);
      this.panel.add(b);
    }
    Button addPlayer = new Button("+");
    addPlayer.setFont(new Font("Arial", 0, 30));
    addPlayer.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GUI.this.numPlayers += 1;
        GUI.this.init();
      }
    });
    setContainerSize(addPlayer, 250, 40);
    
    this.panel.add(addPlayer, Float.valueOf(0.0F));
    this.panel.add(Box.createRigidArea(new Dimension(400, 40)));
    this.panel.add(Box.createRigidArea(new Dimension(1000, 40)));
    
    Label label = new Label("Stastic: ");
    label.setFont(new Font("Arial", 1, 25));
    
    Choice stat = new Choice();
    stat = addStats(stat);
    stat.setFont(new Font("Arial", 0, 28));
    setContainerSize(stat, 500, 40);
    this.panel.add(stat);
    
    Choice c = new Choice();
    c.add("Cumulative");
    c.add("By Year");
    c.setFont(new Font("Arial", 0, 28));
    setContainerSize(c, 200, 40);
    this.panel.add(c);
    this.panel.add(Box.createRigidArea(new Dimension(50, 40)));
    
    Button submit = new Button("Find statistics");
    submit.setFont(new Font("Arial", 1, 30));
    submit.addActionListener(this);
    this.panel.add(submit);
    setVisible(true);
  }
  
  private Choice addStats(Choice stat)
  {
    stat.add("Passing Yards");
    stat.add("Passing Touchdowns");
    stat.add("Completion Percentage");
    stat.add("Interceptions Thrown");
    stat.add("Times Sacked");
    stat.add("Record");
    stat.add("QBR");
    stat.add("Passer Rating");
    stat.add("Yards per Completion");
    
    stat.add("Rushing Yards");
    stat.add("Receiving Yards");
    stat.add("Rushing Touchdowns");
    stat.add("Receiving Touchdowns");
    stat.add("Fumbles");
    stat.add("Yards from Scrimmage");
    stat.add("Yards per Rushing Attempt");
    stat.add("Yards per Catch");
    stat.add("Rushing and Receiving Touchdowns");
    
    stat.add("Interceptions");
    stat.add("Forced Fumbles");
    stat.add("Fumbles Recovered");
    stat.add("Sacks");
    stat.add("Solo Tackles");
    stat.add("Interception Touchdowns");
    stat.add("Fumble Recovery Touchdowns");
    stat.add("Passes Defended");
    
    return stat;
  }
  
  private void setContainerSize(Component comp, int length, int width)
  {
    comp.setPreferredSize(new Dimension(length, width));
    comp.setMinimumSize(new Dimension(length, width));
    comp.setMaximumSize(new Dimension(length, width));
  }
  
  private TextField createTextPrompt(final TextField t, final String initial)
  {
    t.setText(initial);
    setContainerSize(t, 200, 40);
    t.setFont(new Font("Arial", 0, 30));
    t.setForeground(Color.BLACK);
    t.addFocusListener(new FocusListener()
    {
      public void focusGained(FocusEvent event)
      {
        if (t.getText().equals(initial)) {
          t.setText("");
        }
      }
      
      public void focusLost(FocusEvent arg0)
      {
        if (t.getText().equals("")) {
          t.setText(initial);
        }
      }
    });
    return t;
  }
  
  private Choice createYearPrompt(Choice c)
  {
    setContainerSize(c, 100, 25);
    for (Integer i = Integer.valueOf(2014); i.intValue() >= 1920; i = Integer.valueOf(i.intValue() - 1)) {
      c.add(i.toString());
    }
    return c;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    this.statline = new ArrayList();
    Component[] ca = this.panel.getComponents();
    for (int i = 0; i < this.panel.getComponents().length; i++) {
      if (i % 8 == 1) {
        if ((ca[i] instanceof TextField))
        {
          TextField f = (TextField)ca[i];
          TextField l = (TextField)ca[(i + 1)];
          Choice y1 = (Choice)ca[(i + 3)];
          Choice y2 = (Choice)ca[(i + 5)];
          GUI.StatLine s = new GUI.StatLine(f.getText(), l.getText(), y1.getSelectedItem(), y2.getSelectedItem());
          this.statline.add(s);
        }
      }
    }
    display();
  }
  
  public void display()
  {
    Object[][] o = new Object[this.statline.size()][5];
    Component[] c = this.panel.getComponents();
    if (!(c[(c.length - 1)] instanceof Button)) {
      this.panel.remove(c.length - 1);
    }
    c = this.panel.getComponents();
    Choice ch = (Choice)c[(c.length - 3)];
    String choice = ch.getSelectedItem();
    Choice st = (Choice)c[(c.length - 4)];
    String stat = st.getSelectedItem();
    for (int i = 0; i < this.statline.size(); i++)
    {
      GUI.StatLine s = (GUI.StatLine)this.statline.get(i);
      try
      {
        System.out.println(s.first + " " + s.last + " " + stat + " " + s.year1 + " " + s.year2 + " " + choice);
        o[i] = Engine.getStatistic(s.first, s.last, stat, s.year1, s.year2, choice);
        System.out.println(o[i][1]);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    ArrayList<TextArea> textareas = new ArrayList();
    ArrayList<JScrollPane> scrollpanes = new ArrayList();
    int count = -1;
    for (int i = 0; i < o.length; i++)
    {
      if (o[i].length != 4)
      {
        count = i;
        break;
      }
      TextArea ta = new TextArea();
      ta.setFont(new Font("Arial", 0, 20));
      ta.append(o[i][2] + "\n");
      Object[] somestats = (Object[])o[i][0];
      ta.append("Age(s): " + somestats[0] + "\n" + "Position: " + somestats[2] + "\n" + "Team(s): " + somestats[1] + "\n");
      ta.append("Games Played: " + somestats[3] + " \n" + "Statistic: " + o[i][3] + "\n");
      String result = (String)o[i][1];
      ta.append(result);
      setContainerSize(ta, 300, 200);
      JScrollPane scrollPane = new JScrollPane(ta);
      textareas.add(ta);
      scrollpanes.add(scrollPane);
    }
    if (count != -1)
    {
      String s = (String)o[count][1];
      Label label = new Label(s);
      label.setFont(new Font("Arial", 1, 25));
      label.setForeground(Color.RED);
      this.panel.add(label);
      this.panel.revalidate();
      validate();
    }
    else
    {
      this.panel.removeAll();
      for (int i = 0; i < textareas.size(); i++)
      {
        System.out.println("ADDING");
        TextArea textarea = (TextArea)textareas.get(i);
        textarea.setEditable(false);
        this.panel.add(textarea);
        JScrollPane sp = (JScrollPane)scrollpanes.get(i);
        this.panel.add(sp);
      }
      this.panel.add(Box.createRigidArea(new Dimension(1300, 40)));
      Button button = new Button("Return");
      setContainerSize(button, 300, 40);
      button.setFont(new Font("Arial", 0, 30));
      button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent arg0)
        {
          GUI.this.numPlayers = 1;
          GUI.this.init();
        }
      });
      this.panel.add(button, Float.valueOf(0.5F));
      this.panel.revalidate();
      validate();
    }
  }
  
  public static void main(String[] args)
  {
    new GUI();
  }
  
  public void windowClosing(WindowEvent arg0)
  {
    System.exit(0);
  }
  
  public void windowClosed(WindowEvent arg0) {}
  
  public void windowActivated(WindowEvent arg0) {}
  
  public void windowDeactivated(WindowEvent arg0) {}
  
  public void windowDeiconified(WindowEvent arg0) {}
  
  public void windowIconified(WindowEvent arg0) {}
  
  public void windowOpened(WindowEvent arg0) {}
}
*/