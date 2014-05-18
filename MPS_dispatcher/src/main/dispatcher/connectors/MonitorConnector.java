package main.dispatcher.connectors;

import java.util.ArrayList;
import java.util.List;

import main.dispatcher.engine.Balancer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MonitorConnector {
	private Receiver receiver = null;
	private Balancer balancer = null;
	
	protected static String monitorUrl = null;
	
	public MonitorConnector(Balancer balancer) {
		this.monitorUrl = "1.2.3.4";
		this.balancer = balancer;
		this.receiver = new Receiver();
		this.receiver.start();
	}
	
	public void publishTransaction(String host, int port) {
		JSONObject test = new JSONObject();
		test.put("host", host);
		test.put("port", port);
		System.out.println("Sent to monitor: " + test.toJSONString());
		// send address of dispatched transaction to monitor
	}
	
	private class Receiver extends Thread {
		@Override
		public void run() {
			int retries = 3;
			boolean run = true;
			
			while (run) {
				while (true) {
					try {
						Thread.sleep(10000);
						JSONArray testArr = new JSONArray();
						
						JSONObject testObj1 = new JSONObject();
						testObj1.put("host", "1.2.3.4");
						testObj1.put("port", 1234);
						testObj1.put("load", 0.123);
						
						JSONObject testObj2 = new JSONObject();
						testObj2.put("host", "1.2.3.4");
						testObj2.put("port", 1234);
						testObj2.put("load", 0.123);						
						JSONObject testObj3 = new JSONObject();
						testObj3.put("host", "1.2.3.4");
						testObj3.put("port", 1234);
						testObj3.put("load", 0.123);						
						List<JSONObject> hostList = new ArrayList<JSONObject>();
						
						hostList.add(testObj1);
						hostList.add(testObj2);
						hostList.add(testObj3);
						
						testArr.addAll(hostList);
						
						System.out.println("Received from Monitor: " + testArr.toJSONString());
						
						List<JSONObject> activeHosts = new ArrayList<JSONObject>();
						
						for (Object host : testArr.toArray()) {
							activeHosts.add((JSONObject) host);
						}
												
						balancer.setActiveHosts(activeHosts);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// read messages from Monitor and pass them to the balancer
				}
			}
		}
	}
}
