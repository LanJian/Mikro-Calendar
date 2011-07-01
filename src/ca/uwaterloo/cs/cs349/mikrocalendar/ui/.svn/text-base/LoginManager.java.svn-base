package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.twitter.TwitterEventManager;

class LoginManager extends JPanel {
	public LoginManager() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		Border border = BorderFactory.createLineBorder(Color.black);
		Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		top.setBorder(BorderFactory.createCompoundBorder(border, empty));
		
		JLabel lbl = new JLabel("Login"); //Basic text
		top.add(lbl);
		int w = 100 - lbl.getPreferredSize().width;
		top.add(Box.createRigidArea( new Dimension(w, 24) ));
		top.add(Box.createHorizontalGlue());
		final JLabel err = new JLabel("Please enter credentials."); //Space for error print
		top.add(err);
		
		JPanel service = new JPanel();
		service.setLayout(new BoxLayout(service, BoxLayout.LINE_AXIS));
		lbl = new JLabel("Service : "); //Basic text
		service.add(lbl);
		w = 100 - lbl.getPreferredSize().width;
		service.add(Box.createRigidArea( new Dimension(w, 24) ));
		String[] opt = {"Channel W Test", "Channel W"};
		final JComboBox choice = new JComboBox(opt); //Choose service
		service.add(choice);
		
		JPanel user = new JPanel();
		user.setLayout(new BoxLayout(user, BoxLayout.LINE_AXIS));
        lbl = new JLabel("Username : "); //Basic text
		user.add(lbl);
		w = 100 - lbl.getPreferredSize().width;
		user.add(Box.createRigidArea( new Dimension(w, 24) ));
		final JTextField uIn = new JTextField(15); //Space for uname input
		user.add(uIn);
		
		JPanel pass = new JPanel();
		pass.setLayout(new BoxLayout(pass, BoxLayout.LINE_AXIS));
		lbl = new JLabel("Password : "); //Basic text
		pass.add(lbl);
		w = 100 - lbl.getPreferredSize().width;
		pass.add(Box.createRigidArea( new Dimension(w, 24) ));
		//Default field
		final JPasswordField pIn = new JPasswordField(15); //Space for pass input

        //Alternate field
		final JTextField pInClear = new JTextField(15); //Space for pass input
        pInClear.setVisible(false);
		
        JPanel pField = new JPanel();
        pField.setLayout(new CardLayout());
        pass.add(pField);
        
        //pField.add(Box.createRigidArea( new Dimension(1, 24) ));
        pField.add(pIn);
        pField.add(pInClear);
		
		final JPanel actions = new JPanel();
		actions.setLayout(new BoxLayout(actions, BoxLayout.LINE_AXIS));
		JCheckBox check = new JCheckBox("Show password");
		actions.add(check);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) { //Selected
					String p = new String(pIn.getPassword());
					pInClear.setText(p);
					pIn.setVisible(false);
					pInClear.setVisible(true);
				}
				else { //Deselected
					pIn.setText(pInClear.getText());
					pIn.setVisible(true);
					pInClear.setVisible(false);
				}
			}
		});
		
		actions.add(Box.createHorizontalGlue());
		JButton in = new JButton("Login");
		actions.add(in);
        in.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MikroEventManager em = null;
        		String p;
        		if (pIn.isVisible()) {
        			p = new String(pIn.getPassword());
        		}
        		else {
        			p = pInClear.getText();
        		}
        		try {
        			if (choice.getSelectedIndex() == 0) {
        				em = new TwitterEventManager( TwitterEventManager.TESTING_SERVICE_URL, uIn.getText(), p );
        			}
        			else {
        				em = new TwitterEventManager( TwitterEventManager.PRODUCTION_SERVICE_URL, uIn.getText(), p );
        			}
        		}
        		catch (Exception ex) { //Could not log in
        			JOptionPane.showMessageDialog(actions, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
    			informMain(em); //Get here only on login success
        	}
        });
		actions.add(Box.createHorizontalStrut(10));
		
		JButton quit = new JButton("Quit");
		actions.add(quit);
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        
        this.add(Box.createRigidArea( new Dimension(190, 1) )); //Hax
		this.add(top);
		this.add(Box.createVerticalStrut(5));
		this.add(service);
		this.add(Box.createVerticalStrut(5));
		this.add(user);
		this.add(Box.createVerticalStrut(5));
		this.add(pass);
		this.add(Box.createVerticalStrut(5));
		this.add(actions);
		this.setPreferredSize(new Dimension(300,300));
	}
	
	private void informMain(MikroEventManager em) {
		Main mc = Main.getInstance();
		mc.login(em);
		this.setVisible(false);
	}
	
	//There doesn't seem to be a way to actually log out...so we don't do much
	public void logout() { 
		this.setVisible(true);
	}
}