package main.mps_core_mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;


public class SimpleClient extends Thread {
	private static Socket ssocket = new Socket();
	static String instancename;
	static boolean quitProgram = false;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private final static int UPDATE_INTERVAL = 5000;

	public SimpleClient() {
		Random r = new Random();
		
		
		instancename = "mpsCore"+r.nextInt(10);
		String server = "localhost";
		try {
			ssocket.connect(new InetSocketAddress(server, 50000), 5000);
		} catch (IOException e) {
			System.out.println(server + " not responding");
		}

		try {
			reader = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));

			writer = new BufferedWriter(new OutputStreamWriter(ssocket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		// Enter infinite update loop
		while (true) {
			try {
				write("ALIVE " + instancename);
				String input = read();
				assert (input.equals("OK"));
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
			try {
				sleep(UPDATE_INTERVAL);
			} catch (InterruptedException ex) {
				System.err.println(ex.getMessage());
			}
		}

	}

	private static void write(String msg) throws IOException {
		System.out.println("["+instancename+"] Sending: " + msg);
		writer.write(msg);
		writer.newLine();
		writer.flush();
	}

	private static String read() throws IOException {
		String received = reader.readLine();
		System.out.println("["+instancename+"] Received: " + received);
		return received;
	}
}
