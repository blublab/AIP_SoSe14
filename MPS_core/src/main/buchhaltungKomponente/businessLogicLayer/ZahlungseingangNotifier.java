package main.buchhaltungKomponente.businessLogicLayer;

import org.json.simple.JSONObject;

import main.bankAdapter.accessLayer.IBankAdapterFuerBuchhaltungServices;
import main.util.INotifier;

public class ZahlungseingangNotifier implements INotifier {
	private IBankAdapterFuerBuchhaltungServices bankAdapterFuerBuchhaltungServices = null;
	private BuchhaltungKomponenteBusinessLogic buchhaltungKomponenteBusinessLogic;

	public ZahlungseingangNotifier(IBankAdapterFuerBuchhaltungServices bfbs, BuchhaltungKomponenteBusinessLogic bkbl) {
		this.bankAdapterFuerBuchhaltungServices = bfbs;
		this.buchhaltungKomponenteBusinessLogic = bkbl;
	}
	
	@Override
	public void poke() {
		JSONObject message = this.bankAdapterFuerBuchhaltungServices.retrieveZahlungseingang();
		System.out.println("Buchhaltung ueber Zahlungeingang informiert: " + message.toJSONString());
		int betrag = ((Long) message.get("betrag")).intValue();
		int rechnungsNr = ((Long) message.get("rechnungsNr")).intValue();
		this.buchhaltungKomponenteBusinessLogic.verarbeiteZahlungseingangFuerKundenRechnung(rechnungsNr, betrag);
	}
}
