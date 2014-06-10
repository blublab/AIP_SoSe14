package main.transportdientsleisterAdapter.businessLogicLayer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import main.logistikKomponente.dataAccessLayer.Transportauftrag;
import main.transportdientsleisterAdapter.accessLayer.IItransportdienstleisterAdapter;
import org.json.simple.JSONObject;

import javax.ws.rs.core.MediaType;

public class TransportdienstlseiterAdapterBusinessLogic implements IItransportdienstleisterAdapter {

    private static final String restURL = "http://localhost:8080/spediteur/add";

    @Override
    public String sendeTransportauftrag(Transportauftrag ta) {

        JSONObject obj = new JSONObject();
        obj.put("transportauftragsNr", ta.getTransportauftragsNr());
        obj.put("ausgangsdatum", ta.getAusgangsdatum().toString());
        obj.put("transportauftragsNr", ta.getTransportauftragsNr());
        obj.put("lieferungErfolgt", ta.getLieferrungErfolgt());
        obj.put("transportdienstleister", ta.getTransportdienstleister());

        Client cl = Client.create();
        WebResource wr = cl.resource(restURL);

        wr.type(MediaType.APPLICATION_JSON);
        String response = wr.post(String.class, obj.toJSONString());

        return response;
    }
}

