package ca.uwaterloo.cs.cs349.mikrocalendar.ui;

import org.joda.time.DateTime;

/**
 * Interface defining the functionality a date/time picker should
 * implement. The expected use of such an object is to call
 * "showDateTimePicker", which should bring up a modal dialog box
 * within which the user chooses a start and (optionally) end time.
 * 
 * Upon returning from that method call, the object can be queried
 * to see whether the user chose a start or end time (or cancelled).
 * 
 * @author Michael Terry
 */
public interface DateTimePicker {
	public DateTime getStartDateTime();
	public DateTime getEndDateTime();
	public boolean userChoseNoStartTime();
	public boolean userChoseNoEndTime();
	public boolean userCancelled();
	public void showDateTimePicker();
}
