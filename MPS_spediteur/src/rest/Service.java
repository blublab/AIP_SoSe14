package rest;

import com.sun.jersey.api.client.Client;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("spediteur")
public class Service {

    private Response responseRequest(String str){
        return Response.ok(str)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .build();
    }

    @POST
    @Path("add")
    @Produces( MediaType.TEXT_PLAIN )
    public Response addTransportauftrag(String json){
        JSONObject obj = (JSONObject)JSONValue.parse(json);
        Long nr = (Long) obj.get("transportauftragsNr");
        Boolean response = TransList.getInstance().has(nr);
        TransList.getInstance().add(obj);
        response = !response && TransList.getInstance().has(nr);
        return this.responseRequest(response.toString());
    }

    @POST
    @Path("delete")
    @Produces( MediaType.TEXT_PLAIN )
    public Response deleteTransportauftrag(String json){
        System.out.println(json);
        JSONObject obj = (JSONObject)JSONValue.parse(json);
        Long lnr = Long.valueOf(obj.get("tanr").toString());
        if(TransList.getInstance().has(lnr)){
            TransList.getInstance().remove(lnr);

            String url = "http://localhost:8081/core/done";
            String type = MediaType.APPLICATION_JSON;
            JSONObject obj2 = new JSONObject();
            obj2.put("reject", lnr);
            Client.create().resource(url).type(type).post(String.class, obj2.toJSONString());
        }
        Boolean response = TransList.getInstance().has(lnr);
        return this.responseRequest(response.toString());
    }

    @POST
    @Path("set")
    @Produces( MediaType.APPLICATION_JSON )
    public Response setTransportauftrag(String json){
        JSONObject obj = (JSONObject)JSONValue.parse(json);
        if(obj != null && obj.get("taid") != null && obj.get("tale") != null){
            Long index = (Long)obj.get("taid");
            Boolean status = (Boolean)obj.get("tale");
            TransList.getInstance().lieferungErfolgt(index, status);

            if(status == true){
                String url = "http://localhost:8081/core/done";
                String type = MediaType.APPLICATION_JSON;
                JSONObject obj2 = new JSONObject();
                obj2.put("accept", index);
                Client.create().resource(url).type(type).post(String.class, obj2.toJSONString());
            }
        }
        System.out.println(TransList.getInstance().get());
        return this.responseRequest("".toString());
    }

    @GET
    @Path("get")
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTransportauftraege(){
        String str = TransList.getInstance().get().toString();
        return this.responseRequest(str);
    }

    @GET
    @Path("get/{nr}")
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTransportauftrag(@PathParam("nr") Long nr){
        String str = "";
        if(TransList.getInstance().has(nr)){
            str = TransList.getInstance().get(nr).toString();
        }else{
            str = new JSONArray().toJSONString();
        }
        return this.responseRequest(str);
    }
}
