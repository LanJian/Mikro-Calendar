package ca.uwaterloo.cs.cs349.mikrocalendar.events.twitter;

import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;

import twitter4j.Annotation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.BasicAuthorization;
import twitter4j.conf.ConfigurationBuilder;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.AbstractEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventManager;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventUtils;
import ca.uwaterloo.cs.cs349.mikrocalendar.events.MikroEventModel;

/**
 * Provides the functionality needed to post events, retweet events, reply to events,
 * etc., on a server such as Twitter, or, for our class, Channel W or Channel W Test. Maintains references to the user ID and password
 * required to use the service.
 * 
 * @author Michael Terry
 */
public class TwitterEventManager extends AbstractEventManager {

	public final static String PRODUCTION_SERVICE_URL = "http://hci-courses.cs.uwaterloo.ca/channelw/api/";
	public final static String TESTING_SERVICE_URL = "http://hci-courses.cs.uwaterloo.ca/channelw-test/api/";
	
	private Twitter twitter = null;
	private String author = null;

	/**
	 * Creates a connection to the service specified and verifies the user_id and password passed in.
	 * Throws an exception if it can't connect to the service or the user ID / password combo is invalid.
	 * @throws Exception
	 */
	public TwitterEventManager(String serviceURL, String user_id, String password) throws Exception {
		if (!serviceURL.endsWith("/")) {
			serviceURL = serviceURL + "/";
		}
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setRestBaseURL(serviceURL);
		cb.setSearchBaseURL(serviceURL);
		twitter = new TwitterFactory(cb.build()).getInstance(new BasicAuthorization(user_id, password));
		twitter.verifyCredentials(); // Does a user ID / password check
		this.author = user_id;
	}
	
	private Vector<MikroEventModel> statusesToMikroEvents(List<Status> statuses) throws Exception {
		Vector<MikroEventModel> events = new Vector<MikroEventModel>();
		for (Status s : statuses) {
			try {
				events.add(new TwitterMikroEvent(s));
			} catch (Exception e) {
				; // no-op. Ignore. If an exception is thrown, it means it's not one of our events
			}
		}
		return events;
	}
	
	private Vector<Status> tweetsToStatuses(List<Tweet> tweets) throws Exception {
		Vector<Status> statuses = new Vector<Status>();
		for (Tweet t : tweets) {
			statuses.add(twitter.showStatus(t.getId()));
		}
		return statuses;
	}
	
	public Vector<MikroEventModel> getAllEvents() throws Exception {
		final int MAX_NUM_EVENTS = 100;
		Vector<MikroEventModel> returnEvents = new Vector<MikroEventModel>();
		List<Status> statuses = null;
		Paging paging = new Paging();
		paging.setPage(1);
		paging.setCount(MAX_NUM_EVENTS);
		/**
		while (returnEvents.size() < 100) {
			statuses = twitter.getPublicTimeline();
			returnEvents.addAll(statusesToMikroEvents(statuses));
			paging.setPage(paging.getPage()+1);
			if (statuses.size() < 1) {
				break;
			}
		}*/
		statuses = twitter.getPublicTimeline();
		returnEvents.addAll(statusesToMikroEvents(statuses));
		
		return returnEvents;
	}

	public String getAuthor() {
		return author;
	}

	public Vector<MikroEventModel> getEventsWithKeyword(String keyword) throws Exception {
		QueryResult results = twitter.search(new Query(keyword));
		// Need to convert tweets to statuses because tweets don't have annotations with them
		return statusesToMikroEvents(tweetsToStatuses(results.getTweets()));
	}

	public Vector<MikroEventModel> getFavoriteEvents() throws Exception {
		return statusesToMikroEvents(twitter.getFavorites());
	}

