package main.endpoint;

import javax.xml.ws.Endpoint;

public class ClientAdapterPublisher {

	public static void main (String[] args) {
		Endpoint.publish("http://localhost:8080/WS/Test", new ClientAdapterImpl());
	}
}
