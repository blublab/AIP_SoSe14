package main.net;

import java.util.List;

import main.auftragKomponente.accessLayer.AuftragKomponenteFacade;
import main.auftragKomponente.accessLayer.IAuftragServices;
import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.auftragKomponente.accessLayer.Exceptions.InvalidAngebotStatusException;
import main.auftragKomponente.dataAccessLayer.Angebot;
import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.bankAdapter.accessLayer.BankAdapterFacade;
import main.bankAdapter.accessLayer.IBankAdapterFuerBuchhaltungServices;
import main.buchhaltungKomponente.accessLayer.BuchhaltungKomponenteFacade;
import main.buchhaltungKomponente.accessLayer.IBuchhaltungServices;
import main.fertigungKomponente.accessLayer.FertigungKomponenteFacade;
import main.fertigungKomponente.accessLayer.IFertigungServices;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;
import main.fertigungKomponente.dataAccessLayer.Bauteil;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NetBusinessApp implements IBusinessServicesForNet {
	private IAuftragServices auftragServices;
	private IFertigungServices fertigungServices;
	private IAuftragServicesFuerFertigung auftragServicesFuerFertigung;
	private IFertigungServicesFuerAuftrag fertigungsServicesFuerAuftrag;
	private IBankAdapterFuerBuchhaltungServices bankAdapterServicesFuerBuchhaltung;
	private IBuchhaltungServices buchhaltungServices;

	public NetBusinessApp() {
		this.bankAdapterServicesFuerBuchhaltung = new BankAdapterFacade();
		this.buchhaltungServices = new BuchhaltungKomponenteFacade(this.bankAdapterServicesFuerBuchhaltung);
		FertigungKomponenteFacade fertigungFacade = new FertigungKomponenteFacade();
		fertigungServices = fertigungFacade;
		fertigungsServicesFuerAuftrag = fertigungFacade;
		AuftragKomponenteFacade auftragFacade = new AuftragKomponenteFacade(
				fertigungsServicesFuerAuftrag);
		auftragServices = auftragFacade;
		auftragServicesFuerFertigung = auftragFacade;
		fertigungFacade.setAuftragServices(auftragServicesFuerFertigung);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createAngebot(int kundenNr, int bauteilNr) {
		JSONObject response = new JSONObject();
		Angebot angebot = auftragServices.createAngebot(kundenNr, bauteilNr);
		if (angebot != null)
			response.put("response", true);
		else
			response.put("response", false);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject acceptAngebot(int angebotNr) {
		JSONObject response = new JSONObject();
		Auftrag auftrag = null;
		try {
			auftragServices.nimmAngebotAn(angebotNr);
			auftrag = auftragServices.createAuftragFuerAngebot(angebotNr);

		} catch (InvalidAngebotStatusException e) {
			e.printStackTrace();
		}
		if (auftrag != null)
			response.put("response", true);
		else
			response.put("response", false);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getAllAngebote() {
		JSONArray response = new JSONArray();
		List<Angebot> angebotList = auftragServices.readAllAngebote();
		if (angebotList == null) {
			JSONObject jObj = new JSONObject();
			jObj.put("response", false);
			response.add(jObj);
		} else {
			for (Angebot angebot : angebotList) {
				JSONObject jAngebot = new JSONObject();
				jAngebot.put("angebotNr", angebot.getAngebotNr());
				jAngebot.put("gueltigAb", angebot.getGueltigAb().getTime());
				jAngebot.put("gueltigBis", angebot.getGueltigBis().getTime());
				jAngebot.put("preis", angebot.getPreis());
				jAngebot.put("status", new String(angebot.getStatus()
						.toString()));
				jAngebot.put("bauteil", angebot.getBauteilNr());
				Auftrag auftrag = angebot.getAuftrag();
				if (auftrag != null)
					jAngebot.put("auftrag", auftrag.getAuftragsNr());
				else
					jAngebot.put("auftrag", "0");
				response.add(jAngebot);
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getAllAuftraege() {
		JSONArray response = new JSONArray();
		List<Auftrag> auftragList = auftragServices.readAllAuftraege();
		if (auftragList == null) {
			JSONObject jObj = new JSONObject();
			jObj.put("response", false);
			response.add(jObj);
		} else {
			for (Auftrag auftrag : auftragList) {
				JSONObject jAuftrag = new JSONObject();
				jAuftrag.put("auftragNr", auftrag.getAuftragsNr());
				jAuftrag.put("istAbgeschlossen", auftrag.getIstAbgeschlossen());
				jAuftrag.put("beauftragtAm", auftrag.getBeauftragtAm()
						.getTime());
				response.add(jAuftrag);
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getAllBauteile() {
		JSONArray response = new JSONArray();
		List<Bauteil> bauteilListe = fertigungServices.readAllBauteile();
		if (bauteilListe == null) {
			JSONObject jObj = new JSONObject();
			jObj.put("response", false);
			response.add(jObj);
		} else {
			for (Bauteil bauteil : bauteilListe) {
				JSONObject jBauteil = new JSONObject();
				jBauteil.put("bauteilNr", bauteil.getBauteilNr());
				jBauteil.put("name", bauteil.getName());
				response.add(jBauteil);
			}
		}
		return response;
	}
}
