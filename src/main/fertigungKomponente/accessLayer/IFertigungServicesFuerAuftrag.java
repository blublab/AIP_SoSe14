package main.fertigungKomponente.accessLayer;

import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;

public interface IFertigungServicesFuerAuftrag {

	/**
	 * Setzt den übergebenen Auftrag um.
	 * @param auftragNr ID des Auftrages
	 * @throws AuftragServicesNotSetException 
	 */
	public void setzeAuftragUm(int auftragNr) throws AuftragServicesNotSetException;
}
