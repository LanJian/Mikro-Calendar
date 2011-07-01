package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.joda.time.DateTime;

class TimePicker extends JDialog {
	private JPanel main;
	private JLabel sel;
	private JLabel choose;
	final JComboBox Hr;
	boolean HrPrev;
	final JComboBox Min;
	final JComboBox AmPm;
		
	private DateTime ret;
	
	private int Y, M, D;
	
	private boolean TimeR = false;
	
	//Some basic init here
	public TimePicker() {
		this.setTitle("TimePicker");
		this.setModal(true);
		this.setVisible(false);
		this.setSize(325, 160);
		this.getContentPane().setLayout(new CardLayout());
		main = new JPanel();
		this.getContentPane().add(main);
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		/*---Some basic info---*/
		JPanel one = new JPanel();
		one.setLayout(new BoxLayout(one, BoxLayout.LINE_AXIS));
		sel = new JLabel("");
		sel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		one.add(sel);
		one.add(Box.createHorizontalGlue());
		main.add(one);
		main.add(Box.createVerticalStrut(5));
		
		JPanel two = new JPanel();
		two.setLayout(new BoxLayout(two, BoxLayout.LINE_AXIS));
		choose = new JLabel("");
		choose.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		two.add(choose);
		two.add(Box.createHorizontalGlue());
		main.add(two);
		main.add(Box.createVerticalStrut(5));
		
		/*---Labels for the dropboxes---*/
		JPanel labels = new JPanel();
		labels.setLayout(new BoxLayout(labels, BoxLayout.LINE_AXIS));
		labels.add(Box.createHorizontalStrut(10));
		labels.add(new JLabel("Hour"));
		labels.add(Box.createHorizontalStrut(55));
		labels.add(new JLabel("Minute"));
		labels.add(Box.createHorizontalGlue());
		main.add(labels);
		main.add(Box.createVerticalStrut(5));

		/*---Drop boxes---*/
		Hr = new JComboBox();
		Hr.addItemListener(new ItemListener() { //On change need to reconsider selections
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == arg0.SELECTED) {
					if (HrPrev && TimeR) { //Avoid loop
						resetSelection();
					}
				}

			}	
		});

		Min = new JComboBox();

        String[] AmPmOpt = {"AM", "PM"};
		AmPm = new JComboBox(AmPmOpt);
		AmPm.addItemListener(new ItemListener() { //On change need to reconsider selections
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == arg0.SELECTED) {
					if (TimeR) {
						resetSelection();
					}
				}
			}	
		});
		
		JPanel time = new JPanel();
		time.setLayout(new BoxLayout(time, BoxLayout.LINE_AXIS));
		main.add(time);
		main.add(Box.createVerticalStrut(5));
		
		time.add(Hr);
		time.add(Min);
		time.add(AmPm);
		time.add(Box.createHorizontalGlue());
		
		/*---OK/Cancel buttons---*/
		JPanel comm = new JPanel();
		comm.setLayout(new BoxLayout(comm, BoxLayout.LINE_AXIS));
		main.add(comm);
		
		comm.add(Box.createHorizontalGlue());
		JButton Ok = new JButton("OK");
        Ok.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		set(Integer.parseInt((String)Hr.getSelectedItem()) + 12 * AmPm.getSelectedIndex(), 
        			Integer.parseInt((String)Min.getSelectedItem()));
        		close();

        	}
        });
		comm.add(Ok);
		comm.add(Box.createHorizontalStrut(5));
		JButton Cancel = new JButton("Cancel");
        Cancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		close();
        	}
        });
		comm.add(Cancel);
	}
	
	private void set(int Hr, int Min) {
		ret = new DateTime(Y, M, D, Hr, Min, 0, 0);
	}
	
	private void close() {
		this.dispose();
	}
	
	//Here we want to limit options such that the user can only select future time-points
	//The selections made in the Hour and AM/PM fields can block or unblock certain selections
	private void resetSelection() {
		Calendar c = Calendar.getInstance();
		int HrStart = 0;
		int MinStart = 0;
		//Special case for current day scheduling
		//VM time is off by 5 minutes so I made this add 10 (not 5 as originally planned) to get a 5 min window from the current real time
		if ((D == c.get(Calendar.DATE)) && (M == c.get(Calendar.MONTH)) && (Y == c.get(Calendar.YEAR) && TimeR)) {
			HrStart = c.get(Calendar.HOUR);
			MinStart = c.get(Calendar.MINUTE) + 10;
			if (MinStart >= 60) {
				HrStart += 1;
				MinStart = MinStart % 60;
			}
			//MinStart = 60; //For current day must make hour selection before seeing minute options
			System.out.print(Hr.getItemCount());
			if (Hr.getItemCount() != 0) { //First one isn't a number
				//System.out.print((String)Hr.getSelectedItem());
				if (Integer.parseInt((String)Hr.getSelectedItem()) > HrStart) { //If hour is not current have full range of minutes
					MinStart = 0;
				}
			}
			//Now some basic AM/PM switches
			if ((c.get(Calendar.AM_PM) == Calendar.AM) && (AmPm.getSelectedIndex() == 1)) { //AM now, selecting PM -> all options
				HrStart = 0;
				MinStart = 0;
			}
			else if ((c.get(Calendar.AM_PM) == Calendar.PM) && (AmPm.getSelectedIndex() == 0)) { //PM now, selecting AM -> no options
				HrStart = 12;
				MinStart = 60;
			}
		}
		
		String[] HrOpt = new String[12 - HrStart];
		for (int i = 0; i < HrOpt.length; i++) {
			HrOpt[i] = Integer.toString(i + HrStart);
		}
		String[] MinOpt = new String[60 - MinStart];
		for (int i = 0; i < MinOpt.length; i++) {
			if ((i + MinStart) < 10) {
				MinOpt[i] = ("0" + Integer.toString(i + MinStart));
			}
			else {
				MinOpt[i] = Integer.toString(i + MinStart);
			}
		}
		
		int sel = -1;
		if (Hr.getItemCount() != 0) {
			sel = Integer.parseInt((String)Hr.getSelectedItem());
			Hr.removeAllItems();
		}
		for (int i = 0; i < HrOpt.length; i++) {
			HrPrev = false;
			Hr.addItem(HrOpt[i]);
			HrPrev = true;
		}
		if (sel >= 0) {
			for (int i = 0; i < Hr.getItemCount(); i++) {
				if (Integer.parseInt((String)Hr.getItemAt(i)) == sel) {
					HrPrev = false;
					Hr.setSelectedIndex(i); //This triggers another change, *sigh*
					HrPrev = true;
				}
			}
		}
		Hr.repaint();
        
		sel = -1;
		if (Min.getItemCount() != 0) {
			sel = Integer.parseInt((String)Min.getSelectedItem());
			Min.removeAllItems();
		}
		for (int i = 0; i < MinOpt.length; i++) {
			//MinPrev = false;
			Min.addItem(MinOpt[i]);
		}
		if (sel >= 0) {
			for (int i = 0; i < Min.getItemCount(); i++) {
				if (Integer.parseInt((String)Min.getItemAt(i)) == sel) {
					//MinPrev = false;
					Min.setSelectedIndex(i);
				}
			}
		}
		Min.repaint();
	}
	
	public void activate(int currY, int currM, int currD, boolean start) {
		ret = null;
		HrPrev = true;
		Y = currY;
		M = currM;
		D = currD;
		sel.setText("You selected: " + new DateFormatSymbols().getMonths()[M] + " " + D + ", " + Y);
		choose.setText("Please choose the start time for this event.");
		if (!start) {
			choose.setText("Please choose the end time for this event.");
		}
		
		resetSelection();
		
		this.setVisible(true);
	}
	
	public DateTime getSelection() {
		return ret;
	}
}