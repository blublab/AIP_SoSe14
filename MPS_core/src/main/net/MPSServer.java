package main.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

public class MPSServer extends Thread{

	private ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	int local_port;
	NetBusinessApp app;

	public MPSServer(int local_port) {
		this.local_port = local_port;
		this.app = new NetBusinessApp();
	}

	@Override
	public void run() {
		while (true)
			try {
				providerSocket = new ServerSocket(local_port);
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

	void sendMessage(JSONObject obj) {
		try {
			out.writeObject(obj);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
