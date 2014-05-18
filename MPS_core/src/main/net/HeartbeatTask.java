package main.net;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class HeartbeatTask extends TimerTask {
	DatagramSocket requestSocket;
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
            requestSocket = new DatagramSocket();
            sendBeat();
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
        	requestSocket.close();
        }
	}
	
	@SuppressWarnings("unchecked")
	private void sendBeat() throws UnknownHostException
	{
		OperatingSystemMXBean cpu_bean = ManagementFactory.getOperatingSystemMXBean();
		MemoryMXBean memory_bean = ManagementFactory.getMemoryMXBean();
		JSONObject beat = new JSONObject();
		beat.put("host", new String(InetAddress.getLocalHost().getHostAddress()));
		beat.put("port", new Integer(local_port));
		beat.put("systemload", new Double(cpu_bean.getSystemLoadAverage()));
		beat.put("memeory_avail", new Long(memory_bean.getHeapMemoryUsage().getUsed()));
		
		String beatS = beat.toJSONString();
		byte[] sendData = beatS.getBytes();
		InetAddress monitorAddress = InetAddress.getByName(monitor_host);
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, monitorAddress, monitor_port);
		try {
			requestSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
