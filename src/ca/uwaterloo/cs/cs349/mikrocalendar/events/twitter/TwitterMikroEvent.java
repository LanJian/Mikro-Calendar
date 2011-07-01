package ca.uwaterloo.cs.cs349.mikrocalendar.events.twitter;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.AbstractMikroEvent;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventUtils;

import twitter4j.Annotation;
import twitter4j.Annotations;
import twitter4j.Status;

/**
 * An implementation of a MikroEventModel intended to be used with Twitter-like
 * services.
 * 
 * @author Michael Terry
 */
public class TwitterMikroEvent extends AbstractMikroEvent {

	public final static String ANNOTATION_EVENT_TYPE = "mikroevent";
	public final static String EVENT_START_TIME = MikroEventUtils.JSON_START_TIME_KEY;
	public final static String EVENT_END_TIME = MikroEventUtils.JSON_END_TIME_KEY;

	private Object source = null;
	private String author = null;
	private String content = null;
	private DateTime startTime = null;
	private DateTime endTime = null;
	private DateTime originalSubmitTime = null;
	private long statusID = -1;
	
	/**
	 * Used to get annotations from re-tweets.
	 * @param s
	 * @return
	 */
	private Annotations getAnnotations(Status s) {
		if (s == null) {
			return null;
		}
		Annotations annotations = s.getAnnotations();
		if (annotations == null) {
			return getAnnotations(s.getRetweetedStatus());
		} else {
			for (Annotation a : annotations.getAnnotations()) {
				if (a.getType().equals(ANNOTATION_EVENT_TYPE)) {
					return annotations;
				}
			}
			return getAnnotations(s.getRetweetedStatus());
		}
	}
	
	/**
	 * Transforms a Twitter Status into a MikroEvent
	 * @param s
	 * @throws Exception
	 */
	public TwitterMikroEvent(Status s) throws Exception {
		source = s;
		author = s.getUser().getScreenName();
		content = s.getText();
		initTimes(s.getCreatedAt(), getAnnotations(s));
		statusID = s.getId();
	}
	
	private void initTimes(Date creationTime, Annotations annotations) throws Exception {
		originalSubmitTime = new DateTime(creationTime.getTime());
		if (annotations != null) {
			for (Annotation a : annotations.getAnnotations()) {
				if (a.getType().equals(ANNOTATION_EVENT_TYPE)) {
					Map<String, String> attributes = a.getAttributes();
					String startTimeString = attributes.get(EVENT_START_TIME);
					String endTimeString = attributes.get(EVENT_END_TIME);
					startTime = MikroEventUtils.parseEventTime(startTimeString);
					if (endTimeString != null) {
						endTime = MikroEventUtils.parseEventTime(endTimeString);
					}
					break;
				}
			}
		}
		if (startTime == null) {
			throw new Exception("TwitteEventModel error: No startTime annotation found for status/tweet");
		}
	}
	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public DateTime getOriginalSubmitTime() {
		return originalSubmitTime;
	}

	public Object getSource() {
		return source;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public long getStatusID() {
		return statusID;
	}
	public String toString() {
		return MikroEventUtils.eventToJSON(this).toString();
	}
}
