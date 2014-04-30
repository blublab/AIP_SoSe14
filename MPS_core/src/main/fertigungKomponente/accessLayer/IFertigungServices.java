package main.fertigungKomponente.accessLayer;

import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;
import main.fertigungKomponente.dataAccessLayer.Fertigungsauftrag;

public interface IFertigungServices {
	
	/**
	 * Setzt den �bergebenen Fertigungsauftrag um.
	 * @param fertigungsauftrag
	 * @throws AuftragServicesNotSetException 
	 */
	public void starteFertigungsauftrag(Fertigungsauftrag fertigungsauftrag);

	/**
	 * Schnittstelle f�r Mitarbeiter zum Abschliessen eines Frachtauftrags.
	 * @param fertigungsauftrag
	 * @throws AuftragServicesNotSetException 
	 */
	public void completeFertigungsauftrag(Fertigungsauftrag fertigungsauftrag) throws AuftragServicesNotSetException;
	
	/**
	 * Gibt den Fertigungsauftrag mit der �bergebenen ID zur�ck.
	 * @param fertigungsauftragNr
	 * @return Fertigungsauftrag
	 */
	public Fertigungsauftrag readFertigungsauftragById(int fertigungsauftragNr);

	/**
	 * Setzt IAuftragServicesFuerFertigung
	 * @param auftragServices
	 */
	public void setAuftragServices(IAuftragServicesFuerFertigung auftragServices);
}
