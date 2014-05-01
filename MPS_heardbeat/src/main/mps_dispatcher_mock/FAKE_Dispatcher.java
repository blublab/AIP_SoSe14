package main.mps_dispatcher_mock;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import main.Settings;

public class FAKE_Dispatcher {

	public static void main(String argv[]) throws Exception {
		System.out.println("Dispatcher online...");

		String clientSentence;
		String reply;
		ServerSocket socket = new ServerSocket(Settings.DispatcherPort);
		Socket connectionSocket = socket.accept();
		while (true) {
			//TODO:
//			System.out.println("thru ");
//			
//
//
//
//			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//			//DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//
//			clientSentence = inFromClient.readLine();
////			Scanner scan = new Scanner(clientSentence);
////			String bareCommand = scan.next();
//			System.out.println("Received: " + clientSentence);
////			reply = "OK";
////			outToClient.writeBytes(reply);
		}
	}
}
