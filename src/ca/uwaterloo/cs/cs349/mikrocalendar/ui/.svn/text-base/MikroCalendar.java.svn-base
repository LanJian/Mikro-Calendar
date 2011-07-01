package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import javax.swing.*;

import org.joda.time.DateTime;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.*;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.local.*;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.twitter.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Vector;

public class MikroCalendar extends JPanel{
	MikroEventManager em;
	PostView postPanel;
	JScrollPane eventList;
	JButton refreshButton, nextButton, prevButton, lastButton, firstButton, searchCancel;
	JTextField searchField;
	JPanel searchPanel, buttonsPanel;
	Vector<MikroEventModel> events;
	EventViewManager[] eventViews;
	
	int page;
	String searchString;
	
	MikroCalendar(){
		super();
		
		page = 0;
		searchString = "";
		eventViews = new EventViewManager[20];
		
		postPanel = new PostView(this);
		refreshButton = new JButton("Refresh");
		nextButton = new JButton(">");
		nextButton.setToolTipText("Next 20 events");
		prevButton = new JButton("<");
		prevButton.setToolTipText("Previous 20 events");
		lastButton = new JButton(">>");
		lastButton.setToolTipText("Goto last page");
		firstButton = new JButton("<<");
		firstButton.setToolTipText("Goto first page");
		buttonsPanel = new JPanel();
		
		searchField = new JTextField();
		Font f = new Font("Nimbus Sans L Bold", Font.PLAIN, 13);
		searchField.setFont(f);
		searchField.setColumns(25);
		searchCancel = new JButton("X");
		searchPanel = new JPanel();
		searchCancel.setToolTipText("Clear search and refresh event list");
		
		searchField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchString = searchField.getText();
				refreshEvents();
			}
		});
		searchCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				searchField.setText("");
				searchString = searchField.getText();
				refreshEvents();
			}
		});
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				refreshEvents();
			}
		});
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				nextPage();
			}
		});
		prevButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				prevPage();
			}
		});
		firstButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				page=0;
				refreshEventViews();
				updateButtonStatus();
			}
		});
		lastButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				page = events.size()/20;
				refreshEventViews();
				updateButtonStatus();
			}
		});
		eventList = new JScrollPane();
		
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(Box.createHorizontalGlue());
		JLabel l = new JLabel("Showing Future Events                        ");
		f = new Font("Nimbus Sans L Bold", Font.PLAIN, 15);
		l.setFont(f);
		searchPanel.add(l);
		searchPanel.add(new JLabel("Search:"));
		searchPanel.add(searchField);
		searchPanel.add(searchCancel);
		
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(firstButton);
		buttonsPanel.add(prevButton);
		buttonsPanel.add(refreshButton);
		buttonsPanel.add(nextButton);
		buttonsPanel.add(lastButton);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(postPanel);
		this.add(searchPanel);
		this.add(Box.createVerticalStrut(10));
		eventList.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(eventList);
		this.add(buttonsPanel);
		//this.setSize(800, 800);
	}
	private void nextPage(){
		page++;
		refreshEventViews();
		updateButtonStatus();
	}
	private void prevPage(){
		page--;
		refreshEventViews();
		updateButtonStatus();
	}
	
	private void updateButtonStatus() {
		if ((page+1)*20 >= events.size())
			nextButton.setEnabled(false);
		else
			nextButton.setEnabled(true);
		if ((page-1)*20 < 0)
			prevButton.setEnabled(false);
		else
			prevButton.setEnabled(true);
	}

	public static void main(String[] args){
		try {
			MikroCalendar cal = new MikroCalendar();
		} catch (Exception e) {
			error();
		}
	}
	
	private void refreshEventViews(){
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.PAGE_AXIS));
		for(int i=0; i<20; i++){
			if(page*20+i>=events.size())
				break;
			EventViewManager eventModes = new EventViewManager(events.get(page*20+i), this);
			if(i%2==1)
				eventModes.setBg(new Color(235,235,235));
			eventPanel.add(eventModes);
		}
		eventPanel.add(Box.createVerticalGlue());
		eventList.setViewportView(eventPanel);
		JScrollBar b = eventList.getVerticalScrollBar();
		b.setValue(b.getMinimum());
	}

	public void refreshEvents() {
		try {
			if (searchString.equals(""))
				events = em.getEventsOnOrAfter(new DateTime());
			else
				events = em.getEventsWithKeywordBetweenTimes(searchString, new DateTime(), null);
			MikroEventUtils.sortEventsByStartTime(events, false);
		} catch (Exception e) {
			error();
		}
		page=0;
		refreshEventViews();
		updateButtonStatus();
	}
	
	public MikroEventManager getManager() {
		return em;
	}
	
	public void init(MikroEventManager m) {
		em = m;
		refreshEvents();
		updateButtonStatus();
		postPanel.setPromptForUser(m.getAuthor());
		postPanel.reset();
		this.setVisible(true);
	}
	public static void error() {
		JOptionPane.showMessageDialog(null,
			    "An error occured",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);		
	}
}
