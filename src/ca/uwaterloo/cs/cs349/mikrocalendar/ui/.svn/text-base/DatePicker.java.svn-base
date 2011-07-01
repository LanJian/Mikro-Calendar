package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.joda.time.DateTime;

class DatePicker extends JDialog {
	//For rendering
	private int currM; //Note: Jan is 0
	private int currY;
	private boolean cancelled; //useless...
	
	private DateTime start = null;;
	private JLabel sDisp;
	private DateTime end = null;
	private JLabel eDisp;
	
	private JPanel cal;
	private JButton Ok; //want to activate/deac this
	
	//If true restrict date choice to after current real date
	private boolean TimeR = false;
	
	//Some basic init here
	public DatePicker() {
		this.setTitle("DatePicker");
		this.setModal(true);
		this.setVisible(false);
		this.setSize(450, 305);
		this.getContentPane().setLayout(new CardLayout());
		cal = new JPanel();
		cal.setLayout(new BoxLayout(cal, BoxLayout.PAGE_AXIS));
		this.getContentPane().add(cal);
		
		Ok = new JButton("OK");
	    Ok.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		close();
	    	}
	    });
	}
	
		/*---Interface---*/
	
	//Main view calls this when required
	public void activate() {
		//start = end = null;
		cancelled = false;
		if (start == null) {
			sDisp = new JLabel("none");
			Ok.setEnabled(false);
		}
		if (end == null) {
			eDisp = new JLabel("none");
		}

		Calendar c = Calendar.getInstance();
		this.currM = c.get(Calendar.MONTH);
		this.currY = c.get(Calendar.YEAR);
		
		//System.out.println(c.getActualMaximum(Calendar.YEAR));
		//System.out.println(c.getActualMinimum(Calendar.YEAR));
		
		render();
	}
	
	public DateTime getStartDateTime() {
		return start==null ? null : start.plusMonths(1);
	}
	public DateTime getEndDateTime() {
		return end==null ? null : end.plusMonths(1);
	}
	public boolean userChoseNoStartTime() {
		if (start == null) {
			return true;
		}
		return false;
	}
	public boolean userChoseNoEndTime() {
		if (end == null) {
			return true;
		}
		return false;
	}
	public boolean userCancelled() {
		return cancelled;
	}
	//Use this after the user actually posts the current event
	public void reset() {
		start = end = null;
	}
	
	//Shared listener for numbered buttons, brings up the TimePicker and gets selection from there
	private ActionListener choice = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton where = (JButton)e.getSource(); //Get value of button pressed
			String num = where.getText();
			
			TimePicker t = new TimePicker();
			if (start == null) {
				t.activate(currY, currM, Integer.parseInt(num), true);
				DateTime ret = t.getSelection();
				if (ret != null) {
					if ((end != null) && (ret.isAfter(end))) {
						JOptionPane.showMessageDialog(cal, "Selected start time is after selected end time!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						start = ret;
						sDisp.setText(new DateFormatSymbols().getMonths()[ret.monthOfYear().get()] + " " + 
								ret.dayOfMonth().get() + ", " + ret.year().get() + 
								" @ " + ret.hourOfDay().get() + ":");
						if (ret.minuteOfHour().get() < 10) {
							sDisp.setText(sDisp.getText() + "0" + ret.minuteOfHour().get());
						}
						else {
							sDisp.setText(sDisp.getText() + ret.minuteOfHour().get());
						}
						Ok.setEnabled(true); //Now can get out of here
					}
				}
			}
			else {
				t.activate(currY, currM, Integer.parseInt(num), false);
				DateTime ret = t.getSelection();

				if (ret != null) {
					if (ret.isBefore(start)) {
						JOptionPane.showMessageDialog(cal, "Selected end time is before selected start time!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						end = ret;
						eDisp.setText(new DateFormatSymbols().getMonths()[ret.monthOfYear().get()] + " " + 
									ret.dayOfMonth().get() + ", " + ret.year().get() + 
									" @ " + ret.hourOfDay().get() + ":");
						if (ret.minuteOfHour().get() < 10) {
							eDisp.setText(eDisp.getText() + "0" + ret.minuteOfHour().get());
						}
						else {
							eDisp.setText(eDisp.getText() + ret.minuteOfHour().get());
						}
					}
				}
			}	
		}
	};
	
	//This will re-draw the date screen for us, called on user action
	//Can be optimized to reduce number of changes. Actually, only the numbers have to change as well as some button activations
	private void render() {
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		cal.removeAll(); //Simple model, for each month clear and repopulate
		cal.repaint(); //Some months are longer than others so buttons will move, need to clear
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		cal.add(top);
		cal.add(Box.createVerticalStrut(5));
		
		top.add(Box.createRigidArea(new Dimension(3, 24)));
		JButton b = new JButton(" <<");
		if ((currY == Calendar.getInstance().get(Calendar.YEAR) && TimeR) || (currY == 1)) {
			b.setEnabled(false);
		}
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (currY > 1) {
        			currY--;
        			render();
        		}
        	}
        });
		top.add(b);
		b = new JButton("  < ");
		if (((currY == Calendar.getInstance().get(Calendar.YEAR)) && (currM == Calendar.getInstance().get(Calendar.MONTH)) && TimeR)
				|| ((currM == 0) && (currY == 1))){
			b.setEnabled(false);
		}
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if ((currM == 0) && (currY > 1)) {
        			currM = 11;
        			currY--;
        		}
        		else {
        			currM--;
        		}
        		render();
        	}
        });
		top.add(b);
		top.add(Box.createHorizontalGlue());
		JButton disp = new JButton("     " + new DateFormatSymbols().getMonths()[currM] + ",  " + currY + "     ");
		disp.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		disp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDate();
				render();
			}
		});
		top.add(disp);
		top.add(Box.createHorizontalGlue());
		b = new JButton(" >  ");
		if ((currM == 11) && (currY == 292278994)) {
			b.setEnabled(false);
		}
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (currM == 11) {
        			currM = 0;
        			currY++;
        		}
        		else {
        			currM++;
        		}
        		render();
        	}
        });
		top.add(b);
		b = new JButton(">> ");
		if (currY == 292278994) {
			b.setEnabled(false);
		}
        b.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		currY++;
        		render();
        	}
        });
		top.add(b);
		top.add(Box.createRigidArea(new Dimension(3, 24)));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(7, 7));
		cal.add(buttons);
		cal.add(Box.createVerticalStrut(5));
		
		String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
		JLabel l;
		for (String str : days) {
			l = new JLabel("    " + str);
			l.setForeground(Color.BLACK);
			l.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
			buttons.add(l);
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, currM);
		c.set(Calendar.YEAR, currY);
		c.set(Calendar.DATE, 1);
		int day = c.get(Calendar.DAY_OF_WEEK);
		//Add filler
		for (int i = 1; i < day; i++) {
			b = new JButton("");
			b.setEnabled(false);
			buttons.add(b);
		}
		day = c.getActualMaximum(Calendar.DATE);
		for (int i = 1; i <= day; i++) {
			b = new JButton(Integer.toString(i));
			if ((i < Calendar.getInstance().get(Calendar.DATE)) && 
					(currM == Calendar.getInstance().get(Calendar.MONTH)) && 
					(currY == Calendar.getInstance().get(Calendar.YEAR)) &&
					TimeR) {
				b.setEnabled(false);
			}
			else {
				b.addActionListener(choice);
			}
			buttons.add(b);
		}
		day = 43 - c.getActualMaximum(Calendar.DATE) - c.get(Calendar.DAY_OF_WEEK);
		if (day >= 7) {
			day -= 7;
			buttons.setLayout(new GridLayout(6, 7));
		}
		for (int i = 0; i < day; i++) {
			b = new JButton("");
			b.setEnabled(false);
			buttons.add(b);
		}
		
		JPanel comm = new JPanel();
		comm.setLayout(new BoxLayout(comm, BoxLayout.LINE_AXIS));
		cal.add(comm);
		cal.add(Box.createVerticalGlue());
		
		JPanel currSec = new JPanel();
		currSec.setLayout(new BoxLayout(currSec, BoxLayout.PAGE_AXIS));
		comm.add(currSec);
		
		JPanel startDisplay = new JPanel();
		startDisplay.setLayout(new BoxLayout(startDisplay, BoxLayout.LINE_AXIS));
		startDisplay.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		currSec.add(startDisplay);
		
		JLabel ss = new JLabel("Start:");
		startDisplay.add(ss);
		startDisplay.add(Box.createHorizontalStrut(3));
		startDisplay.add(sDisp);
		startDisplay.add(Box.createHorizontalGlue());
		JButton sCancel = new JButton("X");
		sCancel.setForeground(Color.RED);
        sCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		start = null;
        		sDisp.setText("none");
        		Ok.setEnabled(false);
        	}
        });
        startDisplay.add(sCancel);

		JPanel endDisplay = new JPanel();
		endDisplay.setLayout(new BoxLayout(endDisplay, BoxLayout.LINE_AXIS));
		endDisplay.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		currSec.add(endDisplay);
		
		JLabel es = new JLabel("End:");
		endDisplay.add(es);
		endDisplay.add(Box.createHorizontalStrut(13));
		endDisplay.add(eDisp);
		endDisplay.add(Box.createHorizontalGlue());
		JButton eCancel = new JButton("X");
		eCancel.setForeground(Color.RED);
        eCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		end = null;
        		eDisp.setText("none");
        	}
        });
        endDisplay.add(eCancel);

		comm.add(Box.createHorizontalGlue());
		comm.add(Ok);
		comm.add(Box.createHorizontalStrut(5));
		JButton Cancel = new JButton("Cancel");
        Cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cancelled = true;
        		close();
        	}
        });
		comm.add(Cancel);
		comm.add(Box.createRigidArea(new Dimension(3, 24)));
		
		this.setVisible(true);
	}
	
	//Manually set the month/year
	private void setDate() {
		//Set month
		String[] mOpt = new DateFormatSymbols().getMonths();
		String ret = (String)JOptionPane.showInputDialog(cal, "Choose the month:", "Month setting", JOptionPane.PLAIN_MESSAGE, 
				null, mOpt, mOpt[currM]);
		if (ret == null) {
			return;
		}
		for (int i = 0; i < mOpt.length; i++) {
			if (mOpt[i] == ret) {
				currM = i;
			}
		}
		
		//Set year, keep trying until a cancel or proper input
		for (int i = 0; true; i++) {
			if (i < 5) {
				ret = (String)JOptionPane.showInputDialog(cal, "Input a year between 1 and 292278994", "Year setting", JOptionPane.PLAIN_MESSAGE, 
						null, null, Integer.toString(currY));
			}
			else {
				ret = (String)JOptionPane.showInputDialog(cal, "Input a year between 1 and 292278994\n(HOW HARD IS IT? GET IT RIGHT ALREADY!)", 
						"Year setting", JOptionPane.PLAIN_MESSAGE, null, null, Integer.toString(currY));
			}
			if (ret == null) {
				return;
			}
			
			int iRet = 0;
			try {
				iRet = Integer.parseInt(ret);
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(cal, "Input not valid number!", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			
			if ((iRet <= 292278994) && (iRet >= 1)) {
				currY = iRet;
				return;
			}
			else {
				JOptionPane.showMessageDialog(cal, "Input not in prescribed range!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void close() {
		//this.setVisible(false);
		this.dispose();
	}
}