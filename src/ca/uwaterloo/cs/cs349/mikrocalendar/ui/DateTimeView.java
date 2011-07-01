package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.*;

import javax.swing.*;

import org.joda.time.DateTime;

public class DateTimeView extends JPanel {
	DateTime time;
	JLabel month, year, day, t;
	static final String[] MONTHS= {"JAN", "FEB", "MAR", "APR",
		"MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
		"NOV", "DEC"};

	public DateTimeView(DateTime time) {
		super();
		this.time = time;
		Font f = new Font("Monospaced", Font.BOLD, 13);
		Color c = new Color(44,68,152);
		month = new JLabel(strMonth(time.getMonthOfYear()));
		month.setFont(f);
		month.setForeground(c);
		year = new JLabel(""+time.getYear());
		year.setFont(f);
		year.setForeground(c);
		day = new JLabel(""+time.getDayOfMonth());
		day.setFont(new Font("Calibri", Font.BOLD, 26));
		day.setForeground(new Color(126,148,227));
		t = new JLabel(formatTime(time.getHourOfDay(), time.getMinuteOfHour()));
		t.setFont(f);
		t.setForeground(c);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(month);
		topPanel.add(year);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(topPanel);
		this.add(Box.createVerticalStrut(3));
		day.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(day);
		t.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createVerticalStrut(3));
		this.add(t);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
	}

	private String strMonth(int m) {
		return MONTHS[m-1];
	}
	
	private String formatTime(int hour, int min){
		String ampm = hour/12==0 ? "AM":"PM";
		String h = ""+(hour%12==0 ? 12 : hour%12);
		String m = ""+(min<10 ? "0"+min : min);
		return h+":"+m+ampm;
	}
	
	public void setDateTime(DateTime time) {
		month.setText(strMonth(time.getMonthOfYear()));
		year.setText(""+time.getYear());
		day.setText(""+time.getDayOfMonth());
		day.setFont(new Font("Calibri", Font.BOLD, 22));
		t.setText(formatTime(time.getHourOfDay(), time.getMinuteOfHour()));
	}
	

}
