/**
 * 
 */
package twitter;

import java.io.IOException;

import org.xml.sax.SAXException;

import controllers.WebSocket;

import play.Logger;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author chamerling
 * 
 */
public class TwitterListener {

	public static final void listen(String user, String password, StatusListener listener, String[] keywords) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setUser(user);
		cb.setPassword(password);
		cb.setHttpRetryCount(10);

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();

		FilterQuery query = new FilterQuery();
		query.track(keywords);
		query.setIncludeEntities(true);
		twitterStream.addListener(listener);
		twitterStream.filter(query);
	}
}
