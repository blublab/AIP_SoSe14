package main.buchhaltungKomponente.businessLogicLayer;

import java.util.Date;
import java.util.Set;

import main.bankAdapter.accessLayer.IBankAdapterFuerBuchhaltungServices;
import main.buchhaltungKomponente.businessLogicLayer.ZahlungseingangNotifier;
import main.buchhaltungKomponente.dataAccessLayer.Kundenrechnung;
import main.buchhaltungKomponente.dataAccessLayer.Zahlungseingang;
import main.util.GenericDAO;
import main.util.INotifier;

public class BuchhaltungKomponenteBusinessLogic {
	private IBankAdapterFuerBuchhaltungServices bankAdapterFuerBuchhaltungServices;
	private INotifier zahlungseingangNotifier;

	private GenericDAO<Zahlungseingang> zahlungseingangDAO;
	private GenericDAO<Kundenrechnung> kundenrechnungDAO;

	public BuchhaltungKomponenteBusinessLogic(
			IBankAdapterFuerBuchhaltungServices bfbs) {
		this.bankAdapterFuerBuchhaltungServices = bfbs;
		this.zahlungseingangNotifier = new ZahlungseingangNotifier(bfbs, this);
		this.bankAdapterFuerBuchhaltungServices
				.setZahlungseingangNotifier(this.zahlungseingangNotifier);

		this.kundenrechnungDAO = new GenericDAO<Kundenrechnung>(
				Kundenrechnung.class);
		this.zahlungseingangDAO = new GenericDAO<Zahlungseingang>(
				Zahlungseingang.class);
	}

	public void verarbeiteZahlungseingangFuerKundenRechnung(int rechnungNr,
			int betrag) throws IllegalArgumentException {
		assert (rechnungNr > 0);
		assert (betrag > 0);

		Kundenrechnung rechnung = readKundenrechnungById(rechnungNr);

		Zahlungseingang ze = new Zahlungseingang();
		ze.setBetrag(betrag);
		ze.setKundenrechnung(rechnung);
		ze.setEingegangenAm(new Date());

		zahlungseingangDAO.create(ze);
		zahlungseingangDAO.update(ze);

		int sum = betrag;
		Set<Zahlungseingang> zEingaenge = rechnung.getZahlungseingaenge();

		for (Zahlungseingang zahlEin : zEingaenge) {
			sum += zahlEin.getBetrag();
		}

		System.out.println("Rechnung " + rechnung.getKundenrechnungNr()
				+ "Restbetrag: " + (rechnung.getBetrag() - sum));

		if (sum >= rechnung.getBetrag()) {
			rechnung.setIstBezahlt(true);
			kundenrechnungDAO.update(rechnung);
			System.out.println("Kundenrechnung "
					+ rechnung.getKundenrechnungNr() + " wurde bezahlt.");
		}
	}

	public Kundenrechnung readKundenrechnungById(int rechnungNr) {
		assert rechnungNr > 0;

		return kundenrechnungDAO.read(rechnungNr);
	}

	public void createKundenRechnungFuerAuftrag(int auftragID, int betrag) {
		assert (auftragID > 0);

		Kundenrechnung kr = new Kundenrechnung();
		kr.setAuftragsNr(auftragID);
		kr.setBetrag(betrag);
		kr.setIstBezahlt(false);
		kr.setErstelltAm(new Date());

		kundenrechnungDAO.create(kr);
		kundenrechnungDAO.update(kr);
	}
}
