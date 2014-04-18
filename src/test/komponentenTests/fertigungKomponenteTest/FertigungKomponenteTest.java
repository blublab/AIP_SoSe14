package test.komponentenTests.fertigungKomponenteTest;

import static org.junit.Assert.*;


import main.fertigungKomponente.dataAccessLayer.Bauteil;
import main.fertigungKomponente.dataAccessLayer.Fertigungsauftrag;
import main.fertigungKomponente.dataAccessLayer.Fertigungsplan;
import main.fertigungKomponente.dataAccessLayer.Stueckliste;
import main.fertigungKomponente.dataAccessLayer.Stuecklistenposition;
import main.fertigungKomponente.dataAccessLayer.Vorgang;
import main.util.GenericDAO;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FertigungKomponenteTest {
	Mockery context;
	GenericDAO<Fertigungsauftrag> fertigungsauftragDAO;
	GenericDAO<Bauteil> bauteilDAO;
	GenericDAO<Stueckliste> stuecklisteDAO;
	GenericDAO<Stuecklistenposition> suecklistepositionDAO;
	GenericDAO<Fertigungsplan> fertigungsplanDAO;
	GenericDAO<Vorgang> vorgangDAO;
	
	@Before
	public void setUp() throws Exception {
		context = new Mockery(); //Mr. Yavuz crams
			
		fertigungsauftragDAO = new GenericDAO<Fertigungsauftrag>(Fertigungsauftrag.class);
		bauteilDAO = new GenericDAO<Bauteil>(Bauteil.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateSimpleFertigungsauftragWithoutStuecklisteStuecklistenpositionAndFertigungsplanSuccess() {
		//Create Bauteil
		Bauteil bt1 = new Bauteil();
		bt1.setName("Test Mähdrescher");

		bauteilDAO.create(bt1);
		
		//Create Fertigungsauftrag and Set Bauteil to Fertigungsauftrag
		Fertigungsauftrag fa1 = new Fertigungsauftrag();
		fa1.setBauteil(bt1);
		
		fertigungsauftragDAO.create(fa1);
		
		//Read from DB and compare to origin
		Fertigungsauftrag fa1r=	fertigungsauftragDAO.read(fa1.getFertigungsauftragsNr());
		//TODO: Verify check agains object vs. object_ID
		assertEquals(fa1, fa1r);
		//TODO: Verify check agains object vs. object_ID
		assertEquals(fa1.getBauteil().getBauteilNr(), fa1r.getBauteil().getBauteilNr());
	}
	
	@Test
	public void testCreateAdvancedFertigungsauftragWithStuecklisteStuecklistenpositionAndFertigungsplanSuccess(){
		
	}
	
	@Test 
	public void testCreateVorgang(){
		//Create Vorgang
		Vorgang v1 = new Vorgang();
		// v1.setVorgangTyp(Vorgang.ArtTyp.BEREITSTELLUNG);
		// v1.setRuestzeit(new Date());
		// v1.setPersonenZeit(new Date());
		// v1.setMaschienenZeit(new Date());
		//TODO:Verify why nullpointerexeption is thrown
		vorgangDAO.create(v1);
		
		//Read from DB and compare to origin
		Vorgang v1r = vorgangDAO.read(v1.getVorgangNr());
		assertEquals(v1, v1r);
	}
	
//	@Test
//	public void testCreateFertigungsplan(){
//		//Create Fertigungsplan
//		Fertigungsplan fp1 = new Fertigungsplan();
//		
//	}

}
