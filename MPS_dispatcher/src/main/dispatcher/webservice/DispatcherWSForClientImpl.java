package main.dispatcher.webservice;

import java.util.List;

import javax.jws.WebService;

import org.json.simple.JSONObject;

import javax.xml.ws.BindingType;

import main.dispatcher.engine.RQHandler;

@WebService(endpointInterface="main.dispatcher.webservice.IDispatcherWSForClient")
@BindingType(value="http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class DispatcherWSForClientImpl implements IDispatcherWSForClient {

	private RQHandler rqHandler = null;
	
	public DispatcherWSForClientImpl() {
		this.rqHandler = new RQHandler();
	}
	
	@Override
	public String createAngebot(int kundenNr, int bauteilNr) {
		return rqHandler.createAngebot(kundenNr, bauteilNr);
	}

	@Override
	public String nimmAngebotAn(int angebotNr) {
		return rqHandler.nimmAngebotAn(angebotNr);
	}
	
	@Override
	public String getAllAngebote() {
		return rqHandler.getAllAngebote();
	}
	
	@Override
	public String getAllAuftraege() {
		return rqHandler.getAllAuftraege();
	}

	@Override
	public String echo(String s) {
		System.out.println("echoing: " + s);
		return "ECHO: " + s;
	}

}
