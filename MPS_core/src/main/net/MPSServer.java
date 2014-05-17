package main.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class MPSServer {

	private ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	MPSServer(){}
	
	void run()
	{
		Properties prop = new Properties();
		InputStream input = null;
		int port = 0;
		try{
			input = new FileInputStream("cfg/server.cfg");
			prop.load(input);
			port = Integer.parseInt(prop.getProperty("local_port"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try{
			providerSocket = new ServerSocket(port);
			connection = providerSocket.accept();
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			JSONObject response = new JSONObject();
			response.put("response", "Connection established.");
			sendMessage(response);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void sendMessage(JSONObject obj)
	{
		try{
			out.writeObject(obj);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Properties prop = new Properties();
		InputStream input = null;
		long period = 0;
		try{
			input = new FileInputStream("cfg/server.cfg");
			prop.load(input);
			period = Long.parseLong(prop.getProperty("heartbeat_period"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		TimerTask heartbeatTask = new HeartbeatTask();
		Timer timer = new Timer();
		timer.schedule(heartbeatTask, 0, period);
	}
}