	private StatusUpdate prepareStatusUpdate(String content, DateTime startTime, DateTime endTime) {
		StatusUpdate su = new StatusUpdate(content);
		Annotation a = new Annotation(TwitterMikroEvent.ANNOTATION_EVENT_TYPE);
		a.addAttribute(TwitterMikroEvent.EVENT_START_TIME, startTime.toString(MikroEventUtils.getEventTimeFormat()));
		if (endTime != null) {
			a.addAttribute(TwitterMikroEvent.EVENT_END_TIME, endTime.toString(MikroEventUtils.getEventTimeFormat()));
		} else {
			a.addAttribute(TwitterMikroEvent.EVENT_END_TIME, "");
		}
		su.addAnnotation(a);
		return su;
	}
	public MikroEventModel postEvent(String content, DateTime startTime, DateTime endTime) throws Exception {
		StatusUpdate su = prepareStatusUpdate(content, startTime, endTime);
		Status s = twitter.updateStatus(su);
		return new TwitterMikroEvent(s);
	}

	public MikroEventModel replyToEvent(MikroEventModel e, String content) throws Exception {
		StatusUpdate su = prepareStatusUpdate(content, e.getStartTime(), e.getEndTime());
		su.setInReplyToStatusId(e.getStatusID());
		Status s = twitter.updateStatus(su);
		return new TwitterMikroEvent(s);
	}

	public MikroEventModel retweetEvent(MikroEventModel e) throws Exception {
		Status s = twitter.retweetStatus(e.getStatusID());
		return new TwitterMikroEvent(s);
	}

	public void favoriteEvent(MikroEventModel e) throws Exception {
		twitter.createFavorite(e.getStatusID());
	}
	public void unfavoriteEvent(MikroEventModel e) throws Exception {
		twitter.destroyFavorite(e.getStatusID());
	}

	/**
		You won't actually be able to run this because you only have one account each,
		but this code should give you an overview of how to programmatically do most (if not all)
		of the things you need to do with the TwitterEventManager for the assignment.
	*/
	public static void test(String serviceURL,
							String user1,
							String user1_password,
							String user2,
							String user2_password) throws Exception
	{
		MikroEventManager em1 = new TwitterEventManager(serviceURL, user1, user1_password);
		MikroEventManager em2 = new TwitterEventManager(serviceURL, user2, user2_password);
		MikroEventModel e = em1.postEvent("Test post", new DateTime().minusMinutes(1), new DateTime());
		System.out.println(e);
		em2.favoriteEvent(e);
		em2.unfavoriteEvent(e);
		e = em2.replyToEvent(e, "My awesome reply");
		System.out.println(e);
		e = em1.retweetEvent(e);
		System.out.println(e);
		em1.favoriteEvent(e);
		e = em2.postEvent("No end time post", new DateTime(), null);
		
		System.out.println("All events, most recent first:");
		Vector<MikroEventModel> events = em1.getAllEvents();
		MikroEventUtils.sortEventsByStartTime(events, true);
		for (MikroEventModel event : events) {
			System.out.println(event);
		}
		System.out.println("All events, most recent last:");
		MikroEventUtils.sortEventsByStartTime(events, false);
		for (MikroEventModel event : events) {
			System.out.println(event);
		}
		System.out.println("Events with string 'awesome':");
		for (MikroEventModel event : em1.getEventsWithKeyword("awesome")) {
			System.out.println(event);
		}
		System.out.println("Events before 'now':");
		for (MikroEventModel event : em1.getEventsBefore(new DateTime())) {
			System.out.println(event);
		}
		System.out.println("Events within 2 minutes ago:");
		for (MikroEventModel event : em1.getEventsOnOrAfter(new DateTime().minusMinutes(2))) {
			System.out.println(event);
		}
		System.out.println("Events between 5 minutes ago and now");
		for (MikroEventModel event : em1.getEventsBetween(new DateTime().minusMinutes(5), new DateTime())) {
			System.out.println(event);
		}
		System.out.println("Favorite events for em1");
		for (MikroEventModel event : em1.getFavoriteEvents()) {
			System.out.println(event);
		}
		System.out.println("Favorite events for em2");
		for (MikroEventModel event : em2.getFavoriteEvents()) {
			System.out.println(event);
		}
	}
	public static void main(String[] args) {
		try {
			TwitterEventManager.test(TESTING_SERVICE_URL, args[0], args[1], args[2], args[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
