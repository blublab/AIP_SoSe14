package main.auftragKomponente.businessLogicLayer;

import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;

public class AuftragKomponenteBusinessLogic {

	IFertigungServicesFuerAuftrag fertigungServices;

	public AuftragKomponenteBusinessLogic(
			IFertigungServicesFuerAuftrag fertigungServices) {
		this.fertigungServices = fertigungServices;
	}

	public int erstelleFertigungsauftragFuerAuftrag(Auftrag auftrag) {
		int fertigungsauftragNr = 0;
		fertigungsauftragNr = fertigungServices.erstelleFertigungsauftragFuerAuftrag(auftrag.getAuftragsNr(), auftrag.getAngebot().getBauteilNr());
		assert fertigungsauftragNr > 0;
		return fertigungsauftragNr;
	}
}
