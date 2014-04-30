package main.mps_hb_monitor;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import main.mps_hb_monitor.Monitor;

/**
 * Implements the protocol to interact with a particular client
 */
@SuppressWarnings("rawtypes") // Safe because no return value ("future") is expected
public class ServerHandlerTask implements Callable {
	String instancename = "";
	/**
	 * Possible Commands FROM Servers 
	 *
	 */
	private enum InputCommad {
		ALIVE;
	}
	
	/**
	 * Possible Commands TO Server
	 *
	 */
	private enum OutputCommand {
		OK;
	}
	
	private Context ctx;
	
	public ServerHandlerTask(Context c) 
	{
		this.ctx = c;
	}
	
	/**
	 * Entrance point for handling the Server.
	 */
	@Override
	public Object call() throws Exception 
	{		
		boolean quit = false;
		
		while (quit == false) {
			
			try {
				String input 	= ctx.receiveLine();
				Scanner scan = new Scanner(input);
				String bareCommand = scan.next();
				
//				// BYE
//				 if (bareCommand.equals(ClientCommand.BYE.toString())) {
//					logout();
//					replyWith(ServerCommand.BYE.toString());
//					quit = true;
//					ctx.shutdown();
//				} 
//				
//				// INFO
//				else if (bareCommand.equals(ClientCommand.INFO.toString())) {
//					String list = ctx.getParticipants();
//					replyWith(ServerCommand.LIST.toString() + " " + list);
//				}
				
				// Alive
				if (bareCommand.equals(InputCommad.ALIVE.toString())) {
					if (scan.hasNext()) {
						instancename = scan.next();
						if (instancename.length() <= Monitor.NAME_MAX_LENGTH) {
							if (checkSyntax(instancename)) {
								ctx.addOrUpdateServer(instancename,new Date());
								replyWith(OutputCommand.OK.toString());
							} else
							{
								error("Whitespace and special characters are prohibited");
							}
						} else
							error("instancename too long - maximum allowed: " + Monitor.NAME_MAX_LENGTH);
					}
					else
						error("No instancename given");
				} 
				
				else {
					error("unknown command " + bareCommand);
					quit = true;
				}
				scan.close();
			} catch (IOException e) {
				Monitor.errorLogger.log(Level.WARNING, e.getMessage(), e);
				quit = true;
				logout();
				ctx.shutdown(instancename);
			}
		}
		return null;
	}
	
	
	/**
	 * Verify if instancename fits in criteria
	 * @param name instancename of Serverinstance
	 * @return true if accepted; false if not
	 */
	private boolean checkSyntax(String name) {
		for (char c : name.toCharArray())
		{
			if (! Character.isLetterOrDigit(c))
				return false;
		}
		return true;
	}
	
	/**
	 * Remove the current Server from the server list.
	 */
	private void logout() {
		Monitor.infoLogger.log(Level.INFO, "Logging out...");
		ctx.removeServer(instancename);
	}
	
	/**
	 * Send a message to the connected Server
	 * 
	 * @param msg	Message to Server
	 */
	private void replyWith(String msg) {
		try {
			ctx.sendLine(msg);
		} catch (IOException e) {
			Monitor.errorLogger.log(Level.WARNING, e.getMessage(), e);
			logout();
			ctx.shutdown(instancename);
		}
	}
	
	/**
	 * Report a protocol error. Log out Server and terminate connection immediately.
	 * 
	 * @param msg	Description of what caused the error
	 */
	private void error(String msg){
		try {
			ctx.sendLine("ERROR " + msg);
		} catch (IOException e) {
			Monitor.errorLogger.log(Level.WARNING, e.getMessage(), e);
		}
		logout();
		ctx.shutdown(instancename);
	}
}