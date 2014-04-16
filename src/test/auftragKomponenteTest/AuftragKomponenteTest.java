package test.auftragKomponenteTest;

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
	public void testCreateAuftrag() {
		auftragDAO.create(a1);
		auftragDAO.create(a2);
		assert true;
	}
}
