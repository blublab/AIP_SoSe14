import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("spediteur")
public class Service {

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    public String transportauftrag(String json){
        System.out.println(json);
        return json;
    }
}
