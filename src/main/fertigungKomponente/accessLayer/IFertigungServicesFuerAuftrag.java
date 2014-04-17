package main.fertigungKomponente.accessLayer;

public interface IFertigungServicesFuerAuftrag {

	/**
	 * Setzt den übergebenen Auftrag um.
	 * @param auftragID ID des Auftrages
	 */
	public int setzeAuftragUm(int auftragID);
}
