package main.bankAdapter.accessLayer;

import main.util.INotifier;

import org.json.simple.JSONObject;

public interface IBankAdapterFuerBuchhaltungServices {
	
	/**
	 * 
	 * @return
	 */
	public JSONObject retrieveZahlungseingang();
	
	/**
	 * Dependency injection
	 * @param notifier
	 */
	public void setZahlungseingangNotifier(INotifier notifier);
}
