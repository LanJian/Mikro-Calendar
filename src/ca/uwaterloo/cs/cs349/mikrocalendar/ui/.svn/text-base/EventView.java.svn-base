package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

public class EventView extends JPanel {
	JPanel buttonPanel, top;
	JLabel authorLabel;
	JTextArea contentText;
	JButton replyButton, rtButton, favButton;
	EventViewManager evm;
	
	EventView(MikroEventModel e, EventViewManager m){
		super();
		evm = m;
		buttonPanel = new JPanel();
		authorLabel = new JLabel(e.getAuthor());
		Color c = new Color(121,31,25);
		authorLabel.setForeground(c);
		contentText = new JTextArea(e.getContent(), 4, 45);
		Font f = new Font("Nimbus Sans L Bold", Font.PLAIN, 15);
		contentText.setFont(f);
		contentText.setForeground(new Color(80,80,80));
		contentText.setBackground(new Color(242,242,242));
		contentText.setEditable(false);
		contentText.setLineWrap(true);
		contentText.setFocusable(false);
		replyButton = new JButton("REPLY");
		replyButton.setToolTipText("Reply to this event!");
		rtButton = new JButton("RT");
		rtButton.setToolTipText("Retweet this event!");
		favButton = new JButton("+");
		favButton.setToolTipText("Favourite this event!");
		
		replyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.setReplyView();
			}
		});
		rtButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.setRetweetView();
			}
		});
		favButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				evm.favorite();
			}
		});
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(replyButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(rtButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(favButton);
		
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		f = new Font("Nimbus Sans L Bold", Font.PLAIN, 14);
		authorLabel.setFont(f);
		top.add(Box.createHorizontalStrut(5));
		top.add(authorLabel);
		top.add(Box.createHorizontalGlue());
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(top);
		this.add(Box.createVerticalStrut(3));
		this.add(contentText);
		this.add(buttonPanel);
	}
	
	public void setBg(Color c){
		setBackground(c);
		buttonPanel.setBackground(c);
		top.setBackground(c);
		
	}
}
