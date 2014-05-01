package main.mps_core_mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

import main.Settings;

public class FAKE_Core extends Thread {
	private static Socket ssocket = new Socket();
	static String instancename;
	static boolean quitProgram = false;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private final static int UPDATE_INTERVAL = 5000;
	private final static int TIME_TO_RECONNECT = 3000;

	public static void main(String[] args) {
		FAKE_Core sc = new FAKE_Core();
		sc.start();
	}

	public FAKE_Core() {
		Random r = new Random();

		instancename = "mpsCore" + r.nextInt(10);

	}

	private void con() {
		try {
			try {
				ssocket = new Socket();
				ssocket.connect(new InetSocketAddress(Settings.MonitorIP, Settings.MonitorPort));
			} catch (IOException e) {
				System.out.println("CORE: " + Settings.MonitorIP + " " + e.getMessage());
			}

			try {
				reader = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));

				writer = new BufferedWriter(new OutputStreamWriter(ssocket.getOutputStream()));
			} catch (IOException e) {
				System.out.println("CORE: " + e.getMessage());
			}

			sleep(TIME_TO_RECONNECT);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		// Enter infinite update loop
		while (true) {

			if (ssocket.isConnected()) {

				try {
					write("ALIVE " + instancename);
					String input = read();
					assert (input.equals("OK"));
				} catch (IOException ex) {
					
					System.err.println(ex.getMessage());
					con(); //trying to reconnect
				}
				try {
					sleep(UPDATE_INTERVAL);
				} catch (InterruptedException ex) {
					System.err.println(ex.getMessage());
				}
			} else {
				con();
			}
		}

	}

	private static void write(String msg) throws IOException {
		System.out.println("[" + instancename + "] Sending: " + msg);
		writer.write(msg);
		writer.newLine();
		writer.flush();
	}

	private static String read() throws IOException {
		String received = reader.readLine();
		System.out.println("[" + instancename + "] Received: " + received);
		return received;
	}
}
