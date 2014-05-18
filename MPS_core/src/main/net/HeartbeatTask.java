package main.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class HeartbeatTask extends TimerTask {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	int local_port;
	String monitor_host;
	int monitor_port;
	

	public HeartbeatTask(int local_port, String monitor_host, int monitor_port) 
	{
		this.local_port = local_port;
		this.monitor_host = monitor_host;
		this.monitor_port = monitor_port;
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
		beat.put("host", new String(InetAddress.getLocalHost().getHostAddress()));
		beat.put("port", new Integer(local_port));
		beat.put("systemload", new Double(cpu_bean.getSystemLoadAverage()));
		beat.put("memeory_avail", new Long(memory_bean.getHeapMemoryUsage().getUsed()));
		try{
			out.writeObject(beat);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
