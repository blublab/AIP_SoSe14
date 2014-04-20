package main.auftragKomponente.businessLogicLayer;

import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;
import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;

public class AuftragKomponenteBusinessLogic {

	IFertigungServicesFuerAuftrag fertigungServices;

	public AuftragKomponenteBusinessLogic(
			IFertigungServicesFuerAuftrag fertigungServices) {
		this.fertigungServices = fertigungServices;
	}

	public int erstelleFertigungsauftragFuerAuftrag(Auftrag auftrag) {
		int fertigungsauftragNr = 0;
		try {
			fertigungsauftragNr = fertigungServices.erstelleFertigungsauftragFuerAuftrag(auftrag.getAuftragsNr());
		} catch (AuftragServicesNotSetException e) {
			System.err.println("FertigungsServices wasn't initiatet properly.");
			e.printStackTrace();
		}
		return fertigungsauftragNr;
	}
}
