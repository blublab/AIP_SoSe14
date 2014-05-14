package monitor;

import config.Configuration;

import java.util.Timer;

public class Monitor{

    public static void main(String[] args){
        // Configuration
        Configuration config = new Configuration();

        // InstanceListener (UDP)
        InstanceListener instanceListener = new InstanceListener();
        instanceListener.start();

        // DispatcherClient (TCP)
        //DispatcherClient dispatcherClient = DispatcherClient.getInstance();

        // Publisher (HTTP, SOAP)
        WebServicePublisher publisher = new WebServicePublisher();

        // Cleanup
        Integer ci = Integer.parseInt(config.get("cleanupInterval"));
        new Timer().schedule(new Cleanup(), 0, ci);
    }
}
