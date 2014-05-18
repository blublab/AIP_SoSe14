package main.appBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import main.net.HeartbeatTask;
import main.net.MPSServer;

public class Starter {
	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;
		int local_port = 0;
		String monitor_host = "";
		int monitor_port = 0;
		long period = 0;
		try {
			input = new FileInputStream("cfg/server.cfg");
			prop.load(input);
			local_port = Integer.parseInt(prop.getProperty("local_port"));
			monitor_host = prop.getProperty("monitor_host");
			monitor_port = Integer.parseInt(prop.getProperty("monitor_port"));
			period = Long.parseLong(prop.getProperty("heartbeat_period"));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		TimerTask heartbeatTask = new HeartbeatTask(local_port, monitor_host,
				monitor_port);
		Timer timer = new Timer();
		timer.schedule(heartbeatTask, 0, period);
		
		
		Thread mps_server = new MPSServer(local_port);
		mps_server.start();
		try {
			mps_server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
	}
}
