package main.dispatcherWS;

import javax.jws.WebService;

@WebService(endpointInterface="main.dispatcherWS.IDispatcherWSForClient")
public class DispatcherWSForClientImpl implements IDispatcherWSForClient {

	private Dispatcher_BL dispatcherBL = null;
	
	public DispatcherWSForClientImpl() {
		this.dispatcherBL = new Dispatcher_BL();
	}
	
	@Override
	public boolean createAngebot(int kundenNr, int bauteilNr) {
		return dispatcherBL.createAngebot(kundenNr, bauteilNr);
	}

	@Override
	public boolean nimmAngebotAn(int angebotNr) {
		return dispatcherBL.nimmAngebotAn(angebotNr);
	}

	@Override
	public String echo(String s) {
		System.out.println("echoing: " + s);
		return "ECHO: " + s;
	}

}
