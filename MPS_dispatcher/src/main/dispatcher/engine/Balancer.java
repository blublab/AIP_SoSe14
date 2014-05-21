package main.dispatcher.engine;

import java.util.List;

import org.json.simple.JSONObject;

public class Balancer {
	
	// read-only for RQHandler, write-only for MonitorConnector
	// thread safe due to atomicity of reference assignment
	private volatile JSONObject currentSlacker = null;

	private List<JSONObject> activeHosts = null;
	
	public JSONObject getMPSinstance() {
		return currentSlacker;
	}
	
	public void setActiveHosts(List<JSONObject> hosts) {
		this.activeHosts = hosts;
		calculateCurrentSlacker();
	}
	
	public void calculateCurrentSlacker() {
		if (activeHosts.size() == 0)
			currentSlacker = null;
		
//		JSONObject test = new JSONObject();
//		test.put("host", "136.172.80.148");
//		test.put("port", 4321);
//		
//		currentSlacker = test;
//		
		Double lowestLoad = Double.MAX_VALUE;
		Double currentLoad = 0.0; 
		for (JSONObject host : activeHosts) {
			currentLoad = Double.parseDouble((String) host.get("load"));
			if (currentLoad < lowestLoad) {
				lowestLoad = currentLoad;
				currentSlacker = host;
			}
		}
		
//		System.out.println("New Slacker: " + currentSlacker);
	}
}
