package main.auftragKomponente.accessLayer;

public interface IAuftragServicesFuerFertigung {
	
	/**
	 * Hook zum Weiterverarbeiten des Auftrags, nachdem die Fertigung abgeschlossen wurde.
	 * @param auftragNr
	 */
	public void notifyFertigungAbgeschlossen(int auftragNr);
}
