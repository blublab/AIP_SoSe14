package monitor;

import clients.ClientList;
import config.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sun.rmi.server.Dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by michaseverin on 19.05.14.
 */
public class DispatcherServer extends Thread {

    private ServerSocket serverSocket = null;
    private Socket connectionSocket = null;
    private BufferedReader inFromClient = null;

    public DispatcherServer(){

        Configuration config = new Configuration();
        String host = config.get("dispatcherServerHost");
        Integer port = Integer.parseInt(config.get("dispatcherServerPort"));

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        try {
            connectionSocket = serverSocket.accept();
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true)
        {
           String clientSentence = null;
            try {
                clientSentence = inFromClient.readLine();
                System.out.println(clientSentence);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String xHost, xPort;
            Object msg = JSONValue.parse(clientSentence);
            JSONObject obj = (JSONObject)msg;
            xHost = obj.get("host").toString();
            xPort = obj.get("port").toString();


            if(xHost != null && xPort != null){
                ClientList.getInstance().get(xHost, xPort).incrementQuery();
            }
        }
    }
}
