package main.dispatcher.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.json.simple.JSONObject;

@WebService
public interface IDispatcherWSForClient {

	/**
	 * Erstellt ein Angebot.
	 * @param kundenNr
	 * @param bauteilNr
	 * @return
	 */
	@WebMethod
	public String createAngebot(int kundenNr, int bauteilNr);

	/**
	 * Nimmt ein Angebot an.
	 * @param angebotNr
	 * @return
	 */
	@WebMethod
	public String acceptAngebot(int angebotNr);
	
	/**
	 * Liefert Liste aller Angebote
	 * @return
	 */
	@WebMethod
	public String getAllAngebote();
	
	@WebMethod
	public String getAllAuftraege();
//	
//	public List<Kunde> getAllKunden();
//	
	@WebMethod
	public String getAllBauteile();
	
	/**
	 * Echo.
	 * @param s
	 * @return
	 */
	@WebMethod
	public String echo(String s);
}
