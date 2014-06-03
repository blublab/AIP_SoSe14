package main.transportdientsleisterAdapter.businessLogicLayer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import main.logistikKomponente.dataAccessLayer.Transportauftrag;
import main.transportdientsleisterAdapter.accessLayer.IItransportdienstleisterAdapter;
import javax.ws.rs.core.MediaType;
import java.util.Date;

public class TransportdienstlseiterAdapterBusinessLogic implements IItransportdienstleisterAdapter {

    private static final String restURL = "http://localhost:8080/spediteur";

    @Override
    public void sendeTransportauftrag(Transportauftrag ta) {

        String json = "";
        json += "{";
        json += "\"transportauftragsNr\":\"" + ta.getTransportauftragsNr() + "\",";
        json += "\"transportdienstleister\":\"" + ta.getTransportdienstleister() + "\",";
        json += "\"ausgangsdatum\":\"" + ta.getAusgangsdatum() + "\"";
        json += "}";

        Client cl = Client.create();
        WebResource wr = cl.resource(restURL);

        wr.type(MediaType.APPLICATION_JSON);
        String response = wr.post(String.class, json);

        System.out.println(response);
    }
}

