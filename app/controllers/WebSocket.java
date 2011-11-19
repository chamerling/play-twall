/**
 * 
 */
package controllers;

import play.libs.F.EventStream;
import play.mvc.WebSocketController;
import twitter4j.Status;
import twitter4j.json.DataObjectFactory;

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
				//System.out.println("Data to send : " + status.getText());
				// JSON
				// String rawJSON = DataObjectFactory.getRawJSON(status);
				//String rawJSON = DataObjectFactory.getRawJSON(status);
				//System.out.println(rawJSON);
				outbound.send(status.getText());
			} else {
				System.out.println("Message is null... Should not happen...");
			}
		}
	}
}
