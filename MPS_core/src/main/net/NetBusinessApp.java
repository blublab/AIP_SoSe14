package main.net;

import java.util.List;

import jdk.nashorn.internal.scripts.JS;
import main.auftragKomponente.accessLayer.AuftragKomponenteFacade;
import main.auftragKomponente.accessLayer.IAuftragServices;
import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.auftragKomponente.accessLayer.Exceptions.InvalidAngebotStatusException;
import main.auftragKomponente.dataAccessLayer.Angebot;
import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.FertigungKomponenteFacade;
import main.fertigungKomponente.accessLayer.IFertigungServices;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;

import org.json.simple.JSONObject;

import sun.awt.RepaintArea;

public class NetBusinessApp implements IBusinessServicesForNet{
	private IAuftragServices auftragServices;
	private IFertigungServices fertigungServices;
	private IAuftragServicesFuerFertigung auftragServicesFuerFertigung;
	private IFertigungServicesFuerAuftrag fertigungsServicesFuerAuftrag;
	
	public NetBusinessApp()
	{
		FertigungKomponenteFacade fertigungFacade = new FertigungKomponenteFacade();
		fertigungServices = fertigungFacade;
		fertigungsServicesFuerAuftrag = fertigungFacade;
		AuftragKomponenteFacade auftragFacade = new AuftragKomponenteFacade(
				fertigungsServicesFuerAuftrag);
		auftragServices = auftragFacade;
		auftragServicesFuerFertigung = auftragFacade;
		fertigungFacade.setAuftragServices(auftragServicesFuerFertigung);
	}
	
	@Override
	public JSONObject createAngebot(int kundenNr, int bauteilNr)
	{
		JSONObject response = new JSONObject();
		Angebot angebot = auftragServices.createAngebot(kundenNr, bauteilNr);
		if(angebot != null)	
			response.put("response", true);
		else
			response.put("response", false);
		return response;
	}

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
		if(auftrag != null)	
			response.put("response", true);
		else
			response.put("response", false);
		return response;
	}

	@Override
	public JSONObject getAllAngebote() {
		JSONObject response = new JSONObject();
		List<Angebot> angebotList = auftragServices.readAllAngebote();
		for (Angebot angebot : angebotList) {
			response.put("angebotNr", angebot.getAngebotNr());
			response.put("gueltigAb", angebot.getGueltigAb().getTime());
			response.put("gueltigBis", angebot.getGueltigBis().getTime());
			response.put("preis", angebot.getPreis());
			response.put("status", new String(angebot.getStatus().toString()));
			response.put("bauteil", angebot.getBauteilNr());
			response.put("auftrag", angebot.getAuftrag().getAuftragsNr());
		}
		return response;
	}

	@Override
	public JSONObject getAllAuftraege() {
		JSONObject response = new JSONObject();
		List<Auftrag> auftragList = auftragServices.readAllAuftraege();		
		for (Auftrag auftrag : auftragList) {
			response.put(", value)
		}
		return null;
	}

	@Override
	public JSONObject getAllBauteile() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
