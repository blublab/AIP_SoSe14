package main.auftragKomponente.accessLayer;

import java.util.Date;

import main.auftragKomponente.accessLayer.Exceptions.InvalidAngebotStatusException;
import main.auftragKomponente.businessLogicLayer.AuftragKomponenteBusinessLogic;
import main.auftragKomponente.dataAccessLayer.Angebot;
import main.auftragKomponente.dataAccessLayer.Angebot.StatusTyp;
import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;
import main.util.GenericDAO;

public class AuftragKomponenteFacade implements IAuftragServices, IAuftragServicesFuerFertigung {

	private AuftragKomponenteBusinessLogic aKBL;
	private GenericDAO<Auftrag> auftragDAO;
	private GenericDAO<Angebot> angebotDAO;

	public AuftragKomponenteFacade(
			IFertigungServicesFuerAuftrag fertigungServices) {
		this.aKBL = new AuftragKomponenteBusinessLogic(fertigungServices);
		this.angebotDAO = new GenericDAO<Angebot>(Angebot.class);
		this.auftragDAO = new GenericDAO<>(Auftrag.class);
	}

	@Override
	public Auftrag createAuftragFuerAngebot(Angebot angebot)
			throws InvalidAngebotStatusException {
		assert angebot != null;
		if (angebot.getStatus() != Angebot.StatusTyp.ANGENOMMEN) {
			throw new InvalidAngebotStatusException();
		}
		;
		Auftrag auftrag = new Auftrag();
		auftrag.setIstAbgeschlossen(false);
		auftrag.setAngebot(angebot);
		auftrag.setBeauftragtAm(new Date());
		angebot.setAuftrag(auftrag);
		auftragDAO.create(auftrag);
		angebotDAO.update(angebot);
		int fertigungsauftragNr = aKBL.erstelleFertigungsauftragFuerAuftrag(auftrag);
		auftrag.setFertigungsauftragNr(fertigungsauftragNr);
		auftragDAO.update(auftrag);
		return auftrag;
	}

	@Override
	public void nimmAngebotAn(Angebot angebot)
			throws InvalidAngebotStatusException {
		assert angebot != null;
		
		//TODO prüfen, ob Angebot abgelaufen ist -> falls ja, Status auf abgelaufen ändern
		if (angebot.getStatus() != Angebot.StatusTyp.ANGELEGT) {
			throw new InvalidAngebotStatusException();
		}
		;
		angebot.setStatus(StatusTyp.ANGENOMMEN);
		angebotDAO.update(angebot);
	}

	@Override
	public Angebot createAngebot(int kundenNr, int bauteilNr) {
		// TODO Prüfen, ob Bauteil in DB?
		// TODO Prüfen, ob Kunde in DB?
		assert kundenNr > 0;
		assert bauteilNr > 0;

		Angebot angebot = new Angebot();
		angebot.setKundenNr(kundenNr);
		angebot.setBauteilNr(bauteilNr);
		// TODO Preis in BL brechnen?
		angebot.setPreis(350);
		angebot.setGueltigAb(new Date());
		angebot.setGueltigBis(new Date(1576800000000L));
		angebot.setStatus(StatusTyp.ANGELEGT);
		angebotDAO.create(angebot);
		return angebot;
	}

	@Override
	public int getBauteilVonAuftrag(int auftragNr) {
		assert auftragNr > 0;
		
		Auftrag auftrag = auftragDAO.read(auftragNr);
		Angebot angebot = auftrag.getAngebot();
		return angebot.getBauteilNr();
	}

	@Override
	public void notifyFertigungAbgeschlossen(int auftragNr) {
		assert auftragNr > 0;
		
		Auftrag auftrag = auftragDAO.read(auftragNr);
		auftrag.setIstAbgeschlossen(true);
		
		//TODO Rechnung erstellen
		System.out.println("Eine Rechnung für den Kunden wurde erstellt. \n");
		//TODO Lieferung anstoßen
		System.out.println("Die Lieferung wurde beauftrag. \n");

		Angebot angebot = auftrag.getAngebot();
		angebot.setStatus(StatusTyp.AGESCHLOSSEN);
		auftragDAO.update(auftrag);
		angebotDAO.update(angebot);
	}

	@Override
	public Angebot readAngebotById(int angebotNr) {
		assert angebotNr > 0;
		
		return angebotDAO.read(angebotNr);
	}

	@Override
	public Auftrag readAuftragById(int auftragNr) {
		assert auftragNr > 0;
		
		return auftragDAO.read(auftragNr);
	}
}
