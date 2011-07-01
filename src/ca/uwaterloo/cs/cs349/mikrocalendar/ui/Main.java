package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.*;
import javax.swing.plaf.FontUIResource;


import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.twitter.TwitterEventManager;

//Basic singleton class, manages views
public class Main {
	private static Main instance = null;
	protected Main() {
		//No instantiation
	}
	public static Main getInstance() {
		if(instance == null) {
			instance = new Main();
		}
		return instance;
	}

	private static JFrame frame; //Main-frame
	//Our two views
	private static LoginManager lm;
	private static MikroCalendar mc;
	static JMenuItem logout;
	static JPanel p;
	
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		}	
		
        frame = new JFrame("Mikro Calendar");
        frame.getContentPane().setLayout(new CardLayout());
        
        
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		logout = new JMenuItem("Logout");
		JMenuItem quit = new JMenuItem("Quit");
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				logout();
			}
		});
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				logout();
				frame.dispose();
				System.exit(0);
			}
		});
		logout.setEnabled(false);
		
		fileMenu.add(logout);
		fileMenu.addSeparator();
		fileMenu.add(quit);
		
		frame.setJMenuBar(menuBar);
        
        //Login init
        lm = new LoginManager(); //Invisible for now
		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(lm, BorderLayout.CENTER);
		p.setBorder(BorderFactory.createEmptyBorder(150,200,480,200));
        frame.getContentPane().add(p);

        mc = new MikroCalendar();
        frame.getContentPane().add(mc);

        frame.setVisible(true);
		frame.setSize(800, 850);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
	}
	
	//Called from the LoginManager to switch to main view
	public static void login(MikroEventManager m) {
		logout.setEnabled(true);
		p.setVisible(false);
		mc.init(m);
		frame.setTitle("Mikro Calendar - "+ m.getAuthor());
	}
	
	//Called from the Calendar to switch to login screen
	public static void logout() {
		logout.setEnabled(false);
		mc.setVisible(false);
		lm.logout();
		frame.setTitle("Mikro Calendar");
	}
}
