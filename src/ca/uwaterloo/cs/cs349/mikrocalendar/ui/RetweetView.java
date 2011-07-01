package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

public class RetweetView extends JPanel {
	JTextArea contentText;
	JButton submitButton, cancelButton;
	JPanel buttonPanel, top;
	EventViewManager evm;

	public RetweetView(MikroEventModel e, EventViewManager m) {
		super();
		evm = m;
		contentText = new JTextArea(e.getContent(), 4, 45);
		Font f = new Font("Nimbus Sans L Bold", Font.PLAIN, 15);
		contentText.setFont(f);
		contentText.setForeground(new Color(80,80,80));
		contentText.setEditable(false);
		contentText.setLineWrap(true);
		contentText.setFocusable(false);
		buttonPanel = new JPanel();
		submitButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.retweet();
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.setEventView();
			}
		});
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(submitButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(cancelButton);
		
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		f = new Font("Nimbus Sans L Bold", Font.PLAIN, 14);
		JLabel rtLabel = new JLabel("Retweet?");
		rtLabel.setFont(f);
		top.add(Box.createHorizontalStrut(5));
		top.add(rtLabel);
		top.add(Box.createHorizontalGlue());
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(top);
		this.add(Box.createVerticalStrut(3));
		this.add(contentText);
		this.add(buttonPanel);
	}

	public void ready() {
		submitButton.requestFocus();
	}

	public void setBg(Color c) {
		setBackground(c);
		buttonPanel.setBackground(c);
		top.setBackground(c);
	}

}
