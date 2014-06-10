package main.transportdientsleisterAdapter.businessLogicLayer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import main.logistikKomponente.dataAccessLayer.Transportauftrag;
import org.json.simple.JSONObject;

import javax.ws.rs.core.MediaType;

public class TransportdienstlseiterAdapterBusinessLogic {

    private final static String restClientHost = "localhost";
    private final static Integer restClientPort = 8080;
    private final static String restClientPath = "spediteur/add";

    //@Override
    public String sendeTransportauftrag(Transportauftrag ta) {

        JSONObject obj = new JSONObject();
        obj.put("transportauftragsNr", ta.getTransportauftragsNr());
        obj.put("ausgangsdatum", ta.getAusgangsdatum().toString());
        obj.put("transportauftragsNr", ta.getTransportauftragsNr());
        obj.put("lieferungErfolgt", ta.getLieferrungErfolgt());
        obj.put("transportdienstleister", ta.getTransportdienstleister());

        Client cl = Client.create();
        WebResource wr = cl.resource("http://" + TransportdienstlseiterAdapterBusinessLogic.restClientHost + ":" + TransportdienstlseiterAdapterBusinessLogic.restClientPort + "/" + TransportdienstlseiterAdapterBusinessLogic.restClientPath);

        wr.type(MediaType.APPLICATION_JSON);
        String response = wr.post(String.class, obj.toJSONString());

        return response;
    }
}

