package ca.uwaterloo.cs.cs349.mikrocalendar.events;

import java.util.Vector;

import org.joda.time.DateTime;

/**
 * Provides common functionality shared with every MikroEventManager.
 * 
 * @author Michael Terry
 */
public abstract class AbstractEventManager implements MikroEventManager {

	public MikroEventModel postEvent(String content, DateTime eventTime) throws Exception
	{
		return this.postEvent(content, eventTime, null);
	}
	
	public Vector<MikroEventModel> getEventsWithKeywordBetweenTimes(
			String keyword, DateTime startTime, DateTime endTime)
			throws Exception
	{
		return MikroEventUtils.filterEventsBetweenTimes(getEventsWithKeyword(keyword), startTime, endTime);
	}

	public Vector<MikroEventModel> getEventsBefore(DateTime time) throws Exception {
		return MikroEventUtils.filterEventsBetweenTimes(getAllEvents(), null, time);
	}

	public Vector<MikroEventModel> getEventsBetween(DateTime startTime, DateTime endTime) throws Exception {
		return MikroEventUtils.filterEventsBetweenTimes(getAllEvents(), startTime, endTime);
	}

	public Vector<MikroEventModel> getEventsOnOrAfter(DateTime time) throws Exception {
		return MikroEventUtils.filterEventsBetweenTimes(getAllEvents(), time, null);
	}


}
