package monitor;

import clients.Client;
import clients.ClientList;
import config.Configuration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

public class InstanceListener extends Thread {

    private ClientList clientList;
    private DatagramSocket serverSocket;
    private static InstanceListener instace = null;

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

    private HashMap<String, String> parseMessage(String message){
        HashMap<String, String> parsedMap = new HashMap<String, String>();
        String[] groupArray = message.split("&");
        for(int i=0; i<groupArray.length; i++) {
            String[] pairArray = groupArray[i].split("=");
            if(pairArray.length == 2) {
                parsedMap.put(pairArray[0], pairArray[1]);
            }
        }
        return parsedMap;
    }

    @Override
    public void run(){
        DatagramPacket receivePacket;
        String sentence;
        InetAddress receiveAddress;
        Integer receivePort, load;
        HashMap<String, String> msgmap;
        Client client;

        byte[] receiveData = new byte[64];
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

            msgmap = this.parseMessage(sentence);

            if (msgmap.containsKey("ping") && msgmap.containsKey("load")){
                load = Integer.parseInt(msgmap.get("load"));

                System.out.println("<< " + receiveAddress.getHostAddress() + ":" + receivePort + " (" + sentence + ")");

                if(this.clientList.has(receiveAddress, receivePort)){
                    client = this.clientList.get(receiveAddress, receivePort);
                    client.refreshSignal();
                    client.setLoad(load);
                }else{
                    this.clientList.add(receiveAddress, receivePort);
                }
            }
        }
    }
}
