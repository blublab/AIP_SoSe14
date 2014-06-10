package main.transportdientsleisterAdapter.businessLogicLayer;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("core")
public class TransportdienstleisterAdapterRestServer{

    private final static String restServerHost = "localhost";
    private final static Integer restServerPort = 8081;
    private final static String restServerPath = "";

    public static void main(String[] args){
        HttpServer server = null;
        try {
            server = HttpServerFactory.create("http://" + restServerHost + ":" + restServerPort + "/" + restServerPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
    }

    private Response responseRequest(String str){
       return Response.ok(str)
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
               .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
               .build();
    }

    @POST
    @Path("done")
    @Produces( MediaType.APPLICATION_JSON )
    public Response addTransportauftrag(String json){
        JSONObject obj = (JSONObject) JSONValue.parse(json);
        Long nr = (Long) obj.get("taid");
        //TODO
        System.out.println(nr);
        return this.responseRequest("ok");
    }
}
