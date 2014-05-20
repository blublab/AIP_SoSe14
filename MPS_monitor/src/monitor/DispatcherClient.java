package monitor;

import core.Core;
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
            System.out.println("DispatcherClient: " + host + ":" + port);
        } catch (IOException e) {
            System.err.println("No dispatcher connection");
        }
    }

    public void send(ArrayList<Core> cores){
        Core core;
        String message;
        Iterator<Core> iter;

        message = "[";
        iter = cores.iterator();
        while(iter.hasNext()){
            core = iter.next();
            message += "{";
            message += "\"host\":\"" + core.getAddress().getHostAddress() + "\",";
            message += "\"port\":\"" + core.getPort() + "\",";
            message += "\"load\":\"" + core.getLoad() + "\"";
            message += "}";
            message += ",";
        }
        if(cores.size() > 0){
            message = message.substring(0, message.length() - 1 );
        }
        message += "]";

        if(this.outToServer != null){
            try {
                this.outToServer.writeBytes(message + "\n");
                System.out.println(">> " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Couldn't send to dispatcher");
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
