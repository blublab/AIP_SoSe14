package rest;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Server {

    private final static String host = "localhost";
    private final static Integer port = 8080;
    private final static String path = "";

    public static void main( String[] args ) throws Exception{
        String url = "http://" + Server.host + ":" + Server.port + "/" + Server.path;
        System.out.println(url);
        HttpServer server = HttpServerFactory.create(url);
        server.start();
    }
}
