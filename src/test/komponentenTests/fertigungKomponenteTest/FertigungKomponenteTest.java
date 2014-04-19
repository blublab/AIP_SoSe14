package test.komponentenTests.fertigungKomponenteTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

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
	GenericDAO<Stuecklistenposition> stuecklistepositionDAO;
	GenericDAO<Fertigungsplan> fertigungsplanDAO;
	GenericDAO<Vorgang> vorgangDAO;

	@Before
	public void setUp() throws Exception {
		context = new Mockery(); // Mr. Yavuz crams

		fertigungsauftragDAO = new GenericDAO<Fertigungsauftrag>(Fertigungsauftrag.class);
		bauteilDAO = new GenericDAO<Bauteil>(Bauteil.class);
		stuecklisteDAO = new GenericDAO<Stueckliste>(Stueckliste.class);
		stuecklistepositionDAO = new GenericDAO<Stuecklistenposition>(Stuecklistenposition.class);
		fertigungsplanDAO = new GenericDAO<Fertigungsplan>(Fertigungsplan.class);
		vorgangDAO = new GenericDAO<Vorgang>(Vorgang.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateSimpleFertigungsauftragWithoutStuecklisteStuecklistenpositionAndFertigungsplanSuccess() {
		// Create Bauteil
		Bauteil bt1 = new Bauteil();
		bt1.setName("Test Mähdrescher");

		bauteilDAO.create(bt1);

		// Create Fertigungsauftrag and Set Bauteil to Fertigungsauftrag
		Fertigungsauftrag fa1 = new Fertigungsauftrag();
		fa1.setBauteil(bt1);

		fertigungsauftragDAO.create(fa1);

		// Read from DB and compare to origin
		Fertigungsauftrag fa1r = fertigungsauftragDAO.read(fa1.getFertigungsauftragsNr());
		// TODO: Why the hell does Bauteil.Stuecklistenposition has data in DB?!
		assertEquals(fa1, fa1r);
		assertEquals(fa1.getBauteil(), fa1r.getBauteil());
	}

	@Test
	public void testCreateAdvancedFertigungsauftragWithStuecklisteStuecklistenpositionAndFertigungsplanSuccess() {

	}

	@Test
	public void testCreateVorgangSuccess() {
		// Create Vorgang
		Vorgang v1 = new Vorgang();
		v1.setVorgangTyp(Vorgang.ArtTyp.BEREITSTELLUNG);
		v1.setRuestzeit(new Date());
		v1.setPersonenZeit(new Date());
		v1.setMaschienenZeit(new Date());
		vorgangDAO.create(v1);

		// Read from DB and compare to origin
		Vorgang v1r = vorgangDAO.read(v1.getVorgangNr());
		assertEquals(v1, v1r);
	}

	@Test
	public void testCreateFertigungsplanWithOneVorgangSuccess() {
		// Create Vorgang
		Vorgang v1 = new Vorgang();
		vorgangDAO.create(v1);
		LinkedHashSet<Vorgang> lhsV = new LinkedHashSet<>();
		lhsV.add(v1);

		// Create Fertigungsplan and Set Vorgang to Fertigungsplan
		Fertigungsplan fp1 = new Fertigungsplan();
		fp1.setVorgang(lhsV);
		fertigungsplanDAO.create(fp1);

		// Read from DB and compare to origin
		Fertigungsplan fp1r = fertigungsplanDAO.read(fp1.getFertigungsplanNr());
		// TODO: Verify why Vorgang Set got different ID
		assertEquals(fp1, fp1r);
	}

	@Test
	public void testCreateStuecklistenpositionSuccess() {
		// Create Stuecklistenposition
		Stuecklistenposition slp1 = new Stuecklistenposition();
		slp1.setMenge(12);
		slp1.setName("Die Erste Stueckliste");
		stuecklistepositionDAO.create(slp1);

		// Read from DB and compare to origin
		Stuecklistenposition slp1r = stuecklistepositionDAO.read(slp1.getStuecklistpositionNr());
		// TODO: Verify why slp1.name NOT euqual slp1r
		assertEquals(slp1, slp1r);
	}

	@Test
	public void testCreateStuecklisteWithOneStuecklistenpositionSuccess() {
		// Create Stuecklistenposition
		Stuecklistenposition slp1 = new Stuecklistenposition();
		slp1.setMenge(123);
		slp1.setName("Stücklistenposition für die Stueckliste");
		List<Stuecklistenposition> lslp = new ArrayList<Stuecklistenposition>();
		lslp.add(slp1);
		stuecklistepositionDAO.create(slp1);

		// Create Stueckliste and add StuecklistenpositionList
		Stueckliste sl1 = new Stueckliste();
		sl1.setGueltigAb(new Date());
		sl1.setGueltigBis(new Date());
		sl1.setStuecklistenposition(lslp);
		stuecklisteDAO.create(sl1);

		// Read from DB and compare to origin
		Stueckliste sl1r = stuecklisteDAO.read(sl1.getStuecklistNr());
		// TODO: Verify why Stuecklistenposition got different ID
		assertEquals(sl1, sl1r);
	}

}
