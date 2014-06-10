package main.buchhaltungKomponente.accessLayer;

import main.bankAdapter.accessLayer.IBankAdapterFuerBuchhaltungServices;
import main.buchhaltungKomponente.businessLogicLayer.BuchhaltungKomponenteBusinessLogic;

public class BuchhaltungKomponenteFacade implements IBuchhaltungServices {
	private IBankAdapterFuerBuchhaltungServices bankAdapterFuerBuchhaltungServices;
	private BuchhaltungKomponenteBusinessLogic buchhaltungKomponenteBusinessLogic;
	
	public BuchhaltungKomponenteFacade(IBankAdapterFuerBuchhaltungServices bfbs) {
		this.bankAdapterFuerBuchhaltungServices = bfbs;
		this.buchhaltungKomponenteBusinessLogic = new BuchhaltungKomponenteBusinessLogic(this.bankAdapterFuerBuchhaltungServices);
	}
	
	public void createKundenRechnungFuerAuftrag(int auftragID, int betrag) throws IllegalArgumentException {
		this.buchhaltungKomponenteBusinessLogic.createKundenRechnungFuerAuftrag(auftragID, betrag);
	}
}
