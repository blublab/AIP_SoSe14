package webservice;

import core.CoreList;
import core.CoreStatus;

import javax.jws.WebService;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebService(endpointInterface="webservice.WebServiceInterface")
public class WebServiceImpl implements WebServiceInterface {

    public CoreList coreList;

    public WebServiceImpl(){
        this.coreList = CoreList.getInstance();
    }

    @Override
    public String getInstances() {
        return this.coreList.toString();
    }

    @Override
    public void setStatus(String h, String p, String s) {
        InetAddress host = null;
        try {
            host = InetAddress.getByName(h);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Integer port = Integer.parseInt(p);
        CoreStatus coreStatus = CoreStatus.valueOf(s);

        this.coreList.get(host, port).setStatus(coreStatus);
    }
}