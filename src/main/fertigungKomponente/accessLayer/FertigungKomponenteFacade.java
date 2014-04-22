package main.fertigungKomponente.accessLayer;

import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;
import main.fertigungKomponente.dataAccessLayer.Bauteil;
import main.fertigungKomponente.dataAccessLayer.Fertigungsauftrag;
import main.util.GenericDAO;

public class FertigungKomponenteFacade implements IFertigungServices,
		IFertigungServicesFuerAuftrag {

	private IAuftragServicesFuerFertigung auftragServices;
	private GenericDAO<Bauteil> bauteilDAO;
	private GenericDAO<Fertigungsauftrag> fertigungsauftragDAO;
	private boolean auftragServicesSet;

	public FertigungKomponenteFacade() {
		this.bauteilDAO = new GenericDAO<Bauteil>(Bauteil.class);
		this.fertigungsauftragDAO = new GenericDAO<Fertigungsauftrag>(
				Fertigungsauftrag.class);
	}

	@Override
	public int erstelleFertigungsauftragFuerAuftrag(int auftragNr)
			throws AuftragServicesNotSetException {
		assert auftragNr > 0;

		if (!auftragServicesSet) {
			throw new AuftragServicesNotSetException();
		}
		int bauteilNr = auftragServices.getBauteilVonAuftrag(auftragNr);
		Bauteil bauteil = bauteilDAO.read(bauteilNr);

		assert bauteil != null;

		Fertigungsauftrag fertigungsauftrag = new Fertigungsauftrag();
		fertigungsauftrag.setAuftragNr(auftragNr);
		fertigungsauftrag.setBauteil(bauteil);
		fertigungsauftragDAO.create(fertigungsauftrag);
		return fertigungsauftrag.getFertigungsauftragsNr();
	}

	@Override
	public void setAuftragServices(IAuftragServicesFuerFertigung auftragServices) {
		if (auftragServices != null) {
			this.auftragServices = auftragServices;
			this.auftragServicesSet = true;
		}
	}

	@Override
	public void completeFertigungsauftrag(Fertigungsauftrag fertigungsauftrag) {
		assert fertigungsauftrag != null;

		System.out.println("Das Produkt '" + fertigungsauftrag.getBauteil().getName() + "' wurde hergestellt. \n");
		auftragServices.notifyFertigungAbgeschlossen(fertigungsauftrag
				.getAuftragNr());
	}

	@Override
	public void starteFertigungsauftrag(Fertigungsauftrag fertigungsauftrag) {
		boolean complex = checkComplexity(fertigungsauftrag.getBauteil());
		if (complex) {
			notifyProduktionsmitarbeiter(fertigungsauftrag);
		} else {
			System.out.println("Das Produkt '" + fertigungsauftrag.getBauteil().getName() + "' wurde hergestellt. \n");
			auftragServices.notifyFertigungAbgeschlossen(fertigungsauftrag
					.getAuftragNr());
		}
	}

	private boolean checkComplexity(Bauteil bauteil) {
		return (bauteil.getStueckliste() != null);
	}

	private void notifyProduktionsmitarbeiter(
			Fertigungsauftrag fertigungsauftrag) {
		// *tuuuut *tuuuut* ...
		System.out.println("Ein Produktionsmitarbeiter wurde benachrichtigt. \n");
	}

	@Override
	public Fertigungsauftrag readFertigungsauftragById(int fertigungsauftragNr) {
		assert fertigungsauftragNr > 0;
		
		return fertigungsauftragDAO.read(fertigungsauftragNr);
	}
}
