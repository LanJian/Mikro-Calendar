package ca.uwaterloo.cs.cs349.mikrocalendar.events.local;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.uwaterloo.cs.cs349.mikrocalendar.events.AbstractEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventUtils;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

/**
 * An implementation of the MikroEventManager that writes each event to
 * the file specified.
 * 
 * @author Michael Terry
 */
public class LocalEventManager extends AbstractEventManager {
	
	private String user_id = "";
	private Vector<MikroEventModel> events = new Vector<MikroEventModel>();
	private Vector<MikroEventModel> favorites = new Vector<MikroEventModel>();
	private File jsonFile = null;
	private long last_id = 0;

	/**
	 * @param user_id The user ID of the individual
	 * @param localJSONFile A pointer to a file (which may not exist, initially) that is used
	 *                      to store events on disk
	 * @throws Exception
	 */
	public LocalEventManager(	String user_id,
								File localJSONFile) throws Exception
	{
		if (user_id == null || user_id.length() < 1) {
			throw new Exception("Cannot have null/empty user_id in LocalEventManager");
		}
		this.user_id = user_id;
		this.jsonFile = localJSONFile;
		if (this.jsonFile.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(this.jsonFile));
			String line = null;
			StringBuilder fileContents = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				fileContents.append(line);
			}
			reader.close();
			for (MikroEventModel e : MikroEventUtils.jsonEventArrayToEvents(new JSONObject(fileContents.toString()))) {
				events.add(e);
				last_id = Math.max(e.getStatusID(), last_id);
			}
		}
	}
	public MikroEventModel postEvent(String content,
									DateTime startTime,
									DateTime endTime) throws Exception
	{
		MikroEventModel e = new LocalMikroEvent(this.getAuthor(), content, startTime, endTime, new DateTime(), ++last_id);
		this.events.add(e);
		writeJSONFile();
		return e;
	}
	public MikroEventModel replyToEvent(MikroEventModel e, String content) throws Exception {
		MikroEventModel newEvent = new LocalMikroEvent(this.getAuthor(), content, e.getStartTime(), e.getEndTime(), new DateTime(), ++last_id);
		this.events.add(newEvent);
		writeJSONFile();
		return newEvent;
	}
	public MikroEventModel retweetEvent(MikroEventModel e) throws Exception {
		MikroEventModel newEvent = new LocalMikroEvent(this.getAuthor(), e.getContent(), e.getStartTime(), e.getEndTime(), new DateTime(), ++last_id);
		this.events.add(newEvent);
		writeJSONFile();
		return newEvent;
	}
	public void favoriteEvent(MikroEventModel e) throws Exception {
		if (!favorites.contains(e)) {
			favorites.add(e);
		}
	}
	public void unfavoriteEvent(MikroEventModel e) throws Exception {
		favorites.remove(e);
	}
	
	public String getAuthor() {
		return user_id;
	}
	
	public void setAuthor(String author) throws Exception {
		if (author == null || author.length() < 1) {
			throw new Exception("Cannot have null/empty author");
		}
		this.user_id = author;
	}

	@SuppressWarnings("unchecked")
	public Vector<MikroEventModel> getAllEvents() throws Exception {
		return (Vector<MikroEventModel>)events.clone();
	}

	public Vector<MikroEventModel> getEventsByAuthor(String author) throws Exception {
		Vector<MikroEventModel> returnEvents = new Vector<MikroEventModel>();
		for (MikroEventModel e : events) {
			if (e.getAuthor().equals(author)) {
				returnEvents.add(e);
			}
		}
		return returnEvents;
	}

	public Vector<MikroEventModel> getEventsWithKeyword(String keyword) throws Exception {
		Vector<MikroEventModel> returnEvents = new Vector<MikroEventModel>();
		keyword = keyword.toLowerCase();
		for (MikroEventModel e : events) {
			if (e.getContent().toLowerCase().contains(keyword)) {
				returnEvents.add(e);
			}
		}
		return returnEvents;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<MikroEventModel> getFavoriteEvents() throws Exception {
		return  (Vector<MikroEventModel>)favorites.clone();
	}

	private void writeJSONFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(this.jsonFile));
		writer.write(this.toJSON());
		writer.close();
	}
	
	public String toJSON() {
		JSONObject eventContainer = new JSONObject();
		JSONArray eventArray = new JSONArray();
		try {
			for (MikroEventModel e : events) {
				eventArray.put(MikroEventUtils.eventToJSON(e));
			}
			eventContainer.put(MikroEventUtils.JSON_EVENTS_KEY, eventArray);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return eventContainer.toString();
	}

	public static void test(File f) throws Exception {
		MikroEventManager em = new LocalEventManager("mterry", f);
		em.postEvent("Event 1", new DateTime(), new DateTime().plusDays(1));
		em.postEvent("Event 2", new DateTime().minusHours(3), null);
		em.postEvent("Event numero tres", new DateTime().plusMinutes(7), null);
		em = new LocalEventManager("mterry", f);
		
		for (MikroEventModel e : em.getAllEvents()) {
			System.out.println("Event: " + MikroEventUtils.eventToJSON(e));
		}
	}
	public static void main(String[] args) {
		try {
			test(new File("./LocalEventManager_test_file.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
