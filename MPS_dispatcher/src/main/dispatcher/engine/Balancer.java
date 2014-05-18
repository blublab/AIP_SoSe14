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
		
		JSONObject test = new JSONObject();
		test.put("host", "136.172.80.148");
		test.put("port", 4321);
		
		currentSlacker = test;
	}
}
