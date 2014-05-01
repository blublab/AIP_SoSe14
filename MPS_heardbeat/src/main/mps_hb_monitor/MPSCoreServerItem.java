package main.mps_hb_monitor;

import java.util.Date;

public class MPSCoreServerItem {
	String hostname = "";
	String instanceName = "";
	Date lastAliveDate = null;


	public String getHostname() {
		return hostname;
	}
	public void setHostname(String ipAdress) {
		this.hostname = ipAdress;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public Date getLastAliveDate() {
		return lastAliveDate;
	}
	public void setLastAliveDate(Date lastAliveDate) {
		this.lastAliveDate = lastAliveDate;
	}
	
	/**
	 * generate hashCode <b>without</b> taking the date into consideration
	 */
	@Override //without DATE!!!
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instanceName == null) ? 0 : instanceName.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		return result;
	}
	
	/**
	 * generate equals <b>without</b> taking the date into consideration
	 */
	@Override //without DATE!!!
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MPSCoreServerItem other = (MPSCoreServerItem) obj;
		if (instanceName == null) {
			if (other.instanceName != null)
				return false;
		} else if (!instanceName.equals(other.instanceName))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MPSCoreServer [hostname=" + hostname + ", instanceName=" + instanceName + ", lastAliveDate=" + lastAliveDate
				+ "]";
	}
}
