package monitor;

import clients.Client;
import config.Configuration;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class DispatcherClient {

    private Socket clientSocket;
    private DataOutputStream outToServer;

    private static DispatcherClient instance = null;

    public static DispatcherClient getInstance(){
        if(DispatcherClient.instance == null){
            Configuration config = new Configuration();
            String host = config.get("dispatcherClientHost");
            Integer port = Integer.parseInt(config.get("dispatcherClientPort"));
            DispatcherClient.instance = new DispatcherClient(host, port);
        }
        return DispatcherClient.instance;
    }

    private DispatcherClient(String host, Integer port){
        try {
            this.clientSocket = new Socket(host, port);
            this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(ArrayList<Client> clients){
        Client client;
        String message;
        Iterator<Client> iter;

        message = "[";
        iter = clients.iterator();
        while(iter.hasNext()){
            client = iter.next();
            message += "{";
            message += "\"host\":\"" + client.getAddress().getHostAddress() + "\",";
            message += "\"port\":\"" + client.getPort() + "\",";
            message += "\"load\":\"" + client.getLoad() + "\"";
            message += "}";
            message += ",";
        }
        if(clients.size() > 0){
            message = message.substring(0, message.length() - 1 );
        }
        message += "]";

        try {
            this.outToServer.writeBytes(message + "\n");
            System.out.println(">> " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
