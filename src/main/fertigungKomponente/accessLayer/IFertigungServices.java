package main.fertigungKomponente.accessLayer;

import main.fertigungKomponente.dataAccessLayer.Fertigungsauftrag;

public interface IFertigungServices {
	
	/**
	 * Setzt den übergebenen Fertigungsauftrag um.
	 * @param fertigungsauftrag
	 */
	public void starteFertigungsauftrag(Fertigungsauftrag fertigungsauftrag);

	/**
	 * Schnittstelle für Mitarbeiter zum Abschliessen eines Frachtauftrags.
	 * @param fertigungsauftrag
	 */
	public void completeFertigungsauftrag(Fertigungsauftrag fertigungsauftrag);
	
	/**
	 * Gibt den Fertigungsauftrag mit der übergebenen ID zurück.
	 * @param fertigungsauftragNr
	 * @return Fertigungsauftrag
	 */
	public Fertigungsauftrag readFertigungsauftragById(int fertigungsauftragNr);
}
