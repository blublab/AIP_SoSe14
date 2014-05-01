package main.mps_hb_monitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import main.Settings;

public class NotifyDispatcher extends Thread {
	private static Socket ssocket = new Socket();
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private final static int TIME_TO_RECONNECT = 3000;

	public NotifyDispatcher() {

	}

	@Override
	public void run() {
		if (!ssocket.isConnected()) {
			con();
		}
	}

	private void con() {

		while (!ssocket.isConnected()) {
			try {
				ssocket = new Socket();
				ssocket.connect(new InetSocketAddress(Settings.DispatcherIP, Settings.DispatcherPort));

				try {
					reader = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));

					writer = new BufferedWriter(new OutputStreamWriter(ssocket.getOutputStream()));
				} catch (IOException e) {
					System.out.println("NOTIFY DISPATCHER: " + e.getMessage());
				}
			} catch (IOException e) {
				System.out.println("NOTIFY DISPATCHER: " + Settings.DispatcherIP + " " + e.getMessage());
			}

			try {
				sleep(TIME_TO_RECONNECT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void notifyDispatcher() {
		if (ssocket.isConnected()) {

			try {
				ServerList sl = ServerList.getInstance();

				write("LIST");

				for (MPSCoreServerItem mpsCoreServer : sl.getServerList()) {
					write(mpsCoreServer.toString());
				}
				write("/LIST");

			} catch (IOException e) {
				System.err.println("Cannot notify Dispatcher: " + e.getMessage());
			}
		}
	}

	private static void write(String msg) throws IOException {
		System.out.println("[NotifyDispatcher] Sending: " + msg);
		writer.write(msg);
		writer.newLine();
		writer.flush();
	}

	private static String read() throws IOException {
		String received = reader.readLine();
		System.out.println("[NotifyDispatcher] Received: " + received);
		return received;
	}
}
