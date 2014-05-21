package monitor;

import core.CoreList;
import config.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
        System.out.println("DispatcherServer: " + host + ":" + port);
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

            System.out.println("<<" + clientSentence);

            if(xHost != null && xPort != null){
                CoreList.getInstance().get(xHost, xPort).incrementQuery();
            }
        }
    }
}
