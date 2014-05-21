package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WebServiceInterface {

    /**
     * @return
     */
    @WebMethod
    public String getInstances();

    /**
     * @return
     */
    @WebMethod
    public void setStatus(String host, String port, String status);
}
