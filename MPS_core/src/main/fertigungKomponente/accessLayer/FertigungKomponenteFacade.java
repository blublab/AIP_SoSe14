package main.fertigungKomponente.accessLayer;

import java.util.ArrayList;
import java.util.List;

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
	public int erstelleFertigungsauftragFuerAuftrag(int auftragNr, int bauteilNr)
	{
		assert auftragNr > 0;

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
		if (auftragServicesSet == false) {
			this.auftragServices = auftragServices;
			this.auftragServicesSet = true;
		}
	}

	@Override
	public void completeFertigungsauftrag(Fertigungsauftrag fertigungsauftrag) throws AuftragServicesNotSetException {
		assert fertigungsauftrag != null;

		System.out.println("Das Produkt '" + fertigungsauftrag.getBauteil().getName() + "' wurde hergestellt. \n");
		if(auftragServicesSet == false) {
			throw new AuftragServicesNotSetException();
		}
		auftragServices.notifyFertigungAbgeschlossen(fertigungsauftrag
				.getAuftragNr());
	}

	@Override
	public void starteFertigungsauftrag(Fertigungsauftrag fertigungsauftrag){
		boolean complex = checkComplexity(fertigungsauftrag.getBauteil());
		if (complex) {
			notifyProduktionsmitarbeiter(fertigungsauftrag);
		} else {
			try {
				completeFertigungsauftrag(fertigungsauftrag);
			} catch (AuftragServicesNotSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	@Override
	public List<Bauteil> readAllBauteile() {
		List<Bauteil> bauteilList = new ArrayList<Bauteil>();
		bauteilList = bauteilDAO.readAll();
		return bauteilList;
	}
}
