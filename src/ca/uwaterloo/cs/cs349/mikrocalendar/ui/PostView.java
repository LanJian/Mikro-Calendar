package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import org.joda.time.DateTime;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;

public class PostView extends JPanel {
	JTextArea contentArea;
	JButton pickButton, resetButton, submitButton;
	JLabel promptLabel;
	DatePicker dp;
	DateTimeView dtv;
	MikroCalendar mc;
	
	static final String defaultContent = "Enter your event here";
	
	public PostView(MikroCalendar cal){
		super();
		this.mc = cal;
		
		dp = new DatePicker();
		dtv = new DateTimeView(new DateTime());
		
		contentArea = new JTextArea(defaultContent, 4, 45);
		Font f = new Font("Nimbus Sans L Bold", Font.PLAIN, 15);
		contentArea.setFont(f);
		contentArea.setLineWrap(true);
		((AbstractDocument) contentArea.getDocument()).setDocumentFilter(new DocumentSizeFilter(140));
		pickButton = new JButton("?");
		pickButton.setFont(new Font("Calibri", Font.BOLD, 26));
		pickButton.setMaximumSize(new Dimension(140,140));
		pickButton.setMinimumSize(new Dimension(140,140));
		pickButton.setForeground(new Color(126,148,227));
		resetButton = new JButton("Reset");
		submitButton = new JButton("Submit");
		submitButton.setEnabled(false);
		
		pickButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dp.activate();
				if (dp.userChoseNoStartTime()){
					submitButton.setEnabled(false);
					setPickTime();
				}
				else {
					submitButton.setEnabled(true);
					setDateTimeView();
				}
			}
		});
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				reset();
			}
		});
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				post();
				mc.refreshEvents();
				reset();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(submitButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(resetButton);
		
		JPanel left = new JPanel();
		left.setLayout(new OverlayLayout(left));
		left.add(pickButton);
		left.add(dtv);
		setPickTime();
		
		promptLabel = new JLabel("What's coming up?");
		Color c = new Color(44,68,152);
		promptLabel.setForeground(c);
		f = new Font("Nimbus Sans L Bold", Font.PLAIN, 13);
		promptLabel.setFont(f);
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(Box.createHorizontalStrut(5));
		top.add(promptLabel);
		top.add(Box.createHorizontalGlue());
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		right.add(top);
		right.add(Box.createVerticalStrut(3));
		right.add(contentArea);
		right.add(buttonPanel);
		
		this.setLayout(new FlowLayout());
		this.add(left);
		this.add(right);
	}
	
	private void setPickTime(){
		pickButton.setVisible(true);
		dtv.setVisible(false);
	}
	
	private void setDateTimeView(){
		pickButton.setVisible(false);
		dtv.setVisible(true);
		dtv.setDateTime(dp.getStartDateTime());
	}
	
	public void reset(){
		contentArea.setText(defaultContent);
		dp.reset();
		submitButton.setEnabled(false);
		setPickTime();
	}
	
	public void post(){
		try {
			if (dp.getEndDateTime()!=null)
				mc.getManager().postEvent(contentArea.getText(), dp.getStartDateTime(), dp.getEndDateTime());
			else{
				mc.getManager().postEvent(contentArea.getText(), dp.getStartDateTime());
			}
		} catch (Exception e) {
			MikroCalendar.error();
		}
	}
	public void setPromptForUser(String user){
		promptLabel.setText("Welcome, "+user+"! What's coming up?");
	}
	
}
