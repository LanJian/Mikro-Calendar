package ca.uwaterloo.cs.cs349.mikrocalendar.events.local;

import java.net.MalformedURLException;

import org.joda.time.DateTime;
import org.json.JSONException;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.AbstractMikroEvent;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventUtils;

/**
 * Basic, no-frills, representation of an event. Intended for testing
 * uses only. These event objects are intended to be stored locally on a disk, rather
 * than on a server.
 */
public class LocalMikroEvent extends AbstractMikroEvent {
	private String author = "";
	private String content = "";
	private DateTime startTime = null;
	private DateTime endTime = null;
	private DateTime originalSubmitTime = null;
	private long statusID = -1;

	public LocalMikroEvent(String author,
							String content,
							DateTime startTime,
							DateTime endTime,
							DateTime originalSubmitTime,
							long statusID) throws Exception
	{
		if (author == null || author.length() < 1) {
			throw new Exception("Cannot have null or empty author for DefaultMikroEvent");
		}
		if (content == null || content.length() < 1) {
			throw new Exception("Cannot have null or empty content for DefaultMikroEvent");
		}
		if (startTime == null) {
			throw new Exception("Cannot have null startTime for DefaultMikroEvent");
		}
		if (originalSubmitTime == null) {
			throw new Exception("Cannot have null originalSubmitTime for DefaultMikroEvent");
		}
		this.author = author;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.originalSubmitTime = originalSubmitTime;
		this.statusID = statusID;
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

	public DateTime getStartTime() {
		return startTime;
	}

	public String toJSON() {
		return MikroEventUtils.eventToJSON(this).toString();
	}

	public Object getSource() {
		return null;
	}

	public long getStatusID() {
		return statusID;
	}
	
	public static void test() throws MalformedURLException, JSONException {
		System.out.println("Testing DefaultMikroEvent");
		LocalMikroEvent e = null;
		try {
			e = new LocalMikroEvent("test-author", "test-content", new DateTime(100000), new DateTime(900000), new DateTime(50000), 1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		try {
			assert(e.equals(MikroEventUtils.jsonToEvent(MikroEventUtils.eventToJSON(e))));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("Done testing DefaultMikroEvent");
	}
	
	public static void main(String[] args) {
		try {
			test();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
