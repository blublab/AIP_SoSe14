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

	public void erstelleFertigungsauftragFuerAuftrag(Auftrag auftrag) {
//		int fertigungsauftragsNr = fertigungServices.setzeAuftragUm(auftrag
//				.getAuftragsNr());
//		assert fertigungsauftragsNr > 0;
//		auftrag.setFertigungsauftragsNr(fertigungsauftragsNr);
//		return auftrag;
		
		try {
			fertigungServices.setzeAuftragUm(auftrag.getAuftragsNr());
		} catch (AuftragServicesNotSetException e) {
			System.err.println("FertigungsServices wasn't initiatet properly.");
			e.printStackTrace();
		}
	}
}
