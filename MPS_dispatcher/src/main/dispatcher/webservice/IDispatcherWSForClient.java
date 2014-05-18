package main.dispatcher.webservice;

import java.util.List;

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
	public String createAngebot(int kundenNr, int bauteilNr);

	/**
	 * Nimmt ein Angebot an.
	 * @param angebotNr
	 * @return
	 */
	public String nimmAngebotAn(int angebotNr);
	
	/**
	 * Liefert Liste aller Angebote
	 * @return
	 */
	public String getAllAngebote();
	
	public String getAllAuftraege();
//	
//	public List<Kunde> getAllKunden();
//	
//	public List<Bauteil> getAllBauteile();
	
	/**
	 * Echo.
	 * @param s
	 * @return
	 */
	public String echo(String s);
}
