package main.dispatcherWS;

import javax.jws.WebService;

@WebService
public interface IDispatcherWSForClient {

	/**
	 * Erstellt ein Angebot.
	 * @param kundenNr
	 * @param bauteilNr
	 * @return
	 */
	public boolean createAngebot(int kundenNr, int bauteilNr);

	/**
	 * Nimmt ein Angebot an.
	 * @param angebotNr
	 * @return
	 */
	public boolean nimmAngebotAn(int angebotNr);
	
	/**
	 * Echo.
	 * @param s
	 * @return
	 */
	public String echo(String s);
}
