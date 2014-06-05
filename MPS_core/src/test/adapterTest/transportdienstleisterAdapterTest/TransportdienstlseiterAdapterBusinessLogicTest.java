package test.adapterTest.transportdienstleisterAdapterTest;

import main.logistikKomponente.dataAccessLayer.Transportauftrag;
import main.transportdientsleisterAdapter.businessLogicLayer.TransportdienstlseiterAdapterBusinessLogic;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Date;

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

        JSONObject obj = (JSONObject)JSONValue.parse(response);

        assertEquals(obj.get("transportauftragsNr"), ta.getTransportauftragsNr().longValue());
        assertEquals(obj.get("ausgangsdatum"), ta.getAusgangsdatum().toString());
        assertEquals(obj.get("lieferrungErfolgt"), ta.getLieferrungErfolgt());
        assertEquals(obj.get("transportdienstleister"), ta.getTransportdienstleister());
    }
}