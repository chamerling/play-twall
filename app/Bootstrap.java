/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import twitter.TwitterListener;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import controllers.WebSocket;

/**
 * Bootstrap some init data to avoid some useless test for the application long
 * life time
 * 
 * 
 */
@OnApplicationStart
public class Bootstrap extends Job {

	@Override
	public void doJob() throws Exception {
		int count = 1; // load things from database...
		if (count == 0) {
			Fixtures.load("initial-data.yml");
		}

		// TODO = move it elsewhere for better lifecycle management and
		// configuration
		StringTokenizer tokenizer = new StringTokenizer(
				Play.configuration.getProperty("twitter.tags"), ",");
		List<String> tags = new ArrayList<String>(tokenizer.countTokens());
		while (tokenizer.hasMoreTokens()) {
			tags.add(tokenizer.nextToken());
		}
		System.out.println("Starting listening tweets for tags " + tags);

		TwitterListener.listen(Play.configuration.getProperty("twitter.login"),
				Play.configuration.getProperty("twitter.password"),
				new StatusListener() {
					public void onStatus(Status status) {
						WebSocket.liveStream.publish(status);
					}

					public void onDeletionNotice(
							StatusDeletionNotice statusDeletionNotice) {
					}

					public void onTrackLimitationNotice(
							int numberOfLimitedStatuses) {
					}

					public void onScrubGeo(long userId, long upToStatusId) {
					}

					public void onException(Exception ex) {
					}
				}, tags.toArray(new String[tags.size()]));
	}
}
