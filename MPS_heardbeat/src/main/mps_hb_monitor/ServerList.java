package main.mps_hb_monitor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ServerList {
	private static ServerList instance = null;

	private ServerList() {
	}

	public static synchronized ServerList getInstance() {
		if (ServerList.instance == null) {
			ServerList.instance = new ServerList();
		}
		return ServerList.instance;
	}

	private Set<MPSCoreServerItem> sl = new HashSet<>();
	
	

	public Set<MPSCoreServerItem> getServerList() {
		return sl;
	}

	public boolean addServer(String hostname, String instancename, Date currentTime) {
		

		hostname = hostname.replace("/", "");

		MPSCoreServerItem s = new MPSCoreServerItem();
		s.setHostname(hostname);
		s.setInstanceName(instancename);
		s.setLastAliveDate(currentTime); // this Value is ignored for equals!

		if (sl.contains(s)) { // update TimeStamp
			sl.remove(s);
			System.out.println("update: " + s);
			sl.add(s);
			System.out.println(sl.size() + ": "+sl.toString());
			return true;
		} else {
			System.out.println("add: " + s);
			sl.add(s);
			System.out.println(sl.size() + ": "+sl.toString());
			return true;
		}

	}


	public boolean removeServer(String hostname, String instancename) {
		MPSCoreServerItem s = new MPSCoreServerItem();
		s.setHostname(hostname);
		s.setInstanceName(instancename);

		return sl.remove(s);
	}
	
	public boolean removeServer(MPSCoreServerItem s) {
		return sl.remove(s);
	}

}