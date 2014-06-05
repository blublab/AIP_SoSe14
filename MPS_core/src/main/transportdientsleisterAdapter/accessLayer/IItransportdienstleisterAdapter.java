package main.transportdientsleisterAdapter.accessLayer;

import main.logistikKomponente.dataAccessLayer.Transportauftrag;

/**
 * Created by michaseverin on 02.06.14.
 */
public interface IItransportdienstleisterAdapter {

    public String sendeTransportauftrag(Transportauftrag ta);
}
