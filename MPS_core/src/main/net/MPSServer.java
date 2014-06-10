package main.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MPSServer extends Thread {

	private ServerSocket providerSocket;
	Socket connection = null;
	OutputStream out;
	InputStream in;
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
				out = connection.getOutputStream();
				out.flush();
				in = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String request = br.readLine();
				System.out.println(request);
				request = request.trim();
				Object obj_rq = JSONValue.parse(request);
				JSONObject json_rq = (JSONObject) obj_rq;
				String command = (String) json_rq.get("command");
				System.out.println("Received command: " + command);
				if (command.equals("createAngebot")) {
                    Long kundenNrLong = (Long) json_rq.get("kundenNr");
                    int kundenNr = kundenNrLong.intValue();
                    Long bauteilNrLong = (Long) json_rq.get("bauteilNr");
                    int bauteilNr = bauteilNrLong.intValue();
                    sendMessage(app.createAngebot(kundenNr, bauteilNr));
                }else if(command.equals("acceptAngebot")){
                    Long angebotNrLong = (Long) json_rq.get("angebotNr");
                    int angebotNr = angebotNrLong.intValue();
                    sendMessage(app.acceptAngebot(angebotNr));
                }else if(command.equals("getAllAngebote")){
                    sendMessage(app.getAllAngebote());
                }else if(command.equals("getAllAuftraege")){
                    sendMessage(app.getAllAuftraege());
                }else if(command.equals("getAllBauteile")){
                    sendMessage(app.getAllBauteile());
                }
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
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			bw.write(obj.toJSONString());
			bw.write("\n");
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	void sendMessage(JSONArray ary) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			bw.write(ary.toJSONString());
			bw.write("\n");
			bw.flush();
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
