package main.appBuilder;

import main.auftragKomponente.accessLayer.AuftragKomponenteFacade;
import main.auftragKomponente.accessLayer.IAuftragServices;
import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.fertigungKomponente.accessLayer.FertigungKomponenteFacade;
import main.fertigungKomponente.accessLayer.IFertigungServices;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;

public class AppBuilder {
	
	private IAuftragServices auftragServices;
	private IFertigungServices fertigungServices;
	private IAuftragServicesFuerFertigung auftragServicesFuerFertigung;
	private IFertigungServicesFuerAuftrag fertigungsServicesFuerAuftrag;
	
	public AppBuilder()
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
	
	public static void main(String[] args)
	{
		
	}
}
