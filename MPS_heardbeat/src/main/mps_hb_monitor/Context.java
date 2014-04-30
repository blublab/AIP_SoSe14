package main.mps_hb_monitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;

import main.mps_hb_monitor.Monitor;
import main.mps_hb_monitor.ServerList;

/**
 * Provides a facade for each ServerHanderTask to interact with their current
 * connection as well as with backend routines.
 * 
 * @author m215025
 * 
 */
public class Context {
	private BufferedReader reader;
	private BufferedWriter writer;
	private ServerList serverList;
	private Socket socket;

	public Context(Socket socket, ServerList gl) throws IOException {
		this.socket = socket;
		this.serverList = gl;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		Monitor.infoLogger.log(Level.INFO, "Connected to " + socket.getInetAddress().toString());
	}

	/**
	 * Provide the last unread line that the client has sent
	 * 
	 * @return A newline-terminated string as read from the socket
	 * @throws IOException
	 */
	String receiveLine() throws IOException {
		String rec = reader.readLine();
		Monitor.infoLogger.log(Level.INFO, "[ " + socket.getInetAddress().toString() + " ] " + "Received\t" + rec);
		return rec;
	}

	/**
	 * Send a message to the client. Automatically append a 'newline'.
	 * 
	 * @param msg
	 *            Message to be sent to the client
	 * @throws IOException
	 */
	void sendLine(String msg) throws IOException {
		Monitor.infoLogger.log(Level.INFO, "[ " + socket.getInetAddress().toString() + " ] " + "Sending\t" + msg);
		writer.write(msg);
		writer.newLine();
		writer.flush();
	}

	/**
	 * Add of updates the given instancename with the current IP to the global
	 * list of servers; if already in the Set it updates the last alive date
	 * 
	 * @param instancename
	 *            name of Serverinstance
	 * @param currentTime
	 *            timestamp of last (this) transaction
	 * @return true added sucessfully
	 */
	boolean addOrUpdateServer(String instancename, Date currentTime) {
		String hostname = socket.getInetAddress().toString();
		hostname.substring(1);
		serverList.addServer(hostname, instancename, currentTime);

		Monitor.infoLogger.log(Level.INFO, "Added or Updated " + hostname + " as " + instancename);
		return true;
	}

	/**
	 * removes an Server from the global Serverlist
	 * 
	 * @param instancename
	 */
	void removeServer(String instancename) {
		String hostname = socket.getInetAddress().toString();
		hostname.substring(1);
		serverList.removeServer(hostname, instancename);
		Monitor.infoLogger.log(Level.FINE, "Removed " + hostname);
	}

	/**
	 * Terminate this connection:<br/>
	 * 
	 * <li>Remove the current client from the guest list (if logged in).</li>
	 * <li>Close all related streams and sockets for a clean exit.</li>
	 */
	void shutdown(String instancename) {
		try {
			writer.flush();
			writer.close();
			reader.close();
			socket.close();
			Monitor.infoLogger.log(Level.INFO, "socket closed");
		} catch (IOException e) {
			Monitor.errorLogger.log(Level.WARNING, e.getMessage(), e);
		} finally {
			serverList.removeServer(socket.getInetAddress().toString(), instancename);
		}
	}
}