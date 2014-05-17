package main.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class HeartbeatTask extends TimerTask {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	int local_port;
	String monitor_host;
	int monitor_port;
	

	public HeartbeatTask() 
	{
		Properties prop = new Properties();
		InputStream input = null;
		try{
			input = new FileInputStream("cfg/server.cfg");
			prop.load(input);
			local_port = Integer.parseInt(prop.getProperty("local_port"));
			monitor_host = prop.getProperty("monitor_host");
			monitor_port = Integer.parseInt(prop.getProperty("monitor_port"));
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
	}
	
	@Override 
	public void run()
	{
		try{
            requestSocket = new Socket(monitor_host, monitor_port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            sendBeat();
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            try{
                in.close();
                out.close();
                requestSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
	}
	
	private void sendBeat() throws UnknownHostException
	{
		OperatingSystemMXBean cpu_bean = ManagementFactory.getOperatingSystemMXBean();
		MemoryMXBean memory_bean = ManagementFactory.getMemoryMXBean();
		JSONObject beat = new JSONObject();
		beat.put("host", InetAddress.getLocalHost().getHostAddress());
		beat.put("port", local_port);
		beat.put("systemload", cpu_bean.getSystemLoadAverage());
		beat.put("memeory_avail", memory_bean.getHeapMemoryUsage().getUsed());
		try{
			out.writeObject(beat);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
