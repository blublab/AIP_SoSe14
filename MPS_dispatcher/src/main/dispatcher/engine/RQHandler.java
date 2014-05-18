package main.dispatcher.engine;

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
		monitorConnector.publishTransaction("1.2.3.4", 1234);
		return coreconnector.createAngebot(kundenNr, bauteilNr, balancer.getMPSinstance());
	}
	
	public String nimmAngebotAn(int angebotNr) {
		return coreconnector.nimmAngebotAn(angebotNr, balancer.getMPSinstance());
	}
	
	/**
	 * Liefert Liste aller Angebote
	 * @return
	 */
	public String getAllAngebote() {
		return coreconnector.getAllAngebote(balancer.getMPSinstance());
	}
	
	public String getAllAuftraege() {
		return coreconnector.getAllAuftraege(balancer.getMPSinstance());
	}
	
//	public List<Kunde> getAllKunden();
//	
//	public List<Bauteil> getAllBauteile();

}
