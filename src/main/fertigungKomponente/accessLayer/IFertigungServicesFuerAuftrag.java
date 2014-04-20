package main.fertigungKomponente.accessLayer;

import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;

public interface IFertigungServicesFuerAuftrag {

	/**
	 * Erstellt Fertigungsauftrag für den Auftrag
	 * @param auftragNr ID des Auftrages
	 * @return Fertigungsauftrag ID
	 * @throws AuftragServicesNotSetException 
	 */
	public int erstelleFertigungsauftragFuerAuftrag(int auftragNr) throws AuftragServicesNotSetException;
}
