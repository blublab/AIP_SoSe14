package main.bankAdapter.accessLayer;

import org.json.simple.JSONObject;

import main.bankAdapter.businessLogicLayer.BankAdapterBusinessLogic;
import main.util.INotifier;

public class BankAdapterFacade implements IBankAdapterFuerBuchhaltungServices {
	private BankAdapterBusinessLogic bABL = null;

			
	public BankAdapterFacade() {
		this.bABL = new BankAdapterBusinessLogic();
	}
	
	public void setZahlungseingangNotifier(INotifier notifier) {
		this.bABL.setNotifier(notifier);
	}

	@Override
	public JSONObject retrieveZahlungseingang() {
		return this.bABL.retrieveZahlungseingang();
	}
}
