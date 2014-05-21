package monitor;

import config.Configuration;
import webservice.WebServicePublisher;

import java.util.Timer;

public class MainMonitor {

    public static void main(String[] args){

        // CoreListener (UDP)
        CoreListener coreListener = new CoreListener();
        coreListener.start();

        // DispatcherServer (TCP)
        DispatcherServer dispatcherServer = new DispatcherServer();
        dispatcherServer.start();

        // Publisher (HTTP, SOAP)
        WebServicePublisher publisher = new WebServicePublisher();

        // CoreCleanup routine
        Configuration config = new Configuration();
        Integer ci = Integer.parseInt(config.get("cleanupInterval"));
        new Timer().schedule(new CoreCleanup(), 0, ci);
    }
}
