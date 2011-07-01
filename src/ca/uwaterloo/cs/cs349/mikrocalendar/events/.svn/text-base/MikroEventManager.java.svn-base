package ca.uwaterloo.cs.cs349.mikrocalendar.events;

import java.util.Vector;

import org.joda.time.DateTime;

/**
 * The interface used to post new events, reply to events, retweet events,
 * favorite and unfavorite events, and get lists of events.
 * 
 * @author Michael Terry
 */
public interface MikroEventManager {
	/**
	 * Post an event that will start at the time specified.
	 * @param content
	 * @param eventTime
	 * @return
	 * @throws Exception
	 */
	public MikroEventModel postEvent(String content, DateTime eventTime) throws Exception;
	
	/**
	 * Post an event that will start and end at the times specified.
	 * @param content
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public MikroEventModel postEvent(String content, DateTime startTime, DateTime endTime) throws Exception;
	
	/**
	 * Post a reply to the event given. The event must not be null.
	 * @param e
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public MikroEventModel replyToEvent(MikroEventModel e, String content) throws Exception;
	
	/**
	 * Retweet the specified event. The event must not be null.
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public MikroEventModel retweetEvent(MikroEventModel e) throws Exception;
	
	/**
	 * Favorite an event. The event must not be null.
	 * @param e
	 * @throws Exception
	 */
	public void favoriteEvent(MikroEventModel e) throws Exception;
	
	/**
	 * Unfavorite an event. The event must not be null.
	 * @param e
	 * @throws Exception
	 */
	public void unfavoriteEvent(MikroEventModel e) throws Exception;
	
	/**
	 * Get all events in the database. Current implementations limit the total number
	 * of events returned.
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getAllEvents() throws Exception;
	
	/**
	 * Get all events in the database with the given keyword. Current implementations
	 * may limit the total number of events returned.
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getEventsWithKeyword(String keyword) throws Exception;
	
	/**
	 * Get all events in the database with the given keyword in the date/time range
	 * specified. Either or both of start/endTime may be null. Null values for
	 * startTime or endTime will be interpreted as negative or positive infinity, respectively.
	 * Current implementations may limit the total number of events returned.
	 */	
	public Vector<MikroEventModel> getEventsWithKeywordBetweenTimes(String keyword, DateTime startTime, DateTime endTime) throws Exception;
	
	/**
	 * Get all events on or after the date given.
	 * Current implementations may limit the total number of events returned.
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getEventsOnOrAfter(DateTime time) throws Exception;

	/**
	 * Get all events before the date given.
	 * Current implementations may limit the total number of events returned.
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getEventsBefore(DateTime time) throws Exception;
	
	/**
	 * Get all events between the times specified.
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getEventsBetween(DateTime startTime, DateTime endTime) throws Exception;
	
	/**
	 * Get all events the user has favorited.
	 * @return
	 * @throws Exception
	 */
	public Vector<MikroEventModel> getFavoriteEvents() throws Exception;
	
	/**
	 * Returns the user ID (author) represented by this MikroEventManager
	 * @return
	 */
	public String getAuthor();
	
}
