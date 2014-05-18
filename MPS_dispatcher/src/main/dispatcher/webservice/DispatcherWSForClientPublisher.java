package main.dispatcher.webservice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.logging.Logger;

import javax.xml.ws.Endpoint;

public class DispatcherWSForClientPublisher {
	public static Logger logger = null;
	public static String config = null;
	public static Properties props = null;
	
	public static void main (String[] args) {
		config = "dispatcher.cfg";
		logger = Logger.getGlobal();
		createProperties();
		
		String webserviceEndpoint = "http://136.172.80.201:4000/dispatcher";
		
		Endpoint.publish(webserviceEndpoint, new DispatcherWSForClientImpl());
		Timer timer = new Timer();
		timer.schedule(new SayAllive(), 0, 3000);
	}
	
	public static void createProperties() {
		try {
			props = new Properties();
			OutputStream os = new FileOutputStream(config);
			
			props.setProperty("Monitor IP", "1.2.3.4");
			props.setProperty("Monitor Port", "1234");

			props.store(os, null);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
