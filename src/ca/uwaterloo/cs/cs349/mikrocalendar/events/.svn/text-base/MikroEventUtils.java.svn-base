package ca.uwaterloo.cs.cs349.mikrocalendar.events;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.local.LocalMikroEvent;

/**
 * A set of commonly useful utilities for working with events and lists of events.
 * 
 * @author Michael Terry
 */
public class MikroEventUtils {
	public final static String JSON_CONTENT_KEY = "content";
	public final static String JSON_AUTHOR_KEY = "user-id";
	public final static String JSON_EVENT_TIMES_KEY = "event-times";
	public final static String JSON_START_TIME_KEY = "start-time";
	public final static String JSON_END_TIME_KEY = "end-time";
	public final static String JSON_EVENT_ID_KEY = "id";
	public final static String JSON_SUBMIT_TIME_KEY = "submit-time";
	public final static String JSON_EVENTS_KEY = "mikroevents";
	
	/**
	 * Converts an event to its JSON representation.
	 * @param e
	 * @return
	 */
	public static JSONObject eventToJSON(MikroEventModel e) {
		JSONObject jo = new JSONObject();
		try {
			jo.put(MikroEventUtils.JSON_CONTENT_KEY, e.getContent());
			jo.put(MikroEventUtils.JSON_AUTHOR_KEY, e.getAuthor());
			jo.put(MikroEventUtils.JSON_SUBMIT_TIME_KEY, e.getOriginalSubmitTime().toString(MikroEventUtils.getEventTimeFormat()));
			jo.put(MikroEventUtils.JSON_EVENT_ID_KEY, ""+e.getStatusID());
			JSONObject eventTimes = new JSONObject();
			eventTimes.put(MikroEventUtils.JSON_START_TIME_KEY, e.getStartTime().toString(MikroEventUtils.getEventTimeFormat()));
			if (e.getEndTime() != null) {
				eventTimes.put(MikroEventUtils.JSON_END_TIME_KEY, e.getEndTime().toString(MikroEventUtils.getEventTimeFormat()));
			} else {
				eventTimes.put(MikroEventUtils.JSON_END_TIME_KEY, "");
			}
			jo.put(MikroEventUtils.JSON_EVENT_TIMES_KEY, eventTimes);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * Converts a JSON object representing an event into a MikroEvent
	 * @param eventData
	 * @return
	 * @throws Exception
	 */
	public static MikroEventModel jsonToEvent(JSONObject eventData) throws Exception {
		JSONObject eventTimes = eventData.getJSONObject(MikroEventUtils.JSON_EVENT_TIMES_KEY);
		String author = eventData.getString(MikroEventUtils.JSON_AUTHOR_KEY);
		String content = eventData.getString(MikroEventUtils.JSON_CONTENT_KEY);
		DateTime startTime = MikroEventUtils.parseEventTime(eventTimes.getString(MikroEventUtils.JSON_START_TIME_KEY));
		DateTime endTime = MikroEventUtils.parseEventTime(eventTimes.getString(MikroEventUtils.JSON_END_TIME_KEY));
		DateTime originalSubmitTime = MikroEventUtils.parseEventTime(eventData.getString(MikroEventUtils.JSON_SUBMIT_TIME_KEY));
		long id = eventData.getLong(MikroEventUtils.JSON_EVENT_ID_KEY);

		return new LocalMikroEvent(author, content, startTime, endTime, originalSubmitTime, id);
	}
	
	/**
	 * Converts a vector of events into a JSONObject
	 * @param events
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject eventsToJSON(Vector<MikroEventModel> events) throws JSONException {
		JSONObject jo = new JSONObject();
		JSONArray eventArray = new JSONArray();
		for (MikroEventModel eventDataModel : events) {
			eventArray.put(eventArray.length(), eventToJSON(eventDataModel));
		}
		jo.put(JSON_EVENTS_KEY, eventArray);
		return jo;
	}

	/**
	 * Converts a JSONObject representing an array of events into a vector of MikroEventModels
	 * @param jo
	 * @return
	 * @throws Exception
	 */
	public static Vector<MikroEventModel> jsonEventArrayToEvents(JSONObject jo) throws Exception {
		Vector<MikroEventModel> returnEvents = new Vector<MikroEventModel>();
		JSONArray eventArray = jo.optJSONArray(JSON_EVENTS_KEY);
		for (int i = 0; i < eventArray.length(); i++) {
			JSONObject event = eventArray.getJSONObject(i);
			returnEvents.add(jsonToEvent(event));
		}
		return returnEvents;
	}

	/**
	 * Parses a string representing a date/time into an actual DateTime object
	 * @param t
	 * @return
	 */
	public static DateTime parseEventTime(String t) {
		if (t.length() < 15) {
			return null;
		}
		int year = Integer.parseInt(t.substring(0, 4));
		int month = Integer.parseInt(t.substring(5, 7));
		int day = Integer.parseInt(t.substring(8, 10));
		int hour = Integer.parseInt(t.substring(11, 13));
		int minutes = Integer.parseInt(t.substring(14, 16));
		int seconds = Integer.parseInt(t.substring(17, 19));
		return new DateTime(year, month, day, hour, minutes, seconds, 0);
	}
	
	/**
	 * Time format used when representing date/times in events
	 * @return
	 */
	public static String getEventTimeFormat() {
		return "YYYY'-'MM'-'dd'T'HH':'mm':'ss";
	}

	public static void sortEventsByStartTime(Vector<MikroEventModel> events, final boolean mostRecentFirst) {
		Comparator<MikroEventModel> c = new Comparator<MikroEventModel>() {
			public int compare(MikroEventModel l, MikroEventModel r) {
				if (mostRecentFirst) {
					return -1 * l.getStartTime().compareTo(r.getStartTime());
				}
				return l.getStartTime().compareTo(r.getStartTime());
			}
		};
		Collections.sort(events, c);
	}
	
	public static Vector<MikroEventModel> filterEventsBetweenTimes(Vector<MikroEventModel> events, DateTime startTime, DateTime endTime) {
		Vector<MikroEventModel> returnEvents = new Vector<MikroEventModel>();
		for (MikroEventModel e : events) {
			DateTime eventStartTime = e.getStartTime();
			if (startTime == null) {
				if (endTime == null) {
					returnEvents.add(e);
				} else {
					if (eventStartTime.isBefore(endTime) || eventStartTime.equals(endTime)) {
						returnEvents.add(e);
					}
				}
			} else if (endTime == null) {
				if (eventStartTime.isAfter(startTime) || eventStartTime.equals(startTime)) {
					returnEvents.add(e);
				}
			} else {
				if ((eventStartTime.isAfter(startTime) || eventStartTime.equals(startTime)) &&
						(eventStartTime.isBefore(endTime) || eventStartTime.equals(endTime))) {
					returnEvents.add(e);
				}
			}
		}
		return returnEvents;
	}
}
