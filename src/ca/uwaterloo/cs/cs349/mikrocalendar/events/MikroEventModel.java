package ca.uwaterloo.cs.cs349.mikrocalendar.events;

import org.joda.time.DateTime;

/**
 * Data representing a microevent (an event represented with 140 or fewer characters).
 * This interface provides methods for getting all the data needed for listing a
 * single event in the event list
 * shown in the main application window.
 * @author Michael Terry
 *
 */
public interface MikroEventModel {
	/**
	 * The author (user ID) of this event.
	 * Must be a non-null String of length > 0.
	 * @return
	 */
	public String getAuthor();

	/**
	 * The event description (140 characters or fewer).
	 * Must be a non-null String of length > 0.
	 * @return
	 */
	public String getContent();
	
	/**
	 * The start time of the event.
	 * Must be a non-null DateTime.
	 * @return
	 */
	public DateTime getStartTime();
	
	/**
	 * The end time of the event, if known. If there is no
	 * end time, it will be null.
	 * May be null.
	 * @return
	 */
	public DateTime getEndTime();
	
	/**
	 * The time the event was submitted to the service.
	 * Must be non-null DateTime.
	 * @return
	 */
	public DateTime getOriginalSubmitTime();
	
	/**
	 * The "real" object representing the event on the service.
	 * Currently, is either null, or a Twitter4j Status object.
	 * @return
	 */
	public Object getSource();
	
	/**
	 * The arbitrary ID given to this event.
	 */
	public long getStatusID();
}
