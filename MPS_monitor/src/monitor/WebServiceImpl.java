package monitor;

import clients.ClientList;
import clients.Status;

import javax.jws.WebService;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebService(endpointInterface="monitor.WebServiceInterface")
public class WebServiceImpl implements WebServiceInterface {

    public ClientList clientList;

    public WebServiceImpl(){
        this.clientList = ClientList.getInstance();
    }

    @Override
    public String getInstances() {
        return this.clientList.toString();
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
        Status status = Status.valueOf(s);

        this.clientList.get(host, port).setStatus(status);
    }
}