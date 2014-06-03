package test.adapterTest.transportdienstleisterAdapterTest;

import main.logistikKomponente.dataAccessLayer.Transportauftrag;
import main.transportdientsleisterAdapter.businessLogicLayer.TransportdienstlseiterAdapterBusinessLogic;

import java.util.Date;

import static org.junit.Assert.*;

public class TransportdienstlseiterAdapterBusinessLogicTest extends junit.framework.TestCase {

    private Transportauftrag ta;

    @org.junit.Before
    public void setUp() throws Exception {
        ta = new Transportauftrag();
        ta.setTransportauftragsNr(123);
        ta.setAusgangsdatum(new Date());
        ta.setLieferrungErfolgt(false);
        ta.setTransportdienstleister("Müller KG");
    }

    @org.junit.Test
    public void testSendeTransportauftrag() throws Exception {
        TransportdienstlseiterAdapterBusinessLogic tda;
        tda = new TransportdienstlseiterAdapterBusinessLogic();
        String response = tda.sendeTransportauftrag(ta);
        assertEquals(response, "received");
    }
}