package main.dispatcherWS;

import java.util.Timer;

import javax.xml.ws.Endpoint;

public class DispatcherWSForClientPublisher {
	public static void main (String[] args) {
		String url = "http://141.22.68.209:4000/dispatcher";
		Endpoint.publish(url, new DispatcherWSForClientImpl());
		Timer timer = new Timer();
		timer.schedule(new SayAllive(), 0, 3000);
	}
}
