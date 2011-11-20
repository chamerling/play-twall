/**
 * 
 */
package controllers;

import com.google.gson.Gson;

import models.Tweet;
import play.libs.F.EventStream;
import play.mvc.WebSocketController;
import twitter4j.Status;

/**
 * @author chamerling
 * 
 */
public class WebSocket extends WebSocketController {

	public static EventStream<Status> liveStream = new EventStream<Status>();

	public static void twitter() {
		while (inbound.isOpen()) {
			Status status = await(liveStream.nextEvent());
			if (status != null) {
				Tweet tweet = new Tweet();
				tweet.date = status.getCreatedAt().toString();
				tweet.status = status.getText();
				tweet.user = status.getUser().getName();
				tweet.iconURL = status.getUser().getProfileImageURL().toString();
				tweet.screenname = status.getUser().getScreenName();
				String json = new Gson().toJson(tweet);
				outbound.send(json);
				
			} else {
				System.out.println("Message is null... Should not happen...");
			}
		}
	}
}
