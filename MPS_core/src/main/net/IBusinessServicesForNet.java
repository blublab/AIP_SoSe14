package main.net;

import org.json.simple.JSONObject;

public interface IBusinessServicesForNet {

	/**
	 * Erstell Angebot
	 * @param kundenNr
	 * @param bauteilNr
	 * @return
	 */
	JSONObject createAngebot(int kundenNr, int bauteilNr);
	
	/**
	 * Nimmt Angebot an und erstellt Auftrag.
	 * @param angebotNr
	 * @return
	 */
	JSONObject acceptAngebot(int angebotNr);
	
	/**
	 * Liefert alle Angebote.
	 * @return
	 */
	JSONObject getAllAngebote();
	
	/**
	 * Liefert alle Auftraege.
	 * @return
	 */
	JSONObject getAllAuftraege();
	
	/**
	 * Liefert alle Bauteile.
	 * @return
	 */
	JSONObject getAllBauteile();
}
