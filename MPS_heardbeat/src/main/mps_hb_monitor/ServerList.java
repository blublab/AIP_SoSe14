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

	private Set<MPSCoreServer> sl = new HashSet<>();
	
	

	public Set<MPSCoreServer> getServerList() {
		return sl;
	}

	public boolean addServer(String hostname, String instancename, Date currentTime) {
		System.out.println(sl.toString());

		hostname = hostname.replace("/", "");

		MPSCoreServer s = new MPSCoreServer();
		s.setHostname(hostname);
		s.setInstanceName(instancename);
		s.setLastAliveDate(currentTime); // this Value is ignored for equals!

		if (sl.contains(s)) { // update TimeStamp
			sl.remove(s);
			System.out.println("update: " + s);
			return sl.add(s);
		} else {
			System.out.println("add: " + s);
			return sl.add(s);
		}

	}


	public boolean removeServer(String hostname, String instancename) {
		MPSCoreServer s = new MPSCoreServer();
		s.setHostname(hostname);
		s.setInstanceName(instancename);

		return sl.remove(s);
	}
	
	public boolean removeServer(MPSCoreServer s) {
		return sl.remove(s);
	}

}