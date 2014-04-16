package test.auftragKomponenteTest;

import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.auftragKomponente.dataAccessLayer.AuftragDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuftragKomponenteTest {
	Auftrag a;
	AuftragDAO aDAO;

	@Before
	public void setUp() throws Exception {
		a = new Auftrag();
		aDAO = new AuftragDAO();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAuftrag() {
		aDAO.saveAuftrag(a.getClass());
		assert true;
	}
}
