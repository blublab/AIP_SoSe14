package monitor;

import clients.Client;
import clients.ClientList;
import config.Configuration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class InstanceListener extends Thread {

    private ClientList clientList;
    private DatagramSocket serverSocket;

    public InstanceListener(){

        Configuration config = new Configuration();
        String host = config.get("instanceListenerHost");
        Integer port = Integer.parseInt(config.get("instanceListenerPort"));

        this.clientList = ClientList.getInstance();
        try {
            this.serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        DatagramPacket receivePacket;
        String sentence;
        InetAddress receiveAddress;
        Integer receivePort;
        HashMap<String, String> msgmap;
        Client client;
        Double load;

        byte[] receiveData = new byte[1024];
        while(true)
        {
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                this.serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            receiveAddress = receivePacket.getAddress();
            receivePort = receivePacket.getPort();
            sentence = new String( receivePacket.getData()).trim();

            Object msg = JSONValue.parse(sentence);
            JSONObject obj = (JSONObject)msg;

            InetAddress host = null;
            Integer port = null;
            if (obj.get("systemload").toString().length() > 0){

                try {
                    host = InetAddress.getByName(obj.get("host").toString());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                port = Integer.parseInt(obj.get("port").toString());
                load = Double.parseDouble(obj.get("systemload").toString());

//System.out.println("<< " + host.getHostAddress() + ":" + port + " (" + sentence + ")");

                if(this.clientList.has(host, port)){
                    client = this.clientList.get(host, port);
                    client.refreshSignal();
                    client.setLoad(load);
                }else{
                    this.clientList.add(host, port);
                }
            }
        }
    }
}
