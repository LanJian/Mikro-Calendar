package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

public class EventViewManager extends JPanel {
	DateTimeView dateTimePanel;
	JPanel modes;
	EventView eventView;
	ReplyView replyView;
	RetweetView rtView;
	MikroCalendar mc;
	MikroEventModel event;
	boolean hasReply, hasRT;
	Color evColor;

	public EventViewManager(MikroEventModel e, MikroCalendar cal) {
		super();
		mc = cal;
		event = e;
		this.setLayout(new FlowLayout());
		
		dateTimePanel = new DateTimeView(e.getStartTime());
		dateTimePanel.setToolTipText("This is the start date and time of this event");
		
		modes = new JPanel();
		modes.setLayout(new OverlayLayout(modes));
		eventView = new EventView(e, this);
		replyView = new ReplyView(e, this);
		rtView = new RetweetView(e, this);
		//modes.add(replyView);
		//modes.add(rtView);
		modes.add(eventView);
		setEventView();
		
		hasReply = hasRT = false;
		
		this.add(dateTimePanel);
		modes.setAlignmentY(CENTER_ALIGNMENT);
		this.add(modes);
		
		evColor = getBackground();
	}
	
	public void setEventView(){
		eventView.setVisible(true);
		replyView.setVisible(false);
		rtView.setVisible(false);
		setBg(evColor);
	}
	
	public void setReplyView(){
		if (!hasReply){
			modes.add(replyView);
			hasReply = true;
		}
		eventView.setVisible(false);
		replyView.setVisible(true);
		rtView.setVisible(false);
		Color c = new Color(179, 227, 126);
		setBackground(c);
		replyView.setBg(c);
		replyView.ready();
	}

	public void setRetweetView(){
		if (!hasRT){
			modes.add(rtView);
			hasRT = true;
		}
		eventView.setVisible(false);
		replyView.setVisible(false);
		rtView.setVisible(true);
		Color c = new Color(179, 227, 126);
		setBackground(c);
		rtView.setBg(c);
		rtView.ready();
	}

	public void reply(String content) {
		try {
			mc.getManager().replyToEvent(event, content);
			mc.refreshEvents();
		} catch (Exception e) {
			MikroCalendar.error();
		}
	}

	public void retweet() {
		try {
			mc.getManager().retweetEvent(event);
			mc.refreshEvents();
		} catch (Exception e) {
			MikroCalendar.error();
		}
	}

	public void favorite() {
		try {
			mc.getManager().favoriteEvent(event);
		} catch (Exception e) {
			MikroCalendar.error();
		}
	}
	
	public void setBg(Color c){
		setBackground(c);
		eventView.setBg(c);
		replyView.setBg(c);
		rtView.setBg(c);
		evColor = c;
	}
	
}
