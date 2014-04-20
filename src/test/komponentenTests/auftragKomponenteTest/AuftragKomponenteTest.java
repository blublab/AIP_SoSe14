package test.komponentenTests.auftragKomponenteTest;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import main.auftragKomponente.accessLayer.AuftragKomponenteFacade;
import main.auftragKomponente.accessLayer.IAuftragServices;
import main.auftragKomponente.accessLayer.Exceptions.InvalidAngebotStatusException;
import main.auftragKomponente.dataAccessLayer.Angebot;
import main.auftragKomponente.dataAccessLayer.Auftrag;
import main.fertigungKomponente.accessLayer.IFertigungServicesFuerAuftrag;
import main.fertigungKomponente.accessLayer.Exceptions.AuftragServicesNotSetException;
import main.util.GenericDAO;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuftragKomponenteTest {
	GenericDAO<Auftrag> auftragDAO;
	GenericDAO<Angebot> angebotDAO;
	IAuftragServices auftragSerivces;
	Mockery context;
	IFertigungServicesFuerAuftrag fertigungServicesMock;

	@Before
	public void setUp() throws Exception {
		context = new Mockery();
		auftragDAO = new GenericDAO<Auftrag>(Auftrag.class);
		angebotDAO = new GenericDAO<>(Angebot.class);
		fertigungServicesMock = context
				.mock(IFertigungServicesFuerAuftrag.class);
		auftragSerivces = new AuftragKomponenteFacade(fertigungServicesMock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAuftragSuccess() {
		Auftrag a1 = new Auftrag();
		a1.setBeauftragtAm(new Date());
		a1.setIstAbgeschlossen(false);
		Auftrag a2 = new Auftrag();
		a2.setBeauftragtAm(new Date());
		a2.setIstAbgeschlossen(true);
		auftragDAO.create(a1);
		auftragDAO.create(a2);
		Auftrag a1r = auftragDAO.read(a1.getAuftragsNr());
		assertEquals(a1.getAuftragsNr(), a1r.getAuftragsNr());
		Auftrag a2r = auftragDAO.read(a2.getAuftragsNr());

		assertEquals(a2, a2r);
	}

	@Test
	public void testCreateAuftragFuerAngebotSuccess()
			throws InvalidAngebotStatusException, AuftragServicesNotSetException {
		context.checking(new Expectations() {
			{
				oneOf(fertigungServicesMock).erstelleFertigungsauftragFuerAuftrag(with(any(int.class))); // this method will be called on the mock object with any int value (TODO: mybe checkt it's not zero)
			}
		});
		Angebot angebot = auftragSerivces.createAngebot(2, 42);
		auftragSerivces.nimmAngebotAn(angebot);
		Auftrag auftrag = auftragSerivces.createAuftragFuerAngebot(angebot);
		Auftrag auftragVonAngebot = angebotDAO.read(angebot.getAngebotNr())
				.getAuftrag();
		Auftrag auftragVonDB = auftragDAO.read(auftrag.getAuftragsNr());

		context.assertIsSatisfied();

		assertEquals(auftrag, auftragVonAngebot);
		assertEquals(auftrag, auftragVonDB);
		assertEquals(auftragVonDB, auftragVonAngebot);
	}
}
