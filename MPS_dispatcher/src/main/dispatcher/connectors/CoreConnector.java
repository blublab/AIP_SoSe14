package main.dispatcher.connectors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CoreConnector {

	private class Connection {
		private BufferedReader reader = null;
		private BufferedWriter writer = null;
		
		Connection(BufferedReader reader, BufferedWriter writer) {
			this.reader = reader;
			this.writer = writer;
		}
		
		BufferedReader getReader() {
			return this.reader;
		}
		
		BufferedWriter getWriter() {
			return this.writer;
		}
	}
	
	private Connection getConnection(JSONObject mpsInstance) throws IOException {
		String host = (String) mpsInstance.get("host");
		int port = (Integer) mpsInstance.get("port");
		assert((port != 0) && (host != null));
		Socket clientSocket = new Socket(host, port);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		BufferedReader br = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
		
		return new Connection(br, bw);
	}
	
	public String createAngebot(int kundenNr, int bauteilNr, JSONObject mpsInstance) {
		String response = null;
		try {
			Connection con = getConnection(mpsInstance);
			
			BufferedReader br = con.getReader();
			BufferedWriter bw = con.getWriter();
			
			JSONObject request = new JSONObject();
			request.put("command", "createAngebot");
			request.put("kundenNr", new Integer(kundenNr));
			request.put("bauteilNr", bauteilNr);

			System.out.println("Sent: " + request.toJSONString() + " to " + (String) mpsInstance.get("host") + "Port: " + (Integer) mpsInstance.get("port"));			
			
			bw.write(request.toJSONString() + "\n");
			bw.flush();
			System.out.println("waiting for response from: " + (String) mpsInstance.get("host") + " Port: " + (Integer) mpsInstance.get("port"));
			response = br.readLine();
			System.out.println(response);
			Object responseObj = JSONValue.parse(response.trim());
			JSONObject jObj = (JSONObject) responseObj;
			return jObj.toJSONString();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return response;
	}

	public String nimmAngebotAn(int angebotNr, JSONObject mpsInstance) {
		String response = null;
		try {
			Connection con = getConnection(mpsInstance);
			
			BufferedReader br = con.getReader();
			BufferedWriter bw = con.getWriter();
			
			JSONObject request = new JSONObject();
			request.put("command", "acceptAngebot");
			request.put("angebotNr", angebotNr);

			System.out.println("Sent: " + request.toJSONString() + " to " + (String) mpsInstance.get("host") + "Port: " + (Integer) mpsInstance.get("port"));			
			
			bw.write(request.toJSONString() + "\n");
			bw.flush();
			System.out.println("waiting for response from: " + (String) mpsInstance.get("host") + " Port: " + (Integer) mpsInstance.get("port"));
			response = br.readLine();
			System.out.println(response);
			return response;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return response;
	}

	public String getAllAngebote(JSONObject mpsInstance) {
		String response = null;
		try {
			Connection con = getConnection(mpsInstance);
			
			BufferedReader br = con.getReader();
			BufferedWriter bw = con.getWriter();
			
			JSONObject request = new JSONObject();
			request.put("command", "getAllAngebote");

			System.out.println("Sent: " + request.toJSONString() + " to " + (String) mpsInstance.get("host") + "Port: " + (Integer) mpsInstance.get("port"));			
			
			bw.write(request.toJSONString() + "\n");
			bw.flush();
			System.out.println("waiting for response from: " + (String) mpsInstance.get("host") + " Port: " + (Integer) mpsInstance.get("port"));
			response = br.readLine();
			System.out.println(response);
			return response;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return response;
	}
	
	public String getAllAuftraege(JSONObject mpsInstance) {
		String response = null;
		try {
			Connection con = getConnection(mpsInstance);
			
			BufferedReader br = con.getReader();
			BufferedWriter bw = con.getWriter();
			
			JSONObject request = new JSONObject();
			request.put("command", "getAllAuftraege");

			System.out.println("Sent: " + request.toJSONString() + " to " + (String) mpsInstance.get("host") + "Port: " + (Integer) mpsInstance.get("port"));			
			
			bw.write(request.toJSONString() + "\n");
			bw.flush();
			System.out.println("waiting for response from: " + (String) mpsInstance.get("host") + " Port: " + (Integer) mpsInstance.get("port"));
			response = br.readLine();
			System.out.println(response);
			return response;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return response;
	}

}
