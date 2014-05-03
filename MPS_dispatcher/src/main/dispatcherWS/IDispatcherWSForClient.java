package main.dispatcherWS;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IDispatcherWSForClient {

	/**
	 * Erstellt ein Angebot.
	 * @param kundenNr
	 * @param bauteilNr
	 * @return
	 */
	@WebMethod
	public boolean createAngebot(int kundenNr, int bauteilNr);

	/**
	 * Nimmt ein Angebot an.
	 * @param angebotNr
	 * @return
	 */
	@WebMethod
	public boolean nimmAngebotAn(int angebotNr);
	
	/**
	 * Echo.
	 * @param s
	 * @return
	 */
	@WebMethod
	public String echo(String s);
}
