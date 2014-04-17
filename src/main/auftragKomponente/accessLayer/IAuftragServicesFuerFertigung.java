package main.auftragKomponente.accessLayer;

public interface IAuftragServicesFuerFertigung {

	/**
	 * Gibt die ID des Bauteils zurück, das für den Fertigungsauftrag hergestellt werden soll
	 * @param auftragNr ID des Auftrags
	 * @return ID des Bauteils
	 */
	public int getBauteilVonAuftrag(int auftragNr);
	
	/**
	 * Hook zum Weiterverarbeiten des Auftrags, nachdem die Fertigung abgeschlossen wurde.
	 * @param auftragNr
	 */
	public void notifyFertigungAbgeschlossen(int auftragNr);
}
