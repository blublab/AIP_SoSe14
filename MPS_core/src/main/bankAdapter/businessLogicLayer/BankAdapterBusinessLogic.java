package main.bankAdapter.businessLogicLayer;

import java.util.ArrayDeque;
import java.util.Queue;
import main.util.INotifier;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BankAdapterBusinessLogic {
	private BankReceiver bReceiver = null;
	private INotifier notifier = null;
	private Queue<JSONObject> messageQueue = null;
	
	public BankAdapterBusinessLogic() {
		this.bReceiver = new BankReceiver(this);
		this.messageQueue = new ArrayDeque<JSONObject>();
		bReceiver.start();
	}
	
	synchronized public void verarbeiteZahlungseingang(String message) {
		message.trim();
		
		JSONObject jsonMessage = (JSONObject) JSONValue.parse(message);
		Integer rechnungsNr = ((Long) jsonMessage.get("rechnungsNr")).intValue();
		Integer betrag = ((Long) jsonMessage.get("betrag")).intValue();
		
		assert(rechnungsNr != null && betrag != null);
		
		this.messageQueue.add(jsonMessage);
		if (notifier != null) {
			notifier.poke();
		} else {
			System.out.println("[Bankadapter] Warning: no notifier registered - Message queued");
		}
	}
	
	synchronized public JSONObject retrieveZahlungseingang() {
		return this.messageQueue.remove();
	}

	public void setNotifier(INotifier notifier) {
		System.out.println("ZahlungseingangNotifier set");
		this.notifier = notifier;
	}
}
