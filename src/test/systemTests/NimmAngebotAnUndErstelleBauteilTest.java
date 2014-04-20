package test.systemTests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import main.auftragKomponente.accessLayer.AuftragKomponenteFacade;
import main.auftragKomponente.accessLayer.IAuftragServices;
import main.auftragKomponente.accessLayer.IAuftragServicesFuerFertigung;
import main.auftragKomponente.accessLayer.Exceptions.InvalidAngebotStatusException;
import main.auftragKomponente.dataAccessLayer.Angebot;
import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.FertigungKomponenteFacade;
import main.fertigungKomponente.accessLayer.IFertigungServices;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;
import main.fertigungKomponente.dataAccessLayer.Bauteil;
import main.fertigungKomponente.dataAccessLayer.Fertigungsauftrag;
import main.fertigungKomponente.dataAccessLayer.Fertigungsplan;
import main.fertigungKomponente.dataAccessLayer.Stueckliste;
import main.fertigungKomponente.dataAccessLayer.Stuecklistenposition;
import main.fertigungKomponente.dataAccessLayer.Vorgang;
import main.fertigungKomponente.dataAccessLayer.Vorgang.ArtTyp;
import main.util.GenericDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NimmAngebotAnUndErstelleBauteilTest {
	IAuftragServices auftragServices;
	IFertigungServices fertigungServices;
	IAuftragServicesFuerFertigung auftragServicesFuerFertigung;
	IFertigungServicesFuerAuftrag fertigungsServicesFuerAuftrag;

	@Before
	public void setUp() throws Exception {
		// Setup components
		FertigungKomponenteFacade fertigungFacade = new FertigungKomponenteFacade();
		fertigungServices = fertigungFacade;
		fertigungsServicesFuerAuftrag = fertigungFacade;
		AuftragKomponenteFacade auftragFacade = new AuftragKomponenteFacade(
				fertigungsServicesFuerAuftrag);
		auftragServices = auftragFacade;
		auftragServicesFuerFertigung = auftragFacade;
		fertigungFacade.setAuftragServices(auftragServicesFuerFertigung);
	}

	//Fills Database
	@BeforeClass
	public static void fillDatabase() {
		System.out.println("Initializing DB ...");

		// Setup DAOs
		GenericDAO<Bauteil> bauteilDAO = new GenericDAO<Bauteil>(Bauteil.class);
		GenericDAO<Fertigungsplan> fertigungsplanDAO = new GenericDAO<Fertigungsplan>(Fertigungsplan.class);
		GenericDAO<Vorgang> vorgangDAO = new GenericDAO<Vorgang>(Vorgang.class);
		GenericDAO<Stueckliste> stuecklisteDAO = new GenericDAO<Stueckliste>(Stueckliste.class);
		GenericDAO<Stuecklistenposition> stuecklistenpositionDAO = new GenericDAO<Stuecklistenposition>(Stuecklistenposition.class);
		
		// Setup einfaches Bauteil
		Bauteil einfachesBauteil = new Bauteil(); 
		einfachesBauteil.setName("Kabel");
		bauteilDAO.create(einfachesBauteil); // create bauteil
		Vorgang v1 = new Vorgang();
		v1.setVorgangTyp(ArtTyp.BEREITSTELLUNG);
		v1.setMaschienenZeit(new Date());
		v1.setPersonenZeit(new Date());
		v1.setRuestzeit(new Date());
		vorgangDAO.create(v1); // create vorgang
		Fertigungsplan fp1 = new Fertigungsplan();
		fp1.addVorgang(v1);
		fp1.setBauteil(einfachesBauteil);
		fertigungsplanDAO.create(fp1); // create fertigungsplan
		einfachesBauteil.setFertigungsplan(fp1);
		bauteilDAO.update(einfachesBauteil); // update bauteil
		
		// Setup komplexes Bauteil
		Bauteil komplexesBauteil = new Bauteil();
		komplexesBauteil.setName("Mähdrescher");
		bauteilDAO.create(komplexesBauteil); // create bauteil
		Vorgang v2 = new Vorgang();
		v2.setVorgangTyp(ArtTyp.BEREITSTELLUNG);
		v2.setMaschienenZeit(new Date());
		v2.setPersonenZeit(new Date());
		v2.setRuestzeit(new Date());
		vorgangDAO.create(v2); // create vorgang
		Vorgang v3 = new Vorgang();
		v3.setVorgangTyp(ArtTyp.BEREITSTELLUNG);
		v3.setMaschienenZeit(new Date());
		v3.setPersonenZeit(new Date());
		v3.setRuestzeit(new Date());
		vorgangDAO.create(v3); // create vorgang
		Vorgang v4 = new Vorgang();
		v4.setVorgangTyp(ArtTyp.BEREITSTELLUNG);
		v4.setMaschienenZeit(new Date());
		v4.setPersonenZeit(new Date());
		v4.setRuestzeit(new Date());
		vorgangDAO.create(v4); // create vorgang
		Vorgang v5 = new Vorgang();
		v5.setVorgangTyp(ArtTyp.MONTAGE);
		v5.setMaschienenZeit(new Date());
		v5.setPersonenZeit(new Date());
		v5.setRuestzeit(new Date());
		vorgangDAO.create(v5); // create vorgang
		Fertigungsplan fp2 = new Fertigungsplan();
		fp2.addVorgang(v2);
		fp2.addVorgang(v3);
		fp2.addVorgang(v4);
		fp2.addVorgang(v5);
		fp2.setBauteil(komplexesBauteil);
		fertigungsplanDAO.create(fp2); // create fertigungsplan
		Stuecklistenposition slp1 = new Stuecklistenposition();
		slp1.setMenge(3);
		slp1.setName("Klinge");
		stuecklistenpositionDAO.create(slp1); // create stuecklistenposition
		Stuecklistenposition slp2 = new Stuecklistenposition();
		slp2.setMenge(1);
		slp2.setName("Gehäuse");
		stuecklistenpositionDAO.create(slp2); // create stuecklistenposition
		Stuecklistenposition slp3 = new Stuecklistenposition();
		slp3.setMenge(12);
		slp3.setName("Griff");
		stuecklistenpositionDAO.create(slp3); // create stuecklistenposition
		Stueckliste sl = new Stueckliste();
		sl.addStuecklistenposition(slp1);
		sl.addStuecklistenposition(slp2);
		sl.addStuecklistenposition(slp3);
		sl.setGueltigAb(new Date());
		sl.setGueltigBis(new Date(1576800000000L));
		stuecklisteDAO.create(sl); // create stueckliste
		komplexesBauteil.setFertigungsplan(fp2);
		komplexesBauteil.setStueckliste(sl);
		komplexesBauteil.setStuecklistenposition(sl.getStuecklistenposition());
		bauteilDAO.update(komplexesBauteil);
		
		System.out.println("Initializing DB complete.");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNimmAngebotAnUndErstelleEinfachesBauteilTestSuccess() throws InvalidAngebotStatusException {
		Angebot angebot = auftragServices.createAngebot(1, 1); // creats angebot for customer "1" for bauteil "1" (simple)
		System.out.println("Folgendes Angebot wurde erstellt: ");
		System.out.println(angebot + "\n");
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");

		System.out.println("Der Kunde hat das Angebot Nummer " + angebot.getAngebotNr() + " angenommen. \n");
		auftragServices.nimmAngebotAn(angebot); // sets status to ANGENOMMEN
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");
		
		Auftrag auftrag = auftragServices.createAuftragFuerAngebot(angebot); // creates auftrag for angebot
		System.out.println("Folgender Auftrag wurde für das Angebot Nr. " + angebot.getAngebotNr() + " erstellt :");
		System.out.println(auftrag + "\n");
		System.out.println("Folgender Fertigungsauftrag wurde für den Auftrag Nr. " + auftrag.getAuftragsNr() + " erstellt: ");
		Fertigungsauftrag fertigungsauftrag = fertigungServices.readFertigungsauftragById(auftrag.getFertigungsauftragNr()); // gets fertigungsauftrag for auftrag
		System.out.println(fertigungsauftrag + "\n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");

		System.out.println("Der Fertigungsauftrag Nr. " + fertigungsauftrag.getFertigungsauftragsNr() + " wurde gestartet. \n");
		fertigungServices.starteFertigungsauftrag(fertigungsauftrag); // starts fertigungsauftrag
		
		angebot = auftragServices.readAngebotById(angebot.getAngebotNr());
		auftrag = auftragServices.readAuftragById(auftrag.getAuftragsNr());
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");

		assertEquals(angebot.getStatus(), Angebot.StatusTyp.AGESCHLOSSEN);
		assertEquals(auftrag.getIstAbgeschlossen(), true);
	}
	
	@Test
	public void testNimmAngebotAnUndErstelleKomplexesBauteilTestSuccess() throws InvalidAngebotStatusException {
		Angebot angebot = auftragServices.createAngebot(1, 2); // creats angebot for customer "1" for bauteil "2" (complex)
		System.out.println("Folgendes Angebot wurde erstellt: ");
		System.out.println(angebot + "\n");
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");

		System.out.println("Der Kunde hat das Angebot Nummer " + angebot.getAngebotNr() + " angenommen. \n");
		auftragServices.nimmAngebotAn(angebot); // sets status to ANGENOMMEN
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");
		
		Auftrag auftrag = auftragServices.createAuftragFuerAngebot(angebot); // creates auftrag for angebot
		System.out.println("Folgender Auftrag wurde für das Angebot Nr. " + angebot.getAngebotNr() + " erstellt :");
		System.out.println(auftrag + "\n");
		System.out.println("Folgender Fertigungsauftrag wurde für den Auftrag Nr. " + auftrag.getAuftragsNr() + " erstellt: ");
		Fertigungsauftrag fertigungsauftrag = fertigungServices.readFertigungsauftragById(auftrag.getFertigungsauftragNr()); // gets fertigungsauftrag for auftrag
		System.out.println(fertigungsauftrag + "\n");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");

		System.out.println("Der Fertigungsauftrag Nr. " + fertigungsauftrag.getFertigungsauftragsNr() + " wurde gestartet. \n");
		fertigungServices.starteFertigungsauftrag(fertigungsauftrag); // starts fertigungsauftrag
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++ \n");

		fertigungServices.completeFertigungsauftrag(fertigungsauftrag); // Produktionsmitarbeiter completes fertigungsuaftrag
		angebot = auftragServices.readAngebotById(angebot.getAngebotNr());
		auftrag = auftragServices.readAuftragById(auftrag.getAuftragsNr());
		System.out.println("Der Status von Angebot Nummer " + angebot.getAngebotNr() + " wurde auf " + angebot.getStatus() + " gesetz. \n");

		assertEquals(angebot.getStatus(), Angebot.StatusTyp.AGESCHLOSSEN);
		assertEquals(auftrag.getIstAbgeschlossen(), true);
	}
}
