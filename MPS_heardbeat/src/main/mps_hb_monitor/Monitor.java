package main.mps_hb_monitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Monitor {

	// The number of worker threads equals the maximum number of concurrent
	// connections.
	// If the maximum is reached new connections will be stalled until new
	// resources are freed (i.e. running connections are being closed)
	public final static int NUMBER_IO_THREADS = 5;
	public static final int NAME_MAX_LENGTH = 20;

	// Port on which the server listens for Servers
	public final static int PORT = 50000;
	
	// Housekeeping
	public static final int HK_THRESHOLD_LIMIT_IN_SECONDS = -10;
	public static final int HK_CHECKRATE = 1000;

	// Loggers for debugging and error logging purposes
	public final static Logger infoLogger = Logger.getLogger("info");
	public final static Logger errorLogger = Logger.getLogger("errors");

	public static void main(String[] args) {

		ServerList sl = ServerList.getInstance();

		Housekeeping hk = new Housekeeping();
		hk.setCheckRate(HK_CHECKRATE);
		hk.setThresholdLimitInSeconds(HK_THRESHOLD_LIMIT_IN_SECONDS);
		hk.start();

		ExecutorService pool = Executors.newFixedThreadPool(NUMBER_IO_THREADS);

		try {
			ServerSocket ssocket = new ServerSocket(PORT);

			try {
				infoLogger.log(Level.INFO, "Starting Server on Port " + PORT);
				infoLogger.log(Level.INFO, "Number of IO threads: " + NUMBER_IO_THREADS);

				// Main loop for accepting server connections
				while (true) {

					// Return a socket for each actual connection
					Socket s = ssocket.accept();

					infoLogger.log(Level.INFO, "Connecting to " + s.getRemoteSocketAddress());

					// Wrap the received socket in a ServerHandlerTask and
					// submit it to the threadpool
					@SuppressWarnings("unchecked")
					Callable<Void> task = new ServerHandlerTask(new Context(s, sl));
					pool.submit(task);
				}
			} catch (IOException e) {
				errorLogger.log(Level.SEVERE, "Failed to initialize Connection: " + e.getMessage(), e);
			} catch (RuntimeException e) {
				errorLogger.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
			}
		} catch (IOException e) {
			errorLogger.log(Level.SEVERE, "Failed to initialize server: " + e.getMessage(), e);
		} catch (RuntimeException e) {
			errorLogger.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
		}
	}

}