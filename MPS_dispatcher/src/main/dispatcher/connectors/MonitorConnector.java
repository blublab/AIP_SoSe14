package main.dispatcher.connectors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import main.dispatcher.engine.Balancer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MonitorConnector {
	private Receiver receiver = null;
	private Balancer balancer = null;

	protected String monitorUrl = null;
	protected int monitorPort = 0;
	
	private Socket sock = null;
	private BufferedWriter writer = null;

	public MonitorConnector(Balancer balancer) {
<<<<<<< HEAD
		this.monitorUrl = "127.0.0.1";
=======
		this.monitorUrl = "localhost";
>>>>>>> 7ced24635075c07484fa4e2878454b9995c6acf4
		this.monitorPort = 6789;
		this.balancer = balancer;
		this.receiver = new Receiver();
		this.receiver.start();

		boolean retry = true;
		while(retry) {
			try {
				this.sock = new Socket(monitorUrl, monitorPort);
				this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
				System.out.println("Connection established: " + sock.isConnected() );
				retry = false;
			} catch (Exception e) {
				retry = true;
				System.out.println(e.getMessage());
			}
		}
	}

	public void publishTransaction(JSONObject host) {
		System.out.print("Message dispatched to: " + host.toJSONString());
		try {
			this.writer.write(host.toJSONString() + "\n");
			this.writer.flush();
			System.out.println(" ...SUCCESS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class Receiver extends Thread {
		@Override
		public void run() {
			int retries = 3;
			boolean run = true;

			while (run) {
				ServerSocket welcomeSocket;
				try {
					welcomeSocket = new ServerSocket(3301);
					Socket connectionSocket = welcomeSocket.accept();
					BufferedReader inFromClient = new BufferedReader(
							new InputStreamReader(
									connectionSocket.getInputStream()));

					while (run) {
						try {
							String hostList = inFromClient.readLine();
//							if (hostList == null) { continue; }
							hostList = hostList.trim();
							System.out.println("Received: " + hostList);

							JSONArray jHostList = (JSONArray) JSONValue
									.parse(hostList);

							List<JSONObject> activeHosts = new ArrayList<JSONObject>();

							for (Object host : jHostList.toArray()) {
								activeHosts.add((JSONObject) host);
							}

							balancer.setActiveHosts(activeHosts);
						} finally {

						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

				}
			}
		}
	}
}
