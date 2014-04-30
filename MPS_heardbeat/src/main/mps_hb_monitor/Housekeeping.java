package main.mps_hb_monitor;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Housekeeping extends Thread {
	private int thresholdLimitInSeconds;
	private int checkRate;

	public void setThresholdLimitInSeconds(int thresholdLimitInSeconds) {
		this.thresholdLimitInSeconds = thresholdLimitInSeconds;
	}

	public void setCheckRate(int checkRate) {
		this.checkRate = checkRate;
	}

	@Override
	public void run() {
		while (true) {
			ServerList sl = ServerList.getInstance();

			Calendar currentDateMinusTimeRange = Calendar.getInstance();
			currentDateMinusTimeRange.add(Calendar.SECOND, thresholdLimitInSeconds);

			Iterator<MPSCoreServer> serverIterator = sl.getServerList().iterator();
			while (serverIterator.hasNext()) {
				MPSCoreServer s = serverIterator.next();
				if (!isWithinThreshold(s.getLastAliveDate(), currentDateMinusTimeRange)) {
					System.out.println(s.getInstanceName() + " kicked !!!!");
					serverIterator.remove();
				}
			}

			try {
				sleep(checkRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * verify if the lastActiveDate that is stored in the List is within the allowed timeframe
	 * @param lastActiveDate stored date of Server within global Serverlist
	 * @param threshold minimum date within timeframe
	 * @return true if accepted; false if too old
	 */
	boolean isWithinThreshold(Date lastActiveDate, Calendar threshold) {

		return lastActiveDate.after(new Date(threshold.getTimeInMillis()));
	}
}
