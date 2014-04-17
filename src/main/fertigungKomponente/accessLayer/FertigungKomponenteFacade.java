package main.fertigungKomponente.accessLayer;

import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;
import main.fertigungKomponente.dataAccessLayer.*;
import main.util.GenericDAO;

public class FertigungKomponenteFacade implements IFertigungServices,
		IFertigungServicesFuerAuftrag {

	private IAuftragServicesFuerFertigung auftragServices;
	private GenericDAO<Bauteil> bauteilDAO;
	private GenericDAO<Fertigungsauftrag> fertigungsauftragDAO;
	private boolean auftragServicesSet;

	public FertigungKomponenteFacade() {
		this.bauteilDAO = new GenericDAO<Bauteil>(Bauteil.class);
		this.fertigungsauftragDAO = new GenericDAO<Fertigungsauftrag>(Fertigungsauftrag.class);
	}
	
	@Override
	public void setzeAuftragUm(int auftragNr) throws AuftragServicesNotSetException {
		assert auftragNr > 0;
		
		if(!auftragServicesSet) {
			throw new AuftragServicesNotSetException();
		}
		int bauteilNr = auftragServices.getBauteilVonAuftrag(auftragNr);
		Bauteil bauteil = bauteilDAO.read(bauteilNr);
		
		assert bauteil != null;
		
		Fertigungsauftrag fertigungsauftrag = new Fertigungsauftrag();
		fertigungsauftrag.setAuftragNr(auftragNr);
		fertigungsauftrag.setBauteil(bauteil);
		fertigungsauftragDAO.create(fertigungsauftrag);
		boolean complex = checkComplexity(bauteil);
		if(complex) {
			notifyProduktionsmitarbeiter(fertigungsauftrag);
		} else {
			auftragServices.notifyFertigungAbgeschlossen(fertigungsauftrag.getAuftragNr());
		}
	}

	public void setAuftragServices(IAuftragServicesFuerFertigung auftragServices) {
		if(auftragServices != null) {
			this.auftragServices = auftragServices;
			this.auftragServicesSet = true;
		}
	}
	
	private boolean checkComplexity(Bauteil bauteil) {
		return (bauteil.getStueckliste() != null);
	}
	
	private void notifyProduktionsmitarbeiter(Fertigungsauftrag fertigungsauftrag){
		// *tuuuut *tuuuut* ...
	}

	@Override
	public void completeFertigungsauftrag(Fertigungsauftrag fertigungsauftrag) {
		assert fertigungsauftrag != null;
		
		auftragServices.notifyFertigungAbgeschlossen(fertigungsauftrag.getAuftragNr());
	}
}
