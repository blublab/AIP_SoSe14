package main.dispatcher.engine;

import org.json.simple.JSONObject;

import main.dispatcher.connectors.CoreConnector;
import main.dispatcher.connectors.MonitorConnector;

public class RQHandler {
	
	private Balancer balancer = null;
	private CoreConnector coreconnector = null;
	private MonitorConnector monitorConnector = null;
	
	public RQHandler() {
		this.balancer = new Balancer();
		this.coreconnector = new CoreConnector();
		this.monitorConnector = new MonitorConnector(this.balancer);
	}

	public String createAngebot(int kundenNr, int bauteilNr) {
		JSONObject instance = balancer.getMPSinstance();
		if (instance == null) { return "{\"response\":false}"; }
		monitorConnector.publishTransaction(instance);
		return coreconnector.createAngebot(kundenNr, bauteilNr, instance);
	}
	
	public String acceptAngebot(int angebotNr) {
		JSONObject instance = balancer.getMPSinstance();
		if (instance == null) { return "{\"response\":false}"; }
		monitorConnector.publishTransaction(instance);
		return coreconnector.acceptAngebot(angebotNr, instance);
	}
	
	/**
	 * Liefert Liste aller Angebote
	 * @return
	 */
	public String getAllAngebote() {
		JSONObject instance = balancer.getMPSinstance();
		if (instance == null) { return "[]"; }
		monitorConnector.publishTransaction(instance);
		return coreconnector.getAllAngebote(instance);
	}
	
	public String getAllAuftraege() {
		JSONObject instance = balancer.getMPSinstance();
		if (instance == null) { return "[]"; }
		monitorConnector.publishTransaction(instance);
		return coreconnector.getAllAuftraege(instance);
	}
	
//	public List<Kunde> getAllKunden();
//	
	public String getAllBauteile() {
		System.out.println("RQHandler: GetAllBauteile Request");
		
		JSONObject instance = balancer.getMPSinstance();
		if (instance == null) { return "[]"; }
		monitorConnector.publishTransaction(instance);
		return coreconnector.getAllBauteile(instance);
	}
}
