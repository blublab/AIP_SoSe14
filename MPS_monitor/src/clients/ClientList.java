package clients;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientList implements Iterable{

    private static ClientList instance;
    private ArrayList<Client> list;

    private ClientList(){
        ClientList.instance = null;
        this.list = new ArrayList<Client>();
    }

    public static ClientList getInstance() {
        if (ClientList.instance == null) {
            ClientList.instance = new ClientList();
        }
        return ClientList.instance;
    }

    public void add(InetAddress address, Integer port){
        if(this.has(address, port) == false){
            this.list.add(new Client(address, port));
        }
    }
    public void add(String address, String port){
        try {
            this.add(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Client get(InetAddress address, Integer port){
        Client cc;
        Iterator<Client> itcc = this.list.iterator();
        while(itcc.hasNext()){
            cc = itcc.next();
            if(cc.getAddress().equals(address) && cc.getPort().equals(port)){
                return cc;
            }
        }
        return null;
    }
    public Client get(String address, String port){
        try {
            return this.get(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean has(InetAddress address, Integer port){
        Client coreClient;
        Iterator<Client> it = this.list.iterator();
        while(it.hasNext()){
            coreClient = it.next();
            if(coreClient.getAddress().equals(address) && coreClient.getPort().equals(port)){
                return true;
            }
        }
        return false;
    }
    public Boolean has(String address, String port){
        try {
            return this.has(InetAddress.getByName(address), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString(){
        String str = "[";
        Iterator<Client> it = this.list.iterator();
        while(it.hasNext()){
            str += it.next().toString() + ",";
        }
        str = str.substring(0, str.length() - 1);
        str += "]";
        return str;
    }

    @Override
    public Iterator iterator() {
        return this.list.iterator();
    }
}
