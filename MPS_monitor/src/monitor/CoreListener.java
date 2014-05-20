package monitor;

import core.Core;
import core.CoreList;
import config.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class CoreListener extends Thread {

    private CoreList coreList;
    private DatagramSocket serverSocket;

    public CoreListener(){

        Configuration config = new Configuration();
        String host = config.get("coreListenerHost");
        Integer port = Integer.parseInt(config.get("coreListenerPort"));

        this.coreList = CoreList.getInstance();
        try {
            this.serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("CoreListener: " + host + ":" + port);
    }

    @Override
    public void run(){
        DatagramPacket receivePacket;
        String sentence;
        InetAddress receiveAddress;
        Integer receivePort;
        HashMap<String, String> msgmap;
        Core core;
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

                if(this.coreList.has(host, port)){
                    core = this.coreList.get(host, port);
                    core.refreshSignal();
                    core.setLoad(load);
                }else{
                    this.coreList.add(host, port);
                }
            }
        }
    }
}
