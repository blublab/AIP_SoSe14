package test.komponentenTests.auftragKomponenteTest;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.util.GenericDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuftragKomponenteTest {
	Auftrag a1;
	Auftrag a2;
	GenericDAO<Auftrag> auftragDAO;

	@Before
	public void setUp() throws Exception {
		a1 = new Auftrag();
		a1.setBeauftragtAm(new Date());
		a1.setIstAbgeschlossen(false);
		a2 = new Auftrag();
		a2.setBeauftragtAm(new Date());
		a2.setIstAbgeschlossen(true);
		auftragDAO = new GenericDAO<Auftrag>(Auftrag.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAuftragSuccess() {
		auftragDAO.create(a1);
		auftragDAO.create(a2);
		Auftrag a1r = auftragDAO.read(a1.getAuftragsNr());
		assertEquals(a1.getAuftragsNr(), a1r.getAuftragsNr());
		Auftrag a2r = auftragDAO.read(a2.getAuftragsNr());

		assertEquals(a2, a2r);
	}
}
