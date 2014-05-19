package webservice;

import config.Configuration;
import javax.xml.ws.Endpoint;

public class WebServicePublisher {

    public WebServicePublisher(){

        Configuration config = new Configuration();

        String host = config.get("webServicePublisherHost"),
               port = config.get("webServicePublisherPort"),
               path = config.get("webServicePublisherPath");

        String url = "http://" + host + ":" + port + "/" + path;
        Endpoint.publish(url, new WebServiceImpl());

        System.out.println("WebService: " + url);
    }
}
